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
        return InteractionResult.CONSUME;
    }

    public static VoxelShape makeQuarterPounderShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, 0.11874999999999994, 0.3437500000000001, 0.6562499999999999, 0.18124999999999994, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, -6.071532165918825e-17, 0.3437500000000001, 0.6562499999999999, 0.06249999999999994, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.04374999925494194, 0.3343749999999999, 0.6625000000000002, 0.10624999925494193, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, -0.01875000074505806, 0.3343749999999999, 0.6625000000000002, 0.043749999254941906, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.31875000000000003, 0.11718749925494194, 0.31875, 0.6812500000000002, 0.11718749925494194, 0.68125));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.04999999925494194, 0.3343749999999999, 0.6625000000000002, 0.11249999925494192, 0.659375));

        return shape;
    }

    public static VoxelShape makeBigMacShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, 0.2062499999999999, 0.3437500000000001, 0.6562499999999999, 0.26875000000000004, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, -6.245004513516506e-17, 0.3437500000000001, 0.6562499999999999, 0.06249999999999994, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.04374999925494194, 0.3343749999999999, 0.6625000000000002, 0.10624999925494193, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, -0.01875000074505806, 0.3343749999999999, 0.6625000000000002, 0.043749999254941906, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.31875000000000003, 0.04843749925494193, 0.31875, 0.6812500000000002, 0.04843749925494193, 0.68125));
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, 0.08749999999999997, 0.3437500000000001, 0.6562499999999999, 0.14999999999999997, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.31875000000000003, 0.1546874992549419, 0.31875, 0.6812500000000002, 0.1546874992549419, 0.68125));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.14999999925494192, 0.3343749999999999, 0.6625000000000002, 0.21249999925494192, 0.659375));

        return shape;
    }

    public static VoxelShape makeCheeseBurger(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, 0.11874999999999994, 0.3437500000000001, 0.6562499999999999, 0.18124999999999994, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.04374999925494194, 0.3343749999999999, 0.6625000000000002, 0.10624999925494193, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.049999999254941946, 0.3343749999999999, 0.6625000000000002, 0.11249999925494195, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.31875000000000003, 0.11718749925494194, 0.31875, 0.6812500000000002, 0.11718749925494194, 0.68125));
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, -6.245004513516506e-17, 0.3437500000000001, 0.6562499999999999, 0.06249999999999994, 0.6562500000000002));

        return shape;
    }

    public static VoxelShape makeMcRoyalDeluxe(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, 0.14843749999999992, 0.3437500000000001, 0.6562499999999999, 0.21093750000000006, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, -6.071532165918825e-17, 0.3437500000000001, 0.6562499999999999, 0.06249999999999994, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.04374999925494194, 0.3343749999999999, 0.6625000000000002, 0.10624999925494193, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.3375000000000001, 0.04374999925494194, 0.3343749999999999, 0.6625000000000002, 0.10624999925494191, 0.659375));
        shape = Shapes.or(shape, Shapes.box(0.3437499999999999, 0.08749999999999997, 0.3437500000000001, 0.6562499999999999, 0.14999999999999997, 0.6562500000000002));
        shape = Shapes.or(shape, Shapes.box(0.31875000000000003, 0.1546874992549419, 0.31875, 0.6812500000000002, 0.1546874992549419, 0.68125));

        return shape;
    }
}
