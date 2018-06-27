package mjaroslav.mcmods.heracleum.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import mjaroslav.mcmods.heracleum.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTileEntityHeracleum extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/models/heracleum.png");

    private static final float size1 = 0.015625F; // = 1 / 64
    private static final float size2 = 1.0606601717798212866012665431573F;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float ticks) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        bindTexture(texture);
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glColor3f(1, 1, 1);
        Tessellator t = Tessellator.instance;
        int brightness = tile.getBlockType().getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord, tile.yCoord,
                tile.zCoord);
        int color = tile.getBlockType().colorMultiplier(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
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
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    private void renderBottom(Tessellator t, double x, double y, double z, int brightness, int color) {
        renderStem(t, brightness, color, true);
        renderSprite(t, brightness, color, 0F, 0.375F, 0.5F, 0.75F);
        renderSprite(t, brightness, 0xFFFFFF, 0.375F, 0.75F, 0.5F, 0.75F);
    }

    private void renderMiddle(Tessellator t, double x, double y, double z, int brightness, int color) {
        renderStem(t, brightness, color, true);
        renderSprite(t, brightness, color, 0F, 0.375F, 0.25F, 0.5F);
        renderSprite(t, brightness, 0xFFFFFF, 0.375F, 0.75F, 0.25F, 0.5F);
    }

    private void renderTop(Tessellator t, double x, double y, double z, int brightness, int color) {
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

    private void renderSmall(Tessellator t, double x, double y, double z, int brightness, int color) {
        renderStem(t, brightness, color, false);
        renderSprite(t, brightness, color, 0F, 0.373046875F, 0.75F, 1);
        renderSprite(t, brightness, 0xFFFFFF, 0.375F, 0.748046875F, 0.75F, 1);
    }

    private void renderSprite(Tessellator t, int brightness, int color, float minX, float maxX, float minY,
            float maxY) {
        for (int i = 0; i < 2; i++) {
            GL11.glPushMatrix();
            GL11.glRotated(90 * i, 0, 1, 0);
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
            GL11.glPopMatrix();
        }
    }

    private void renderStem(Tessellator t, int brightness, int color, boolean full) {
        float s = full ? 4 : 2;
        float h = full ? 0.9999F : 0.5F;
        for (int i = 0; i < 4; i++) {
            GL11.glPushMatrix();
            GL11.glRotated(90 * i, 0, 1, 0);
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
            GL11.glPopMatrix();
        }
        t.startDrawingQuads();
        t.setColorOpaque_I(full ? 0xFFFFFF : color);
        t.setBrightness(brightness);
        t.addVertexWithUV(-size1 * s, h, -size1 * s, full ? 0.953125F : 0.984375F, full ? 0.03125F : 0.015625F);
        t.addVertexWithUV(-size1 * s, h, size1 * s, full ? 0.953125F : 0.984375F, full ? 0F : 0F);
        t.addVertexWithUV(size1 * s, h, size1 * s, full ? 0.96875F : 0.9921875F, full ? 0F : 0F);
        t.addVertexWithUV(size1 * s, h, -size1 * s, full ? 0.96875F : 0.9921875F, full ? 0.03125F : 0.015625F);
        t.setColorOpaque_I(0xFFFFFF);
        t.setBrightness(brightness);
        t.addVertexWithUV(size1 * s, 0.0001, -size1 * s, full ? 0.96875F : 0.9921875F, full ? 0.03125F : 0.015625F);
        t.addVertexWithUV(size1 * s, 0.0001, size1 * s, full ? 0.96875F : 0.9921875F, full ? 0F : 0F);
        t.addVertexWithUV(-size1 * s, 0.0001, size1 * s, full ? 0.984375F : 1F, full ? 0F : 0F);
        t.addVertexWithUV(-size1 * s, 0.0001, -size1 * s, full ? 0.984375F : 1F, full ? 0.03125F : 0.015625F);
        t.draw();
    }
}
