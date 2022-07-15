package com.github.mjaroslav.heracleum.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class ItemBlockWithMetadata extends ItemBlock {
    protected boolean splitNames;

    public ItemBlockWithMetadata(@NotNull Block block, boolean splitNames) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.splitNames = splitNames;
    }

    public ItemBlockWithMetadata(@NotNull Block block) {
        this(block, false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(@Range(from = 0, to = Short.MAX_VALUE - 1) int damage) {
        return field_150939_a.getIcon(2, damage);
    }

    @Override
    public int getMetadata(@Range(from = 0, to = Short.MAX_VALUE - 1) int damage) {
        return Math.min(damage, 15);
    }

    @Override
    public String getUnlocalizedName(@NotNull ItemStack stack) {
        val original = super.getUnlocalizedName(stack);
        return splitNames ? original + "." + stack.getItemDamage() : original;
    }
}
