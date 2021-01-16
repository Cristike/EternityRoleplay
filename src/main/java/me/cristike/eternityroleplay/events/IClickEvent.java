package me.cristike.eternityroleplay.events;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.enums.RomanNumeral;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class IClickEvent implements Listener {

    @EventHandler
    public void nationsMenuClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        if (!e.getView().getTitle().contains(Util.color("&8Nations"))) return;
        e.setCancelled(true);
        int pag = RomanNumeral.romanToArabic(ChatColor.stripColor(e.getView().getTitle()).split(" ")[1]);
        if (e.getSlot() == 18) {
            p.closeInventory();
            Inventory i = Main.indexedMenus.get("nations " + (pag - 1));
            p.openInventory(i);
        }
        else if (e.getSlot() == 26) {
            p.closeInventory();
            Inventory i = Main.indexedMenus.get("nations " + (pag + 1));
            p.openInventory(i);
        }
        else {
            String[] keys = Main.instance.getConfig().getConfigurationSection("Nations").getKeys(false).stream().toArray(String[]::new);
            for (int k = 4 * (pag-1); k < pag * 4 - 1; k++) {
                if (k > keys.length-1) break;
                if (e.getCurrentItem().isSimilar(Util.configToItem("Nations." + keys[k] + ".Menu Item"))) {
                    p.closeInventory();
                    Main.creatingCharacter.get(p.getUniqueId()).setNation(keys[k]);
                    p.sendMessage(Util.color(Main.instance.getConfig().getString("NationSet").replace("{nation}", keys[k])));
                    break;
                }
            }
        }
    }

    @EventHandler
    public void religionsMenuClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        if (!e.getView().getTitle().contains(Util.color("&8Religions"))) return;
        e.setCancelled(true);
        int pag = RomanNumeral.romanToArabic(ChatColor.stripColor(e.getView().getTitle()).split(" ")[1]);
        if (e.getSlot() == 18) {
            p.closeInventory();
            Inventory i = Main.indexedMenus.get("religions " + (pag - 1));
            p.openInventory(i);
        }
        else if (e.getSlot() == 26) {
            p.closeInventory();
            Inventory i = Main.indexedMenus.get("religions " + (pag + 1));
            p.openInventory(i);
        }
        else {
            String[] keys = Main.instance.getConfig().getConfigurationSection("Religions").getKeys(false).stream().toArray(String[]::new);
            for (int k = 4 * (pag-1); k < pag * 4 - 1; k++) {
                if (k > keys.length-1) break;
                if (e.getCurrentItem().isSimilar(Util.configToItem("Religions." + keys[k] + ".Menu Item"))) {
                    p.closeInventory();
                    Main.creatingCharacter.get(p.getUniqueId()).setReligion(keys[k]);
                    p.sendMessage(Util.color(Main.instance.getConfig().getString("ReligionSet").replace("{religion}", keys[k])));
                    break;
                }
            }
        }
    }
}
