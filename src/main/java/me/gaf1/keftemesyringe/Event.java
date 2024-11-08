package me.gaf1.keftemesyringe;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;


public class Event implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if (event.getPlayer().getInventory().getItemInMainHand().getType().isAir()){
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
            return;
        }

        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("syringe"), PersistentDataType.INTEGER)){
            Syringe.instance.useSyringe(event.getPlayer());
        }
    }


}
