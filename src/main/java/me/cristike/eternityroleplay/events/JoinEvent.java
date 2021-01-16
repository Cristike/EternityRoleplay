package me.cristike.eternityroleplay.events;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.objects.Character;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void characterCheck(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Main.characters.containsKey(p.getUniqueId())) return;
        if (Main.instance.getConfig().getBoolean("hidePlayers")) {
            for (Player t : Bukkit.getOnlinePlayers()) {
                if (!t.getUniqueId().equals(p.getUniqueId())) {
                    p.hidePlayer(Main.instance, t);
                    t.hidePlayer(Main.instance, p);
                }
            }
        }
        if (Main.instance.getConfig().getBoolean("blindness")) Util.blindPlayer(p);
        Main.creatingCharacter.put(p.getUniqueId(), new Character());
    }
}
