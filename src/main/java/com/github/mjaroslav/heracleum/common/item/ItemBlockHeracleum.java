package com.github.mjaroslav.heracleum.common.item;

import lombok.val;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.common.block.BlockHeracleum.*;

public class ItemBlockHeracleum extends ItemBlockWithMetadata {
    public ItemBlockHeracleum(@NotNull Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(@NotNull ItemStack stack) {
        var result = getUnlocalizedName();
        val damage = stack.getItemDamage();
        val part = getPartFromMeta(damage);
        val dry = isDryFromMeta(damage);
        var blooming = isBloomingFromMeta(damage);
        switch (part) {
            case META_PART_SPROUT:
                result += ".sprout";
                break;
            case META_PART_MIDDLE:
            case META_PART_BOTTOM:
                result += ".stem";
                break;
            case META_PART_TOP:
                result += ".umbels";
                break;
        }
        if (dry)
            result += ".dry";
        if (blooming)
            result += ".blooming";
        return result;
    }
}
