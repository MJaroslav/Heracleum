package mjaroslav.mcmods.heracleum.client.gui;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import mjaroslav.mcmods.heracleum.lib.ModInfo;
import mjaroslav.mcmods.mjutils.mod.ModMJUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ModGuiFactory implements IModGuiFactory {
    public void initialize(Minecraft minecraftInstance) {
    }

    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGui.class;
    }

    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class ConfigGui extends GuiConfig {
        public ConfigGui(GuiScreen parentScreen) {
            super(parentScreen, ModMJUtils.config.toElementList(), ModInfo.MODID, false, false, ModInfo.NAME);
        }
    }
}