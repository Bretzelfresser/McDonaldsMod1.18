package com.bretzelfresser.mcdonalds.client.renderer;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.client.util.RenderUtil;
import com.bretzelfresser.mcdonalds.common.blockentity.BurgerMachineBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BurgerMachineRenderer implements BlockEntityRenderer<BurgerMachineBlockEntity> {

    private static final ResourceLocation MODEL_TEXTURE = McDonalds.modLoc("textures/block/top_burger_machine.png");

    @Override
    public void render(BurgerMachineBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
        if (entity != null){
            Minecraft mc = Minecraft.getInstance();
            poseStack.pushPose();
            float rotation = 0;
            Direction facing = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
            switch (facing) {
                case EAST:
                    rotation = 90;
                    break;
                case SOUTH:
                    rotation = 180;
                    break;
                case WEST:
                    rotation = 270;
                    break;
                default:
                    break;
            }
            if (entity.isClosing()){

            }
            poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
            poseStack.translate(0.1, 1.2f, 0);
            poseStack.mulPose(Vector3f.XN.rotationDegrees(entity.getDegrees()));
            poseStack.translate(-0.6, 0, 0.65);
            poseStack.mulPose(Vector3f.XN.rotationDegrees(180));
            MODEL.render(poseStack, bufferSource.getBuffer(MODEL.getRenderType(MODEL_TEXTURE)), RenderUtil.getLightLevel(mc.level, entity.getBlockPos().above()), combinedOverlayIn, 1,1,1,1f);
            poseStack.popPose();
            if (!entity.getInv().getItem(0).isEmpty()){
                RenderUtil.renderItem(entity.getInv().getItem(0), new double[]{0.5, 1.01, 0.5}, Vector3f.YP.rotationDegrees(rotation), poseStack, bufferSource, combinedOverlayIn, RenderUtil.getLightLevel(mc.level, entity.getBlockPos()), 0.5f);
            }
        }
    }
}
