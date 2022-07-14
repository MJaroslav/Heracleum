package com.github.mjaroslav.heracleum.common.init;

import com.github.mjaroslav.heracleum.common.item.ItemHeracleumUmbel;
import com.github.mjaroslav.heracleum.lib.ModInfo;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import net.minecraft.item.Item;

@Module(ModInfo.MOD_ID)
public class ModItems implements Modular {
    public static Item heracleumUmbel;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        heracleumUmbel = new ItemHeracleumUmbel();
    }
}
