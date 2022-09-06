package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.core.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_126511_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_, McDonalds.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.NEEDS_STONE_TOOL).add(BlockInit.COPPING_BOARD.get(), BlockInit.BURGER_MACHINE.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(BlockInit.COPPING_BOARD.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.BURGER_MACHINE.get());
    }
}
