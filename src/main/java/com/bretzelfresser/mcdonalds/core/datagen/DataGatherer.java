package com.bretzelfresser.mcdonalds.core.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.io.IOException;

public class DataGatherer {

    public static final void gatherData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeClient())
            gatherClientData(gen, helper);
        if (event.includeServer())
            gatherServerData(gen, helper);
        try {
            gen.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final void gatherServerData(DataGenerator gen, ExistingFileHelper helper){
        gen.addProvider(new ModBlockTagsProvider(gen, helper));
        gen.addProvider(new ModLootTableProvider(gen));
        gen.addProvider(new ModRecipeProvider(gen));
    }

    private static final void gatherClientData(DataGenerator gen, ExistingFileHelper helper){
        gen.addProvider(new ModBlockStateProvider(gen, helper));
        gen.addProvider(new ModItemModelsProvider(gen, helper));
    }
}
