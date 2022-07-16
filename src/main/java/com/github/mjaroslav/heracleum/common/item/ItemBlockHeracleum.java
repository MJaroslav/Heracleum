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
        val part = getPartFromMeta(stack.getItemDamage());
        val dry = isDryFromMeta(stack.getItemDamage());
        switch (part) {
            case META_SPROUT:
                result += ".sprout";
                break;
            case META_MIDDLE:
            case META_BOTTOM:
                result += ".stem";
                break;
            case META_TOP:
                result += ".umbels";
                break;
        }
        if (dry)
            result += ".dry";
        return result;
    }
}
