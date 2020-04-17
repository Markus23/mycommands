package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;


public class CommandFly {
    public static PlayerAbilities pla = new PlayerAbilities();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("fly");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandFly::execute)
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .executes(CommandFly::execut));

        dispatcher.register(literal);
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(playerEntity);

        if (playerEntity.interactionManager.getGameMode() == GameMode.SURVIVAL || playerEntity.interactionManager.getGameMode() == GameMode.ADVENTURE) {
            if (!playerEntity.abilities.allowFlying) {
                playerEntity.abilities.allowFlying = true;
                playerEntity.sendAbilitiesUpdate();

//                playerEntity.sendMessage(new TranslatableText("commands.fly.enabled"), false);
                chat.send("You can now fly!");
            } else {
                playerEntity.abilities.allowFlying = false;
                playerEntity.abilities.flying = false;
                playerEntity.sendAbilitiesUpdate();

//                playerEntity.sendMessage(new TranslatableText("commands.fly.disabled"), false);
                chat.send("You can no longer fly!");
            }
        } else {
//            playerEntity.sendMessage(new TranslatableText("commands.fly.error"), false);
            chat.send("Fly: An error occurred!");
        }

        return 1;
    }

    private static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        ServerPlayerEntity requestedPlayer = Command.getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));
        ChatMessage chatRP = new ChatMessage(requestedPlayer);
        ChatMessage chatSP = new ChatMessage(playerEntity);

        if (playerEntity.interactionManager.getGameMode() == GameMode.SURVIVAL || playerEntity.interactionManager.getGameMode() == GameMode.ADVENTURE) {
            if (!requestedPlayer.abilities.allowFlying) {
                requestedPlayer.abilities.allowFlying = true;
                requestedPlayer.sendAbilitiesUpdate();

//                requestedPlayer.sendMessage(new TranslatableText("commands.fly.enabled"), false);
                chatRP.send("You can now fly!");
                chatSP.send(requestedPlayer.getName() + " can now fly!");

            } else {
                requestedPlayer.abilities.allowFlying = false;
                requestedPlayer.abilities.flying = false;
                requestedPlayer.sendAbilitiesUpdate();

//                requestedPlayer.sendMessage(new TranslatableText("commands.fly.disabled"), false);
                chatRP.send("You can no longer fly!");
                chatSP.send(requestedPlayer.getName() + " can no longer fly!");
            }
        } else {
//            playerEntity.sendMessage(new TranslatableText("commands.fly.error"), false);
            chatSP.send("Fly: An error occurred!");
        }

        return 1;
    }

}