package com.quandz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Covid {

    Main main = Main.getMain();
    String country = main.getCountry();
    String sURL = "https://covid-19.dataflowkit.com/v1/" + country;
    String cases, death, recovered, new_cases, new_death, update_time;
    ArrayList<String> messageParsed = new ArrayList<>();

    //return true if data is new
    //
    // return false if date is old

    private boolean getData() throws IOException {
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject obj = root.getAsJsonObject();

        if(obj.get("Last Update").getAsString().equalsIgnoreCase(this.update_time)) {
            return false;
        }

        update_time = obj.get("Last Update").getAsString();
        cases = obj.get("Total Cases_text").getAsString();
        death = obj.get("Total Deaths_text").getAsString();
        recovered = obj.get("Total Recovered_text").getAsString();
        new_cases = obj.get("New Cases_text").getAsString();
        new_death = obj.get("New Deaths_text").getAsString();
        return true;
    }

    private void parseMessage() throws IOException {

        if(getData()) {
            if(!messageParsed.isEmpty()) {
                messageParsed.clear();
            }
            for(String s: main.getBroadcast_messages()) {
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
    }

    public void broadcast() throws IOException {
        parseMessage();
        messageParsed.forEach(Bukkit::broadcastMessage);
    }
}
