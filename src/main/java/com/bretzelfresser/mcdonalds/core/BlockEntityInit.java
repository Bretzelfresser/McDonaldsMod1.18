package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.blockentity.ChoppingBoardBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, McDonalds.MOD_ID);

    public static final RegistryObject<BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD = TES.register("chopping_board", () -> BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, BlockInit.COPPING_BOARD.get()).build(null));
}
