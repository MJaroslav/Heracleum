package com.github.mjaroslav.heracleum.client.render.model;

import lombok.experimental.UtilityClass;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class WavefrontObjectUtils {
    public void fitToHeracleumIcon(@NotNull WavefrontObject obj, @NotNull IIcon icon) {
        obj.textureCoordinates.forEach(texCoord -> {
            texCoord.u = icon.getInterpolatedU(texCoord.u * 16);
            texCoord.v = icon.getInterpolatedV(texCoord.v * 8);
        });
    }
}
