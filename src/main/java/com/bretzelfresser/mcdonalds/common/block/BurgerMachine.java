package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.common.blockentity.BurgerMachineBlockEntity;
import com.bretzelfresser.mcdonalds.common.recipe.BurgerMachineRecipe;
import com.bretzelfresser.mcdonalds.common.util.WorldUtils;
import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import com.bretzelfresser.mcdonalds.core.ItemInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BurgerMachine extends BaseEntityBlock{

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public BurgerMachine() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5, 5).noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide){
            ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
            BurgerMachineRecipe recipe = worldIn.getRecipeManager().getRecipeFor(RecipeInit.BURGER_RECIPE, new SimpleContainer(held), worldIn).orElse(null);
            BurgerMachineBlockEntity te = WorldUtils.getTileEntity(BurgerMachineBlockEntity.class, worldIn, pos);
            if (te != null){
                if (player.isCrouching() && !te.isClosed() && !te.isOpening() && !te.getInv().getItem(0).isEmpty()){
                    te.setClosing();
                    worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                    return InteractionResult.SUCCESS;
                }else if (held.getItem() == ItemInit.SPATULA.get() && !te.isClosed() && !te.getInv().getItem(0).isEmpty()){
                    player.addItem(te.getInv().getItem(0));
                    te.getInv().setItem(0, ItemStack.EMPTY);
                    worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                    return InteractionResult.SUCCESS;
                }else if(recipe != null && recipe.getInput().test(held) && te.getInv().getItem(0).isEmpty()){
                    ItemStack copy = held.copy();
                    if (!player.isCreative())
                        held.shrink(1);
                    copy.setCount(1);
                    te.getInv().setItem(0, copy);
                    worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation mirror) {
        return state.setValue(FACING, mirror.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BurgerMachineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityInit.BURGER_MACHINE.get(), BurgerMachineBlockEntity::tick);
    }
}
