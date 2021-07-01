package com.quandz;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    Main main = Main.getMain();
    PluginDescriptionFile file = main.getDescription();

    @Override
    public @NotNull String getIdentifier() {
        return "covid19";
    }

    @Override
    public @NotNull String getAuthor() {
        return file.getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return file.getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("total_cases")) {
            return Covid.getCases();
        }
        if (params.equalsIgnoreCase("total_death")) {
            return Covid.getDeath();
        }
        if (params.equalsIgnoreCase("new_cases")) {
            return Covid.getNew_cases();
        }
        if (params.equalsIgnoreCase("new_death")) {
            return Covid.getNew_death();
        }
        if (params.equalsIgnoreCase("total_recovered")) {
            return Covid.getRecovered();
        }
        if (params.equalsIgnoreCase("update_time")) {
            return Covid.getUpdate_time();
        }
        return null;
    }
}
