package thefacereade.thefacereade.status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import thefacereade.thefacereade.MyUtility;
import thefacereade.thefacereade.Status;

public class Yangban extends Status {

    public Yangban(Plugin plugin){
        super(plugin);
        statusType = StatusType.YANGBAN;
    }

    public void releaseKingSlave(){
        Bukkit.broadcastMessage(MS + "§f왕에게 붙잡힌 모든 노비가 풀려납니다!");
        for(String uuid : ingamePlayerList.keySet()){

            Status status = ingamePlayerList.get(uuid);
            if(status.statusType == StatusType.KING_SLAVE){
                Player victim = Bukkit.getPlayer(uuid);
                setStatus(victim, new Slave(plugin));
            }
        }
    }

    public void onCatch(Player damager, Player victim, EntityDamageByEntityEvent event){


        Status damagerStatus = this;
        Status victimStatus = getStatus(victim);

        if(damagerStatus.statusType.index + 1 == victimStatus.statusType.index){
            changeStatusWith(damager, victim);

            Bukkit.broadcastMessage(MS + "§f왕권이 교체되었습니다!");
            Bukkit.broadcastMessage(MS + "§f이제 새로운 왕은 "+ damager.getName()+ "님 입니다!");
            releaseKingSlave();

            for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
                MyUtility.fireworkEvent(plugin, onlinePlayer.getLocation());
            }
        } else {
            event.setCancelled(true);
        }


    }

}
