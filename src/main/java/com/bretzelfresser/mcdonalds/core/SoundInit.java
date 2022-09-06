package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, McDonalds.MOD_ID);

    public static final RegistryObject<SoundEvent> BURGER_FINISHED = register("burger_finished");

    public static final RegistryObject<SoundEvent> register(String name){
        return SOUNDS.register(name, () -> new SoundEvent(McDonalds.modLoc(name)));
    }
}
