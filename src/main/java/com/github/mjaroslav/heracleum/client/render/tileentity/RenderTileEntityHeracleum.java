package com.github.mjaroslav.heracleum.client.render.tileentity;

import com.github.mjaroslav.heracleum.client.render.model.ModelWrapperDisplayList;
import com.github.mjaroslav.heracleum.common.block.BlockHeracleum;
import lombok.val;
import net.minecraft.client.renderer.Tessellator;
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

// TODO: May be remake on ISBRH?
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
        val t = Tessellator.instance;
        val brightness = tile.getBlockType().getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord, tile.yCoord,
                tile.zCoord);
        val color = tile.getBlockType().colorMultiplier(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        switch (getPartFromMeta(tile.getBlockMetadata())) {
            case META_BOTTOM:
                renderPart(t, "bottom", brightness, color);
                renderPart(t, "bottomOverlay", brightness, 0xFFFFFF);
                break;
            case META_MIDDLE:
                renderPart(t, "middle", brightness, color);
                renderPart(t, "middleOverlay", brightness, 0xFFFFFF);
                break;
            case META_TOP:
                renderPart(t, "top", brightness, color);
                renderPart(t, "topOverlay", brightness, 0xFFFFFF);
                break;
            case META_SPROUT:
                renderPart(t, "sprout", brightness, color);
                renderPart(t, "sproutOverlay", brightness, 0xFFFFFF);
                break;
        }
        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }

    private void renderPart(@NotNull Tessellator tessellator, @NotNull String name, int brightness, int color) {
        val r = (color >> 16 & 255) / 255F;
        val g = (color >> 8 & 255) / 255F;
        val b = (color & 255) / 255F;
        glColor3d(r, g, b);
        glPushMatrix();
        model.renderPart(name);
        glPopMatrix();
    }
}
