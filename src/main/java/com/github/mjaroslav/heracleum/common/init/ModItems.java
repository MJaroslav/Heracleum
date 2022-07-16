package com.github.mjaroslav.heracleum.common.init;

import com.github.mjaroslav.heracleum.common.item.ItemHeracleumStem;
import com.github.mjaroslav.heracleum.common.item.ItemHeracleumUmbel;
import com.github.mjaroslav.heracleum.lib.ModInfo;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import net.minecraft.item.Item;

@Module(ModInfo.MOD_ID)
public class ModItems implements Modular {
    public static Item heracleumUmbel;
    public static Item heracleumStem;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        heracleumUmbel = new ItemHeracleumUmbel();
        heracleumStem = new ItemHeracleumStem();
    }

    @Override
    public int priority() {
        return 1;
    }
}
