package com.bretzelfresser.mcdonalds.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Burger extends ShapedBlock {
    public Burger(Properties p_49795_, VoxelShape shape) {
        super(p_49795_, shape);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && player.isShiftKeyDown()){
            if (player.addItem(this.asItem().getDefaultInstance())){
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static VoxelShape makeQuarterPounderShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, 0, 0.3437500000000001, 0.6562499999999999, 0.04375, 0.6562500000000001));
        shape = Shapes.or(shape, Shapes.box(0.34375, 0.04374999925494194, 0.34375, 0.65625, 0.04999999925494194, 0.65625));
        shape = Shapes.or(shape, Shapes.box(0.3125000000000001, 0.04999999925494194, 0.3124999999999999, 0.6875000000000001, 0.11249999925494195, 0.6874999999999999));
        shape = Shapes.or(shape, Shapes.box(0.34375, 0.11249999925494195, 0.34375, 0.65625, 0.11874999925494195, 0.65625));
        shape = Shapes.or(shape, Shapes.box(0.4103553390593274, 0.11249999999999999, 0.2911611652351684, 0.4728553390593274, 0.11875, 0.3536611652351684));
        shape = Shapes.or(shape, Shapes.box(0.6541053390593273, 0.11249999999999999, 0.4161611652351684, 0.7166053390593273, 0.11875, 0.4786611652351684));
        shape = Shapes.or(shape, Shapes.box(0.5291053390593273, 0.11249999999999999, 0.6661611652351684, 0.5916053390593273, 0.11875, 0.7286611652351684));
        shape = Shapes.or(shape, Shapes.box(0.27910533905932733, 0.11249999999999999, 0.5411611652351684, 0.34160533905932733, 0.11875, 0.6036611652351684));
        shape = Shapes.or(shape, Shapes.box(0.56875, 0.11249999999999999, 0.25, 0.5812499999999999, 0.125, 0.375));
        shape = Shapes.or(shape, Shapes.box(0.5250000000000001, 0.11249999999999999, 0.6000000000000001, 0.5375000000000001, 0.125, 0.7250000000000001));
        shape = Shapes.or(shape, Shapes.box(0.609907041736319, 0.11249999999999999, 0.5331526560867289, 0.734907041736319, 0.125, 0.5456526560867289));
        shape = Shapes.or(shape, Shapes.box(0.2536570417363191, 0.11249999999999999, 0.46440265608672915, 0.3786570417363191, 0.125, 0.4769026560867291));
        shape = Shapes.or(shape, Shapes.box(0.3312499999999999, 0.11875, 0.3312500000000001, 0.66875, 0.225, 0.6687500000000002));

        return shape;
    }
}
