package com.github.mjaroslav.heracleum.client.render.block;

import com.github.mjaroslav.heracleum.client.render.model.ModelWrapperDisplayList;
import com.github.mjaroslav.heracleum.client.render.model.WavefrontObjectUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.common.block.BlockHeracleum.*;
import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;

public class BlockHeracleumRenderer implements ISimpleBlockRenderingHandler {
    public static final ResourceLocation texture = new ResourceLocation(prefix("textures/models/blocks/heracleum.png"));
    protected static WavefrontObject wavefront = (WavefrontObject) AdvancedModelLoader
            .loadModel(new ResourceLocation(prefix("models/blocks/heracleum.obj")));
    public static final IModelCustom model = new ModelWrapperDisplayList(wavefront);
    protected final IconHolder holder = new IconHolder();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, @NotNull Block block, int modelId, RenderBlocks renderer) {
        val icon = block.getIcon(0, 0);
        if (!holder.isFit(icon)) {
            wavefront = (WavefrontObject) AdvancedModelLoader
                    .loadModel(new ResourceLocation(prefix("models/blocks/heracleum.obj")));
            WavefrontObjectUtils.fitToHeracleumIcon(wavefront, icon);
        }
        val tessellator = Tessellator.instance;
        tessellator.addTranslation(x + 0.5f, y, z + 0.5f);
        val color = block.colorMultiplier(world, x, y, z);
        val brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        val meta = world.getBlockMetadata(x, y, z);
        tessellator.setColorOpaque_I(color);
        tessellator.setBrightness(brightness);
        val blooming = isBloomingFromMeta(meta);
        switch (getPartFromMeta(meta)) {
            case META_PART_BOTTOM:
                renderPart(tessellator, "bottom", color, blooming);
                break;
            case META_PART_MIDDLE:
                renderPart(tessellator, "middle", color, blooming);
                break;
            case META_PART_TOP:
                renderPart(tessellator, "top", color, blooming);
                break;
            case META_PART_SPROUT:
                renderPart(tessellator, "sprout", color, blooming);
                break;
        }
        tessellator.addTranslation(-(x + 0.5f), -y, -(z + 0.5f));
        return true;
    }

    private void renderPart(@NotNull Tessellator tessellator, @NotNull String name, int color, boolean blooming) {
        wavefront.tessellatePart(tessellator, name);
        if (blooming)
            wavefront.tessellatePart(tessellator, name + "Overlay");
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return 0;
    }

    @Getter
    @NoArgsConstructor
    private static class IconHolder implements IIcon {
        private int iconWidth, iconHeight;
        private float minU, maxU, minV, maxV;

//        public IconHolder(@NotNull IIcon icon) {
//            iconWidth = icon.getIconWidth();
//            iconHeight = icon.getIconHeight();
//            minU = icon.getMinU();
//            maxU = icon.getMaxU();
//            minV = icon.getMinV();
//            maxV = icon.getMaxV();
//        }

        @Override
        public float getInterpolatedU(double u) {
            return 0;
        }

        @Override
        public float getInterpolatedV(double v) {
            return 0;
        }

        @Override
        public String getIconName() {
            return null;
        }

        public boolean isFit(@NotNull IIcon icon) {
            var result = iconWidth == icon.getIconWidth();
            iconWidth = icon.getIconWidth();
            if (iconHeight != icon.getIconHeight())
                result = false;
            iconHeight = icon.getIconHeight();
            if (minU != icon.getMinU())
                result = false;
            minU = icon.getMinU();
            if (maxU != icon.getMaxU())
                result = false;
            maxU = icon.getMaxU();
            if (minV != icon.getMinV())
                result = false;
            minV = icon.getMinV();
            if (maxV != icon.getMaxV())
                result = false;
            maxV = icon.getMaxV();
            return result;
        }
    }
}
