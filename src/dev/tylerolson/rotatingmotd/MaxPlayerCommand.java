package dev.tylerolson.rotatingmotd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MaxPlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        String firstArg = args[0].toLowerCase();
        switch (firstArg) {
            case "set":
                if (args.length == 1) {
                    sender.sendMessage("/maxplayers [set] playercount");
                    return true;
                }

                String combinedArgs = "";
                for (int i = 1; i < args.length; i++) {
                    if (i == args.length - 1){
                        combinedArgs+= args[i];
                    } else {
                        combinedArgs+= args[i] + " ";
                    }
                }

                String combinedArgsNum = combinedArgs.replaceAll("current", "").replaceAll("\\-", "").replaceAll("\\+", "").replaceAll("\\s", "");

                try {
                    Integer.parseInt(combinedArgsNum);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§6That is not a valid player count!");
                    return true;
                }

                Main.instance.getConfig().set("maxPlayers", combinedArgs);
                Main.instance.saveConfig();
                sender.sendMessage("§6Set max player count to " + combinedArgs + ".");
                return true;
            case "get":
                String maxPlayers = Main.instance.getConfig().getString("maxPlayers");
                sender.sendMessage("§6Current max player count is: " + maxPlayers + ".");
                return true;
            default:
                return false;
        }
    }
}
