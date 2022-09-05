package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.core.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, McDonalds.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockInit.COPPING_BOARD.get(), new ModelFile.ExistingModelFile(modLoc("block/chopping_board"), models().existingFileHelper));
        simpleBlockItem(BlockInit.COPPING_BOARD.get(), new ModelFile.ExistingModelFile(modLoc("block/chopping_board"), models().existingFileHelper));
    }


}
