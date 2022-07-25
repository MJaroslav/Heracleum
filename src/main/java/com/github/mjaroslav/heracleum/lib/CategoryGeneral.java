package com.github.mjaroslav.heracleum.lib;

import lombok.val;
import mjaroslav.mcmods.mjutils.module.ConfigurationCategory;
import mjaroslav.mcmods.mjutils.module.ConfigurationProperty;

@ConfigurationCategory(modID = ModInfo.MOD_ID, name = ConfigurationCategory.GENERAL_NAME,
        comment = ConfigurationCategory.GENERAL_COMMENT)
public class CategoryGeneral {
    @ConfigurationCategory(name = "client", comment = "Cosmetic and clint side options.")
    public static class CategoryClient {
        @ConfigurationProperty(comment = "Color of dry heracleum plant blocks in '#HEX' or 'DECIMAL' format",
                defaultString = "#B5B54A")
        public static String dryHeracleumColor;

        public static int parseDryHeracleumColor() {
            try {
                val hex = dryHeracleumColor.startsWith("#");
                return Integer.parseInt(hex ? dryHeracleumColor.substring(1) : dryHeracleumColor, hex ? 16 : 10);
            } catch (Exception ignored) {
                return 0xB5B54A;
            }
        }
    }

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

        @ConfigurationProperty(comment = "Chance in percent for plant growth", defaultInt = 30, minInt = 1, maxInt = 100)
        public static int growthChance;

        @ConfigurationProperty(comment = "Chance in percent for plant spread", defaultInt = 10, minInt = 1, maxInt = 100)
        public static int spreadChance;
    }
}
