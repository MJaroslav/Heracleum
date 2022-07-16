package com.github.mjaroslav.heracleum.common.item;

import com.github.mjaroslav.heracleum.HeracleumMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemReed;
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;

public class ModItemReed extends ItemReed {
    protected final @NotNull String name;

    public ModItemReed(@NotNull String name, @NotNull Block block) {
        super(block);
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
