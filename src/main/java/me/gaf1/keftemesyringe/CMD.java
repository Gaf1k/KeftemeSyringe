package me.gaf1.keftemesyringe;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class CMD implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            System.out.println("Ты консоль тебе нельзя!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0){
            Syringe.instance.getSyringe(player);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            Plugin.getInstance().reloadConfig();
            player.sendMessage(Syringe.color(Plugin.getInstance().getConfig().getString("reload_config_message")));
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){
            return List.of("reload");
        }

        return null;
    }
}
