package com.bretzelfresser.mcdonalds.client;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.client.renderer.BurgerMachineRenderer;
import com.bretzelfresser.mcdonalds.client.renderer.ChoppingRenderer;
import com.bretzelfresser.mcdonalds.client.renderer.FryerRenderer;
import com.bretzelfresser.mcdonalds.client.renderer.TableRenderer;
import com.bretzelfresser.mcdonalds.client.screens.PaperBagScreen;
import com.bretzelfresser.mcdonalds.common.block.BurgerBox;
import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import com.bretzelfresser.mcdonalds.core.BlockInit;
import com.bretzelfresser.mcdonalds.core.ContainerInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = McDonalds.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static final void clientSetup(FMLClientSetupEvent event){
        BlockEntityRenderers.register(BlockEntityInit.CHOPPING_BOARD.get(), ChoppingRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.BURGER_MACHINE.get(), BurgerMachineRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.FRYER.get(), FryerRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.TABLE.get(), TableRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(BlockInit.FRYER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BURGER_MACHINE.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.EMPTY_BOWL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BIG_MAC_BOWL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.MAYO_BOWL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.MUSTARD_BOWL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.KETCHUP_BOWL.get(), RenderType.cutout());

        MenuScreens.register(ContainerInit.PAPER_BAG.get(), PaperBagScreen::new);
    }
}
