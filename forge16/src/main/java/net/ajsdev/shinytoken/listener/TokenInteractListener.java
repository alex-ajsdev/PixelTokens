package net.ajsdev.shinytoken.listener;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.ajsdev.shinytoken.PixelTokens;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listener handling interactions when players use Shiny Tokens on Pixelmon entities.
 */
public final class TokenInteractListener {

    private static final String NON_OWNER_MESSAGE = PixelTokens.MSG_PREFIX + "You don't own this pokemon!";
    private static final String ALREADY_SHINY_MESSAGE = PixelTokens.MSG_PREFIX + "This pokemon is already shiny!";


    /**
     * Handles the event when a player right-clicks on an entity.
     */
    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent.EntityInteract event) {
        handleShinyTokenInteraction(event);
    }

    /**
     * Validates and processes the interaction when a player uses a shiny token on a Pixelmon.
     */
    private void handleShinyTokenInteraction(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        ItemStack itemStack = event.getItemStack();

        // Check for empty hand or if the held item isn't a shiny token.
        if (itemStack.isEmpty()) return;
        if (!itemStack.getOrCreateTag().contains(PixelTokens.NBT_TAG)) return;

        // Ensure the right-clicked entity is a Pixelmon.
        if (!(target instanceof PixelmonEntity)) return;

        Pokemon pokemon = ((PixelmonEntity) target).getPokemon();
        // Check if the player is the owner of the Pixelmon.
        if (pokemon.getOwnerPlayerUUID() == null || !pokemon.getOwnerPlayerUUID().equals(player.getUUID())) {
            endEvent(event, NON_OWNER_MESSAGE);
            return;
        }

        // Prevent using the token on already shiny Pixelmon.
        if (pokemon.isShiny()) {
            endEvent(event, ALREADY_SHINY_MESSAGE);
            return;
        }

        // Convert the Pixelmon to shiny and reduce the token count.
        pokemon.setShiny(true);
        itemStack.shrink(1);
        PixelTokens.getLogger().info(String.format("%s used a shiny token.", player.getDisplayName().getString()));
        endEvent(event, null);
    }

    /**
     * Sends an optional message to the player and cancels the event to prevent further processing.
     */
    private void endEvent(PlayerInteractEvent.EntityInteract event, String message) {
        if (message != null) {
            PlayerEntity player = event.getPlayer();
            player.sendMessage(new StringTextComponent(message), Util.NIL_UUID);
        }
        event.setCanceled(true);
        event.setCancellationResult(ActionResultType.FAIL);
    }
}
