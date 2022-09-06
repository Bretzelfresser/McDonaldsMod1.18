package com.bretzelfresser.mcdonalds.client.model;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.client.model.block.TopBurgerMachine;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = McDonalds.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModLayers {

    public static final ModelLayerLocation BURGER_MACHINE = register("burger_machine");


    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BURGER_MACHINE, TopBurgerMachine::createLayer);
    }

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation(McDonalds.MOD_ID, p_171301_), p_171302_);
    }
}
