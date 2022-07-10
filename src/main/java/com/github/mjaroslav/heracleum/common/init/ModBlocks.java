package com.github.mjaroslav.heracleum.common.init;

import com.github.mjaroslav.heracleum.common.block.BlockHeracleum;
import com.github.mjaroslav.heracleum.lib.ModInfo;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import net.minecraft.block.Block;

@Module(ModInfo.MOD_ID)
public final class ModBlocks implements Modular {
    public static Block heracleum;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        heracleum = new BlockHeracleum();
    }

    @Override
    public boolean canLoad() {
        return true;
    }
}
