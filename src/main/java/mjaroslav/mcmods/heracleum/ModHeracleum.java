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
import mjaroslav.mcmods.mjutils.module.AnnotationBasedConfiguration;
import mjaroslav.mcmods.mjutils.module.ModuleSystem;
import mjaroslav.mcmods.mjutils.module.Proxy;

@Mod(modid = MODID, name = NAME, version = VERSION, guiFactory = GUIFACTORY)
public class ModHeracleum {
    @Instance
    public static ModHeracleum instance;

    @SidedProxy(clientSide = CLIENTPROXY, serverSide = COMMONPROXY)
    public static Proxy proxy;

    public static ModuleSystem initHandler;

    public static AnnotationBasedConfiguration config = new AnnotationBasedConfiguration(MODID, LOG);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        initHandler.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        initHandler.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        initHandler.postInit(event);
    }

    @EventHandler
    public void constr(FMLConstructionEvent event) {
        initHandler = new ModuleSystem(MODID, config, proxy);
        initHandler.initSystem(event);
    }
}
