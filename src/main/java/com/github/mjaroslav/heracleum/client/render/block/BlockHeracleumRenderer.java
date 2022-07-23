package com.github.mjaroslav.heracleum.client.render.block;

import com.github.mjaroslav.heracleum.client.ClientProxy;
import com.github.mjaroslav.heracleum.client.render.model.obj.IconScaledWavefrontObject;
import com.github.mjaroslav.heracleum.common.init.ModBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.common.block.BlockHeracleum.*;
import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;
import static org.lwjgl.opengl.GL11.*;

public class BlockHeracleumRenderer implements ISimpleBlockRenderingHandler {
    public static final IconScaledWavefrontObject model = new IconScaledWavefrontObject(new ResourceLocation(prefix("models/blocks/heracleum.obj")));

    @Override
    public void renderInventoryBlock(@NotNull Block block, int damage, int modelId, @NotNull RenderBlocks renderer) {
        val tessellator = Tessellator.instance;
        val part = getPartFromMeta(damage);
        glPushMatrix();
        glTranslated(0, -0.5, 0);
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(block.getRenderColor(damage));
        renderPartForItem(part, ModBlocks.heracleum.getIcon(damage, false), tessellator);
        if (isBloomingFromMeta(damage)) {
            tessellator.setColorOpaque_I(0xFFFFFF);
            renderPartForItem(part, ModBlocks.heracleum.getIcon(damage, true), tessellator);
        }
        tessellator.draw();
        glPopMatrix();
    }

    private void renderPartForItem(int part, @NotNull IIcon icon, @NotNull Tessellator tessellator) {
        switch (part) {
            case META_PART_SPROUT:
                model.tessellatePart(icon, tessellator, "smallStem");
                model.tessellatePart(icon, tessellator, "smallStemBottom");
                break;
            case META_PART_BOTTOM:
            case META_PART_MIDDLE:
                model.tessellatePart(icon, tessellator, "stem");
                model.tessellatePart(icon, tessellator, "stemTop");
                model.tessellatePart(icon, tessellator, "stemBottom");
                break;
        }
        model.tessellatePart(icon, tessellator, "cross");
    }

    @Override
    public boolean renderWorldBlock(@NotNull IBlockAccess world, int x, int y, int z, @NotNull Block block, int modelId,
                                    @NotNull RenderBlocks renderer) {
        val meta = world.getBlockMetadata(x, y, z);
        val icon = ModBlocks.heracleum.getIcon(meta, false);
        val iconOverlay = ModBlocks.heracleum.getIcon(meta, true);
        val tessellator = Tessellator.instance;
        tessellator.addTranslation(x + 0.5f, y, z + 0.5f);
        val color = block.colorMultiplier(world, x, y, z);
        val brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        tessellator.setColorOpaque_I(color);
        tessellator.setBrightness(brightness);
        val blooming = isBloomingFromMeta(meta);
        switch (getPartFromMeta(meta)) {
            case META_PART_BOTTOM:
            case META_PART_MIDDLE:
                renderStemPart(world, block, x, y, z, icon, iconOverlay, blooming, tessellator);
                break;
            case META_PART_TOP:
                renderTopPart(world, block, x, y, z, icon, iconOverlay, blooming, tessellator);
                break;
            case META_PART_SPROUT:
                renderSproutPart(world, block, x, y, z, icon, iconOverlay, blooming, tessellator);
                break;
        }
        tessellator.addTranslation(-(x + 0.5f), -y, -(z + 0.5f));
        return true;
    }

    private void renderTopPart(@NotNull IBlockAccess world, @NotNull Block block, int x, int y, int z,
                               @NotNull IIcon icon, @NotNull IIcon iconOverlay, boolean blooming,
                               @NotNull Tessellator tessellator) {
        val flagTop = block.shouldSideBeRendered(world, x, y, z, 1);
        if (flagTop)
            model.tessellatePart(icon, tessellator, "stemTopBottom");
        model.tessellatePart(icon, tessellator, "cross");
        if (blooming) {
            tessellator.setColorOpaque_I(0xFFFFFF);
            if (flagTop)
                model.tessellatePart(iconOverlay, tessellator, "stemTopBottom");
            model.tessellatePart(iconOverlay, tessellator, "cross");
        }
    }

    private void renderSproutPart(@NotNull IBlockAccess world, @NotNull Block block, int x, int y, int z,
                                  @NotNull IIcon icon, @NotNull IIcon iconOverlay, boolean blooming,
                                  @NotNull Tessellator tessellator) {
        val flagBottom = block.shouldSideBeRendered(world, x, y, z, 0);
        if (flagBottom)
            model.tessellatePart(icon, tessellator, "smallStemBottom");
        model.tessellatePart(icon, tessellator, "smallStem");
        model.tessellatePart(icon, tessellator, "cross");
        if (blooming) {
            tessellator.setColorOpaque_I(0xFFFFFF);
            if (flagBottom)
                model.tessellatePart(iconOverlay, tessellator, "smallStemBottom");
            model.tessellatePart(iconOverlay, tessellator, "smallStem");
            model.tessellatePart(iconOverlay, tessellator, "cross");
        }
    }

    private void renderStemPart(@NotNull IBlockAccess world, @NotNull Block block, int x, int y, int z,
                                @NotNull IIcon icon, @NotNull IIcon iconOverlay, boolean blooming,
                                @NotNull Tessellator tessellator) {
        val flagBottom = block.shouldSideBeRendered(world, x, y, z, 0);
        val flagTop = block.shouldSideBeRendered(world, x, y, z, 1);
        if (flagBottom)
            model.tessellatePart(icon, tessellator, "stemBottom");
        if (flagTop)
            model.tessellatePart(icon, tessellator, "stemTop");
        model.tessellatePart(icon, tessellator, "stem");
        model.tessellatePart(icon, tessellator, "cross");
        if (blooming) {
            tessellator.setColorOpaque_I(0xFFFFFF);
            if (flagBottom)
                model.tessellatePart(iconOverlay, tessellator, "stemBottom");
            if (flagTop)
                model.tessellatePart(iconOverlay, tessellator, "stemTop");
            model.tessellatePart(iconOverlay, tessellator, "stem");
            model.tessellatePart(iconOverlay, tessellator, "cross");
        }
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.heracleumRenderId;
    }
}
