package mjaroslav.mcmods.heracleum;

import static mjaroslav.mcmods.heracleum.lib.ModInfo.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.lib.module.ConfigurationHandler;
import mjaroslav.mcmods.mjutils.lib.module.ModuleHandler;
import mjaroslav.mcmods.mjutils.lib.module.ProxyBase;

@Mod(modid = MODID, name = NAME, version = VERSION, guiFactory = GUIFACTORY)
public class ModHeracleum {
    @Instance
    public static ModHeracleum instance;

    @SidedProxy(clientSide = CLIENTPROXY, serverSide = COMMONPROXY)
    public static ProxyBase proxy;

    public static ModuleHandler modules;

    public static ConfigurationHandler config = new ConfigurationHandler(MODID, LOG);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modules.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        modules.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        modules.postInit(event);
    }

    @EventHandler
    public void constr(FMLConstructionEvent event) {
        modules = new ModuleHandler(MODID, config, proxy);
        modules.findModules(event);
    }
}
