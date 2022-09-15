package com.bretzelfresser.mcdonalds.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SauceBowl extends Block {

    private static final VoxelShape SHAPE = makeShape();

    public SauceBowl(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.2812500000000001, 0, 0.2812499999999999, 0.7187500000000001, 0.3125, 0.7187499999999999), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.2812500000000001, 0, 0.2812499999999999, 0.7187500000000001, 0.3125, 0.7187499999999999), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.2812500000000001, 0.3125, 0.2812499999999999, 0.7187500000000001, 0.3125, 0.7187499999999999), BooleanOp.OR);

        return shape;
    }
}
