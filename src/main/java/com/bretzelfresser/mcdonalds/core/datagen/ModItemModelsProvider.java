package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.core.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public class ModItemModelsProvider extends ItemModelProvider {
    public final ModelFile generated;
    public final ModelFile spawnEgg;
    public final ModelFile handheld;


    public ModItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, McDonalds.MOD_ID, existingFileHelper);
        generated = getExistingFile(mcLoc("item/generated"));
        spawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));
        handheld = getExistingFile(mcLoc("item/handheld"));
    }

    @Override
    protected void registerModels() {
        registerSimpleModel(ItemInit.BASKET.get(), ItemInit.BURNT_BREAD.get(), ItemInit.BURNT_BREAD2.get(), ItemInit.BURNT_BREAD_BOTTOM.get());
        registerSimpleModel(ItemInit.RAW_BREAD.get(), ItemInit.RAW_BREAD2.get(), ItemInit.RAW_BREAD_BOTTOM.get());
        registerSimpleModel(ItemInit.KETCHUP_BOTTLE.get(), ItemInit.KETCHUP_BOWL.get());
        registerSimpleModel(ItemInit.MAYO_BOTTLE.get(), ItemInit.MAYO_BOWL.get());
        registerSimpleModel(ItemInit.MUSTARD_BOWL.get());
        registerSimpleModel(ItemInit.OIL.get(), ItemInit.GLASS_BOWL.get(), ItemInit.CREAM_BOWL.get());
    }

    private void registerSimpleModel(Item... items) {
        Arrays.stream(items).forEach(this::registerSimpleModel);
    }

    private void registerHandheldModel(Item... items){
        Arrays.stream(items).forEach(this::registerHandheldModel);
    }

    private void registerSimpleModel(Item item){
        String name = item.getRegistryName().getPath();
        getBuilder(name).parent(generated).texture("layer0", "item/" + name);
    }

    private void registerHandheldModel(Item item){
        String name = item.getRegistryName().getPath();
        getBuilder(name).parent(handheld).texture("layer0", "item/" + name);
    }
    private void registerSpawnEgg(Item item) {
        String name = item.getRegistryName().getPath();
        getBuilder(name).parent(spawnEgg);
    }
}
