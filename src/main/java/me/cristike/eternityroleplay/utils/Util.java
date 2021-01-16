package me.cristike.eternityroleplay.utils;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.enums.RomanNumeral;
import me.cristike.eternityroleplay.objects.Nation;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static final String RAW_HEX_REGEX = "<#[A-Fa-f0-9]{6}>";

    public static String color(String mess) {
        Matcher matcher = Pattern.compile(RAW_HEX_REGEX).matcher(mess);
        int hexAmount = 0;
        while (matcher.find()) {
            matcher.region(matcher.end() - 1, mess.length());
            hexAmount++;
        }
        int startIndex = 0;
        for (int hexIndex = 0; hexIndex < hexAmount; hexIndex++) {
            int msgIndex = mess.indexOf("<#", startIndex);
            String hex = mess.substring(msgIndex + 1, msgIndex + 8);
            startIndex = msgIndex + 2;
            mess = mess.replace("<" + hex + ">", net.md_5.bungee.api.ChatColor.of(hex) + "");
        }

        return ChatColor.translateAlternateColorCodes('&', mess);
    }

    public static List<String> colorList(List<String> list) {
        for (int i = 0; i < list.size(); i++) list.set(i, color(list.get(i)));
        return list;
    }

    public static boolean isNumber(String n) {
        try {
            Integer.parseInt(n);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static void sendList(Player p, List<String> list) {
        for (String string : list) p.sendMessage(Util.color(string));
    }

    public static void blindPlayer(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255));
    }

    public static void unblindPlayer(Player p) {
        p.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    public static ItemStack configToItem(String section) {
        ItemStack i = new ItemStack(Material.valueOf(Main.instance.getConfig().getString(section + ".material")));
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(color(Main.instance.getConfig().getString(section + ".displayName")));
        if (!Main.instance.getConfig().getStringList(section + ".lore").isEmpty())
            m.setLore(colorList(Main.instance.getConfig().getStringList(section + ".lore")));
        i.setItemMeta(m);
        return i;
    }

    public static Location configToLocation(String section) {
        World world = Bukkit.getWorld(Main.instance.getConfig().getString(section + ".world"));
        double x = Main.instance.getConfig().getDouble(section + ".x");
        double y = Main.instance.getConfig().getDouble(section + ".y");
        double z = Main.instance.getConfig().getDouble(section + ".z");
        int yaw = Main.instance.getConfig().getInt(section + ".yaw");
        int pitch = Main.instance.getConfig().getInt(section + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Inventory nationsMenu(int pag) {
        Inventory i = Bukkit.createInventory(null, 27, color("&8Nations " + RomanNumeral.arabicToRoman(pag)));
        ItemStack[] items = new ItemStack[4];
        String[] keys = Main.instance.getConfig().getConfigurationSection("Nations").getKeys(false).stream().toArray(String[]::new);
        int nr = 0;

        for (int k = 4 * (pag-1); k < pag * 4 - 1; k++) {
            if (k > keys.length-1) break;
            Main.nations.put(keys[k], new Nation(configToLocation("Nations." + keys[k] + ".spawn")));
            nr++;
            items[nr-1] = configToItem("Nations." + keys[k] + ".Menu Item");
        }

        switch (nr) {
            case 1:
                i.setItem(13, items[0]);
                break;
            case 2:
                i.setItem(12, items[0]);
                i.setItem(14, items[1]);
                break;
            case 3:
                i.setItem(11, items[0]);
                i.setItem(13, items[1]);
                i.setItem(15, items[2]);
                break;
            case 4:
                i.setItem(10, items[0]);
                i.setItem(12, items[1]);
                i.setItem(14, items[2]);
                i.setItem(16, items[3]);
                break;
        }
        if (keys.length > pag * 4) i.setItem(26, configToItem("NextPageItem"));
        if (pag != 1) i.setItem(18, configToItem("PreviousPageItem"));
        if (Main.instance.getConfig().getBoolean("FillInventory"))
            for (int k = 0; k < 27; k++)
                if (i.getItem(k) == null)
                    i.setItem(k, configToItem("Menu Fill"));
        return i;
    }

    public static void loadNations() {
        int pages;
        pages = Main.instance.getConfig().getConfigurationSection("Nations").getKeys(false).size() / 4;
        if (Main.instance.getConfig().getConfigurationSection("Nations").getKeys(false).size() % 4 != 0) pages++;

        for (int i = 1; i <= pages; i++) {
            Main.indexedMenus.put("nations " + i, nationsMenu(i));
        }
    }

    private static Inventory religionsMenu(int pag) {
        Inventory i = Bukkit.createInventory(null, 27, color("&8Religions " + RomanNumeral.arabicToRoman(pag)));
        ItemStack[] items = new ItemStack[4];
        String[] keys = Main.instance.getConfig().getConfigurationSection("Religions").getKeys(false).stream().toArray(String[]::new);
        int nr = 0;

        for (int k = 4 * (pag-1); k < pag * 4 - 1; k++) {
            if (k > keys.length-1) break;
            nr++;
            items[nr-1] = configToItem("Religions." + keys[k] + ".Menu Item");
        }

        switch (nr) {
            case 1:
                i.setItem(13, items[0]);
                break;
            case 2:
                i.setItem(12, items[0]);
                i.setItem(14, items[1]);
                break;
            case 3:
                i.setItem(11, items[0]);
                i.setItem(13, items[1]);
                i.setItem(15, items[2]);
                break;
            case 4:
                i.setItem(10, items[0]);
                i.setItem(12, items[1]);
                i.setItem(14, items[2]);
                i.setItem(16, items[3]);
                break;
        }
        if (keys.length > pag * 4) i.setItem(26, configToItem("NextPageItem"));
        if (pag != 1) i.setItem(18, configToItem("PreviousPageItem"));
        if (Main.instance.getConfig().getBoolean("FillInventory"))
            for (int k = 0; k < 27; k++)
                if (i.getItem(k) == null)
                    i.setItem(k, configToItem("Menu Fill"));
        return i;
    }

    public static void loadReligions() {
        int pages;
        pages = Main.instance.getConfig().getConfigurationSection("Religions").getKeys(false).size() / 4;
        if (Main.instance.getConfig().getConfigurationSection("Religions").getKeys(false).size() % 4 != 0) pages++;

        for (int i = 1; i <= pages; i++) {
            Main.indexedMenus.put("religions " + i, religionsMenu(i));
        }
    }

    public static void deleteCharacter(UUID uuid) {
        Main.characters.remove(uuid);
        Main.db.deleteCharacter(uuid);
    }
}
