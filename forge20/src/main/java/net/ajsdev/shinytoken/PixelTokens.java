package net.ajsdev.shinytoken;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod(PixelTokens.MOD_ID)
public class PixelTokens {

    protected static final String MOD_ID = "pixeltokens";
    protected static final String VERSION = "0.0.0";

    private static PixelTokens instance;

    public PixelTokens() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onInit(ServerStartingEvent event) {

    }


    @SubscribeEvent
    public void onServerStart(RegisterCommandsEvent event) {

    }

    @SubscribeEvent
    public void onServerStop(ServerStoppingEvent event) {

    }

    public static PixelTokens getInstance() {
        return instance;
    }
}