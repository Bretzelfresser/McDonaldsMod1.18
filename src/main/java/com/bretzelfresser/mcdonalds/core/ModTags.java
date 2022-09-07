package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.lwjgl.system.CallbackI;

public class ModTags {

    public static final class Items{

        public static final TagKey<Item> BURGER = tag("burger");
        public static final TagKey<Item> FRIES = tag("fries");
        public static final TagKey<Item> DRINK = tag("drinks");

        public static final TagKey<Item> tag(String name){
            return ItemTags.create(McDonalds.modLoc(name));
        }
    }
}
