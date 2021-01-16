package me.cristike.eternityroleplay;

import me.cristike.eternityroleplay.commands.*;
import me.cristike.eternityroleplay.database.SQLite;
import me.cristike.eternityroleplay.events.*;
import me.cristike.eternityroleplay.objects.Character;
import me.cristike.eternityroleplay.objects.Nation;
import me.cristike.eternityroleplay.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static JavaPlugin instance;
    public static SQLite db;

    public static ArrayList<UUID> localChat = new ArrayList<>();

    public static HashMap<UUID, Character> characters = new HashMap<>();
    public static HashMap<UUID, Character> creatingCharacter = new HashMap<>();
    public static HashMap<String, Nation> nations = new HashMap<>();
    public static HashMap<String, Inventory> indexedMenus = new HashMap<>();

    @Override
    public void onEnable() {
        load();
    }

    @Override
    public void onDisable() {
        unload();
    }

    private void load() {
        instance = this;
        saveDefaultConfig();
        events();
        commands();
        Util.loadNations();
        Util.loadReligions();
        db = new SQLite();
        Bukkit.getConsoleSender().sendMessage(Util.color("&7[&bEternityRoleplay&7] &fThe plugin has been &aenabled"));
    }

    private void events() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new QuitEvent(), this);
        pm.registerEvents(new MoveEvent(), this);
        pm.registerEvents(new IClickEvent(), this);
        pm.registerEvents(new AsyncChatEvent(), this);
        pm.registerEvents(new BBreakEvent(), this);
        pm.registerEvents(new BPlaceEvent(), this);
        pm.registerEvents(new DeathEvent(), this);
        pm.registerEvents(new RespawnEvent(), this);
    }

    private void commands() {
        this.getCommand("rpset").setExecutor(new rpset());
        this.getCommand("rpinfo").setExecutor(new card());
        this.getCommand("finishcharacter").setExecutor(new finishcharacter());
        this.getCommand("localchat").setExecutor(new localchat());
        this.getCommand("globalchat").setExecutor(new globalchat());
    }

    private void unload() {
        db.saveCharacters();
        db.disconnect();
        Bukkit.getConsoleSender().sendMessage(Util.color("&7[&bEternityRoleplay&7] &fThe plugin has been &cdisabled"));
    }
}

