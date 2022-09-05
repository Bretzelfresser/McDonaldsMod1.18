package com.bretzelfresser.mcdonalds.client.model;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.core.ItemInit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = McDonalds.MOD_ID, value = Dist.CLIENT)
public class McDonaldsModels {

    public static final RegistryObject<ItemLike>[] ITEM_MODLES = new RegistryObject[]{ItemInit.BURNT_MEAT_10_1, ItemInit.BOX, ItemInit.COOKED_MEAT_10_1, ItemInit.MEAT_10_1, ItemInit.SPATULA, ItemInit.FRIES_WITH_BOX};

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> map = event.getModelRegistry();

        for (String item : Arrays.stream(ITEM_MODLES).map(RegistryObject::get).map(ItemLike::asItem).map( i -> i.getRegistryName().getPath()).collect(Collectors.toList())) {
            ResourceLocation modelInventory = new ModelResourceLocation("mcdonalds:" + item, "inventory");
            ResourceLocation modelHand = new ModelResourceLocation("mcdonalds:" + item + "_in_hand", "inventory");

            BakedModel bakedModelDefault = map.get(modelInventory);
            BakedModel bakedModelHand = map.get(modelHand);
            BakedModel modelWrapper = new BakedModel() {
                @NotNull
                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
                    return bakedModelDefault.getQuads(state, side, rand, extraData);
                }

                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, Random rand) {
                    return bakedModelDefault.getQuads(state, direction,rand);
                }

                @Override
                public boolean useAmbientOcclusion() {
                    return bakedModelDefault.useAmbientOcclusion();
                }

                @Override
                public boolean isGui3d() {
                    return bakedModelDefault.isGui3d();
                }

                @Override
                public boolean usesBlockLight() {
                    return false;
                }

                @Override
                public boolean isCustomRenderer() {
                    return bakedModelDefault.isCustomRenderer();
                }

                @Override
                public TextureAtlasSprite getParticleIcon() {
                    return bakedModelDefault.getParticleIcon();
                }

                @Override
                public TextureAtlasSprite getParticleIcon(@NotNull IModelData data) {
                    return bakedModelDefault.getParticleIcon(data);
                }

                @Override
                public ItemOverrides getOverrides() {
                    return bakedModelDefault.getOverrides();
                }

                @Override
                public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack mat) {
                    BakedModel modelToUse = bakedModelDefault;
                    if (cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
                            || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                        modelToUse = bakedModelHand;
                    }
                    return ForgeHooksClient.handlePerspective(modelToUse, cameraTransformType, mat);
                }
            };
            map.put(modelInventory, modelWrapper);
        }
    }

    @SubscribeEvent
    public static final void specialModels(ModelRegistryEvent event){
        for (String item : Arrays.stream(ITEM_MODLES).map(RegistryObject::get).map(ItemLike::asItem).map( i -> i.getRegistryName().getPath()).collect(Collectors.toList())) {
            ForgeModelBakery.addSpecialModel(new ModelResourceLocation("mcdonalds:" + item + "_in_hand", "inventory"));
        }
    }
}
