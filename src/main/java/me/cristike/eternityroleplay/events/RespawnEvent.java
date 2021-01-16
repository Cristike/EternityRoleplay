package me.cristike.eternityroleplay.events;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (!Main.instance.getConfig().getBoolean("teleportToSpawnOnDeath")) return;
        p.teleport(Util.configToLocation("spawn"));
    }
}
