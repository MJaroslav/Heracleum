package mjaroslav.mcmods.heracleum.client;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.heracleum.client.render.tileentity.RenderTileEntityHeracleum;
import mjaroslav.mcmods.heracleum.common.CommonProxy;
import mjaroslav.mcmods.heracleum.common.tileentity.TileEntityHeracleum;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public EntityPlayer getEntityPlayer(MessageContext ctx) {
        return (EntityPlayer) (ctx.side == Side.CLIENT ? Minecraft.getMinecraft().thePlayer
                : ctx.getServerHandler().playerEntity);
    }

    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        rendererTileEntity(TileEntityHeracleum.class, new RenderTileEntityHeracleum());
    }
}
