package thefacereade.thefacereade.status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import thefacereade.thefacereade.Status;

public class Slave extends Status {

    public Slave(Plugin plugin){
        super(plugin);
        statusType = StatusType.SLAVE;
    }



}
