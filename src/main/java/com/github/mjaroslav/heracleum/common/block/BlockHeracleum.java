package com.github.mjaroslav.heracleum.common.block;

import com.github.mjaroslav.heracleum.common.item.ItemBlockWithMetadata;
import com.github.mjaroslav.heracleum.common.tileentity.TileEntityHeracleum;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.val;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public class BlockHeracleum extends ModBlockContainer<TileEntityHeracleum> {
    public BlockHeracleum() {
        super("heracleum", Material.plants, TileEntityHeracleum.class, ItemBlockWithMetadata.class);
        setBlockTextureName("minecraft:tallgrass");
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public void addCollisionBoxesToList(@NotNull World world, int x, int y, int z, @NotNull AxisAlignedBB bb,
                                        @NotNull List list, @NotNull Entity entity) {
        // TODO: WFT?
        val meta = world.getBlockMetadata(x, y, z);
        if (meta > 0) {
            if (meta != 3)
                setBlockBounds(0.4375F, 0, 0.4375F, 0.5625F, 1, 0.5625F);
        } else
            setBlockBounds(0.46875F, 0, 0.46875F, 0.53125F, 0.5F, 0.53125F);
        if (meta != 3)
            super.addCollisionBoxesToList(world, x, y, z, bb, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(@NotNull IBlockAccess world, int x, int y, int z) {
        setBlockBounds(0.2F, 0, 0.2F, 0.8F, world.getBlockMetadata(x, y, z) > 0 ? 1F : 0.5F, 0.8F);
    }

    @Override
    public @Range(from = 0, to = Short.MAX_VALUE) int getDamageValue(@NotNull World world, int x, int y, int z) {
        val meta = world.getBlockMetadata(x, y, z);
        return meta > 3 ? 0 : meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(@NotNull IBlockAccess world, int x, int y, int z) {
        return world.getBiomeGenForCoords(x, z).getBiomeGrassColor(x, y, z);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(@NotNull Item item, @UnknownNullability CreativeTabs tab, @NotNull List list) {
        for (var i = 0; i < 4; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntityHeracleum createNewTileEntity(@NotNull World world,
                                                   @Range(from = 0, to = 15) int metadata) {
        return new TileEntityHeracleum();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(@Range(from = 0, to = 16) int meta) {
        return getBlockColor();
    }
}
