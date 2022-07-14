package com.github.mjaroslav.heracleum.common.util;

import com.github.mjaroslav.heracleum.common.init.ModItems;
import com.github.mjaroslav.heracleum.lib.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModCreativeTab extends CreativeTabs {
    public ModCreativeTab() {
        super(ModInfo.MOD_ID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getTabIconItem() {
        return ModItems.heracleumUmbel;
    }
}
