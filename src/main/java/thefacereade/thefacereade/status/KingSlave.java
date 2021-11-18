package thefacereade.thefacereade.status;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import thefacereade.thefacereade.Status;

public class KingSlave extends Status {

    public KingSlave(Plugin plugin){
        super(plugin);
        statusType = StatusType.KING_SLAVE;
    }



}
