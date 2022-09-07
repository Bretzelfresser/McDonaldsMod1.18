package com.bretzelfresser.mcdonalds.client.screens;

import com.bretzelfresser.mcdonalds.common.container.PaperBagContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PaperBagScreen extends AbstractContainerScreen<PaperBagContainer> {
    public PaperBagScreen(PaperBagContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float partialTicks, int mouseX, int mouseY) {

    }
}
