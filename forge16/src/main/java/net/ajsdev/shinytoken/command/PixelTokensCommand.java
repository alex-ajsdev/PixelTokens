package net.ajsdev.shinytoken.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ajsdev.shinytoken.PixelTokens;
import net.ajsdev.simplecommands.Argument;
import net.ajsdev.simplecommands.SimpleCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class PixelTokensCommand extends SimpleCommand {
    @Override
    protected List<String> addAliases() {
        return List.of(
                "pixeltokens",
                "pt"
        );
    }

    @Override
    protected List<Argument> addArguments() {
        return null;
    }

    @Override
    protected List<SimpleCommand> addSubCommands() {
        return List.of(
                new GiveCommand()
        );
    }

    @Override
    protected int addPermissionLevel() {
        return 2;
    }

    @Override
    protected int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String msg = PixelTokens.MSG_PREFIX + "This server is running PixelTokens " + PixelTokens.VERSION +
                ". For more information visit https://github.com/alex-ajsdev/PixelTokens";
        ITextComponent message = new StringTextComponent(msg);
        context.getSource().sendSuccess(message, false);
        return 0;
    }
}
