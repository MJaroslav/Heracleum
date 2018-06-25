package mjaroslav.mcmods.heracleum.client.render.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import mjaroslav.mcmods.heracleum.common.init.ModuleBlocks;
import mjaroslav.mcmods.heracleum.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class RenderHeracleum implements ISimpleBlockRenderingHandler {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/models/heracleum.png");
    public static final float xPixel = 0.001953125F;
    public static final float yPixel = 0.00390625F;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        int previousGLRenderType = Tessellator.instance.drawMode;
        Tessellator.instance.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x, y, z);
        renderHeracleum(world, x, y, z, block, modelId, renderer, Tessellator.instance);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator.instance.startDrawing(previousGLRenderType);
        return true;
    }

    public void renderHeracleum(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer, Tessellator t) {
        int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        int color = block.colorMultiplier(world, x, y, z);
        switch (world.getBlockMetadata(x, y, z)) {
        case 1:
            bottom(Tessellator.instance, x, y, z, brightness, color);
            break;
        case 2:
            middle(Tessellator.instance, x, y, z, brightness, color);
            break;
        case 3:
            top(Tessellator.instance, x, y, z, brightness, color);
            break;
        default:
            small(Tessellator.instance, x, y, z, brightness, color);
            break;
        }
    }

    public static void small(Tessellator t, int x, int y, int z, int brightness, int color) {
        double p = 0.5606601717798213;
        double o = 0.015625;
        //
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 - o * 2, 0, 0.5 - o * 2, xPixel * 478, 1);
        t.addVertexWithUV(0.5 - o * 2, 0.5, 0.5 - o * 2, xPixel * 478, yPixel * 224);
        t.addVertexWithUV(0.5 + o * 2, 0.5, 0.5 - o * 2, xPixel * 482, yPixel * 224);
        t.addVertexWithUV(0.5 + o * 2, 0, 0.5 - o * 2, xPixel * 482, 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 + o * 2, 0, 0.5 + o * 2, xPixel * 478, 1);
        t.addVertexWithUV(0.5 + o * 2, 0.5, 0.5 + o * 2, xPixel * 478, yPixel * 224);
        t.addVertexWithUV(0.5 - o * 2, 0.5, 0.5 + o * 2, xPixel * 482, yPixel * 224);
        t.addVertexWithUV(0.5 - o * 2, 0, 0.5 + o * 2, xPixel * 482, 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 + o * 2, 0, 0.5 - o * 2, xPixel * 478, 1);
        t.addVertexWithUV(0.5 + o * 2, 0.5, 0.5 - o * 2, xPixel * 478, yPixel * 224);
        t.addVertexWithUV(0.5 + o * 2, 0.5, 0.5 + o * 2, xPixel * 482, yPixel * 224);
        t.addVertexWithUV(0.5 + o * 2, 0, 0.5 + o * 2, xPixel * 482, 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 - o * 2, 0, 0.5 + o * 2, xPixel * 478, 1);
        t.addVertexWithUV(0.5 - o * 2, 0.5, 0.5 + o * 2, xPixel * 478, yPixel * 224);
        t.addVertexWithUV(0.5 - o * 2, 0.5, 0.5 - o * 2, xPixel * 482, yPixel * 224);
        t.addVertexWithUV(0.5 - o * 2, 0, 0.5 - o * 2, xPixel * 482, 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, -p, 0, 1);
        t.addVertexWithUV(-p, 1, -p, 0, yPixel * 64 * 3);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, 1);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.addVertexWithUV(-p, 1, -p, 0, yPixel * 64 * 3);
        t.addVertexWithUV(-p, 0, -p, 0, 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, 1 + p, 0, 1);
        t.addVertexWithUV(-p, 1, 1 + p, 0, yPixel * 64 * 3);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, 1);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.addVertexWithUV(-p, 1, 1 + p, 0, yPixel * 64 * 3);
        t.addVertexWithUV(-p, 0, 1 + p, 0, 1);
        t.draw();
    }

    public static void bottom(Tessellator t, int x, int y, int z, int brightness, int color) {
        double p = 0.5606601717798213;
        double o = 0.015625;
        //
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 - o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 - o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 - o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 - o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 + o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 + o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 + o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 + o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 - o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 - o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 + o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 + o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 + o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 + o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 - o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 - o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, -p, 0, yPixel * 64 * 3);
        t.addVertexWithUV(-p, 1, -p, 0, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 1, -p, 0, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 0, -p, 0, yPixel * 64 * 3);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, 1 + p, 0, yPixel * 64 * 3);
        t.addVertexWithUV(-p, 1, 1 + p, 0, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 3);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 1, 1 + p, 0, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 0, 1 + p, 0, yPixel * 64 * 3);
        t.draw();
    }

    public static void middle(Tessellator t, int x, int y, int z, int brightness, int color) {
        double p = 0.5606601717798213;
        double o = 0.015625;
        //
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 - o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 - o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 - o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 - o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 + o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 + o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 + o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 + o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 - o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 - o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 1, 0.5 + o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 + o * 4, 0, 0.5 + o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 + o * 4, xPixel * 476, yPixel * 191);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 + o * 4, xPixel * 476, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 1, 0.5 - o * 4, xPixel * 484, yPixel * 64);
        t.addVertexWithUV(0.5 - o * 4, 0, 0.5 - o * 4, xPixel * 484, yPixel * 191);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, -p, 0, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 1, -p, 0, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, -p, 0, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 0, -p, 0, yPixel * 64 * 2);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, 1 + p, 0, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 1, 1 + p, 0, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, 1 + p, 0, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 0, 1 + p, 0, yPixel * 64 * 2);
        t.draw();
        //
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(-p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 6, yPixel * 64 * 2);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 6, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(-p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.addVertexWithUV(-p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 6, yPixel * 64 * 2);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 6, yPixel * 64 * 2);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 2);
        t.draw();
    }

    public static void top(Tessellator t, int x, int y, int z, int brightness, int color) {
        double p = 0.5606601717798213;
        double o = 0.015625;
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, -p, 0, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, -p, 0, 0);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(-p, 1, -p, 0, 0);
        t.addVertexWithUV(-p, 0, -p, 0, yPixel * 64 * 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(-p, 0, 1 + p, 0, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, 1 + p, 0, 0);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(color);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(-p, 1, 1 + p, 0, 0);
        t.addVertexWithUV(-p, 0, 1 + p, 0, yPixel * 64 * 1);
        t.draw();
        //
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(-p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, -p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 6, 0);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(1 + p, 0, 1 + p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, 1 + p, xPixel * 64 * 6, 0);
        t.addVertexWithUV(-p, 1, -p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(-p, 0, -p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(-p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.addVertexWithUV(-p, 1, 1 + p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 6, 0);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.draw();
        t.startDrawingQuads();
        t.setBrightness(brightness);
        t.setColorOpaque_I(0xFFFFFF);
        t.addVertexWithUV(1 + p, 0, -p, xPixel * 64 * 6, yPixel * 64 * 1);
        t.addVertexWithUV(1 + p, 1, -p, xPixel * 64 * 6, 0);
        t.addVertexWithUV(-p, 1, 1 + p, xPixel * 64 * 3, 0);
        t.addVertexWithUV(-p, 0, 1 + p, xPixel * 64 * 3, yPixel * 64 * 1);
        t.draw();
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return ModuleBlocks.renderHeracleumID;
    }
}
