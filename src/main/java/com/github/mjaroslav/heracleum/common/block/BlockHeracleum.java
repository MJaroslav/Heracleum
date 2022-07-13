package com.github.mjaroslav.heracleum.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;

import com.github.mjaroslav.heracleum.common.init.ModBlocks;
import com.github.mjaroslav.heracleum.common.item.ItemBlockWithMetadata;
import com.github.mjaroslav.heracleum.common.tileentity.TileEntityHeracleum;
import com.google.common.collect.Lists;

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
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHeracleum extends ModBlockContainer<TileEntityHeracleum> implements IPlantable, IShearable {
    public BlockHeracleum() {
        super("heracleum", Material.plants, TileEntityHeracleum.class, ItemBlockWithMetadata.class);
        // TODO: Add ItemBlock icon or model
        setBlockTextureName("minecraft:tallgrass");
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabs.tabDecorations);
        setTickRandomly(true);
    }

    @Override
    public void addCollisionBoxesToList(@NotNull World world, int x, int y, int z, @NotNull AxisAlignedBB bb, @NotNull List list, @NotNull Entity entity) {
        // TODO: rewrite this, it's not work properly 
        val meta = world.getBlockMetadata(x, y, z);
        if (meta > 0 && meta < 4)
            setBlockBounds(0.5F - 1F / 16F, 0F, 0.5F - 1F / 16F, 0.5F + 1F / 16F, meta == 3 ? 0.5F : 1F, 0.5F + 1F / 16F);
        else if (meta == 0)
            setBlockBounds(0.5F - 0.5F / 16F, 0F, 0.5F - 0.5F / 16F, 0.5F + 0.5F / 16F, 0.5F, 0.5F + 0.5F / 16F);
        else
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
    public TileEntityHeracleum createNewTileEntity(@NotNull World world, @Range(from = 0, to = 15) int metadata) {
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

    @Override
    public EnumPlantType getPlantType(@NotNull IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public Block getPlant(@NotNull IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(@NotNull IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean canPlaceBlockAt(@NotNull World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(@NotNull World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        checkAndDropBlock(world, x, y, z);
    }

    @Override
    public void updateTick(@NotNull World world, int x, int y, int z, @NotNull Random rand) {
        checkAndDropBlock(world, x, y, z);
    }

    @Override
    public boolean canBlockStay(@NotNull World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z).canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    protected void checkAndDropBlock(@NotNull World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z)) {

            // TODO: rewrite this, it's not work properly 
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);

            world.setBlock(x, y, z, getBlockById(0), 0, 2);
        }
    }

    @Override
    public boolean canSustainPlant(@NotNull IBlockAccess world, int x, int y, int z, @NotNull ForgeDirection direction, @NotNull IPlantable plantable) {
        // TODO: rewrite this, it's not work properly 
        val plant = plantable.getPlant(world, x, y + 1, z);
        if (plant == ModBlocks.heracleum && this == ModBlocks.heracleum) {
            val meta = world.getBlockMetadata(x, y, z);
            val plantMeta = plantable.getPlantMetadata(world, x, y + 1, z);
            return (meta == 1 || meta == 2) && (plantMeta == 2 || plantMeta == 3);
        }
        return super.canSustainPlant(world, x, y, z, direction, plantable);
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, @NotNull IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public @NotNull ArrayList<ItemStack> onSheared(@NotNull ItemStack item, @NotNull IBlockAccess world, int x, int y, int z, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, world.getBlockMetadata(x, z, z)));
    }
}
