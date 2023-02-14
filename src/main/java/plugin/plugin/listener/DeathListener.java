package plugin.plugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import plugin.plugin.Plugin;
import plugin.plugin.timer.Timer;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Timer timer = Plugin.getInstance().getTimer();
        if(timer.isRunning()) {
            timer.end();
        }
    }
}
