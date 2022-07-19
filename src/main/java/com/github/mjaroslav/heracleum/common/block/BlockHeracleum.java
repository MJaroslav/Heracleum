package com.github.mjaroslav.heracleum.common.block;

import com.github.mjaroslav.heracleum.common.init.ModItems;
import com.github.mjaroslav.heracleum.common.item.ItemBlockHeracleum;
import com.github.mjaroslav.heracleum.common.item.ItemHeracleumStem;
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


    public static final int COLOR_DRY = 0xB5B54A;

    public static final int META_PART_SPROUT = 0b0000;
    public static final int META_PART_BOTTOM = 0b0001;
    public static final int META_PART_MIDDLE = 0b0010;
    public static final int META_PART_TOP = 0b0011;
    public static final int META_BIT_DRY = 0b0100;
    public static final int META_BIT_BLOOM = 0b1000;

    private final ThreadLocal<Boolean> harvestedByShearsItem = ThreadLocal.withInitial(() -> false);
    private final ThreadLocal<Integer> blockMeta = ThreadLocal.withInitial(() -> 0);

    public BlockHeracleum() {
        super("heracleum", Material.plants, TileEntityHeracleum.class, ItemBlockHeracleum.class);
        // TODO: Add ItemBlock icon or model
        setBlockTextureName("minecraft:tallgrass");
        setStepSound(Block.soundTypeGrass);
        setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(@NotNull Item item, @UnknownNullability CreativeTabs tab, @NotNull List list) {
        for (var i = 0; i < 16; i++)
            list.add(new ItemStack(this, 1, i));
    }

    @Override
    public void addCollisionBoxesToList(@NotNull World world, int x, int y, int z, @NotNull AxisAlignedBB bb,
                                        @NotNull List list, @UnknownNullability Entity entity) {
        val part = getPartFromMeta(world.getBlockMetadata(x, y, z));
        if (part != META_PART_SPROUT)
            setBlockBounds(0.5F - 1F / 16F, 0F, 0.5F - 1F / 16F, 0.5F + 1F / 16F, part == META_PART_TOP ? 0F : 1F, 0.5F + 1F / 16F);
        else
            setBlockBounds(0.5F - 0.5F / 16F, 0F, 0.5F - 0.5F / 16F, 0.5F + 0.5F / 16F, 0.5F, 0.5F + 0.5F / 16F);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(@NotNull IBlockAccess world, int x, int y, int z) {
        val part = getPartFromMeta(world.getBlockMetadata(x, y, z));
        setBlockBounds(0.2F, 0, 0.2F, 0.8F, part != META_PART_SPROUT ? 1F : 0.5F, 0.8F);
    }

    @Override
    public @Range(from = 0, to = Short.MAX_VALUE) int getDamageValue(@NotNull World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(@NotNull IBlockAccess world, int x, int y, int z) {
        return isDryFromMeta(world.getBlockMetadata(x, y, z)) ? COLOR_DRY : world.getBiomeGenForCoords(x, z).getBiomeGrassColor(x, y, z);
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
        return (part == META_PART_SPROUT || part == META_PART_BOTTOM) ? Plains : typeHeracleum;
    }

    @Override
    public boolean canReplace(World world, int x, int y, int z, int side, @NotNull ItemStack stack) {
        if (stack.getItem() == ModItems.heracleumUmbel)
            blockMeta.set(META_PART_SPROUT);
        else if (stack.getItem() == ModItems.heracleumStem) {
            val meta = stack.getItemDamage();
            val dry = ItemHeracleumStem.isDryFromMeta(meta) ? META_BIT_DRY : 0;
            if (world.getBlock(x, y - 1, z) instanceof BlockHeracleum) {
                if (dry != META_BIT_DRY && isDryFromMeta(world.getBlockMetadata(x, y - 1, z)))
                    return false;
                val part = getPartFromMeta(world.getBlockMetadata(x, y - 1, z));
                if (part == META_PART_BOTTOM || part == META_PART_MIDDLE)
                    blockMeta.set(META_PART_MIDDLE | dry);
                else return false;
            } else blockMeta.set(META_PART_BOTTOM | dry);
        } else blockMeta.set(stack.getItemDamage());
        return super.canReplace(world, x, y, z, side, stack);
    }

    @Override
    public Block getPlant(@NotNull IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(@NotNull IBlockAccess world, int x, int y, int z) {
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
        if (tryGrowth(world, x, y, z, rand))
            return;
        trySpread(world, x, y, z, rand);
    }


    protected void trySpread(@NotNull World world, int x, int y, int z, @NotNull Random rand) {
        val meta = world.getBlockMetadata(x, y, z);
        if (!isBloomingFromMeta(meta) || getPartFromMeta(meta) != META_PART_TOP)
            return;
        var newX = x + rand.nextInt(61) - 30;
        var newZ = z + rand.nextInt(61) - 30;
        var newY = world.getPrecipitationHeight(newX, newZ) ;//+ 1;
//        for (var noise = 0; noise < 4; noise++) {
//            blockMeta.set(0);
//            if (world.isAirBlock(newX, newY, newZ) && canBlockStay(world, newX, newY, newZ)) {
//                x = newX;
//                y = newY;
//                z = newZ;
//            }
//            newX = x + rand.nextInt(3) - 1;
//            newZ = z + rand.nextInt(3) - 1;
//            newY = world.getPrecipitationHeight(x, y) + 1;
//        }
        blockMeta.set(0);
        if (world.isAirBlock(newX, newY, newZ) && canBlockStay(world, newX, newY, newZ))
            world.setBlock(newX, newY, newZ, this, META_PART_SPROUT, 2);
    }

    // TODO: Fix this
    protected boolean tryGrowth(@NotNull World world, int x, int y, int z, @NotNull Random rand) {
        var height = 1;
        for (; world.getBlock(x, y - height, z) == this; height++) ;
        val canGrowthUp = world.isAirBlock(x, y + 1, z) && height < 4;
        val meta = world.getBlockMetadata(x, y, z);
        if (isDryFromMeta(meta))
            return false;
        val part = getPartFromMeta(meta);
        val blooming = isBloomingFromMeta(meta);
        var newMeta = META_PART_SPROUT;
        switch (part) {
            case META_PART_SPROUT:
                newMeta = blooming ? META_PART_BOTTOM : META_PART_SPROUT;
                break;
            case META_PART_BOTTOM:
                newMeta = META_PART_BOTTOM;
                break;
            case META_PART_MIDDLE:
                newMeta = META_PART_MIDDLE;
                break;
            case META_PART_TOP:
                newMeta = blooming ? META_PART_MIDDLE : META_PART_TOP;
                break;
        }
        if (blooming) {
            if (!canGrowthUp)
                return false;
            world.setBlockMetadataWithNotify(x, y, z, newMeta | META_BIT_BLOOM, 2);
            world.setBlock(x, y + 1, z, this, META_PART_TOP, 2);
        } else world.setBlockMetadataWithNotify(x, y, z, newMeta | META_BIT_BLOOM, 2);
        return true;
    }

    @Override
    public boolean canBlockStay(@NotNull World world, int x, int y, int z) {
        if (isDryFromMeta(world.isAirBlock(x, y, z) ? blockMeta.get() : world.getBlockMetadata(x, y, z)))
            return true;
        val block = world.getBlock(x, y - 1, z);
        return block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    @Override
    public ArrayList<ItemStack> getDrops(@NotNull World world, int x, int y, int z,
                                         @Range(from = 0, to = 15) int metadata, int fortune) {
        val result = new ArrayList<ItemStack>();
        if (!harvestedByShearsItem.get()) {
            val bloom = isBloomingFromMeta(metadata);
            val dry = isDryFromMeta(metadata);
            switch (getPartFromMeta(metadata)) {
                case META_PART_SPROUT:
                    if (bloom) result.add(new ItemStack(ModItems.heracleumUmbel, 1));
                    break;
                case META_PART_BOTTOM:
                    result.add(new ItemStack(ModItems.heracleumStem, 1, dry ? 1 : 0));
                    break;
                case META_PART_MIDDLE:
                    result.add(new ItemStack(ModItems.heracleumStem, 1, dry ? 1 : 0));
                    if (bloom) {
                        val count = 1 + world.rand.nextInt(3) + world.rand.nextInt(fortune + 1);
                        for (var i = 0; i < count; i++)
                            result.add(new ItemStack(ModItems.heracleumUmbel, 1));
                    }
                    break;
                case META_PART_TOP:
                    if (bloom) {
                        val count = 1 + world.rand.nextInt(6) + world.rand.nextInt(fortune + 1);
                        for (var i = 0; i < count; i++)
                            result.add(new ItemStack(ModItems.heracleumUmbel, 1));
                    }
                    break;
            }
        }
        harvestedByShearsItem.set(false);
        return result;
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
            val heracleumMeta = plantable.getPlantMetadata(world, x, y + 1, z);
            val heracleumPart = getPartFromMeta(heracleumMeta);
            if (isDryFromMeta(heracleumMeta))
                return true;
            else if (isDryFromMeta(world.getBlockMetadata(x, y, z)))
                return false;
            if (heracleumPart == META_PART_TOP || heracleumPart == META_PART_MIDDLE)
                return part == META_PART_MIDDLE || part == META_PART_BOTTOM;
            return (heracleumPart == META_PART_BOTTOM || heracleumPart == META_PART_SPROUT) &&
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

    public static boolean isDryFromMeta(int meta) {
        return (meta & META_BIT_DRY) == META_BIT_DRY;
    }

    public static boolean isBloomingFromMeta(int meta) {
        return (meta & META_BIT_BLOOM) == META_BIT_BLOOM;
    }
}
