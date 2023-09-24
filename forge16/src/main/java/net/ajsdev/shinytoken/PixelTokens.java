package net.ajsdev.shinytoken;

import net.ajsdev.shinytoken.command.PixelTokensCommand;
import net.ajsdev.shinytoken.listener.TokenInteractListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PixelTokens.MOD_ID)
public class PixelTokens {

    protected static final String MOD_ID = "pixeltokens";
    public static final String NBT_TAG = "PIXEL_TOKENS";
    public static final String MSG_PREFIX = "[PixelTokens] ";
    public static final String VERSION = "v0.1.0";
    private static PixelTokens instance;
    private final Logger logger = LogManager.getLogger(MOD_ID);


    public PixelTokens() {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    @SubscribeEvent
    public void onInit(FMLServerStartingEvent event) {
        MinecraftForge.EVENT_BUS.register(new TokenInteractListener());
    }


    @SubscribeEvent
    public void onServerStart(RegisterCommandsEvent event) {
        PixelTokensCommand command = new PixelTokensCommand();
        command.register(event.getDispatcher());
        logger.info("Registered commands");
    }

    @SubscribeEvent
    public void onServerStop(FMLServerStoppingEvent event) {}

    public static Logger getLogger() {
        return instance.logger;
    }
}