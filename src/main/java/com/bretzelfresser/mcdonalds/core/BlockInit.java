package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.block.*;
import com.bretzelfresser.mcdonalds.common.item.BurgerBoxItem;
import com.bretzelfresser.mcdonalds.core.util.ModCreativeTab;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, McDonalds.MOD_ID);

    public static final RegistryObject<ChoppingBoard> COPPING_BOARD = register("chopping_board", ChoppingBoard::new, ModCreativeTab.MC_DONALDS_TAB);
    public static final RegistryObject<BurgerMachine> BURGER_MACHINE = register("burger_machine", BurgerMachine::new, ModCreativeTab.MC_DONALDS_TAB);
    public static final RegistryObject<Fryer> FRYER = register("fryer", Fryer::new, ModCreativeTab.MC_DONALDS_TAB);
    public static final RegistryObject<BurgerBox> BURGER_BOX = register("burger_box", () -> new BurgerBox(BlockBehaviour.Properties.of(Material.WOOD).strength(3).noOcclusion()), BurgerBoxItem::new);

    public static final RegistryObject<Burger> QUARTER_POUNDER = register("quarter_pounder",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeQuarterPounderShape()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.QUARTER_POUNDER));

    public static final RegistryObject<Burger> BIG_MAC = register("big_mac",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeBigMacShape()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BIG_MAC));

    public static final RegistryObject<Burger> CHEESEBURGER = register("cheeseburger",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeCheeseBurger()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.CHEESE_BURGER));
    public static final RegistryObject<Burger> ROYAL_DELUXE = register("royal_deluxe",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeMcRoyalDeluxe()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.MC_ROYAL_DELUXE));


    public static final RegistryObject<Block> KETCHUP_BOTTLE = register("ketchup_bottle",
            () -> new SauceBottle(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.WOOD).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));
    public static final RegistryObject<Block> MAYO_BOTTLE = register("mayo_bottle",
            () -> new SauceBottle(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.WOOD).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));
    public static final RegistryObject<Block> MUSTARD_BOTTLE = register("mustard_bottle",
            () -> new SauceBottle(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.WOOD).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));

    public static final RegistryObject<Block> KETCHUP_BOWL = register("ketchup_bowl",
            () -> new SauceBowl(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.GLASS).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));
    public static final RegistryObject<Block> MAYO_BOWL = register("mayo_bowl",
            () -> new SauceBowl(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.GLASS).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));
    public static final RegistryObject<Block> MUSTARD_BOWL = register("mustard_bowl",
            () -> new SauceBowl(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.GLASS).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));
    public static final RegistryObject<Block> BIG_MAC_BOWL = register("big_mac_bowl",
            () -> new SauceBowl(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.GLASS).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));
    public static final RegistryObject<Block> EMPTY_BOWL = register("empty_bowl",
            () -> new SauceBowl(BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.GLASS).noOcclusion()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));

    public static final RegistryObject<Block> OPEN_CHEESBURGER_BOX = register("open_cheesburger_box", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.CACTUS).instabreak().noOcclusion()), ModCreativeTab.MC_DONALDS_TAB);
    public static final RegistryObject<Block> OPEN_BIG_MAC_BOX = register("open_big_mac_box", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.CACTUS).instabreak().noOcclusion()), ModCreativeTab.MC_DONALDS_TAB);
    public static final RegistryObject<Block> OPEN_RYAL_DELUXE_BOX = register("open_royal_deluxe_box", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.CACTUS).instabreak().noOcclusion()), ModCreativeTab.MC_DONALDS_TAB);
    public static final RegistryObject<Block> OPEN_QUARTER_POUNDER_BOX = register("open_quarter_pounder_box", () -> new RotatableBlock(BlockBehaviour.Properties.of(Material.CACTUS).instabreak().noOcclusion()), ModCreativeTab.MC_DONALDS_TAB);

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, CreativeModeTab tab){
        return register(name, blockSupplier, new Item.Properties().tab(tab));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties properties){
        return register(name, blockSupplier, b -> new BlockItem(b, properties));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction){
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }

}
