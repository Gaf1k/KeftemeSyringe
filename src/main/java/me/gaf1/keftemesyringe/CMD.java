package me.gaf1.keftemesyringe;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            System.out.println("Ты консоль тебе нельзя!");
            return true;
        }
        Player player = (Player) sender;
        Syringe.instance.getSyringe(player);
        return true;
    }
}
