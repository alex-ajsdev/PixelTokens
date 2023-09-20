package net.ajsdev.shinytoken.util;

import com.envyful.api.forge.player.ForgeEnvyPlayer;
import net.ajsdev.shinytoken.ShinyToken;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

/**
 * Utility class for creating and managing Shiny Tokens.
 */
public final class TokenUtils {

    private static final net.minecraft.item.Item TOKEN_ITEM_TYPE = Items.EMERALD;
    private static final String TOKEN_NAME = TextFormatting.AQUA + "Shiny Token";
    private static final List<String> TOKEN_LORE = List.of(
            TextFormatting.GRAY + "Right click on a pokemon to use!"
    );
    private static final String RECEIVED_MESSAGE = "[ShinyToken] You received %d shiny tokens!";
    private static final boolean SHOULD_GLOW = true;

    /**
     * Gives a specified amount of Shiny Tokens to the player.
     */
    public static void giveToken(ForgeEnvyPlayer player, int amount) {
        player.getParent().addItem(createToken(amount));
        player.message(String.format(RECEIVED_MESSAGE, amount));
    }

    /**
     * Creates and returns a Shiny Token item with the specified amount.
     */
    private static ItemStack createToken(int amount) {
        ItemStack token = new ItemStack(TOKEN_ITEM_TYPE, amount);
        CompoundNBT nbt = token.getOrCreateTag();


        nbt.putInt(ShinyToken.NBT_TAG, 1); // Tag identifying the item as a shiny token
        addDisplayName(nbt);
        addLore(nbt);
        nbt.putString("tooltip", ""); // Hide Pixelmon tooltip

        if (SHOULD_GLOW) {
            nbt.putInt("HideFlags", 1); // Hide enchantment data for "glow"
            addEnchantments(nbt);
        }

        token.setTag(nbt);
        return token;
    }

    /**
     * Adds the display name to the item's NBT data.
     */
    private static void addDisplayName(CompoundNBT nbt) {
        CompoundNBT display = nbt.getCompound("display");
        StringTextComponent nameComponent = new StringTextComponent(TOKEN_NAME);
        display.putString("Name", ITextComponent.Serializer.toJson(nameComponent));
        nbt.put("display", display);
    }

    /**
     * Adds the lore to the item's NBT data.
     */
    private static void addLore(CompoundNBT nbt) {
        CompoundNBT display = nbt.getCompound("display");
        ListNBT loreList = new ListNBT();
        for (String loreLine : TOKEN_LORE) {
            StringTextComponent loreComponent = new StringTextComponent(loreLine);
            loreList.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(loreComponent)));
        }
        display.put("Lore", loreList);
        nbt.put("display", display);
    }

    /**
     * Adds enchantments to the item's NBT data for the "glow" effect without actual enchantment.
     */
    private static void addEnchantments(CompoundNBT nbt) {
        ListNBT enchantments = new ListNBT();
        enchantments.add(new CompoundNBT());
        nbt.put("Enchantments", enchantments);
    }
}