package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.blockentity.BurgerMachineBlockEntity;
import com.bretzelfresser.mcdonalds.common.blockentity.ChoppingBoardBlockEntity;
import com.bretzelfresser.mcdonalds.common.blockentity.FryerBlockEntity;
import com.bretzelfresser.mcdonalds.common.blockentity.TableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, McDonalds.MOD_ID);

    public static final RegistryObject<BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD = TES.register("chopping_board", () -> BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, BlockInit.COPPING_BOARD.get()).build(null));
    public static final RegistryObject<BlockEntityType<BurgerMachineBlockEntity>> BURGER_MACHINE = TES.register("burger_machine", () ->BlockEntityType.Builder.of(BurgerMachineBlockEntity::new, BlockInit.BURGER_MACHINE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FryerBlockEntity>> FRYER = TES.register("fryer", () -> BlockEntityType.Builder.of(FryerBlockEntity::new, BlockInit.FRYER.get()).build(null));
    public static final RegistryObject<BlockEntityType<TableBlockEntity>> TABLE = TES.register("table", () -> BlockEntityType.Builder.of(TableBlockEntity::new, BlockInit.TABLE.get()).build(null));
}
