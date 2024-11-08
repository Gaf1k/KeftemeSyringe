package me.gaf1.keftemesyringe;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    public static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new Event(),this);
        getCommand("syringe").setExecutor(new CMD());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
