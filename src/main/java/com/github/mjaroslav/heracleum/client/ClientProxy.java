package com.github.mjaroslav.heracleum.client;

import com.github.mjaroslav.heracleum.client.render.block.BlockHeracleumRenderer;
import com.github.mjaroslav.heracleum.client.render.tileentity.RenderTileEntityHeracleum;
import com.github.mjaroslav.heracleum.common.CommonProxy;
import com.github.mjaroslav.heracleum.common.tileentity.TileEntityHeracleum;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class ClientProxy extends CommonProxy {
    public static int heracleumRenderId;

    @Override
    public @UnknownNullability EntityPlayer getEntityPlayer(@NotNull MessageContext ctx) {
        return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().thePlayer
                : ctx.getServerHandler().playerEntity;
    }

    @Override
    public void init(@NotNull FMLInitializationEvent event) {
        heracleumRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(heracleumRenderId, new BlockHeracleumRenderer());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeracleum.class, new RenderTileEntityHeracleum());
    }
}
