package com.quandz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Covid {

    Main main = Main.getMain();
    String country = main.getCountry();
    ArrayList<String> messageParsed = new ArrayList<>();

    private static String cases, death, recovered, new_cases, new_death, update_time;
    private final String sURL = "https://covid-19.dataflowkit.com/v1/" + country;

    //return true if data is new
    //
    // return false if data is old

    private String blankCheck(String str) {
        if (str.equalsIgnoreCase("")) {
            str = "0";
        }
        return str;
    }

    private void getData() throws IOException {
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject obj = root.getAsJsonObject();

        update_time = blankCheck(obj.get("Last Update").getAsString());
        cases = blankCheck(obj.get("Total Cases_text").getAsString());
        death = blankCheck(obj.get("Total Deaths_text").getAsString());
        recovered = blankCheck(obj.get("Total Recovered_text").getAsString());
        new_cases = blankCheck(obj.get("New Cases_text").getAsString());
        new_death = blankCheck(obj.get("New Deaths_text").getAsString());
        request.disconnect();
    }

    private void parseMessage() {

        if (!messageParsed.isEmpty()) {
            messageParsed.clear();
        }
        for (String s : main.getBroadcast_messages()) {
            s = ChatColor.translateAlternateColorCodes('&', s);
            s = s.replace("{date_time}", update_time)
                    .replace("{cases}", cases)
                    .replace("{death}", death)
                    .replace("{recovered}", recovered)
                    .replace("{new_cases}", new_cases)
                    .replace("{new_death}", new_death);
            messageParsed.add(s);
        }
    }

    public void broadcast() throws IOException {
        getData();
        parseMessage();

        new BukkitRunnable() {
            @Override
            public void run() {
                messageParsed.forEach(Bukkit::broadcastMessage);
                if (main.sound_isEnable()) {
                    Bukkit.getOnlinePlayers().forEach((p) -> p.playSound(p.getLocation(), main.getSound(), 1, 1));
                }
            }
        }.runTask(main);
    }

    public static String getCases() {
        return cases;
    }

    public static String getDeath() {
        return death;
    }

    public static String getRecovered() {
        return recovered;
    }

    public static String getNew_cases() {
        return new_cases;
    }

    public static String getNew_death() {
        return new_death;
    }

    public static String getUpdate_time() {
        return update_time;
    }

    public String getsURL() {
        return sURL;
    }
}
