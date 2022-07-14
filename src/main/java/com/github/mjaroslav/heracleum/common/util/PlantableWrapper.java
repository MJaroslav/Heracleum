package com.github.mjaroslav.heracleum.common.util;

import org.jetbrains.annotations.NotNull;

import lombok.RequiredArgsConstructor;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

@RequiredArgsConstructor
public class PlantableWrapper implements IPlantable {
    private final @NotNull EnumPlantType type;
    private final Block block;
    private final int meta;

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return type;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return block;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return meta;
    }

}
