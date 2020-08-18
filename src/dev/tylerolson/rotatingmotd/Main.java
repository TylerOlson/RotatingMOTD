package dev.tylerolson.rotatingmotd;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PingListener(), this);
        getCommand("rotatingmotd").setExecutor(new RotatingCommand());

        for (int i = 0; i < getConfig().getStringList("motds").size(); i++) {
            Main.instance.getLogger().info("Found MOTD: " + Main.instance.getConfig().getStringList("motds").get(i));
        }
    }
}
