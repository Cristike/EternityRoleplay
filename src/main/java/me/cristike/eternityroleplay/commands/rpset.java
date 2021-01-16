package me.cristike.eternityroleplay.commands;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.objects.Character;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class rpset implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rpset")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("eternityroleplay.set")) {
                    if (Main.creatingCharacter.containsKey(p.getUniqueId())) {
                        if (args.length == 0) Util.sendList(p, Main.instance.getConfig().getStringList("RPSet Help"));
                        else if (args.length == 1) {
                            switch (args[0]) {
                                case "help":
                                    Util.sendList(p, Main.instance.getConfig().getStringList("RPSet Help"));
                                    break;
                                case "nation":
                                    setNation(p);
                                    break;
                                case "religion":
                                    setReligion(p);
                                    break;
                                case "clear":
                                    clear(p.getUniqueId());
                                    break;
                                default:
                                    p.sendMessage(Util.color(Main.instance.getConfig().getString("UnknownSyntax")));
                                    break;
                            }
                        }
                        else if (args.length == 2) {
                            switch (args[0]) {
                                case "name":
                                    setName(args[1], p);
                                    break;
                                case "age":
                                    setAge(args[1], p);
                                    break;
                                case "gender":
                                    setGender(args[1], p);
                                    break;
                                default:
                                    p.sendMessage(Util.color(Main.instance.getConfig().getString("UnknownSyntax")));
                                    break;
                            }
                        }
                        else {
                            if (args[0].equalsIgnoreCase("name")) {
                                StringBuilder name = new StringBuilder();
                                for (int i = 1; i < args.length; i++)
                                     if (i == 1) name.append(args[1]);
                                     else name.append(" ").append(args[i]);
                                setName(name.toString(), p);
                            }
                            else p.sendMessage(Util.color(Main.instance.getConfig().getString("UnknownSyntax")));
                        }
                    }
                    else p.sendMessage(Util.color(Main.instance.getConfig().getString("AlreadyCreated")));
                }
                else p.sendMessage(Util.color(Main.instance.getConfig().getString("NoPerm")));
            }
            else sender.sendMessage(Util.color("&cOnly players can do this"));
        }
        return true;
    }

    private void setNation(Player p) {
        Inventory i = Main.indexedMenus.get("nations 1");
        p.openInventory(i);
    }

    private void setReligion(Player p) {
        Inventory i = Main.indexedMenus.get("religions 1");
        p.openInventory(i);
    }

    private void setName(String name, Player p) {
        if (name.replace(" ", "").matches("[a-zA-Z]+")) {
            if (Main.instance.getConfig().getStringList("Names Blacklist").contains(name))
                p.sendMessage(Util.color(Main.instance.getConfig().getString("BlacklistName")));
            else {
                if (name.length() > Main.instance.getConfig().getInt("NameMaxLength"))
                    p.sendMessage(Util.color(Main.instance.getConfig().getString("NameTooLong")
                            .replace("{length}", Main.instance.getConfig().getInt("NameMaxLength") + "")));
                else if (name.length() < Main.instance.getConfig().getInt("NameMinLength"))
                    p.sendMessage(Util.color(Main.instance.getConfig().getString("NameTooShort")
                            .replace("{length}", Main.instance.getConfig().getInt("NameMinLength") + "")));
                else {
                    Main.creatingCharacter.get(p.getUniqueId()).setName(name);
                    p.sendMessage(Util.color(Main.instance.getConfig().getString("NameSet").
                            replace("{name}", name)));
                }
            }

        }
        else p.sendMessage(Util.color(Main.instance.getConfig().getString("InvalidName")));
    }

    private void  setAge(String age, Player p) {
        if (Util.isNumber(age)) {
            int a = Integer.parseInt(age);
            if (a > Main.instance.getConfig().getInt("MaxAge"))
                p.sendMessage(Util.color(Main.instance.getConfig().getString("AgeTooAdvanced").replace("{max}", Main.instance.getConfig().getInt("MaxAge") + "")));
            else if (a < Main.instance.getConfig().getInt("MinAge"))
                p.sendMessage(Util.color(Main.instance.getConfig().getString("AgeTooYoung").replace("{min}", Main.instance.getConfig().getInt("MinAge") + "")));
            else {
                Main.creatingCharacter.get(p.getUniqueId()).setAge(a);
                p.sendMessage(Util.color(Main.instance.getConfig().getString("AgeSet").replace("{age}", String.valueOf(a))));
            }
        }
        else p.sendMessage(Util.color(Main.instance.getConfig().getString("InvalidAge")));
    }

    private void setGender(String gender, Player p) {
        if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
            Main.creatingCharacter.get(p.getUniqueId()).setGender(gender.toLowerCase());
            p.sendMessage(Util.color(Main.instance.getConfig().getString("GenderSet")));
        }
        else p.sendMessage(Util.color(Main.instance.getConfig().getString("InvalidGender")));
    }

    private void clear(UUID uuid) {
        Main.creatingCharacter.remove(uuid);
        Main.creatingCharacter.put(uuid, new Character());
    }
}
