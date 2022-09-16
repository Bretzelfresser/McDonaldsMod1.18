package com.bretzelfresser.mcdonalds;

import com.bretzelfresser.mcdonalds.client.renderer.BurgerMachineRenderer;
import com.bretzelfresser.mcdonalds.client.renderer.ChoppingRenderer;
import com.bretzelfresser.mcdonalds.client.renderer.FryerRenderer;
import com.bretzelfresser.mcdonalds.client.renderer.TableRenderer;
import com.bretzelfresser.mcdonalds.client.screens.PaperBagScreen;
import com.bretzelfresser.mcdonalds.common.block.Burger;
import com.bretzelfresser.mcdonalds.common.block.BurgerBox;
import com.bretzelfresser.mcdonalds.core.*;
import com.bretzelfresser.mcdonalds.core.config.McDonaldsConfig;
import com.bretzelfresser.mcdonalds.core.datagen.DataGatherer;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(McDonalds.MOD_ID)
public class McDonalds {
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "mcdonalds";

    public static final ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public McDonalds() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, McDonaldsConfig.init());
        bus.addGenericListener(RecipeSerializer.class, RecipeInit::registerRecipes);
        bus.addListener(this::clientSetup);

        bus.addListener(DataGatherer::gatherData);

        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);

        BlockEntityInit.TES.register(bus);
        ContainerInit.MENUS.register(bus);

        SoundInit.SOUNDS.register(bus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        BlockEntityRenderers.register(BlockEntityInit.CHOPPING_BOARD.get(), ChoppingRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.BURGER_MACHINE.get(), BurgerMachineRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.FRYER.get(), FryerRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.TABLE.get(), TableRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(BlockInit.FRYER.get(), RenderType.translucent());

        MenuScreens.register(ContainerInit.PAPER_BAG.get(), PaperBagScreen::new);

        /** never do it like me again*/
        ItemProperties.register(BlockInit.BURGER_BOX.get().asItem(), new ResourceLocation("burger"), (stack, clientWorld, living, another) -> {
            if (!BurgerBox.getBurger(stack).isEmpty() && BurgerBox.getBurger(stack).getItem() instanceof BlockItem) {
                Block b = ((BlockItem) BurgerBox.getBurger(stack).getItem()).getBlock();
                return BurgerBox.convert(b).ordinal();
            }

            return 0;
        });
    }


}
