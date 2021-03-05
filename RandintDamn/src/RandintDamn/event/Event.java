package RandintDamn.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
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
    final int DEFAULT_THE_NUM = 30; //불행한 일이 일어날 기본 확률입니다
    final int LUCKY_THE_NUM = 10; //행운 효과가 있을떄 불행한 일이 일어날 확률입니다


    /**
     * 인챈트 할 때 호출됩니다
     */
    @EventHandler
    public void onEnChantItem(EnchantItemEvent event){
        int TheNum= DEFAULT_THE_NUM; //인챈트한 플레이어의 확률입니다
        if (event.getEnchanter().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = LUCKY_THE_NUM; //행운 효과가 걸려있는 플레이어의 확률입니다
        }
        if (random.nextInt(101) < TheNum){
            /**
             * 아이템이 파괴됩니다.
             */
            event.getItem().setAmount(0);
            event.getItem().setType(Material.AIR);

            /**
             * 이벤트를 취소하고 메세지를 보냅니다.
             */
            event.setCancelled(true);
            event.getEnchanter().sendMessage(TheNum/10+"%줄 알았으나 사실"+TheNum+"%확률로 인챈트에 실패했습니다..");

        }else{
            event.getEnchanter().sendMessage("인첸트에 성공했습니다!"); //성공했습니다
        }
    }


    /**
     * 블럭이 아이템을 드랍할 때 발생합니다.
     */
    @EventHandler
    public void onBlockDrops(BlockDropItemEvent event){
        int TheNum= DEFAULT_THE_NUM; //인챈트한 플레이어의 확률입니다
        if (event.getPlayer().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = LUCKY_THE_NUM; //행운 효과가 걸려있는 플레이어의 확률입니다
        }
        if (random.nextInt(101) < TheNum){
            /**
             * 블럭 이벤트를 취소하고 메세지를 보냅니다.
             */
            event.getPlayer().sendMessage("블럭이 캐진줄 알았는데 알고 보니 아니였다?");
            event.setCancelled(true);
        }else if(random.nextInt(1001) > 999){
            /**
             * 0.1%확률로 네더라이트 파편이 나옵니다.
             */
            List<Item> items =  event.getItems();
            for (Item item: items) {
                item.setItemStack(new ItemStack(Material.NETHERITE_SCRAP));
                Bukkit.broadcastMessage(event.getPlayer().getName()+" 님이 0.1%확률로 네더라이트 파편을 획득했습니다!");
            }
        }
    }


    /**
     * 아이템을 제작할때 호출됩니다
     */
    @EventHandler
    public void onCraftItem(CraftItemEvent event){
        int TheNum= DEFAULT_THE_NUM; //인챈트한 플레이어의 확률입니다
        if (event.getWhoClicked().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = LUCKY_THE_NUM; //행운 효과가 걸려있는 플레이어의 확률입니다
        }
        if (random.nextInt(101) < TheNum){
            /**
             * 제작창의 아이템을 모두 없앱니다.
             */
            for (int i = 0; i < event.getInventory().getMatrix().length+1; i++) {
                event.getInventory().setItem(i, new ItemStack(Material.AIR));
            }

            /**
             * 이벤트를 취소하고 메세지를 보냅니다.
             */
            event.getWhoClicked().sendMessage("아이템 조합법을 까먹어서 조합에 실패했습니다");
            event.setCancelled(true);
        }
    }


    /**
     * 플레이어가 리스폰할때 호출됩니다
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        int TheNum=10; //인챈트한 플레이어의 확률입니다
        if (event.getPlayer().hasPotionEffect(PotionEffectType.LUCK)){
            TheNum = 5; //행운 효과가 걸려있는 플레이어의 확률입니다
        }
        for (int i=0;i<=35;i++){
            if (random.nextInt(101)<TheNum) {
                event.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR)); //일정한 확률로 인벤토리의 몇몇 아이템을 없앱니다
            }
        }
    }

    /**
     * 행운 포션을 부여합니다
     * 다이아몬드를 들고 왼손에 들면 다이아몬드가 하나 없어지며 행운의 포션이 들어옵니다
     */
    @EventHandler
    public void onGetLuckyPotion(PlayerSwapHandItemsEvent event){
        ItemStack leftHand = event.getOffHandItem();
        if (leftHand.getType().equals(Material.DIAMOND)){
            event.setOffHandItem(new ItemStack(Material.DIAMOND, leftHand.getAmount()-1)); //다이아몬드를 하나 없앱니다

            /**
             * 행운의 포션을 생성자로 생성합니다
             */
            ItemStack potion = new ItemStack(Material.POTION);
            PotionMeta pm = ((PotionMeta)(potion.getItemMeta()));
            pm.setBasePotionData(new PotionData(PotionType.LUCK));
            potion.setItemMeta(pm);

            event.getPlayer().getInventory().addItem(potion); //플레이어에게 행운의 포션을 넣습니다
        }
    }
}
