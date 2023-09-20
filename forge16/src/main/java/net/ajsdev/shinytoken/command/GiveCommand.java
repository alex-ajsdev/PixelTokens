package net.ajsdev.shinytoken.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.executor.Argument;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Completable;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.command.completion.player.PlayerTabCompleter;
import com.envyful.api.forge.player.ForgeEnvyPlayer;
import net.ajsdev.shinytoken.util.TokenUtils;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;

/**
 * Command for giving shiny tokens to a specific player.
 */
@Command(
        value = {
                "give",
                "g"
        }
)
@Permissible("net.ajsdev.shinytoken.command.give")
public final class GiveCommand {
        private static final String GIVE_MESSAGE = "[ShinyToken] You gave %s %d shiny tokens.";
        @CommandProcessor
        public void onCommand(@Sender ICommandSource sender,
                              @Completable(PlayerTabCompleter.class) @Argument(tabComplete = true) ForgeEnvyPlayer target,
                              @Argument(defaultValue = "1") int amount) {

                sender.sendMessage(UtilChatColour.colour(String.format(GIVE_MESSAGE, target.getName(), amount)), Util.NIL_UUID);
                TokenUtils.giveToken(target, amount);
        }
}
