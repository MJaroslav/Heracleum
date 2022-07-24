package com.github.mjaroslav.heracleum.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class ModInfo {
    public static final String MOD_ID = "heracleum";
    public static final String NAME = "Heracleum";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:mjutils@[1.6,);";
    public static final String GUI_FACTORY = "com.github.mjaroslav.heracleum.client.gui.ModGuiFactory";
    public static final String CLIENT_PROXY = "com.github.mjaroslav.heracleum.client.ClientProxy";
    public static final String COMMON_PROXY = "com.github.mjaroslav.heracleum.common.CommonProxy";
    public static final Logger LOG = LogManager.getLogger(NAME);

    @NotNull
    public static String prefix(@NotNull String str) {
        return MOD_ID + ":" + str;
    }
}
