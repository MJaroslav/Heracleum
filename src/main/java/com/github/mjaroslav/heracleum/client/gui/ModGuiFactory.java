package com.github.mjaroslav.heracleum.client.gui;

import com.github.mjaroslav.heracleum.HeracleumMod;
import com.github.mjaroslav.heracleum.lib.ModInfo;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public final class ModGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(@NotNull Minecraft minecraftInstance) {
    }

    @Override
    public @NotNull Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGui.class;
    }

    @Override
    public @Nullable Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public @Nullable RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class ConfigGui extends GuiConfig {
        public ConfigGui(GuiScreen parentScreen) {
            super(parentScreen, HeracleumMod.config.generalToElementList(), ModInfo.MOD_ID, false, false, ModInfo.NAME);
        }
    }
}