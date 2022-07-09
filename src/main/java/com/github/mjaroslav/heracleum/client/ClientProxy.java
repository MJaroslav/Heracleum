package com.github.mjaroslav.heracleum.client;

import com.github.mjaroslav.heracleum.client.render.tileentity.RenderTileEntityHeracleum;
import com.github.mjaroslav.heracleum.common.CommonProxy;
import com.github.mjaroslav.heracleum.common.tileentity.TileEntityHeracleum;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public EntityPlayer getEntityPlayer(MessageContext ctx) {
        return (EntityPlayer) (ctx.side == Side.CLIENT ? Minecraft.getMinecraft().thePlayer
                : ctx.getServerHandler().playerEntity);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeracleum.class, new RenderTileEntityHeracleum());
    }
}
