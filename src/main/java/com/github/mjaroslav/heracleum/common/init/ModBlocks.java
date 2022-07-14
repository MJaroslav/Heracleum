package com.github.mjaroslav.heracleum.common.init;

import com.github.mjaroslav.heracleum.common.block.BlockHeracleum;
import com.github.mjaroslav.heracleum.common.block.BlockHeracleum.Part;
import com.github.mjaroslav.heracleum.lib.ModInfo;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;

@Module(ModInfo.MOD_ID)
public final class ModBlocks implements Modular {
    public static BlockHeracleum heracleumSprout;
    public static BlockHeracleum heracleumBottom;
    public static BlockHeracleum heracleumMiddle;
    public static BlockHeracleum heracleumTop;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        heracleumSprout = new BlockHeracleum(Part.SPROUT);
        heracleumBottom = new BlockHeracleum(Part.BOTTOM);
        heracleumMiddle = new BlockHeracleum(Part.MIDDLE);
        heracleumTop = new BlockHeracleum(Part.TOP);
    }
}
