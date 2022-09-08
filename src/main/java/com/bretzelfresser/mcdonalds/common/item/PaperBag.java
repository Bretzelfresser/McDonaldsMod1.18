package com.bretzelfresser.mcdonalds.common.item;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.container.PaperBagContainer;
import com.bretzelfresser.mcdonalds.core.util.ModCreativeTab;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PaperBag extends Item {

    public static final int INVENTORY_SIZE = 3;

    public PaperBag() {
        super(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).stacksTo(1));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        CapabilityProvider provider = new CapabilityProvider(INVENTORY_SIZE);
        if (nbt != null) {
            provider.deserializeNBT(nbt);
        }
        return provider;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player.isShiftKeyDown()) {
            ItemStack helItem = player.getItemInHand(hand);
            openGui(level, player, helItem);
            return InteractionResultHolder.success(helItem);
        }

        return super.use(level, player, hand);
    }

    private static final void openGui(Level level, Player player, ItemStack holdSTack) {
        if (!level.isClientSide()) {
            NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
                    return new PaperBagContainer(id, playerInv, holdSTack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalStateException::new));
                }

                @Override
                public Component getDisplayName() {
                    return new TranslatableComponent("container." + McDonalds.MOD_ID + ".paper_bag");
                }
            }, buf -> buf.writeInt(INVENTORY_SIZE));
        }
    }

    public static final class CapabilityProvider implements ICapabilitySerializable<CompoundTag> {

        ItemStackHandler inv;

        public CapabilityProvider(int inventorySize) {
            this.inv = new ItemStackHandler(inventorySize){
                @Override
                protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                    return 1;
                }
            };
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return LazyOptional.of(() -> inv).cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return inv.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            inv.deserializeNBT(nbt);
        }
    }
}
