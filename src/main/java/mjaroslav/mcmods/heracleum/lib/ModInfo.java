package mjaroslav.mcmods.heracleum.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModInfo {
	public static final String MODID = "heracleum";
	public static final String NAME = "Heracleum";
	public static final String VERSION = "1.0.0";
	public static final String GUIFACTORY = "mjaroslav.mcmods.heracleum.client.gui.ModGuiFactory";
	public static final String CLIENTPROXY = "mjaroslav.mcmods.heracleum.client.ClientProxy";
	public static final String COMMONPROXY = "mjaroslav.mcmods.heracleum.common.CommonProxy";
	public static final Logger LOG = LogManager.getLogger(NAME);
}
