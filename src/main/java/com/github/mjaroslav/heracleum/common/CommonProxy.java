package com.github.mjaroslav.heracleum.common;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mjaroslav.mcmods.mjutils.module.Proxy;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class CommonProxy extends Proxy {
    @Override
    public @UnknownNullability EntityPlayer getEntityPlayer(@NotNull MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }
}
