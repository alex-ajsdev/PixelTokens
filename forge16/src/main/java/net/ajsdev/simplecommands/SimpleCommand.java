package net.ajsdev.simplecommands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SimpleCommand {
    private final String name;
    private final List<String> aliases = new ArrayList<>();
    private final List<SimpleCommand> subCommands = new ArrayList<>();
    private final List<Argument> arguments = new ArrayList<>();
    private final int permissionLevel;
    private int argumentChainIndex = 0;
    private final List<LiteralArgumentBuilder<CommandSource>> nodes = new ArrayList<>();
    protected SimpleCommand() {
        List<String> names = new ArrayList<>(addAliases());
        Collections.sort(names);
        this.name = names.remove(0);
        if(!names.isEmpty()) this.aliases.addAll(names);

        List<Argument> args = addArguments();
        if (args != null) this.arguments.addAll(args);

        List<SimpleCommand> subs = addSubCommands();
        if (subs != null)  this.subCommands.addAll(subs);

        this.permissionLevel = addPermissionLevel();

        this.build();
    }
    protected abstract List<String> addAliases();
    protected abstract List<Argument> addArguments();
    protected abstract List<SimpleCommand> addSubCommands();
    protected abstract int addPermissionLevel();
    protected abstract int execute(CommandContext<CommandSource> context) throws CommandSyntaxException;

    public void register(CommandDispatcher<CommandSource> dispatcher) {
        for(LiteralArgumentBuilder<CommandSource> command : nodes) {
            dispatcher.register(command);
        }
    }
    private void build() {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal(name);
        builder.requires((req) -> req.hasPermission(permissionLevel));
        this.buildArguments(builder);
        this.buildSubCommands(builder);
        this.nodes.add(builder);
        this.buildAliases(builder.build());
    }
    private void buildArguments(LiteralArgumentBuilder<CommandSource> builder) {
        if (!arguments.isEmpty()) {
            ArgumentBuilder<CommandSource, ?> argumentChain = buildArgumentChain();
            builder.then(argumentChain);
        } else {
            builder.executes(this::execute);
        }
    }
    private ArgumentBuilder<CommandSource, ?> buildArgumentChain() {
        if (argumentChainIndex >= arguments.size()) {
            return null;
        }

        Argument currentArg = arguments.get(argumentChainIndex);
        RequiredArgumentBuilder<CommandSource, ?> currentBuilder = Commands.argument(currentArg.getName(), currentArg.getType());

        if (isLastArgument()) {
            currentBuilder.executes(this::execute);
        } else {
            attachNextBuilderToCurrent(currentBuilder);
        }

        return currentBuilder;
    }

    private boolean isLastArgument() {
        return argumentChainIndex == arguments.size() - 1;
    }

    private void attachNextBuilderToCurrent(RequiredArgumentBuilder<CommandSource, ?> currentBuilder) {
        argumentChainIndex++;
        ArgumentBuilder<CommandSource, ?> nextBuilder = buildArgumentChain();
        if (nextBuilder != null) {
            currentBuilder.then(nextBuilder);
        }
    }

    private void buildSubCommands(LiteralArgumentBuilder<CommandSource> builder) {
        for (SimpleCommand subCommand : subCommands) {
            for (LiteralArgumentBuilder<CommandSource> subNode : subCommand.nodes) {
                builder.then(subNode);
            }
        }
    }
    private void buildAliases(LiteralCommandNode<CommandSource> commandNode) {
        for (String alias : aliases) {
            nodes.add(
                    Commands.literal(alias)
                            .redirect(commandNode)
                            .requires((req) -> req.hasPermission(permissionLevel))
            );
        }
    }
}


