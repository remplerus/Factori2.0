package com.rempler.factori20.utils;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.nio.file.Path;

public class F20Config {
    @Nonnull public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.IntValue scannerEnergyCost;
    public static final ForgeConfigSpec.IntValue scannerMaxEnergy;

    public static final ForgeConfigSpec.IntValue max_copper;
    public static final ForgeConfigSpec.IntValue max_tin;
    public static final ForgeConfigSpec.IntValue max_lead;
    public static final ForgeConfigSpec.IntValue max_silicon;
    public static final ForgeConfigSpec.IntValue max_oil;
    public static final ForgeConfigSpec.IntValue max_natural_gas;
    public static final ForgeConfigSpec.IntValue max_iron;
    public static final ForgeConfigSpec.IntValue max_gold;
    public static final ForgeConfigSpec.IntValue max_diamond;
    public static final ForgeConfigSpec.IntValue max_emerald;
    @Nonnull private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    static {
        COMMON_BUILDER.push("Scanner");
        scannerMaxEnergy = COMMON_BUILDER.comment("How much energy should a scanner have")
                .defineInRange("max_energy", 100000, 0, Integer.MAX_VALUE);
        scannerEnergyCost = COMMON_BUILDER.comment("How much energy should a scan cost")
                .defineInRange("energy_cost", 10, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Resource Generation").comment("How many of the following resources should be generated max");
        max_copper = COMMON_BUILDER.defineInRange("max_copper", 15000, 0, Integer.MAX_VALUE);
        max_tin = COMMON_BUILDER.defineInRange("max_tin", 5000, 0, Integer.MAX_VALUE);
        max_lead = COMMON_BUILDER.defineInRange("max_lead", 2500, 0, Integer.MAX_VALUE);
        max_silicon = COMMON_BUILDER.defineInRange("max_silicon", 2500, 0, Integer.MAX_VALUE);
        max_oil = COMMON_BUILDER.defineInRange("max_oil", 20000, 0, Integer.MAX_VALUE);
        max_natural_gas = COMMON_BUILDER.defineInRange("max_natural_gas", 40000, 0, Integer.MAX_VALUE);
        max_iron = COMMON_BUILDER.defineInRange("max_iron", 10000, 0, Integer.MAX_VALUE);
        max_gold = COMMON_BUILDER.defineInRange("max_gold", 1200, 0, Integer.MAX_VALUE);
        max_diamond = COMMON_BUILDER.defineInRange("max_diamond", 125, 0, Integer.MAX_VALUE);
        max_emerald = COMMON_BUILDER.defineInRange("max_emerald", 100, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        CommentedFileConfig config = CommentedFileConfig.builder(path).sync().autosave().autoreload().writingMode(WritingMode.REPLACE).build();

        config.load();
        spec.setConfig(config);
    }
}
