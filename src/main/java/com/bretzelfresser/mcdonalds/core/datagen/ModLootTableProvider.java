package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.core.BlockInit;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator p_124437_) {
        super(p_124437_);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLoot::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((id, table) -> LootTables.validate(validationtracker, id, table));
    }

    public static final class ModBlockLoot extends BlockLoot{
     private List<Block> knownBlocks = Lists.newArrayList();

        @Override
        protected void addTables() {
            dropSelf(BlockInit.BURGER_MACHINE.get());
            dropSelf(BlockInit.COPPING_BOARD.get());
            dropSelf(BlockInit.FRYER.get());
            dropSelf(BlockInit.QUARTER_POUNDER.get());
            dropSelf(BlockInit.ROYAL_DELUXE_BOX.get());
            dropSelf(BlockInit.CHEESBURGER_BOX.get());
            dropSelf(BlockInit.BIG_MAC_BOX.get());
            dropSelf(BlockInit.QUARTER_POUNDER_BOX.get());
            dropSelf(BlockInit.OPEN_ROYAL_DELUXE_BOX.get());
            dropSelf(BlockInit.OPEN_CHEESEBURGER_BOX.get());
            dropSelf(BlockInit.OPEN_BIG_MAC_BOX.get());
            dropSelf(BlockInit.OPEN_QUARTER_POUNDER_BOX.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return this.knownBlocks;
        }

        @Override
        protected void add(Block block, LootTable.Builder p_124167_) {
            super.add(block, p_124167_);
            this.knownBlocks.add(block);
        }

    }
}
