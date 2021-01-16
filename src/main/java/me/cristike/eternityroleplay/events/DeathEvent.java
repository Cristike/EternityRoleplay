package me.cristike.eternityroleplay.events;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = (Player) e.getEntity();
        if (!Main.instance.getConfig().getBoolean("deleteCharacterOnDeath")) return;
        if (!Main.characters.containsKey(p.getUniqueId())) return;
        Util.deleteCharacter(p.getUniqueId());
    }
}
