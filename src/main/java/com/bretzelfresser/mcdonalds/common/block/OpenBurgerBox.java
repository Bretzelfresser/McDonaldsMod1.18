package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OpenBurgerBox extends ShapedRotatableBlock {
    public OpenBurgerBox(Properties p_49795_, VoxelShape shape) {
        super(p_49795_, shape);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            Block.popResource(level, pos, new ItemStack(this));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
