package thefacereade.thefacereade.status;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import thefacereade.thefacereade.Status;

public class Civil extends Status {
    public Civil(Plugin plugin){
        super(plugin);
        statusType = StatusType.CIVIL;
    }



}
