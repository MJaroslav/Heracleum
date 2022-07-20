package com.github.mjaroslav.heracleum.lib;

import mjaroslav.mcmods.mjutils.module.ConfigurationCategory;
import mjaroslav.mcmods.mjutils.module.ConfigurationProperty;

@ConfigurationCategory(modID = ModInfo.MOD_ID, name = ConfigurationCategory.GENERAL_NAME,
        comment = ConfigurationCategory.GENERAL_COMMENT)
public class CategoryGeneral {
    @ConfigurationCategory(name = "plant_parameters", comment = "Block plant spread and growth parameters")
    public static class CategoryPlantParameters {
        @ConfigurationProperty(comment = "Enable heracleum spreading", defaultBoolean = true)
        public static boolean canSpread;

        @ConfigurationProperty(comment = "Spread radius for umbels (top block)", defaultInt = 30, minInt = 1, maxInt = 50)
        public static int spreadRadiusTop;

        @ConfigurationProperty(comment = "Spread radius for stem with umbels (middle block)", defaultInt = 15, minInt = 1, maxInt = 50)
        public static int spreadRadiusMiddle;

        @ConfigurationProperty(comment = "Spread radius for stem base and sprout (bottom block)", defaultInt = 5, minInt = 1, maxInt = 50)
        public static int spreadRadiusBottom;

        @ConfigurationProperty(comment = "Try to spread after growth (blooming start or +1 block to plant height)", defaultBoolean = true)
        public static boolean canSpreadAfterGrowth;

        @ConfigurationProperty(comment = "You can disable plant growth for debugging or something", defaultBoolean = true)
        public static boolean canGrowth;

        @ConfigurationProperty(comment = "Max plant height", defaultInt = 4, minInt = 1, maxInt = 256)
        public static int maxGrowthHeight;
    }
}
