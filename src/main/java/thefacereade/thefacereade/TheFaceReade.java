package thefacereade.thefacereade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import thefacereade.thefacereade.status.*;

public final class TheFaceReade extends JavaPlugin implements Listener {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getLogger().info("관상 플러그인 로드됨");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Player getPlayerFromOnline(String targetName){

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            if(onlinePlayer.getName().equalsIgnoreCase(targetName)){
                return onlinePlayer;
            }
        }

        return null;

    }

    @EventHandler
    public void onCommandInput(PlayerCommandPreprocessEvent e) {
        String[] args = e.getMessage().split(" ");
        String cmd = args[0];

        Player p = e.getPlayer();

        if (cmd.equalsIgnoreCase("/신분") && p.isOp()) {

            if (args.length == 1) {

                p.sendMessage("§a신분을 입력하세요.");
                p.sendMessage("§a/신분 <노비/평민/상인/양반/왕/해제> <닉네임>");

            } else if (args.length == 2) {
                p.sendMessage("§a닉네임을 입력하세요.");
                p.sendMessage("§a/신분 <노비/평민/상인/양반/왕/해제> <닉네임>");
            } else {

                String targetName = args[2];

                Player target = getPlayerFromOnline(targetName);
                if(target == null){
                    p.sendMessage("§c"+targetName+" §a님은 접속중이지 않습니다.");
                } else {
                    String status = args[1];
                    boolean isSuc = true;

                    if(status.equalsIgnoreCase("노비"))
                        Status.setStatus(target, new Slave(plugin));
                    else if(status.equalsIgnoreCase("평민"))
                        Status.setStatus(target, new Civil(plugin));
                    else if(status.equalsIgnoreCase("상인"))
                        Status.setStatus(target, new Trader(plugin));
                    else if(status.equalsIgnoreCase("양반"))
                        Status.setStatus(target, new Yangban(plugin));
                    else if(status.equalsIgnoreCase("왕"))
                        Status.setStatus(target, new King(plugin));
                    else if(status.equalsIgnoreCase("해제"))
                        Status.release(target);
                    else
                        isSuc = false;

                    if(isSuc){
                        p.sendMessage("§c"+status + " §a신분을 " + targetName + "에게 부여했습니다.");
                    } else {
                        p.sendMessage("§c"+status + " §a신분은 §a존재하지 않는 신분입니다.");
                    }
                }

            }
            e.setCancelled(true);
        }
    }

}
