package com.github.mjaroslav.heracleum.common.item;

import com.github.mjaroslav.heracleum.HeracleumMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;

public class ModItem extends Item {
    protected final @NotNull String name;

    public ModItem(@NotNull String name) {
        this.name = name;
        setUnlocalizedName(prefix(name));
        setTextureName(prefix(name));
        setCreativeTab(HeracleumMod.TAB);
        registerItem(name);
    }

    protected void registerItem(@NotNull String name) {
        GameRegistry.registerItem(this, name);
    }
}
