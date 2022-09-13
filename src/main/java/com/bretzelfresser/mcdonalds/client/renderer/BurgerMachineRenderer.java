package com.bretzelfresser.mcdonalds.client.renderer;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.client.model.ModLayers;
import com.bretzelfresser.mcdonalds.client.model.block.TopBurgerMachine;
import com.bretzelfresser.mcdonalds.client.util.RenderUtil;
import com.bretzelfresser.mcdonalds.common.blockentity.BurgerMachineBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BurgerMachineRenderer implements BlockEntityRenderer<BurgerMachineBlockEntity> {

    private static final ResourceLocation MODEL_TEXTURE = McDonalds.modLoc("textures/block/top.png");

    private final BlockEntityRendererProvider.Context ctx;
    private final TopBurgerMachine model;

    public BurgerMachineRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
        this.model = new TopBurgerMachine(ctx.bakeLayer(ModLayers.BURGER_MACHINE));
    }

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
            if (!entity.getInv().getItem(0).isEmpty()){
                RenderUtil.renderItem(entity.getInv().getItem(0), new double[]{0.5, 1.01, 0.5}, Vector3f.YP.rotationDegrees(rotation), poseStack, bufferSource, combinedOverlayIn, RenderUtil.getLightLevel(mc.level, entity.getBlockPos()), 0.5f);
            }
            model.Top.xRot = (float) Math.toRadians(entity.getDegrees());
            poseStack.translate(0.5f, -0.35, 0.5f);
            poseStack.mulPose(Vector3f.YN.rotationDegrees(rotation));
            this.model.renderToBuffer(poseStack, bufferSource.getBuffer(model.renderType(MODEL_TEXTURE)), RenderUtil.getLightLevel(mc.level, entity.getBlockPos().above()), combinedOverlayIn, 1,1,1,1f);
            poseStack.popPose();
        }
    }
}
