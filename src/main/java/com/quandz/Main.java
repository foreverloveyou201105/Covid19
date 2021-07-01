package com.quandz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
    boolean enable_sound;
    String broadcast_sound;
    long delay;

    @Override
    public void onEnable() {
        main = this;
        saveDefaultConfig();
        loadConfig();
        c = new Covid();
        new Task(c).runTaskTimerAsynchronously(this, 20, 20 * delay);
        registerPlaceholder();
        console = Bukkit.getConsoleSender();
        console.sendMessage(console_prefix + " Plugin made by quandz, report bug add phungthequan030@gmail.com");
    }

    @Override
    public void onDisable() {
        console.sendMessage(console_prefix + " Plugin da tat");
        console.sendMessage(console_prefix + " Plugin made by quandz, report bug add phungthequan030@gmail.com");
    }

    public void registerPlaceholder() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getPluginManager().disablePlugin(this);
        }
        new Placeholders().register();
    }

    public void loadConfig() {
        country = getConfig().getString("country");
        broadcast_messages = getConfig().getStringList("messages");
        console_prefix = getConfig().getString("console_prefix");
        delay = getConfig().getLong("delay");
        enable_sound = getConfig().getBoolean("sound.enable");
        broadcast_sound = getConfig().getString("sound.sound");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("covid19")) {
            if (!sender.hasPermission("covid19.admin")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission 'covid19.admin'");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "COMMAND: /covid19 reload");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                loadConfig();
                sender.sendMessage(ChatColor.GREEN + "Reload config.yml successfully");
            }
        }
        return true;
    }

    public List<String> getBroadcast_messages() {
        return broadcast_messages;
    }

    public String getCountry() {
        return country;
    }

    public boolean sound_isEnable() {
        return enable_sound;
    }

    public Sound getSound() {
        return Sound.valueOf(broadcast_sound);
    }

    public static Main getMain() {
        return main;
    }
}
