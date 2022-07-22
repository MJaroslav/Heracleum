package com.github.mjaroslav.heracleum.client.render.model.obj;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

import java.util.ArrayList;

public class IconScaledGroupObject {
    public String name;
    public ArrayList<IconScaledFace> faces = new ArrayList<>();
    public int glDrawingMode;

    public IconScaledGroupObject() {
        this("");
    }

    public IconScaledGroupObject(String name) {
        this(name, -1);
    }

    public IconScaledGroupObject(String name, int glDrawingMode) {
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        if (faces.size() > 0) {
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(glDrawingMode);
            render(tessellator);
            tessellator.draw();
        }
    }

    @SideOnly(Side.CLIENT)
    public void render(Tessellator tessellator) {
        if (faces.size() > 0) {
            for (IconScaledFace face : faces) {
                face.addFaceForRender(tessellator);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void render(IIcon icon, Tessellator tessellator) {
        if (faces.size() > 0) {
            for (IconScaledFace face : faces) {
                face.addFaceForRender(icon, tessellator);
            }
        }
    }
}
