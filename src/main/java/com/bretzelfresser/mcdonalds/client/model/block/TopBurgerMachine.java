package com.bretzelfresser.mcdonalds.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class TopBurgerMachine extends Model {

    private final ModelPart Top;

    public TopBurgerMachine(ModelPart root) {
        super(RenderType::entitySolid);
        this.Top = root.getChild("Top");
    }
    public static LayerDefinition createLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        root.addOrReplaceChild("Top", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-7.0F, -2.5F, -14.75F, 14.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -3.0F, -13.75F, 14.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -2.0F, -15.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -2.0F, -15.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -2.0F, -15.75F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 7.75F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
