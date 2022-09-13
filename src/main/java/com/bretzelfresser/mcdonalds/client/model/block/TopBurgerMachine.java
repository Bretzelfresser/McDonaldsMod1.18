package com.bretzelfresser.mcdonalds.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class TopBurgerMachine extends Model {

    public final ModelPart Top;

    public TopBurgerMachine(ModelPart root) {
        super(RenderType::entitySolid);
        this.Top = root.getChild("Top");
    }
    public static LayerDefinition createLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        PartDefinition top = root.addOrReplaceChild("Top", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-7.05F, -2.0F, -14.75F, 14.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-7.025F, -3.4F, -13.35F, 14.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -1.5F, -16.25F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 7.75F));

        top.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(0, 30).addBox(-7.05F, 1.8F, 6.225F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, -9.0745F, -4.2789F, -0.7854F, 0.0F, 0.0F));

        top.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(30, 31).addBox(-7.0F, -3.0F, -13.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0711F, -3.4363F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
