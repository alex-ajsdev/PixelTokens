package net.ajsdev.shinytoken.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.executor.Argument;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.player.ForgeEnvyPlayer;
import net.ajsdev.shinytoken.ShinyToken;
import net.ajsdev.shinytoken.util.TokenUtils;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;

/**
 * Command for giving shiny tokens to all online players.
 */
@Command(
        value = {
                "giveall",
                "ga"
        }
)
@Permissible("net.ajsdev.shinytoken.command.giveall")
public final class GiveAllCommand {
        private static final String GIVEALL_MESSAGE = "[ShinyToken] You gave %d shiny tokens to all online players.";
        @CommandProcessor
        public void onCommand(@Sender ICommandSource sender,
                              @Argument(defaultValue = "1") int amount) {
                sender.sendMessage(UtilChatColour.colour(String.format(GIVEALL_MESSAGE, amount)), Util.NIL_UUID);
                for(ForgeEnvyPlayer player : ShinyToken.getPlayerManager().getOnlinePlayers()) {
                        TokenUtils.giveToken(player, amount);
                }
        }
}
