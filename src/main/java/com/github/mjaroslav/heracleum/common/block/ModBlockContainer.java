package com.github.mjaroslav.heracleum.common.block;

import com.github.mjaroslav.heracleum.HeracleumMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;

public abstract class ModBlockContainer<T extends TileEntity> extends BlockContainer {
    protected final @NotNull String name;
    protected final @NotNull Class<T> tileEntityClass;

    public ModBlockContainer(@NotNull String name, @NotNull Material material, @NotNull Class<T> tileEntityClass,
                             @Nullable Class<? extends ItemBlock> itemBlockClass,
                             @NotNull Object... itemCtorArgs) {
        super(material);
        this.name = name;
        this.tileEntityClass = tileEntityClass;
        setBlockTextureName(prefix(name));
        setBlockName(prefix(name));
        setCreativeTab(HeracleumMod.TAB);
        registerBlock(name, itemBlockClass, itemCtorArgs);
        registerTile(name);
    }

    public ModBlockContainer(@NotNull String name, @NotNull Material material, @NotNull Class<T> tileEntityClass) {
        this(name, material, tileEntityClass, null);
    }

    @Override
    public abstract @Nullable T createNewTileEntity(@NotNull World world, @Range(from = 0, to = 15) int metadata);

    protected void registerBlock(@NotNull String name, @Nullable Class<? extends ItemBlock> itemBlockClass,
                                 @NotNull Object... itemCtorArgs) {
        GameRegistry.registerBlock(this, itemBlockClass == null ? ItemBlock.class : itemBlockClass, name,
                itemCtorArgs);
    }

    protected void registerTile(@NotNull String name) {
        GameRegistry.registerTileEntity(tileEntityClass, prefix(name));
    }
}
