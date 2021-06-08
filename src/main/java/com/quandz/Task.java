package com.quandz;

import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class Task extends BukkitRunnable {

    private Covid c;

    public Task(Covid c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            c.broadcast();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
