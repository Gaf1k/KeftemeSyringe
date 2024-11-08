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
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


public class Syringe {
    public static final Syringe instance = new Syringe();

    public ItemStack syringe;

    private List<ItemStack> syringeStack = new ArrayList<>();

    public void getSyringe(Player player){
        String materialType = Plugin.getInstance().getConfig().getString("Material").toUpperCase();
        Material material = Material.valueOf(materialType);
        syringe = new ItemStack(material);
        ItemMeta meta = syringe.getItemMeta();

        meta.setDisplayName(color(Plugin.getInstance().getConfig().getString("Name")));
        meta.setCustomModelData(Plugin.getInstance().getConfig().getInt("Custom_model_data"));

        meta.getPersistentDataContainer().set(NamespacedKey.fromString("syringe"), PersistentDataType.INTEGER,1);

        int value = syringeStack.size()+1;
        if (Plugin.getInstance().getConfig().getBoolean("One_item_in_stack")) {
            if (syringeStack.isEmpty()) {
                meta.getPersistentDataContainer().set(NamespacedKey.fromString("stack"), PersistentDataType.INTEGER, 1);
            } else {
                meta.getPersistentDataContainer().set(NamespacedKey.fromString("stack"), PersistentDataType.INTEGER, value);
            }
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

                    if (player.getHealth()<=17) {
                        player.setHealth(player.getHealth() + Plugin.getInstance().getConfig().getInt("Instant_health_recovery"));
                    }

                        new BukkitRunnable(){
                            int amount = Plugin.getInstance().getConfig().getInt("Interval_health_recovery");
                            @Override
                            public void run() {
                                if (amount<=0){
                                    cancel();
                                }
                                if (player.getHealth()!=20) {
                                    player.setHealth(player.getHealth() + 1);
                                    amount--;
                                }else {
                                    cancel();
                                }
                            }
                        }.runTaskTimer(Plugin.getInstance(),0,5L);


                    if (player.getInventory().contains(syringe)){
                        player.getInventory().removeItem(syringe);
                    }
                    cancel();
                }

                ctr++;
            }
        }.runTaskTimer(Plugin.getInstance(),0,20L);
    }

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])");

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', HEX_PATTERN.matcher(message).replaceAll("&x&$1&$2&$3&$4&$5&$6"));
    }
}
