package com.bretzelfresser.mcdonalds.common.block;

import com.bretzelfresser.mcdonalds.core.BlockInit;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class BurgerBox extends ShapedRotatableBlock {

    public static final EnumProperty<BurgerType> TYPE = EnumProperty.create("burger", BurgerType.class);

    public static final BurgerType convert(Block b){
        if (b == BlockInit.ROYAL_DELUXE.get()){
            return BurgerType.MC_RYAL_DELUXE;
        }
        if (b == BlockInit.BIG_MAC.get()){
            return BurgerType.BIG_MAC;
        }
        if(b == BlockInit.QUARTER_POUNDER.get()){
            return BurgerType.QUARTER_POUNDER;
        }
        return BurgerType.CHEESBURGER;
    }

    public static ItemStack getBurger(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("burger"))
            return ItemStack.of(stack.getTag().getCompound("burger"));
        return ItemStack.EMPTY;
    }

    public static final ItemStack setBurger(ItemStack stack, ItemStack burger){
        stack.getOrCreateTag().put("burger", burger.serializeNBT());
        return stack;
    }

    public BurgerBox(Properties properties) {
        super(properties, Shapes.block());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (!level.isClientSide() && !BurgerBox.getBurger(stack).isEmpty() && BurgerBox.getBurger(stack).getItem() instanceof BlockItem){
            Block b = ((BlockItem)BurgerBox.getBurger(stack).getItem()).getBlock();
            level.setBlock(pos, state.setValue(TYPE, convert(b)), 3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    public enum BurgerType implements StringRepresentable {
        CHEESBURGER("cheeseburger"),
        BIG_MAC("big_mac"),
        QUARTER_POUNDER("quarter_pounder"),
        MC_RYAL_DELUXE("mc_royal_deluxe");

        private final String name;
        BurgerType(String name){
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
