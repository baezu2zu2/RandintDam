package RandintDamn.event;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.Random;

public class Event implements Listener {
    Random random = new Random();
    final int DEFAULT_THE_NUM = 30;
    final int LUCKY_THE_NUM = 10;



    @EventHandler
    public void onEnChantItem(EnchantItemEvent event){
        int TheNum= DEFAULT_THE_NUM;
        if (event.getEnchanter().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = LUCKY_THE_NUM;
        }
        if (random.nextInt(100) < TheNum){
            event.getItem().setAmount(0);
            event.getItem().setType(Material.AIR);

            event.setCancelled(true);
            event.getEnchanter().sendMessage("5%줄 알았으나 사실 50%확률로 인챈트에 실패했습니다..");
        }else{
            event.getEnchanter().sendMessage("인첸트에 성공했습니다!");
        }
    }

    @EventHandler
    public void onBlockDrops(BlockDropItemEvent event){
        int TheNum= DEFAULT_THE_NUM;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = LUCKY_THE_NUM;
        }
        if (random.nextInt(100) < TheNum){
            event.getPlayer().sendMessage("블럭이 캐진줄 알았는데 알고 보니 아니였다?");
            event.setCancelled(true);
        }else if(random.nextInt(1000) > 998){
            List<Item> items =  event.getItems();
            for (Item item: items) {
                item.setItemStack(new ItemStack(Material.NETHERITE_SCRAP));
                Bukkit.broadcastMessage(event.getPlayer().getName()+" 님이 0.1%확률로 네더라이트 파편을 획득했습니다!");
            }
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event){
        int TheNum= DEFAULT_THE_NUM;
        if (event.getWhoClicked().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = LUCKY_THE_NUM;
        }
        if (random.nextInt(100) < TheNum){
            for (int i = 0; i < event.getInventory().getMatrix().length+1; i++) {
                event.getInventory().setItem(i, new ItemStack(Material.AIR));
            }
            event.getWhoClicked().sendMessage("아이템 조합법을 까먹어서 조합에 실패했습니다");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        int TheNum= DEFAULT_THE_NUM;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = LUCKY_THE_NUM;
        }
        for (int i=0;i<=35;i++){
            if (random.nextInt(100)<TheNum) {
                event.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler
    public void onGetLuckyPotion(PlayerSwapHandItemsEvent event){
        ItemStack leftHand = event.getOffHandItem();
        if (leftHand.getType().equals(Material.DIAMOND)){
            event.setOffHandItem(new ItemStack(Material.DIAMOND, leftHand.getAmount()-1));
            ItemStack potion = new ItemStack(Material.POTION);
            PotionMeta pm = ((PotionMeta)(potion.getItemMeta()));
            pm.setBasePotionData(new PotionData(PotionType.LUCK));
            potion.setItemMeta(pm);
            event.getPlayer().getInventory().addItem(potion);
        }
    }
}
