package com.bretzelfresser.mcdonalds.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SauceBottle extends Block {

    private static final VoxelShape SHAPE = makeShape();

    public SauceBottle(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3437499999999999, 0, 0.3437500000000001, 0.6562499999999999, 0.625, 0.6562500000000001), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5000000000000002, 0.625, 0.4687500000000001, 0.5000000000000002, 0.875, 0.5312500000000001), BooleanOp.OR);

        return shape;
    }
}
