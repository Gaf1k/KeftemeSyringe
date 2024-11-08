package me.gaf1.keftemesyringe;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class Syringe {
    public static final Syringe instance = new Syringe();

    public ItemStack syringe;

    private List<ItemStack> syringeStack = new ArrayList<>();

    public void getSyringe(Player player){
        syringe = new ItemStack(Material.SCUTE);
        ItemMeta meta = syringe.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&fМедицинский шприц"));
        meta.setCustomModelData(1);

        meta.getPersistentDataContainer().set(NamespacedKey.fromString("syringe"), PersistentDataType.INTEGER,1);

        int value = syringeStack.size()+1;
        if (syringeStack.isEmpty()) {
            meta.getPersistentDataContainer().set(NamespacedKey.fromString("stack"), PersistentDataType.INTEGER, 1);
        }else {
            meta.getPersistentDataContainer().set(NamespacedKey.fromString("stack"), PersistentDataType.INTEGER, value);
        }

        syringe.setItemMeta(meta);
        syringeStack.add(syringe);

        player.getInventory().addItem(syringe);
    }
    public void useSyringe(Player player){

        new BukkitRunnable(){
            String msg;
            int ctr = 0;
            @Override
            public void run() {
                if (player.getInventory().getItemInMainHand().getType().isAir() || !(player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("syringe"),PersistentDataType.INTEGER))){
                    cancel();
                }

                if (ctr==0) {
                    msg = ChatColor.translateAlternateColorCodes('&', "&c░░░░░");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
                } else if (ctr==1){
                    msg = ChatColor.translateAlternateColorCodes('&',"&a░&c░░░░");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
                }else if (ctr==2) {
                    msg = ChatColor.translateAlternateColorCodes('&', "&a░░&c░░░");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
                }else if (ctr==3) {
                    msg = ChatColor.translateAlternateColorCodes('&', "&a░░░&c░░");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
                }else if (ctr==4) {
                    msg = ChatColor.translateAlternateColorCodes('&', "&a░░░░&c░");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
                }

                else if (ctr==5) {
                    msg = ChatColor.translateAlternateColorCodes('&', "&a░░░░░");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
                        new BukkitRunnable(){
                            int amount = 1;
                            @Override
                            public void run() {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,1,0));
                                if (amount<=0){
                                    cancel();
                                }
                                amount--;
                            }
                        }.runTaskTimer(Plugin.getInstance(),0,20L);

                    if (player.getInventory().contains(syringe)){
                        player.getInventory().removeItem(syringe);
                    }
                    cancel();
                }

                ctr++;
            }
        }.runTaskTimer(Plugin.getInstance(),0,20L);
    }
}
