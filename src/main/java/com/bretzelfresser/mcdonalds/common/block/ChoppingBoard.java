package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.common.blockentity.ChoppingBoardBlockEntity;
import com.bretzelfresser.mcdonalds.common.recipe.ChoppingRecipe;
import com.bretzelfresser.mcdonalds.common.util.WorldUtils;
import com.bretzelfresser.mcdonalds.core.ItemInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.HashMap;
import java.util.Map;

public class ChoppingBoard extends ShapedRotatableBlock implements EntityBlock {


    public ChoppingBoard(Properties properties, VoxelShape shape) {
        super(properties, shape);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ChoppingBoardBlockEntity te = WorldUtils.getTileEntity(ChoppingBoardBlockEntity.class, level, pos);
            if (te != null) {
                ItemStackHandler handler = te.getInv();
                if (handler.getStackInSlot(0).isEmpty()) {
                    ItemStack held = player.getItemInHand(hand);
                    if (!held.isEmpty() && getRecipe(level, new SimpleContainer(held)) != null) {
                        ItemStack copy = held.copy();
                        copy.setCount(1);
                        handler.insertItem(0, copy, false);
                        held.shrink(1);
                        level.sendBlockUpdated(pos, state, state, 3);
                        return InteractionResult.SUCCESS;
                    }
                } else if (player.getItemInHand(hand).isEmpty()) {
                    if (player.addItem(handler.getStackInSlot(0))) {
                        handler.extractItem(0, Integer.MAX_VALUE, false);
                        level.sendBlockUpdated(pos, state, state, 3);
                        return InteractionResult.SUCCESS;
                    }

                } else if (player.getItemInHand(hand).getItem() == ItemInit.KNIFE.get()) {
                    ChoppingRecipe recipe = getRecipe(level, new SimpleContainer(handler.getStackInSlot(0)));
                    if (recipe != null) {
                        handler.setStackInSlot(0, recipe.getResultItem().copy());
                        player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        level.sendBlockUpdated(pos, state, state, 3);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide() && !state.is(newState.getBlock())) {
            ChoppingBoardBlockEntity te = WorldUtils.getTileEntity(ChoppingBoardBlockEntity.class, level, pos);
            if (te != null) {
                for (int i = 0; i < te.getInv().getSlots(); i++) {
                    Block.popResource(level, pos, te.getInv().getStackInSlot(i));
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @javax.annotation.Nullable
    private static ChoppingRecipe getRecipe(Level world, Container inv) {
        return world.getRecipeManager().getRecipeFor(RecipeInit.CHOPPING_RECIPE, inv, world).orElse(null);
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.059375, 0, 0.453125, 0.12187500000000001, 0.0625, 0.578125));
        shape = Shapes.or(shape, Shapes.box(0.184375, 0, 0.203125, 0.934375, 0.0625, 0.828125));
        shape = Shapes.or(shape, Shapes.box(0.059375, 0, 0.578125, 0.184375, 0.0625, 0.828125));
        shape = Shapes.or(shape, Shapes.box(0.059375, 0, 0.203125, 0.184375, 0.0625, 0.453125));


        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChoppingBoardBlockEntity(pos, state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newSTate, LevelAccessor level, BlockPos pos, BlockPos adjacentPos) {
        if (!level.isClientSide() && direction == Direction.DOWN){
           if (!canSurvive(state, level, pos))
               return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.isFaceFull(world.getBlockState(pos.below()).getShape(world, pos.below()), Direction.UP);
    }
}
