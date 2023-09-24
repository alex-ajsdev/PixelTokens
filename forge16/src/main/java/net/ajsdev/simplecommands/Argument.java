package net.ajsdev.simplecommands;

import com.mojang.brigadier.arguments.ArgumentType;

public class Argument {
    private String name;
    private ArgumentType<?> type;

    public Argument(String name, ArgumentType<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ArgumentType<?> getType() {
        return type;
    }
}
