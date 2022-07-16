package com.github.mjaroslav.heracleum.client.render.tileentity;

import com.github.mjaroslav.heracleum.client.render.model.ModelWrapperDisplayList;
import com.github.mjaroslav.heracleum.common.block.BlockHeracleum;
import lombok.val;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.jetbrains.annotations.NotNull;

import static com.github.mjaroslav.heracleum.common.block.BlockHeracleum.*;
import static com.github.mjaroslav.heracleum.lib.ModInfo.prefix;
import static org.lwjgl.opengl.GL11.*;

public final class RenderTileEntityHeracleum extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(prefix("textures/models/blocks/heracleum.png"));
    public static final IModelCustom model = new ModelWrapperDisplayList((WavefrontObject) AdvancedModelLoader
            .loadModel(new ResourceLocation(prefix("models/blocks/heracleum.obj"))));

    @Override
    public void renderTileEntityAt(@NotNull TileEntity tile, double x, double y, double z, float partial) {
        if (!(tile.getBlockType() instanceof BlockHeracleum))
            return;
        glPushMatrix();
        glDisable(GL_CULL_FACE);
        bindTexture(texture);
        glTranslated(x + 0.5, y, z + 0.5);
        val color = tile.getBlockType().colorMultiplier(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        val blooming = isBloomingFromMeta(tile.getBlockMetadata());
        switch (getPartFromMeta(tile.getBlockMetadata())) {
            case META_PART_BOTTOM:
                renderPart("bottom", color, blooming);
                break;
            case META_PART_MIDDLE:
                renderPart("middle", color, blooming);
                break;
            case META_PART_TOP:
                renderPart("top", color, blooming);
                break;
            case META_PART_SPROUT:
                renderPart("sprout", color, blooming);
                break;
        }
        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }

    private void renderPart(@NotNull String name, int color, boolean blooming) {
        val r = (color >> 16 & 255) / 255F;
        val g = (color >> 8 & 255) / 255F;
        val b = (color & 255) / 255F;
        glPushMatrix();
        glColor3d(r, g, b);
        model.renderPart(name);
        glPopMatrix();
        if (blooming) {
            glPushMatrix();
            glColor3d(1, 1, 1);
            model.renderPart(name + "Overlay");
            glPopMatrix();
        }
    }
}
