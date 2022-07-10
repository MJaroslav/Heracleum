package com.github.mjaroslav.heracleum;

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
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.lib.ModInfo.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, guiFactory = GUI_FACTORY)
public final class HeracleumMod {
    @Instance
    public static HeracleumMod instance;
    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static Proxy proxy;
    public static ModuleSystem initHandler;
    public static AnnotationBasedConfiguration config;

    @EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        initHandler.preInit(event);
    }

    @EventHandler
    public void init(@NotNull FMLInitializationEvent event) {
        initHandler.init(event);
    }

    @EventHandler
    public void postInit(@NotNull FMLPostInitializationEvent event) {
        initHandler.postInit(event);
    }

    @EventHandler
    public void constr(@NotNull FMLConstructionEvent event) {
        config = new AnnotationBasedConfiguration(MOD_ID, LOG);
        initHandler = new ModuleSystem(MOD_ID, config, proxy);
        initHandler.initSystem(event);
    }
}
