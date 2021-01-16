package me.cristike.eternityroleplay.commands;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.objects.Character;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class finishcharacter implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("finishcharacter")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("eternityroleplay.finish")) {
                    if (Main.creatingCharacter.containsKey(p.getUniqueId())) {
                        Character c = Main.creatingCharacter.get(p.getUniqueId());
                        if (c.getName() != null && c.getAge() != 0 && c.getGender() != null && c.getNation() != null && c.getReligion() != null) {
                            Main.characters.put(p.getUniqueId(), c);
                            Main.creatingCharacter.remove(p.getUniqueId());
                            p.sendMessage(Util.color(Main.instance.getConfig().getString("CharacterCreated")));
                            if (Main.instance.getConfig().getBoolean("blindness")) Util.unblindPlayer(p);
                            if (Main.instance.getConfig().getBoolean("hidePlayers")) {
                                for (Player t : Bukkit.getOnlinePlayers())
                                    if (!t.getUniqueId().equals(p.getUniqueId())) {
                                        p.showPlayer(Main.instance, t);
                                        t.showPlayer(Main.instance, p);
                                    }
                            }
                            p.teleport(c.getNation().getSpawn());
                        }
                        else p.sendMessage(Util.color(Main.instance.getConfig().getString("RequirementsNotFulfilled")));
                    }
                    else p.sendMessage(Util.color(Main.instance.getConfig().getString("AlreadyCreated")));
                }
                else p.sendMessage(Util.color(Main.instance.getConfig().getString("NoPerm")));
            }
            else sender.sendMessage(Util.color("&cOnly players can do this"));
        }
        return true;
    }
}
