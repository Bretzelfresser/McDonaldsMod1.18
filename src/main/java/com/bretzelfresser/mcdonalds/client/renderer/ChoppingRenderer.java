package com.bretzelfresser.mcdonalds.client.renderer;

import com.bretzelfresser.mcdonalds.client.util.RenderUtil;
import com.bretzelfresser.mcdonalds.common.blockentity.ChoppingBoardBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ChoppingRenderer implements BlockEntityRenderer<ChoppingBoardBlockEntity> {

    private final BlockEntityRendererProvider.Context ctx;

    public ChoppingRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(ChoppingBoardBlockEntity te, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (te != null && !te.getInv().getStackInSlot(0).isEmpty()) {
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
            Minecraft mc = Minecraft.getInstance();
            RenderUtil.renderItem(te.getInv().getStackInSlot(0), new double[]{0.5d, 0.07d, 0.5d}, Vector3f.YP.rotation(rotation), poseStack, buffer, combinedOverlay, RenderUtil.getLightLevel(mc.level, te.getBlockPos()), 0.5f);
        }
    }
}
