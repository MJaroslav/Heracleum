package com.github.mjaroslav.heracleum.client.render.tileentity;

import com.github.mjaroslav.heracleum.lib.ModInfo;
import lombok.val;
import lombok.var;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static org.lwjgl.opengl.GL11.*;

// TODO: Remake render and use GL List
public final class RenderTileEntityHeracleum extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MOD_ID, "textures/models/heracleum.png");

    private static final float size1 = 0.015625F; // = 1 / 64
    private static final float size2 = 1.0606601717798212866012665431573F;

    @Override
    public void renderTileEntityAt(@NotNull TileEntity tile, double x, double y, double z, float ticks) {
        glPushMatrix();
        glDisable(GL_LIGHTING);
        glDisable(GL_CULL_FACE);
        bindTexture(texture);
        glTranslated(x + 0.5, y, z + 0.5);
        glColor3f(1, 1, 1);
        val t = Tessellator.instance;
        val brightness = tile.getBlockType().getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord, tile.yCoord,
                tile.zCoord);
        val color = tile.getBlockType().colorMultiplier(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        switch (tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)) {
            case 1:
                renderBottom(t, x, y, z, brightness, color);
                break;
            case 2:
                renderMiddle(t, x, y, z, brightness, color);
                break;
            case 3:
                renderTop(t, x, y, z, brightness, color);
                break;
            default:
                renderSmall(t, x, y, z, brightness, color);
                break;
        }
        glEnable(GL_LIGHTING);
        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }

    private void renderBottom(@NotNull Tessellator t, double x, double y, double z, int brightness, int color) {
        renderStem(t, brightness, color, true);
        renderSprite(t, brightness, color, 0F, 0.375F, 0.5F, 0.75F);
        renderSprite(t, brightness, 0xFFFFFF, 0.375F, 0.75F, 0.5F, 0.75F);
    }

    private void renderMiddle(@NotNull Tessellator t, double x, double y, double z, int brightness, int color) {
        renderStem(t, brightness, color, true);
        renderSprite(t, brightness, color, 0F, 0.375F, 0.25F, 0.5F);
        renderSprite(t, brightness, 0xFFFFFF, 0.375F, 0.75F, 0.25F, 0.5F);
    }

    private void renderTop(@NotNull Tessellator t, double x, double y, double z, int brightness, int color) {
        renderSprite(t, brightness, color, 0F, 0.375F, 0F, 0.24F);
        renderSprite(t, brightness, 0xFFFFFF, 0.375F, 0.75F, 0F, 0.24F);
        t.startDrawingQuads();
        t.setColorOpaque_I(color);
        t.setBrightness(brightness);
        t.addVertexWithUV(-size1 * 4, 0.0001F, -size1 * 4, 0.953125F, 0.0625F);
        t.addVertexWithUV(-size1 * 4, 0.0001F, size1 * 4, 0.953125F, 0.03125F);
        t.addVertexWithUV(size1 * 4, 0.0001F, size1 * 4, 0.96875F, 0.03125F);
        t.addVertexWithUV(size1 * 4, 0.0001F, -size1 * 4, 0.96875F, 0.0625F);
        t.draw();
    }

    private void renderSmall(@NotNull Tessellator t, double x, double y, double z, int brightness, int color) {
        renderStem(t, brightness, color, false);
        renderSprite(t, brightness, color, 0F, 0.373046875F, 0.75F, 1);
        renderSprite(t, brightness, 0xFFFFFF, 0.375F, 0.748046875F, 0.75F, 1);
    }

    private void renderSprite(@NotNull Tessellator t, int brightness, int color, float minX, float maxX, float minY,
                              float maxY) {
        for (var i = 0; i < 2; i++) {
            glPushMatrix();
            glRotated(90 * i, 0, 1, 0);
            t.startDrawingQuads();
            t.setColorOpaque_I(color);
            t.setBrightness(brightness);
            t.addVertexWithUV(-size2, 0.0001F, -size2, minX, maxY);
            t.addVertexWithUV(-size2, 0.9999F, -size2, minX, minY);
            t.addVertexWithUV(size2, 0.9999F, size2, maxX, minY);
            t.addVertexWithUV(size2, 0.0001F, size2, maxX, maxY);
            t.setColorOpaque_I(color);
            t.setBrightness(brightness);
            t.addVertexWithUV(size2, 0.0001F, size2, maxX, maxY);
            t.addVertexWithUV(size2, 0.9999F, size2, maxX, minY);
            t.addVertexWithUV(-size2, 0.9999F, -size2, minX, minY);
            t.addVertexWithUV(-size2, 0.0001F, -size2, minX, maxY);
            t.draw();
            glPopMatrix();
        }
    }

    private void renderStem(@NotNull Tessellator t, int brightness, int color, boolean full) {
        val s = full ? 4f : 2f;
        val h = full ? 0.9999F : 0.5F;
        for (var i = 0; i < 4; i++) {
            glPushMatrix();
            glRotated(90 * i, 0, 1, 0);
            t.startDrawingQuads();
            t.setColorOpaque_I(color);
            t.setBrightness(brightness);
            t.addVertexWithUV(-size1 * s, 0.0001F, -size1 * s, full ? 0.953125F : 0.984375F,
                    full ? 0.28125F : 0.140625F);
            t.addVertexWithUV(-size1 * s, h, -size1 * s, full ? 0.953125F : 0.984375F, full ? 0.03125F : 0.015625F);
            t.addVertexWithUV(size1 * s, h, -size1 * s, full ? 0.96875F : 0.9921875F, full ? 0.03125F : 0.015625F);
            t.addVertexWithUV(size1 * s, 0.0001F, -size1 * s, full ? 0.96875F : 0.9921875F,
                    full ? 0.28125F : 0.140625F);
            if (full) {
                t.setColorOpaque_I(color);
                t.setBrightness(brightness);
                t.addVertexWithUV(size1 * 2, 0.0001F, -size1 * 2, 0.97265625F, 0.28125F);
                t.addVertexWithUV(size1 * 2, 0.9999F, -size1 * 2, 0.97265625F, 0.03125F);
                t.addVertexWithUV(-size1 * 2, 0.9999F, -size1 * 2, 0.98046875F, 0.03125F);
                t.addVertexWithUV(-size1 * 2, 0.0001F, -size1 * 2, 0.98046875F, 0.28125F);
            }
            t.draw();
            glPopMatrix();
        }
        t.startDrawingQuads();
        t.setColorOpaque_I(full ? 0xFFFFFF : color);
        t.setBrightness(brightness);
        t.addVertexWithUV(-size1 * s, h, -size1 * s, full ? 0.953125F : 0.984375F, full ? 0.03125F : 0.015625F);
        t.addVertexWithUV(-size1 * s, h, size1 * s, full ? 0.953125F : 0.984375F, 0F);
        t.addVertexWithUV(size1 * s, h, size1 * s, full ? 0.96875F : 0.9921875F, 0F);
        t.addVertexWithUV(size1 * s, h, -size1 * s, full ? 0.96875F : 0.9921875F, full ? 0.03125F : 0.015625F);
        t.setColorOpaque_I(0xFFFFFF);
        t.setBrightness(brightness);
        t.addVertexWithUV(size1 * s, 0.0001, -size1 * s, full ? 0.96875F : 0.9921875F, full ? 0.03125F : 0.015625F);
        t.addVertexWithUV(size1 * s, 0.0001, size1 * s, full ? 0.96875F : 0.9921875F, 0F);
        t.addVertexWithUV(-size1 * s, 0.0001, size1 * s, full ? 0.984375F : 1F, 0F);
        t.addVertexWithUV(-size1 * s, 0.0001, -size1 * s, full ? 0.984375F : 1F, full ? 0.03125F : 0.015625F);
        t.draw();
    }
}
