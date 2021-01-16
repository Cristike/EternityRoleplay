package me.cristike.eternityroleplay.events;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Main.localChat.remove(p.getUniqueId());
        if (Main.characters.containsKey(p.getUniqueId())) return;
        if (Main.instance.getConfig().getBoolean("blindness")) Util.unblindPlayer(p);
        Main.creatingCharacter.remove(p.getUniqueId());
    }
}
