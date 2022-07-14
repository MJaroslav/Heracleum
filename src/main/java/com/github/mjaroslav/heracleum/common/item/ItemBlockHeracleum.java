package com.github.mjaroslav.heracleum.common.item;

import org.jetbrains.annotations.NotNull;

import com.github.mjaroslav.heracleum.common.util.PlantableWrapper;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBlockHeracleum extends ItemBlockWithMetadata {
    public ItemBlockHeracleum(@NotNull Block block) {
        super(block);
    }

    @Override
    public boolean onItemUse(@NotNull ItemStack stack, @NotNull EntityPlayer player, @NotNull World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (side != 1)
            return false;
        if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
            if (world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, new PlantableWrapper(EnumPlantType.Plains, field_150939_a, stack.getItemDamage())) && world.isAirBlock(x, y + 1, z)) {
                world.setBlock(x, y + 1, z, field_150939_a);
                --stack.stackSize;
                return true;
            } else
                return false;
        } else
            return false;
    }
}
