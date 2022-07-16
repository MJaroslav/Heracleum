package com.github.mjaroslav.heracleum.common.item;

import com.github.mjaroslav.heracleum.common.block.BlockHeracleum;
import com.github.mjaroslav.heracleum.common.init.ModBlocks;
import lombok.val;
import lombok.var;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

// TODO: Add bloom bit and use it for placing
public class ItemHeracleumStem extends ModItemReed {
    public static final int META_DRY = 0b1;

    public IIcon iconDry;

    public ItemHeracleumStem() {
        super("heracleum_stem", ModBlocks.heracleum);
        setHasSubtypes(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(@NotNull Item item, @UnknownNullability CreativeTabs tab, @NotNull List list) {
        list.add(new ItemStack(this, 1));
        list.add(new ItemStack(this, 1, META_DRY));
    }

    @Override
    public void registerIcons(@NotNull IIconRegister register) {
        super.registerIcons(register);
        iconDry = register.registerIcon(getIconString() + "_dry");
    }

    @Override
    public IIcon getIconFromDamage(@Range(from = 0, to = Short.MAX_VALUE) int damage) {
        return isDryFromMeta(damage) ? iconDry : super.getIconFromDamage(damage);
    }

    @Override
    public String getUnlocalizedName(@NotNull ItemStack stack) {
        var result = getUnlocalizedName();
        if (isDryFromMeta(stack.getItemDamage()))
            result += ".dry";
        return result;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        val block = world.getBlock(x, y, z);

        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
            side = 1;
        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
            if (side == 0)
                --y;

            if (side == 1)
                ++y;

            if (side == 2)
                --z;

            if (side == 3)
                ++z;

            if (side == 4)
                --x;

            if (side == 5)
                ++x;
        }

        if (!player.canPlayerEdit(x, y, z, side, stack))
            return false;
        else if (stack.stackSize == 0)
            return false;
        else if (world.canPlaceEntityOnSide(ModBlocks.heracleum, x, y, z, false, side, null, stack)) {
            val placePart = world.getBlock(x, y - 1, z) == ModBlocks.heracleum
                    ? BlockHeracleum.META_PART_MIDDLE : BlockHeracleum.META_PART_BOTTOM;
            val dry = isDryFromMeta(stack.getItemDamage()) ? BlockHeracleum.META_BIT_DRY : 0;
            val i1 = ModBlocks.heracleum.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, placePart | dry);

            if (world.setBlock(x, y, z, ModBlocks.heracleum, i1, 3)) {
                if (world.getBlock(x, y, z) == ModBlocks.heracleum) {
                    ModBlocks.heracleum.onBlockPlacedBy(world, x, y, z, player, stack);
                    ModBlocks.heracleum.onPostBlockPlaced(world, x, y, z, i1);
                }

                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F,
                        ModBlocks.heracleum.stepSound.func_150496_b(),
                        (ModBlocks.heracleum.stepSound.getVolume() + 1.0F) / 2.0F,
                        ModBlocks.heracleum.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }
        }

        return true;
    }

    public static boolean isDryFromMeta(@Range(from = 0, to = Short.MAX_VALUE) int meta) {
        return (meta & META_DRY) == META_DRY;
    }
}
