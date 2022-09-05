package com.bretzelfresser.mcdonalds.common.blockentity;

import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

public class ChoppingBoardBlockEntity extends BlockEntity {

    private ItemStackHandler inv = new ItemStackHandler(1);

    public ChoppingBoardBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityInit.CHOPPING_BOARD.get(), p_155229_, p_155230_);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.inv.deserializeNBT(tag.getCompound("items"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("items", this.inv.serializeNBT());
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    public ItemStackHandler getInv() {
        return inv;
    }
}
