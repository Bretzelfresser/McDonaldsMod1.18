package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.block.Burger;
import com.bretzelfresser.mcdonalds.common.block.BurgerMachine;
import com.bretzelfresser.mcdonalds.common.block.ChoppingBoard;
import com.bretzelfresser.mcdonalds.common.block.Fryer;
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

    public static final RegistryObject<Burger> QUARTER_POUNDER = register("quarter_pounder",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeQuarterPounderShape()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.QUARTER_POUNDER));

    public static final RegistryObject<Burger> BIG_MAC = register("big_mac",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeBigMacShape()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.BIG_MAC));

    public static final RegistryObject<Burger> CHEESEBURGER = register("cheeseburger",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeCheeseBurger()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.CHEESE_BURGER));
    public static final RegistryObject<Burger> ROYAL_DELUXE = register("royal_deluxe",
            () -> new Burger(BlockBehaviour.Properties.of(Material.CAKE).sound(SoundType.WOOL).noOcclusion().instabreak(), Burger.makeMcRoyalDeluxe()), new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB).food(FoodInit.MC_ROYAL_DELUXE));
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
