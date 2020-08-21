package dev.tylerolson.rotatingmotd;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Arrays;
import java.util.Random;

public class PingListener implements Listener {

    @EventHandler
    public void onServerListPingEvent(ServerListPingEvent event) {
        FileConfiguration config = Main.instance.getConfig();

        //motd
        int motdSize = config.getStringList("motds").size();

        if (motdSize == 0) {
            event.setMotd("No config configured.");
        } else {
            int randomMOTD = new Random().nextInt(motdSize);
            event.setMotd(config.getStringList("motds").get(randomMOTD));
        }

        //List<String> motdList = Main.instance.getConfig().getStringList("motds");
        //event.setMotd(String.join(" ", motdList));

        //playercount
        String maxPlayers = config.getString("maxPlayers");
        if (maxPlayers.contains("current")) {
            maxPlayers = maxPlayers.replace("current", "" + event.getNumPlayers()); //replace keyword
            String[] numbers = maxPlayers.replaceAll("\\s", "").split("\\+|(?=-)"); //make separate ints with signs
            int result = Arrays.stream(numbers).mapToInt(Integer::parseInt).sum(); //combine ints with signs
            event.setMaxPlayers(result);
        } else {
            if (isNumber(maxPlayers)) {
                event.setMaxPlayers(Integer.parseInt(maxPlayers));
            }
        }

        //Main.instance.getLogger().info("" + Integer.parseInt(maxPlayers));
    }

    public boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
