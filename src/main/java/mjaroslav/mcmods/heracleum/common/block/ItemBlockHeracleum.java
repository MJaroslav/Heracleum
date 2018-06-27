package mjaroslav.mcmods.heracleum.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockHeracleum extends ItemBlock {
    public ItemBlockHeracleum(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
