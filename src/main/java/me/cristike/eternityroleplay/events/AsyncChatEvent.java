package me.cristike.eternityroleplay.events;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncChatEvent implements Listener {

    @EventHandler
    public void localChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!Main.localChat.contains(p.getUniqueId())) return;
        if (e.isCancelled()) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                int radius = Main.instance.getConfig().getInt("localchat.radius");
                String mess = "";
                if (e.getMessage().startsWith(Main.instance.getConfig().getString("localchat.emotionSymbol")))
                    mess = Util.color(Main.instance.getConfig().getString("localchat.emotionFormat"))
                            .replace("{name}", Main.characters.get(p.getUniqueId()).getName())
                            .replace("{message}", e.getMessage());
                else if (e.getMessage().startsWith(Main.instance.getConfig().getString("localchat.whisperSymbol"))) {
                    radius = Main.instance.getConfig().getInt("localchat.whisperRadius");
                    mess = Util.color(Main.instance.getConfig().getString("localchat.emotionFormat"))
                            .replace("{name}", Main.characters.get(p.getUniqueId()).getName())
                            .replace("{message}", e.getMessage());
                    sendLocalMessage(p, mess, radius);
                }
                else if (e.getMessage().startsWith(Main.instance.getConfig().getString("localchat.shoutSymbol"))) {
                    radius = Main.instance.getConfig().getInt("localchat.shoutRadius");
                    mess = Util.color(Main.instance.getConfig().getString("localchat.emotionFormat"))
                            .replace("{name}", Main.characters.get(p.getUniqueId()).getName())
                            .replace("{message}", e.getMessage());
                    sendLocalMessage(p, mess, radius);
                }
                else
                    mess = Util.color(Main.instance.getConfig().getString("localchat.format")
                            .replace("{name}", Main.characters.get(p.getUniqueId()).getName()))
                            .replace("{message}", e.getMessage());
                sendLocalMessage(p, mess, radius);
                p.sendMessage(mess);
            }
        }.runTaskLater(Main.instance, 1);

        e.setCancelled(true);
    }

    private void sendLocalMessage(Player p, String mess, int radius) {
        for (Entity en : p.getNearbyEntities(radius, radius, radius))
            if (en instanceof Player)
                en.sendMessage(mess);
    }
}
