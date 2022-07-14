package com.github.mjaroslav.heracleum.common.block;

import com.github.mjaroslav.heracleum.common.init.ModBlocks;
import com.github.mjaroslav.heracleum.common.tileentity.TileEntityHeracleum;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;

public class BlockHeracleum extends ModBlockContainer<TileEntityHeracleum> implements IPlantable, IShearable {
    private static boolean tileRegistered = false;

    @NotNull
    public final Part part;

    public BlockHeracleum(@NotNull Part part) {
        super("heracleum_" + part.name().toLowerCase(), Material.plants, TileEntityHeracleum.class);
        if (part == Part.UNKNOWN)
            throw new IllegalArgumentException("UNKNOWN is preserved value for internal logic");
        this.part = part;
        // TODO: Add ItemBlock icon or model
        setBlockTextureName("minecraft:tallgrass");
        setBlockName(prefix("heracleum"));
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabs.tabDecorations);
        setTickRandomly(true);
    }

    @Override
    protected void registerTile(@NotNull String name) {
        if (!tileRegistered) {
            super.registerTile("heracleum");
            tileRegistered = true;
        }
    }

    @Override
    public void addCollisionBoxesToList(@NotNull World world, int x, int y, int z, @NotNull AxisAlignedBB bb, @NotNull List list, @UnknownNullability Entity entity) {
        if (part != Part.SPROUT)
            setBlockBounds(0.5F - 1F / 16F, 0F, 0.5F - 1F / 16F, 0.5F + 1F / 16F, part == Part.TOP ? 0F : 1F, 0.5F + 1F / 16F);
        else
            setBlockBounds(0.5F - 0.5F / 16F, 0F, 0.5F - 0.5F / 16F, 0.5F + 0.5F / 16F, 0.5F, 0.5F + 0.5F / 16F);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(@NotNull IBlockAccess world, int x, int y, int z) {
        setBlockBounds(0.2F, 0, 0.2F, 0.8F, part != Part.SPROUT ? 1F : 0.5F, 0.8F);
    }

    @Override
    public @Range(from = 0, to = Short.MAX_VALUE) int getDamageValue(@NotNull World world, int x, int y, int z) {
        return part.stackDamage;
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
        return 0;
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
        val block = world.getBlock(x, y - 1, z);
        return block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    protected void checkAndDropBlock(@NotNull World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z)) {
            // TODO: rewrite this, it's not work properly 
            dropBlockAsItem(world, x, y, z, part.stackDamage, 0);

            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean canSustainPlant(@NotNull IBlockAccess world, int x, int y, int z, @NotNull ForgeDirection direction, @NotNull IPlantable plantable) {
        val plant = plantable.getPlant(world, x, y + 1, z);
        if (plant == ModBlocks.heracleumTop)
            return this == ModBlocks.heracleumMiddle || this == ModBlocks.heracleumBottom;
        if (plant == ModBlocks.heracleumMiddle)
            return this == ModBlocks.heracleumBottom;
        return (plant == ModBlocks.heracleumBottom || plant == ModBlocks.heracleumSprout) &&
                super.canSustainPlant(world, x, y, z, direction, plantable);
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, @NotNull IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public @NotNull ArrayList<ItemStack> onSheared(@NotNull ItemStack item, @NotNull IBlockAccess world, int x, int y, int z, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, part.stackDamage));
    }

    // Костыли, спасибо mc/forge
    @RequiredArgsConstructor
    public static enum Part {
        UNKNOWN(-1), SPROUT(0), BOTTOM(1), MIDDLE(2), TOP(3);

        private final int stackDamage;
    }
}
