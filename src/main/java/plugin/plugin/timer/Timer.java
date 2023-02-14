package plugin.plugin.timer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import plugin.plugin.utils.Config;
import plugin.plugin.Plugin;

public class Timer {

    private boolean running;
    private int time;
    private boolean show;
    private boolean TimerMode;
    public String timer;

    public Timer(boolean running, int time, boolean show, boolean TimerMode) {
        this.running = running;
        this.time = time;
        this.show = show;
        this.TimerMode = TimerMode;

        Config config = Plugin.getInstance().getConfiguration();

        if (config.getConfig().contains("timer.time")) {
            this.time = config.getConfig().getInt("timer.time");
        } else {
            this.time = 0;
        }

        run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String gettimer() {
        int sec = time % 60;
        int min = (time / 60)%60;
        int hours = (time/60)/60;

        String strSec=(sec<10)?"0"+ sec :Integer.toString(sec);
        String strMin=(min<10)?"0"+ min :Integer.toString(min);
        String strHours=(hours<10)?"0"+ hours :Integer.toString(hours);

        return timer = strHours + ":" + strMin + ":" + strSec;
    }

    public String getTimer() {
        int sec = time % 60;
        int min = (time / 60)%60;
        int hours = (time/60)/60;

        String strSec=(sec<10)?"0"+ sec :Integer.toString(sec);
        String strMin=(min<10)?"0"+ min :Integer.toString(min);
        String strHours=(hours<10)?"0"+ hours :Integer.toString(hours);

        return timer = strHours + "h " + strMin + "m " + strSec + "s";
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isTimerMode() {
        return TimerMode;
    }

    public void setTimerMode(boolean TimerMode) {
        this.TimerMode = TimerMode;
    }

    public void sendActionBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (isShow()) {

                if (!isRunning()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Timer paused"));
                    continue;
                }

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + ChatColor.BOLD + gettimer()));
            }
        }
    }

    public void save() {
        Config config = Plugin.getInstance().getConfiguration();

        config.getConfig().set("timer.time", time);
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {

                sendActionBar();

                if (!isRunning()) {
                    return;
                }

                if (TimerMode) {
                    setTime(getTime() - 1);
                    if (getTime() == 0) {
                        end();
                    }
                }
                else {
                    setTime(getTime() + 1);
                }

            }
        }.runTaskTimer(Plugin.getInstance(), 20, 20);
    }

    public void end() {
        setRunning(false);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.RED + "You lost the challenge after " + getTimer() + " because someone participating died");
            player.setGameMode(GameMode.SPECTATOR);
        }
        setTime(0);
    }
}
