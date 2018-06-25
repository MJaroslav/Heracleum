package mjaroslav.mcmods.heracleum.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mjaroslav.mcmods.mjutils.lib.module.ProxyBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy extends ProxyBase {
    @Override
    public void init(FMLInitializationEvent arg0) {
    }

    @Override
    public void postInit(FMLPostInitializationEvent arg0) {
    }

    @Override
    public void preInit(FMLPreInitializationEvent arg0) {
    }

    @Override
    public EntityPlayer getEntityPlayer(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }

    @Override
    public Minecraft getMinecraft() {
        return null;
    }

    @Override
    public void spawnParticle(String name, double x, double y, double z, Object... args) {
    }
}
