package net.ajsdev.shinytoken.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ajsdev.shinytoken.PixelTokens;
import net.ajsdev.shinytoken.util.TokenUtils;
import net.ajsdev.simplecommands.Argument;
import net.ajsdev.simplecommands.SimpleCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;
import java.util.List;

public class GiveCommand extends SimpleCommand {

    @Override
    protected List<String> addAliases() {
        return List.of(
                "give",
                "g"
        );
    }

    @Override
    protected List<Argument> addArguments() {
        return List.of(
                new Argument("targets", EntityArgument.players()),
                new Argument("amount", IntegerArgumentType.integer(1, 64))
        );
    }

    @Override
    protected List<SimpleCommand> addSubCommands() {
        return null;
    }

    @Override
    protected int addPermissionLevel() {
        return 2;
    }

    @Override
    protected int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {

        Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        for(ServerPlayerEntity player : players) {
            TokenUtils.giveToken(player, amount);
        }
        String msg = PixelTokens.MSG_PREFIX + "You gave %d shiny tokens to your target.";
        ITextComponent message = new StringTextComponent(String.format(msg, amount));
        context.getSource().sendSuccess(message, false);
        return 1;
    }
}
