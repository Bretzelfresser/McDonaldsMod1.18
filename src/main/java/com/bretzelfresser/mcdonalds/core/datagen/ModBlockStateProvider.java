package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.block.Fryer;
import com.bretzelfresser.mcdonalds.core.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, McDonalds.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockInit.COPPING_BOARD.get(), new ModelFile.ExistingModelFile(modLoc("block/chopping_board"), models().existingFileHelper));
        simpleBlockItem(BlockInit.COPPING_BOARD.get(), new ModelFile.ExistingModelFile(modLoc("block/chopping_board"), models().existingFileHelper));
        simpleBlockItem(BlockInit.FRYER.get(), new ModelFile.ExistingModelFile(modLoc("block/fryer/fryer_without_basket_without_oil"), models().existingFileHelper));
        simpleBlock(BlockInit.QUARTER_POUNDER.get(), new ModelFile.ExistingModelFile(modLoc("block/quarter_pounder"), models().existingFileHelper));
        simpleBlockItem(BlockInit.QUARTER_POUNDER.get(), new ModelFile.ExistingModelFile(modLoc("block/quarter_pounder"), models().existingFileHelper));
        makeFryer();
    }

    private void makeFryer(){
        getVariantBuilder(BlockInit.FRYER.get()).forAllStates(state -> {
            ConfiguredModel.Builder builder = ConfiguredModel.builder();
            String name = "fryer/fryer";
            if (state.getValue(Fryer.BASKET)){
                name += "_with_basket";
            }else{
                name += "_without_basket";
            }
            switch (state.getValue(Fryer.OIL)){
                case 0 -> name += "_without_oil";
                case 1 -> name += "_with_low_oil";
                case 2 -> name += "_with_middle_oil";
                case 3 -> name += "_with_oil";
                default -> throw new IllegalStateException("fryer shouldnt have mode then 3 states of oil");
            }
            builder.modelFile(existingBlock(name));
            builder.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360);
            return builder.build();
        });
    }

    protected ModelFile existingBlock(String path){
        return new ModelFile.ExistingModelFile(modLoc("block/" + path), models().existingFileHelper);
    }


}
