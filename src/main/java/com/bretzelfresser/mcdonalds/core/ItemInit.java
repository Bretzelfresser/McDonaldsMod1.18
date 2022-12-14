package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.item.ReplaceableItem;
import com.bretzelfresser.mcdonalds.common.item.PaperBag;
import com.bretzelfresser.mcdonalds.common.item.SodaItem;
import com.bretzelfresser.mcdonalds.core.util.ModCreativeTab;
import com.bretzelfresser.mcdonalds.core.util.ModToolMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, McDonalds.MOD_ID);

    public static final RegistryObject<SwordItem> KNIFE = ITEMS.register("knife",
            () -> new SwordItem(ModToolMaterial.IRON_KNIFE, 3, -2.5f, new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> MEAT_10_1 = ITEMS.register("raw_meat",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.RAW_MEAT)));
    public static final RegistryObject<Item> COOKED_MEAT_10_1 = ITEMS.register("cooked_meat",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.COOKED_MEAT)));
    public static final RegistryObject<Item> BURNT_MEAT_10_1 = ITEMS.register("burnt_meat",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BURNT_MEAT)));

    public static final RegistryObject<Item> BREAD_CROWN = ITEMS.register("bread_crown",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BREAD_CROWN)));
    public static final RegistryObject<Item> BREAD_MIDDLE = ITEMS.register("bread_middle",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BREAD_MIDDLE)));
    public static final RegistryObject<Item> BREAD_BASE = ITEMS.register("bread_base",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BREAD_BASE)));
    public static final RegistryObject<Item> BREAD_SIMPLE = ITEMS.register("bread_simple",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BREAD_SIMPLE)));
    public static final RegistryObject<Item> BURNT_BREAD_CROWN = ITEMS.register("burnt_bread",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BURNT_BREAD_SIMPLE= ITEMS.register("burnt_bread_2",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BURNT_BREAD_BASE = ITEMS.register("burnt_bread_bottom",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BURNT_BREAD_MIDDLE = ITEMS.register("burnt_bread_middle",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> RAW_BREAD_CROWN = ITEMS.register("raw_bread",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> RAW_BREAD_SIMPLE = ITEMS.register("raw_bread_2",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> RAW_BREAD_BASE = ITEMS.register("raw_bread_bottom",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> RAW_BREAD_MIDDLE = ITEMS.register("raw_bread_middle",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> AMERICAN_CHEESE = ITEMS.register("american_cheese",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.AMERICAN_CHEESE)));
    public static final RegistryObject<Item> PICKLES = ITEMS.register("pickles",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.PICKLES)));
    public static final RegistryObject<Item> ONION = ITEMS.register("onion",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.ONION)));
    public static final RegistryObject<Item> ONION_RING = ITEMS.register("onion_ring",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.ONION_RING)));
    public static final RegistryObject<Item> ICEBERG_LETTUCE = ITEMS.register("iceberg_lettuce",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.ICEBERG_LETTUCE)));
    public static final RegistryObject<Item> ICEBERG_LETTUCE_SLICE = ITEMS.register("iceberg_lettuce_slice",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.ICEBERG_LETTUCE_SLICE)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.TOMATO)));
    public static final RegistryObject<Item> TOMATO_SLICE = ITEMS.register("tomato_slice",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.TOMATO_SLICE)));

    public static final RegistryObject<Item> FRIES = ITEMS.register("fries",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.FRIES)));
    public static final RegistryObject<Item> BURNT_FRIES = ITEMS.register("burnt_fries",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BURNT_FRIES)));
    public static final RegistryObject<Item> FRIES_WITH_BOX = ITEMS.register("fries_with_box",
            ()-> new ReplaceableItem(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.FRIES_WITH_BOX), ItemInit.BOX));
    public static final RegistryObject<Item> BOX = ITEMS.register("box",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> STRIPPED_POTATOES = ITEMS.register("stripped_potatoes",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.STRIPPED_POTATOES)));

    public static final RegistryObject<Item> SPATULA = ITEMS.register("spatula",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> PAPER_BAG = ITEMS.register("paper_bag", PaperBag::new);

    public static final RegistryObject<Item> BASKET = ITEMS.register("basket",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> OIL = ITEMS.register("oil",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> SODA = ITEMS.register("soda",
            ()-> new SodaItem(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.SODA).defaultDurability(10)));
    public static final RegistryObject<Item> EMPTY_SODA = ITEMS.register("empty_soda",
            ()-> new Item(new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB)));

}
