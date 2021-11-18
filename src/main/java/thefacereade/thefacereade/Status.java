package thefacereade.thefacereade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import thefacereade.thefacereade.status.Slave;
import thefacereade.thefacereade.status.StatusType;

import java.util.HashMap;

public class Status {

    public static HashMap<String, Status> ingamePlayerList = new HashMap<>();
    public static StatusEvent statusEvent;
    public static Plugin plugin;
    public final static String MS = "§f[ §b관상 §f] ";
    public static Material bak = Material.MAGMA_CREAM;

    public String myUUID;
    public StatusType statusType = StatusType.SLAVE;

    public Status(Plugin plugin){

        if(statusEvent == null){
            this.plugin = plugin;
            statusEvent = new Slave.StatusEvent();
            Bukkit.getPluginManager().registerEvents(statusEvent, plugin);
        }

    }

    public static Status getStatus(Player player){

        String uuid = (String) player.getUniqueId().toString();

        return ingamePlayerList.get(uuid);
    }

    public static void setStatus(Player player, Status status){
        String uuid = player.getUniqueId().toString();
        status.myUUID = uuid;
        ingamePlayerList.put(uuid, status);

        player.sendMessage(MS + "§7당신은 이제 §a" + status.statusType.name + "§7 신분이 되었습니다.");
        player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 0.45f);
        player.setPlayerListName(player.getName() + " §f[§a"+status.statusType.name+"§f]");
    }

    public static void release(Player player){
        String uuid = player.getUniqueId().toString();
        ingamePlayerList.remove(uuid);

        player.sendMessage(MS + "§7당신은 이제 §a" + "무직" + "§7 신분이 되었습니다.");
        player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 0.45f);

    }

    public static void changeStatusWith(Player player, Player target){

        Status playerStatus = getStatus(player);
        Status targetStatus = getStatus(target);

        if(playerStatus != null
                && targetStatus != null){
            Bukkit.broadcastMessage(MS + "§b" +player.getName() + "§7[§a"+playerStatus.statusType.name+"§7] 가 "
                            + "§b" +target.getName() + "§7[§a"+targetStatus.statusType.name+"§7] 을 붙잡아 신분이 교체되었습니다.");
            setStatus(target, playerStatus);
            setStatus(player, targetStatus);
        }

    }

    public void onCatch(Player damager, Player victim, EntityDamageByEntityEvent event){

        Status damagerStatus = this;
        Status victimStatus = getStatus(victim);

        if(damagerStatus.statusType.index + 1 == victimStatus.statusType.index){
            changeStatusWith(damager, victim);
        } else {
            event.setCancelled(true);
        }

    }

    public class StatusEvent implements Listener {

        @EventHandler
        public void onEntityDamagedByEntity(EntityDamageByEntityEvent e){

            if(e.getDamager() instanceof Player
                    && e.getEntity() instanceof Player){

                Player damager = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();

                if(damager.getInventory().getItemInMainHand().getType() != bak)
                    return;

                MyUtility.removeItem(damager, bak, 1);

                Status statusDamager = getStatus(damager);
                Status statusVictim = getStatus(victim);

                if(statusDamager == null
                        || statusVictim == null)
                    return;

                statusDamager.onCatch(damager, victim, e);
            }
        }

    }
}
