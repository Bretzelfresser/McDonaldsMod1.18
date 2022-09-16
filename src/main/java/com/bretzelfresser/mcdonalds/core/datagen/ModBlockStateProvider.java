package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.block.BurgerBox;
import com.bretzelfresser.mcdonalds.common.block.Fryer;
import com.bretzelfresser.mcdonalds.common.block.TableBlock;
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
        makeBurgerBox();
        simpleBlockItem(BlockInit.BURGER_BOX.get(), existingBlock("burger_box/box_cheeseburger"));
        makeFryer();
        horizontalBlock(BlockInit.OPEN_QUARTER_POUNDER_BOX.get(), existingBlock("burger_box/open_box_quarterpound"));
        simpleBlockItem(BlockInit.OPEN_QUARTER_POUNDER_BOX.get(), existingBlock("burger_box/open_box_quarterpound"));
        horizontalBlock(BlockInit.OPEN_RYAL_DELUXE_BOX.get(),  existingBlock("burger_box/open_box_mcroyaldeluxe"));
        simpleBlockItem(BlockInit.OPEN_RYAL_DELUXE_BOX.get(), existingBlock("burger_box/open_box_mcroyaldeluxe"));
        horizontalBlock(BlockInit.OPEN_CHEESBURGER_BOX.get(), existingBlock("burger_box/open_box_cheeseburger"));
        simpleBlockItem(BlockInit.OPEN_CHEESBURGER_BOX.get(), existingBlock("burger_box/open_box_cheeseburger"));
        horizontalBlock(BlockInit.OPEN_BIG_MAC_BOX.get(), existingBlock("burger_box/open_box_big_mac"));
        simpleBlockItem(BlockInit.OPEN_BIG_MAC_BOX.get(), existingBlock("burger_box/open_box_big_mac"));
        makeTable();
        simpleBlockItem(BlockInit.TABLE.get(), existingBlock("table"));
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

    private void makeBurgerBox(){
        getVariantBuilder(BlockInit.BURGER_BOX.get()).forAllStates(state -> {
            ConfiguredModel.Builder builder = ConfiguredModel.builder();
            String name = "burger_box/box_";
            switch (state.getValue(BurgerBox.TYPE)){
                case CHEESBURGER -> name += "cheeseburger";
                case BIG_MAC -> name += "big_mac";
                case MC_RYAL_DELUXE -> name += "mc_royal_box";
                case QUARTER_POUNDER -> name += "quarter_pounder";
                default -> name += "none";
            }
            builder.modelFile(existingBlock(name));
            builder.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360);
            return builder.build();
        });
    }

    private void makeTable(){
        getVariantBuilder(BlockInit.TABLE.get()).forAllStates(state -> {
            ConfiguredModel.Builder builder = ConfiguredModel.builder();
            String name = "table";
            if (state.getValue(TableBlock.CONNECTED)){
                name += "_without_feet";
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
