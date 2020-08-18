package dev.tylerolson.rotatingmotd;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RotatingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        } else if (args[0].equalsIgnoreCase("add")) {
            if (args.length == 1) {
                sender.sendMessage("/rotatingmotd [add] motd");
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
            List<String> motdList = Main.instance.getConfig().getStringList("motds");
            motdList.add(combinedArgs);
            Main.instance.getConfig().set("motds", motdList);
            Main.instance.saveConfig();
            sender.sendMessage("§6Added '" + combinedArgs + "' as an MOTD.");
            return true;
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 1) {
                sender.sendMessage("/rotatingmotd [remove] motd");
                return true;
            }

            List<String> motdList = Main.instance.getConfig().getStringList("motds");
            int motdIndex;
            try {
                motdIndex = Integer.parseInt(args[1]) - 1;
            } catch (NumberFormatException e) {
                sender.sendMessage("§6Could not find MOTD # " + args[1]);
                return true;
            }
            if (motdIndex >= motdList.size() || motdIndex < 0) {
                sender.sendMessage("§6Could not find MOTD # " + args[1]);
                return true;
            }
            String motdRemoved = motdList.get(motdIndex);
            motdList.remove(motdIndex);
            Main.instance.getConfig().set("motds", motdList);
            Main.instance.saveConfig();
            sender.sendMessage("§6Removed '" + motdRemoved + "' as an MOTD.");

            return true;
        } else if (args[0].equalsIgnoreCase("list")) {
            List<String> motdList = Main.instance.getConfig().getStringList("motds");
            sender.sendMessage("§6Current rotating MOTDs are: ");
            for(int i = 0; i < motdList.size(); i++) {
                sender.sendMessage(i+1 + ". " + motdList.get(i));
            }
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            Main.instance.reloadConfig();
            sender.sendMessage("§6Reload MOTD config.");
            return true;
        }
        return false;
    }
}
