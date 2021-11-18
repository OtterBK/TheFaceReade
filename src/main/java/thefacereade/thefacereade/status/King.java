package thefacereade.thefacereade.status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import thefacereade.thefacereade.MyUtility;
import thefacereade.thefacereade.Status;

public class King extends Status {

    public King(Plugin plugin){
        super(plugin);
        statusType = StatusType.KING;
    }

    public void onCatchSlave(Player victim){
        Bukkit.broadcastMessage(MS + "§f왕이 노비 §b" + victim.getName() + "§f을 붙잡았습니다!");
        setStatus(victim, new KingSlave(plugin));
        boolean slaveTrue = false;
        for(Status status : Status.ingamePlayerList.values()){
            if(status.statusType == StatusType.SLAVE){
                slaveTrue = true;
            }
        }
        if(!slaveTrue){
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
                onlinePlayer.sendMessage(MS+"§f왕이 모든 노비를 붙잡았습니다!!!");
                MyUtility.fireworkEvent(plugin, onlinePlayer.getLocation());
            }
        }
    }

    //왕의 경우
    @Override
    public void onCatch(Player damager, Player victim, EntityDamageByEntityEvent event){

        Status damagerStatus = this;
        Status victimStatus = getStatus(victim);

        //노비를 잡았다면
        if(victimStatus.statusType.index == 0){

            onCatchSlave(victim);

        } else {
            event.setCancelled(true);
        }

    }
}
