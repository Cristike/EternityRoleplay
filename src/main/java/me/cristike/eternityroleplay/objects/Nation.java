package me.cristike.eternityroleplay.objects;

import org.bukkit.Location;

public class Nation {
    private Location spawn;

    public Nation(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }
}
