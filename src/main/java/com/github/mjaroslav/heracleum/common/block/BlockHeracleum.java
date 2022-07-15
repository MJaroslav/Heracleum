package com.github.mjaroslav.heracleum.common.block;

import com.github.mjaroslav.heracleum.common.init.ModItems;
import com.github.mjaroslav.heracleum.common.item.ItemBlockHeracleum;
import com.github.mjaroslav.heracleum.common.tileentity.TileEntityHeracleum;
import com.github.mjaroslav.heracleum.lib.ModInfo;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.common.util.ForgeDirection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.minecraftforge.common.EnumPlantType.Plains;

public class BlockHeracleum extends ModBlockContainer<TileEntityHeracleum> implements IPlantable, IShearable {
    public static final EnumPlantType typeHeracleum = EnumHelper.addEnum(EnumPlantType.class, "Heracleum",
            new Class<?>[0], new Object[0]);

    public static final int META_SPROUT = 0b00;
    public static final int META_BOTTOM = 0b01;
    public static final int META_MIDDLE = 0b10;
    public static final int META_TOP = 0b11;

    public final ThreadLocal<Boolean> harvestedByShearsItem = ThreadLocal.withInitial(() -> false);
    private final ThreadLocal<Integer> blockMeta = ThreadLocal.withInitial(() -> 0);

    public BlockHeracleum() {
        super("heracleum", Material.plants, TileEntityHeracleum.class, ItemBlockHeracleum.class);
        // TODO: Add ItemBlock icon or model
        setBlockTextureName("minecraft:tallgrass");
        setStepSound(Block.soundTypeGrass);
        setTickRandomly(true);
    }

    @Override
    public void addCollisionBoxesToList(@NotNull World world, int x, int y, int z, @NotNull AxisAlignedBB bb,
                                        @NotNull List list, @UnknownNullability Entity entity) {
        val part = getPartFromMeta(world.getBlockMetadata(x, y, z));
        if (part != META_SPROUT)
            setBlockBounds(0.5F - 1F / 16F, 0F, 0.5F - 1F / 16F, 0.5F + 1F / 16F, part == META_TOP ? 0F : 1F, 0.5F + 1F / 16F);
        else
            setBlockBounds(0.5F - 0.5F / 16F, 0F, 0.5F - 0.5F / 16F, 0.5F + 0.5F / 16F, 0.5F, 0.5F + 0.5F / 16F);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(@NotNull IBlockAccess world, int x, int y, int z) {
        val part = getPartFromMeta(world.getBlockMetadata(x, y, z));
        setBlockBounds(0.2F, 0, 0.2F, 0.8F, part != META_SPROUT ? 1F : 0.5F, 0.8F);
    }

    @Override
    public @Range(from = 0, to = Short.MAX_VALUE) int getDamageValue(@NotNull World world, int x, int y, int z) {
        return getPartFromMeta(world.getBlockMetadata(x, y, z));
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
    public int getRenderColor(@Range(from = 0, to = 15) int meta) {
        return getBlockColor();
    }

    @Override
    public EnumPlantType getPlantType(@NotNull IBlockAccess world, int x, int y, int z) {
        val part = getPartFromMeta(getPlantMetadata(world, x, y, z));
        return (part == META_SPROUT || part == META_BOTTOM) ? Plains : typeHeracleum;
    }

    @Override
    public boolean canReplace(World world, int x, int y, int z, int side, ItemStack stack) {
        ModInfo.LOG.info("canReplace");
        blockMeta.set(stack.getItemDamage());
        return super.canReplace(world, x, y, z, side, stack);
    }

    @Override
    public Block getPlant(@NotNull IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(@NotNull IBlockAccess world, int x, int y, int z) {
        // TODO: Get true fucking meta
        if (world.isAirBlock(x, y, z))
            ModInfo.LOG.info("getPlantMetadata");
        return world.isAirBlock(x, y, z) ? blockMeta.get() : world.getBlockMetadata(x, y, z);
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
        if (checkAndDropBlock(world, x, y, z))
            return;

    }

    @Override
    public boolean canBlockStay(@NotNull World world, int x, int y, int z) {
        val block = world.getBlock(x, y - 1, z);
        return block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    @Override
    public ArrayList<ItemStack> getDrops(@NotNull World world, int x, int y, int z,
                                         @Range(from = 0, to = 15) int metadata, int fortune) {
        val result = super.getDrops(world, x, y, z, metadata, fortune);
        harvestedByShearsItem.set(false);
        return result;
    }

    @Override
    public Item getItemDropped(@Range(from = 0, to = 15) int meta, @NotNull Random rand, int fortune) {
        val part = getPartFromMeta(meta);
        return !harvestedByShearsItem.get() && (part == META_TOP || part == META_MIDDLE) ? ModItems.heracleumUmbel : null;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        val part = getPartFromMeta(meta);
        return part == META_TOP ? 1 + random.nextInt(6) + random.nextInt(fortune + 1) :
                part == META_MIDDLE ? 1 + random.nextInt(3) + random.nextInt(fortune + 1) :
                        super.quantityDropped(meta, fortune, random);
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    protected boolean checkAndDropBlock(@NotNull World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, getPartFromMeta(world.getBlockMetadata(x, y, z)), 0);
            world.setBlockToAir(x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public boolean canSustainPlant(@NotNull IBlockAccess world, int x, int y, int z, @NotNull ForgeDirection direction, @NotNull IPlantable plantable) {
        val plant = plantable.getPlant(world, x, y + 1, z);
        if (plant instanceof BlockHeracleum) {
            val part = getPartFromMeta(world.getBlockMetadata(x, y, z));
            val heracleumPart = getPartFromMeta(plantable.getPlantMetadata(world, x, y + 1, z));
            if (heracleumPart == META_TOP || heracleumPart == META_MIDDLE)
                return part == META_MIDDLE || part == META_BOTTOM;
            return (heracleumPart == META_BOTTOM || heracleumPart == META_SPROUT) &&
                    super.canSustainPlant(world, x, y, z, direction, plantable);
        }
        return false;
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, @NotNull IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public @NotNull ArrayList<ItemStack> onSheared(@NotNull ItemStack item, @NotNull IBlockAccess world, int x, int y, int z, int fortune) {
        harvestedByShearsItem.set(true);
        return Lists.newArrayList(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
    }

    public static int getPartFromMeta(int meta) {
        return meta & 3;
    }
}
