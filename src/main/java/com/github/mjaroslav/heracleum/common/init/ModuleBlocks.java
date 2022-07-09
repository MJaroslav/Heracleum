package com.github.mjaroslav.heracleum.common.init;

import com.github.mjaroslav.heracleum.common.block.BlockHeracleum;
import com.github.mjaroslav.heracleum.common.block.ItemBlockHeracleum;
import com.github.mjaroslav.heracleum.common.tileentity.TileEntityHeracleum;
import com.github.mjaroslav.heracleum.lib.ModInfo;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import net.minecraft.block.Block;

@Module(ModInfo.MODID)
public class ModuleBlocks implements Modular {
    public static int renderHeracleumID = RenderingRegistry.getNextAvailableRenderId();

    public static Block heracleum;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        heracleum = new BlockHeracleum();
        GameRegistry.registerBlock(heracleum, ItemBlockHeracleum.class, "heracleum");
        GameRegistry.registerTileEntity(TileEntityHeracleum.class, "heracleum");
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
}
