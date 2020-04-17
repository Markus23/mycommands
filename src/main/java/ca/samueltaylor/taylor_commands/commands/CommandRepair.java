package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class CommandRepair {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("repair");
        literal.requires(source -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandRepair::execute);

        dispatcher.register(literal);
        LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("rp").
                requires(source -> {
                    return Permission.hasperm(source, literal);
                }).executes(CommandRepair::execute);
        dispatcher.register(literal1);
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ItemStack item = player.getMainHandStack();
        ChatMessage chat = new ChatMessage(player);

        if (item.isDamaged()) {
            item.setDamage(0);
//            player.sendMessage(new TranslatableText("commands.repair.done"), false);
            chat.send("Item repaired!");
        } else {
//            player.sendMessage(new TranslatableText("commands.repair.failure"), false);
            chat.send("Item is not damaged!");
        }
        return Command.SINGLE_SUCCESS;
    }
}
