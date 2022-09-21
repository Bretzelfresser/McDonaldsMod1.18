package com.bretzelfresser.mcdonalds.client.renderer;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.client.util.RenderUtil;
import com.bretzelfresser.mcdonalds.common.blockentity.TableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class TableRenderer implements BlockEntityRenderer<TableBlockEntity> {

    private final BlockEntityRendererProvider.Context ctx;

    public TableRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }


    @Override
    public void render(TableBlockEntity te, float partialTicks, PoseStack poseStack, MultiBufferSource source, int combinedLight, int combinedOverlay) {
        if (te != null) {
            Random rand = new Random(10);
            for (int i = 0; i < te.getContainerSize(); i++) {
                ItemStack stack = te.getItem(i);
                //McDonalds.LOGGER.info("" + stack.toString());
                if (!stack.isEmpty()) {
                    poseStack.pushPose();
                    poseStack.translate(0, 0.062 + (i * 0.0625), 0);
                    poseStack.translate(0.5, 0.95, 0.5);
                    poseStack.mulPose(Vector3f.YN.rotationDegrees(rand.nextFloat() * 360.0f));
                    poseStack.mulPose(Vector3f.XN.rotationDegrees(90));
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.NONE, RenderUtil.getLightLevel(Minecraft.getInstance().level, te.getBlockPos().above()), combinedOverlay, poseStack, source, 0);
                    poseStack.popPose();
                }
            }
        }
    }
}
