package me.cristike.eternityroleplay.commands;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.objects.Character;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class card implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("card")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("eternityroleplay.card")) {
                    if (args.length == 0) {
                        if (Main.characters.containsKey(p.getUniqueId())) {
                            Character c = Main.characters.get(p.getUniqueId());
                            for (String string : Main.instance.getConfig().getStringList("RPInfo")) {
                                string = string.replace("{name}", c.getName())
                                        .replace("{age}", String.valueOf(c.getAge()))
                                        .replace("{nation}", c.getNationName())
                                        .replace("{religion}", c.getReligion())
                                        .replace("{player}", p.getName());
                                if (string.contains("{gender}")) {
                                    if (c.getGender().equals("f"))
                                        string = string.replace("{gender}", Main.instance.getConfig().getString("Female"));
                                    else string = string.replace("{gender}", Main.instance.getConfig().getString("Male"));
                                }
                                p.sendMessage(Util.color(string));
                            }
                        }
                        else p.sendMessage(Util.color(Main.instance.getConfig().getString("NoCharacter")));
                    }
                    else if (args.length == 1) {
                        Player t = Bukkit.getPlayer(args[0]);
                        if (t != null) {
                            if (t.isOnline()) {
                                if (Main.characters.containsKey(t.getUniqueId())) {
                                    Character c = Main.characters.get(t.getUniqueId());
                                    for (String string : Main.instance.getConfig().getStringList("RPInfo")) {
                                        string = string.replace("{name}", c.getName())
                                                .replace("{age}", String.valueOf(c.getAge()))
                                                .replace("{nation}", c.getNationName())
                                                .replace("{religion}", c.getReligion())
                                                .replace("{player}", p.getName());
                                        if (string.contains("{gender}")) {
                                            if (c.getGender().equals("f"))
                                                string = string.replace("{gender}", Main.instance.getConfig().getString("Female"));
                                            else string = string.replace("{gender}", Main.instance.getConfig().getString("Male"));
                                        }
                                        t.sendMessage(Util.color(string));
                                    }
                                }
                                else p.sendMessage(Util.color(Main.instance.getConfig().getString("NoCharacter")));
                            }
                            else p.sendMessage(Util.color(Main.instance.getConfig().getString("OfflinePlayer")));
                        }
                        else p.sendMessage(Util.color(Main.instance.getConfig().getString("OfflinePlayer")));
                    }
                    else p.sendMessage(Util.color(Main.instance.getConfig().getString("UnknownSyntax")));
                }
                else p.sendMessage(Util.color(Main.instance.getConfig().getString("NoPerm")));
            }
            else sender.sendMessage(Util.color("&cOnly players can do this"));
        }
        return true;
    }
}
