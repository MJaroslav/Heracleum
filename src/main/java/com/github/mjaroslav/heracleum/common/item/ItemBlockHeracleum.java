package com.github.mjaroslav.heracleum.common.item;

import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.common.block.BlockHeracleum.*;

public class ItemBlockHeracleum extends ItemBlockWithMetadata {
    public ItemBlockHeracleum(@NotNull Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(@NotNull ItemStack stack) {
        val original = getUnlocalizedName();
        val part = getPartFromMeta(stack.getItemDamage());
        switch (part) {
            case META_SPROUT:
                return original + ".sprout";
            case META_MIDDLE:
            case META_BOTTOM:
                return original + ".stem";
            case META_TOP:
                return original + ".umbels";
            default:
                return original;
        }
    }
}
