package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.common.blockentity.FryerBlockEntity;
import com.bretzelfresser.mcdonalds.common.util.WorldUtils;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Fryer extends RotatableBlock implements EntityBlock {
    public Fryer() {
        super(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().requiresCorrectToolForDrops().strength(3));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            FryerBlockEntity te = WorldUtils.getTileEntity(FryerBlockEntity.class, level, pos);
            if (te != null) {
                ItemStack held = player.getItemInHand(hand);
                if (held.isEmpty() && !te.getInv().getStackInSlot(1).isEmpty()) {
                    if (player.addItem(te.getInv().getStackInSlot(1))) {
                        te.getInv().setStackInSlot(1, ItemStack.EMPTY);
                        return InteractionResult.SUCCESS;
                    }
                }
                if (level.getRecipeManager().getRecipeFor(RecipeInit.FRYER_RECIPE, new SimpleContainer(held), level).isPresent()) {
                    ItemStack remainder = te.getInv().insertItem(1, held, false);
                    player.setItemInHand(hand, remainder);
                }
                //TODO the oil must be added
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
}
