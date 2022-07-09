package com.github.mjaroslav.heracleum.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModInfo {
	public static final String MODID = "heracleum";
	public static final String NAME = "Heracleum";
	public static final String VERSION = "@VERSION@";
	public static final String GUIFACTORY = "com.github.mjaroslav.heracleum.client.gui.ModGuiFactory";
	public static final String CLIENTPROXY = "com.github.mjaroslav.heracleum.client.ClientProxy";
	public static final String COMMONPROXY = "com.github.mjaroslav.heracleum.common.CommonProxy";
	public static final Logger LOG = LogManager.getLogger(NAME);
}
