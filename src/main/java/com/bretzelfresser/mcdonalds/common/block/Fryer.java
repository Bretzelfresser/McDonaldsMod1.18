package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.common.blockentity.FryerBlockEntity;
import com.bretzelfresser.mcdonalds.common.util.WorldUtils;
import com.bretzelfresser.mcdonalds.core.ItemInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public class Fryer extends RotatableBlock implements EntityBlock {

    public static final IntegerProperty OIL = IntegerProperty.create("oil", 0, 3);
    public static final BooleanProperty BASKET = BooleanProperty.create("basket");

    public Fryer() {
        super(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().requiresCorrectToolForDrops().strength(3));
        this.registerDefaultState(this.getStateDefinition().any().setValue(OIL, 0).setValue(BASKET, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            FryerBlockEntity te = WorldUtils.getTileEntity(FryerBlockEntity.class, level, pos);
            if (te != null) {
                ItemStack held = player.getItemInHand(hand);
                if (held.isEmpty() && !te.getInv().getStackInSlot(0).isEmpty()) {
                    if (player.addItem(te.getInv().getStackInSlot(0))) {
                        te.getInv().setStackInSlot(0, ItemStack.EMPTY);
                        return InteractionResult.SUCCESS;
                    }
                }
                if (level.getRecipeManager().getRecipeFor(RecipeInit.FRYER_RECIPE, new SimpleContainer(held), level).isPresent()) {
                    ItemStack remainder = te.getInv().insertItem(0, held, false);
                    if (!player.isCreative() && !ItemStack.matches(remainder, held))
                        player.setItemInHand(hand, remainder);
                    return InteractionResult.SUCCESS;
                }
                if (held.getItem() == ItemInit.OIL.get() && state.getValue(OIL) < OIL.getPossibleValues().stream().max(Comparator.comparingInt(i -> i)).orElse(0)){
                    level.setBlock(pos, state.setValue(OIL, state.getValue(OIL) + 1), 3);
                    held.shrink(1);
                    return InteractionResult.SUCCESS;
                }
                if (held.getItem() == ItemInit.BASKET.get() && !state.getValue(BASKET)){
                    level.setBlock(pos,state.setValue(BASKET, true), 3);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide()) {
            if (!state.is(newState.getBlock())) {
                FryerBlockEntity te = WorldUtils.getTileEntity(FryerBlockEntity.class, level, pos);
                if (te != null) {
                    for (int i = 0; i < te.getInv().getSlots(); i++) {
                        Block.popResource(level, pos, te.getInv().getStackInSlot(i));
                    }
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FryerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (level, state, pos, blockentity) -> ((FryerBlockEntity) blockentity).tick();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OIL, BASKET);
    }
}
