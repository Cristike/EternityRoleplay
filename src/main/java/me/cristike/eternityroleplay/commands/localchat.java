package me.cristike.eternityroleplay.commands;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class localchat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("localchat")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("eternityroleplay.chat")) {
                    if (Main.characters.containsKey(p.getUniqueId())) {
                        if (!Main.localChat.contains(p.getUniqueId())) {
                            Main.localChat.add(p.getUniqueId());
                            p.sendMessage(Util.color(Main.instance.getConfig().getString("ChatSetLocal")));
                        }
                        else p.sendMessage(Util.color(Main.instance.getConfig().getString("AlreadyInLocalChat")));
                    }
                    else p.sendMessage(Util.color(Main.instance.getConfig().getString("NoCharacter")));
                }
                else p.sendMessage(Util.color(Main.instance.getConfig().getString("NoPerm")));
            }
            else sender.sendMessage(Util.color("&cOnly players can do this"));
        }
        return true;
    }
}
