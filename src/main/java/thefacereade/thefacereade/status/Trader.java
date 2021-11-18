package thefacereade.thefacereade.status;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import thefacereade.thefacereade.Status;

public class Trader extends Status {
    public Trader(Plugin plugin){
        super(plugin);
        statusType = StatusType.TRADER;
    }



}
