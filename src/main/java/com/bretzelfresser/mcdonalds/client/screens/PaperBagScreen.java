package com.bretzelfresser.mcdonalds.client.screens;

import com.bretzelfresser.mcdonalds.common.container.PaperBagContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PaperBagScreen extends AbstractContainerScreen<PaperBagContainer> {

    private static final ResourceLocation PAPER_BAG_SCREEN = new ResourceLocation("textures/gui/paper_bag.png");

    public PaperBagScreen(PaperBagContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    public void render(PoseStack poseStack, int p_97796_, int p_97797_, float p_97798_) {
        this.renderBackground(poseStack);
        super.render(poseStack, p_97796_, p_97797_, p_97798_);
        this.renderTooltip(poseStack, p_97796_, p_97797_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, PAPER_BAG_SCREEN);
    }
}
