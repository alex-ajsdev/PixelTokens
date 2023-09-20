package net.ajsdev.shinytoken.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.forge.chat.UtilChatColour;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;

/**
 * Base command for ShinyToken.
 */
@Command(
        value = {
                "shinytoken",
                "st"
        }
)
@Permissible("net.ajsdev.shinytoken.command")
@SubCommands({GiveCommand.class, GiveAllCommand.class})
public final class ShinyTokenCommand {
        private static final String TOKEN_MESSAGE = "[ShinyToken] You can give tokens with /shinytoken give <name> <amount>";

        @CommandProcessor
        public void onCommand(@Sender ICommandSource sender) {
                sender.sendMessage(UtilChatColour.colour(TOKEN_MESSAGE), Util.NIL_UUID);
        }
}
