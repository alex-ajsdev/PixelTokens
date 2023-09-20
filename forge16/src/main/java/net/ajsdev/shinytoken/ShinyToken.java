package net.ajsdev.shinytoken;

import com.envyful.api.concurrency.UtilLogger;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.api.forge.command.parser.ForgeAnnotationCommandParser;
import com.envyful.api.forge.player.ForgePlayerManager;
import net.ajsdev.shinytoken.command.ShinyTokenCommand;
import net.ajsdev.shinytoken.listener.TokenInteractListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ShinyToken.MOD_ID)
public class ShinyToken {

    protected static final String MOD_ID = "shinytoken";
    public static final String NBT_TAG = "SHINY_TOKEN";
    private static ShinyToken instance;
    private final ForgePlayerManager playerManager = new ForgePlayerManager();
    private final ForgeCommandFactory commandFactory = new ForgeCommandFactory(ForgeAnnotationCommandParser::new, playerManager);
    private final Logger logger = LogManager.getLogger(MOD_ID);


    public ShinyToken() {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    @SubscribeEvent
    public void onInit(FMLServerStartingEvent event) {
        MinecraftForge.EVENT_BUS.register(new TokenInteractListener());
    }


    @SubscribeEvent
    public void onServerStart(RegisterCommandsEvent event) {
        this.commandFactory.registerCommand(event.getDispatcher(), this.commandFactory.parseCommand(new ShinyTokenCommand()));
        logger.info("Registered commands");
    }

    @SubscribeEvent
    public void onServerStop(FMLServerStoppingEvent event) {

    }

    public static ShinyToken getInstance() {
        return instance;
    }

    public static ForgePlayerManager getPlayerManager() {
        return instance.playerManager;
    }

    public static Logger getLogger() {
        return instance.logger;
    }
}