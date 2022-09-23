package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.blockentity.TableBlockEntity;
import com.bretzelfresser.mcdonalds.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TableBlock extends Block implements EntityBlock {

    protected static final Map<Direction, VoxelShape> SHAPES = new HashMap<Direction, VoxelShape>();

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");

    public TableBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().strength(5).requiresCorrectToolForDrops());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(CONNECTED, false));
        runCalculation(withoutFeet());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND && level.getBlockState(pos.above()).isAir()) {
            TableBlockEntity te = WorldUtils.getTileEntity(TableBlockEntity.class, level, pos);
            if (te != null && te.getRecipe() != null && !player.getItemInHand(hand).isEmpty()) {
                te.getInv().add(ItemStack.EMPTY);
                //te.getRecipe().getIngs().forEach(i -> McDonalds.LOGGER.info("" + Arrays.toString(i.getItems())));
                ItemStack remainder = te.insertItem(te.getContainerSize() - 1, player.getItemInHand(hand), false);
                if (!player.isCreative()) {
                    player.setItemInHand(hand, remainder);
                }
                if (checkRecipe(te)){
                    for (ItemStack stack : te.getInv()){
                        if (stack.getItem().hasContainerItem(stack)){
                            Block.popResource(level, pos, stack.getContainerItem());
                        }
                    }
                    ItemStack result = te.getRecipe().getResultItem().copy();
                    te.clearContent();
                    te.getInv().add(result);
                    te.blockUpdate();
                }
                te.blockUpdate();
                return InteractionResult.SUCCESS;
            }
            if (te != null && player.getItemInHand(hand).isEmpty() && player.isCrouching() && te.getContainerSize() > 0) {
                if (player.addItem(te.getItem(te.getContainerSize() - 1).copy())) {
                    McDonalds.LOGGER.info("" + te.getItem(te.getContainerSize() - 1).toString());
                    te.getInv().remove(te.getContainerSize() - 1);
                    te.blockUpdate();
                    return InteractionResult.SUCCESS;
                }
            }
            te.blockUpdate();
        }
        return InteractionResult.PASS;
    }

    private static boolean checkRecipe(TableBlockEntity te) {
        if (te == null || te.getRecipe() == null || te.getInv().size() != te.getRecipe().getIngs().size())
            return false;
        int counter = 0;
        for (int i = 0; i < te.getContainerSize(); i++) {
            if (!te.getRecipe().getIngs().get(i).test(te.getItem(i))) {
                return false;
            }else{
                counter++;
            }
        }
        return counter == te.getRecipe().getIngs().size();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (!level.isClientSide()) {
            for (Direction d : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                BlockPos neighbor = pos.relative(d);
                if (!state.getValue(CONNECTED) && level.getBlockState(neighbor).getBlock() instanceof TableBlock && !level.getBlockState(neighbor).getValue(CONNECTED)) {
                    level.setBlock(pos, state.setValue(FACING, d.getOpposite()).setValue(CONNECTED, true), 3);
                    level.setBlock(neighbor, state.setValue(FACING, d).setValue(CONNECTED, true), 3);
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!level.isClientSide() && state.getValue(CONNECTED)) {
            if (state.getValue(FACING).getOpposite() == direction && !(neighborState.getBlock() instanceof TableBlock)) {
                return state.setValue(FACING, Direction.NORTH).setValue(CONNECTED, false);
            }
        }
        return state;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newSTate, boolean isMoving) {
        if (!level.isClientSide() && !state.is(newSTate.getBlock()) && level.getBlockEntity(pos) != null){
            TableBlockEntity te = WorldUtils.getTileEntity(TableBlockEntity.class, level, pos);
            for (int i = 0;i < te.getContainerSize();i++){
                Block.popResource(level, pos, te.getItem(i));
            }
        }
        super.onRemove(state, level, pos, newSTate, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, CONNECTED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TableBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext p_60558_) {
        if (!state.getValue(CONNECTED))
            return withFeet();
        return SHAPES.get(state.getValue(FACING));
    }

    protected static void calculateShapes(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

        int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1],
                    Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        SHAPES.put(to, buffer[0]);
    }

    private void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            calculateShapes(direction, shape);
        }
    }

    public VoxelShape withFeet(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.9375, 0, 0, 1, 0.9375, 0.0625));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0, 0.0625, 0.9375, 0.0625));
        shape = Shapes.or(shape, Shapes.box(0.9375, 0, 0.9375, 1, 0.9375, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0.9375, 0, 1, 0.9984375, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0.9375, 0.0625, 0.9375, 1));

        return shape;
    }

    public VoxelShape withoutFeet(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0, 0, 0.0625, 0.9375, 0.0625));
        shape = Shapes.or(shape, Shapes.box(0.9375, 0, 0, 1, 0.9375, 0.0625));
        shape = Shapes.or(shape, Shapes.box(0, 0.9375, 0, 1, 0.9984375, 1));

        return shape;
    }
}
