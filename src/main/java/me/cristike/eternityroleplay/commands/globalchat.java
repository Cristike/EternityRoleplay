package me.cristike.eternityroleplay.commands;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class globalchat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("globalchat")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("eternityroleplay.chat")) {
                    if (Main.characters.containsKey(p.getUniqueId())) {
                        if (Main.localChat.contains(p.getUniqueId())) {
                            Main.localChat.remove(p.getUniqueId());
                            p.sendMessage(Util.color(Main.instance.getConfig().getString("ChatSetGlobal")));
                        }
                        else p.sendMessage(Util.color(Main.instance.getConfig().getString("AlreadyInGlobalChat")));
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
