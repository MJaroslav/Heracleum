package mjaroslav.mcmods.heracleum.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.heracleum.common.tileentity.TileEntityHeracleum;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeracleum extends Block implements ITileEntityProvider {
    public BlockHeracleum() {
        super(Material.grass);
        setBlockName("heracleum");
        setBlockTextureName("minecraft:tallgrass");
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB bb, List list, Entity entity) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta > 0) {
            if (meta != 3)
                setBlockBounds(0.4375F, 0, 0.4375F, 0.5625F, 1, 0.5625F);
        } else
            setBlockBounds(0.46875F, 0, 0.46875F, 0.53125F, 0.5F, 0.53125F);
        if (meta != 3)
            super.addCollisionBoxesToList(world, x, y, z, bb, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        setBlockBounds(0.2F, 0, 0.2F, 0.8F, world.getBlockMetadata(x, y, z) > 0 ? 1F : 0.5F, 0.8F);
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return meta > 3 ? 0 : meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return world.getBiomeGenForCoords(x, z).getBiomeGrassColor(x, y, z);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 4; i++)
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
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityHeracleum();
    }
}
