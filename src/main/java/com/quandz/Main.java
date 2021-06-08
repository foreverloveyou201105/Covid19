package com.quandz;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin {

    private static Main main;
    private Covid c;
    ConsoleCommandSender console;

    List<String> broadcast_messages;
    String console_prefix;
    String country;
    long delay;

    @Override
    public void onEnable() {
        main = this;
        loadConfig();
        c = new Covid();
        new Task(c).runTaskTimerAsynchronously(this, 20, 20*delay);
        console = Bukkit.getConsoleSender();
        console.sendMessage(console_prefix + " Plugin made by quandz, report bug add phungthequan030@gmail.com");
    }

    @Override
    public void onDisable() {
        console.sendMessage(console_prefix + " Plugin da tat");
        console.sendMessage(console_prefix + " Plugin made by quandz, report bug add phungthequan030@gmail.com");
    }

    public void loadConfig() {
        this.saveDefaultConfig();
        country = getConfig().getString("country");
        broadcast_messages = getConfig().getStringList("messages");
        console_prefix = getConfig().getString("console_prefix");
        delay = getConfig().getLong("delay");
    }

    public List<String> getBroadcast_messages() {
        return broadcast_messages;
    }

    public String getCountry() {
        return country;
    }

    public static Main getMain() {
        return main;
    }
}
