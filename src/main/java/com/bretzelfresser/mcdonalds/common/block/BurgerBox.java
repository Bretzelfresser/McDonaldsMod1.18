package com.bretzelfresser.mcdonalds.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class BurgerBox extends ShapedRotatableBlock {

    private final Supplier<Block> burger, openBox;

    public BurgerBox(Properties properties, VoxelShape shape, Supplier<Block> burger, Supplier<Block> openBox) {
        super(properties, shape);
        this.burger = burger;
        this.openBox = openBox;
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (player.isCrouching() && player.getItemInHand(hand).isEmpty()) {
                Block.popResource(level, pos, new ItemStack(burger.get()));
                level.setBlock(pos, openBox.get().defaultBlockState().setValue(FACING, state.getValue(FACING)), 1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
