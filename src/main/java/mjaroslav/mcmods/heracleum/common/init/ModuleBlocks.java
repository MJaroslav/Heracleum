package mjaroslav.mcmods.heracleum.common.init;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.heracleum.common.block.BlockHeracleum;
import mjaroslav.mcmods.heracleum.lib.ModInfo;
import mjaroslav.mcmods.mjutils.lib.module.IModule;
import mjaroslav.mcmods.mjutils.lib.module.ModModule;
import net.minecraft.block.Block;

@ModModule(modid = ModInfo.MODID)
public class ModuleBlocks implements IModule {
    public static int renderHeracleumID = RenderingRegistry.getNextAvailableRenderId();
    
    public static Block heracleum;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        heracleum = new BlockHeracleum();
        GameRegistry.registerBlock(heracleum, "heracleum");
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public boolean canLoad() {
        return true;
    }

    @Override
    public String getModuleName() {
        return "Blocks";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String[] modDependencies() {
        return null;
    }
}
