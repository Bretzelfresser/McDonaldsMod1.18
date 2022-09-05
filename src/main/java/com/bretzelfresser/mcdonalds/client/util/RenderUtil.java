package com.bretzelfresser.mcdonalds.client.util;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class RenderUtil {

    public static int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
    public static void renderItem(ItemStack stack, double[] translation, Quaternion rotation, PoseStack matrixStack,
                                  MultiBufferSource buffer, int combinedOverlay, int lightLevel, float scale) {
        Minecraft mc = Minecraft.getInstance();
        matrixStack.pushPose();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.mulPose(rotation);
        matrixStack.mulPose(Vector3f.XN.rotationDegrees(90));
        matrixStack.scale(scale, scale, scale);

        Minecraft.getInstance().getItemRenderer().renderStatic(mc.player, stack, ItemTransforms.TransformType.NONE, false, matrixStack, buffer, mc.level, combinedOverlay, combinedOverlay, lightLevel);
        matrixStack.popPose();
    }
}
