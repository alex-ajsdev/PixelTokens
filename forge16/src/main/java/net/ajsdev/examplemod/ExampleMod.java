package net.ajsdev.examplemod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod(ExampleMod.MOD_ID)
public class ExampleMod {

    protected static final String MOD_ID = "examplemod";
    protected static final String VERSION = "0.0.0";

    private static ExampleMod instance;

    public ExampleMod() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onInit(FMLServerStartingEvent event) {

    }


    @SubscribeEvent
    public void onServerStart(RegisterCommandsEvent event) {

    }

    @SubscribeEvent
    public void onServerStop(FMLServerStoppingEvent event) {

    }

    public static ExampleMod getInstance() {
        return instance;
    }
}