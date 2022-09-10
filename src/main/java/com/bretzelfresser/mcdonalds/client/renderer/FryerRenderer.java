package com.bretzelfresser.mcdonalds.client.renderer;

import com.bretzelfresser.mcdonalds.client.util.RenderUtil;
import com.bretzelfresser.mcdonalds.common.blockentity.FryerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class FryerRenderer implements BlockEntityRenderer<FryerBlockEntity> {
    private final BlockEntityRendererProvider.Context ctx;

    public FryerRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(FryerBlockEntity te, float partialTicks, PoseStack matrixStack, MultiBufferSource source, int combinedLight, int combinedOverlay) {
        if (te != null){
            float rotation = 0;
            Direction facing = te.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
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
            if (!te.getInv().getStackInSlot(0).isEmpty())
                RenderUtil.renderItem(te.getInv().getStackInSlot(0), new double[]{0.53,0.85,0.55f}, Vector3f.YP.rotation(rotation), matrixStack, source,combinedOverlay,  RenderUtil.getLightLevel(Minecraft.getInstance().level, te.getBlockPos().above()), 0.2f);
            if (!te.getInv().getStackInSlot(1).isEmpty())
                RenderUtil.renderItem(te.getInv().getStackInSlot(1), new double[]{0.35,0.85,0.45f}, Vector3f.YP.rotation(rotation), matrixStack, source,combinedOverlay,  RenderUtil.getLightLevel(Minecraft.getInstance().level, te.getBlockPos().above()), 0.2f);
        }
    }
}
