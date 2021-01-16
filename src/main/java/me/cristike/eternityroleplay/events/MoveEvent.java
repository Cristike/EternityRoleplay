package me.cristike.eternityroleplay.events;

import me.cristike.eternityroleplay.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void freeze(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!Main.instance.getConfig().getBoolean("freeze")) return;
        if (Main.characters.containsKey(p.getUniqueId())) return;
        e.setCancelled(true);
    }
}
