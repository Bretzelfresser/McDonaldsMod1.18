package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.common.blockentity.ChoppingBoardBlockEntity;
import com.bretzelfresser.mcdonalds.common.recipe.ChoppingRecipe;
import com.bretzelfresser.mcdonalds.common.util.WorldUtils;
import com.bretzelfresser.mcdonalds.core.ItemInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;

public class ChoppingBoard extends ShapedRotatableBlock implements EntityBlock {
    public ChoppingBoard() {
        super(BlockBehaviour.Properties.of(Material.WOOD).strength(2, 3).noOcclusion(), makeShape());
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

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.create(0.4375, 0, 0.0625, 0.5625, 0.0625, 0.125));
        shape = Shapes.or(shape, Shapes.create(0.1875, 0, 0.0625, 0.4375, 0.0625, 0.1875));
        shape = Shapes.or(shape, Shapes.create(0.5625, 0, 0.0625, 0.8125, 0.0625, 0.1875));
        shape = Shapes.or(shape, Shapes.create(0.1875, 0, 0.1875, 0.8125, 0.0625, 0.9375));

        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChoppingBoardBlockEntity(pos, state);
    }
}
