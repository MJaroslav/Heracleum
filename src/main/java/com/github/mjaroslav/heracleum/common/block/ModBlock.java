package com.github.mjaroslav.heracleum.common.block;

import com.github.mjaroslav.heracleum.HeracleumMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;

public class ModBlock extends Block {
    protected final @NotNull String name;

    public ModBlock(@NotNull String name, @NotNull Material material,
                    @Nullable Class<? extends ItemBlock> itemBlockClass, @Nullable Object... itemCtorArgs) {
        super(material);
        this.name = name;
        setBlockTextureName(prefix(name));
        setCreativeTab(HeracleumMod.TAB);
        setBlockName(prefix(name));
        registerBlock(name, itemBlockClass, itemCtorArgs);
    }

    public ModBlock(@NotNull String name, @NotNull Material material) {
        this(name, material, null);
    }

    protected void registerBlock(@NotNull String name, @Nullable Class<? extends ItemBlock> itemBlockClass, @Nullable Object... itemCtorArgs) {
        GameRegistry.registerBlock(this, itemBlockClass == null ? ItemBlock.class : itemBlockClass, name,
                itemCtorArgs);
    }
}
