package com.bretzelfresser.mcdonalds.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class McDonaldsConfig {

    public static ForgeConfigSpec.IntValue TIME_TO_BURN;

    public static final ForgeConfigSpec init(){
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        machineConfig(builder);
        return builder.build();
    }

    public static void machineConfig(ForgeConfigSpec.Builder builder){
        builder.push("Machines");
        builder.comment("how much ticks until a finished meat will then be burnt");
        builder.comment("place -1 so things will never burn");
        TIME_TO_BURN = builder.defineInRange("timeToBurn", 200, -2, Integer.MAX_VALUE);
        builder.pop();
    }
}
