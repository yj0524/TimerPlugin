package plugin.plugin.timer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import plugin.plugin.Plugin;

import java.util.Objects;

public class TimerMenuHandler implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Timer timer = Plugin.getInstance().getTimer();
        Player player = (Player) event.getWhoClicked();
        ItemStack is = event.getCurrentItem();
        if ((event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            if (event.getView().getTitle().equals("Timer Options")) {
                event.setCancelled(true);
                if ((event.getCurrentItem().getType() == Material.LIME_DYE)) {
                    assert is != null;
                    if ((is.hasItemMeta()) && (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(ChatColor.RED + "Stop Timer"))) {
                        timer.setRunning(false);
                        player.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Timer is now paused");
                        player.closeInventory();
                    }
                }
                if ((event.getCurrentItem().getType() == Material.GRAY_DYE)) {
                    assert is != null;
                    if ((is.hasItemMeta()) && (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Start Timer"))) {
                        timer.setRunning(true);
                        player.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Timer is now running");
                        player.closeInventory();
                    }
                }
                if ((event.getCurrentItem().getType() == Material.BARRIER)) {
                    assert is != null;
                    if ((is.hasItemMeta()) && (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Reset Timer"))) {
                        timer.setTime(0);
                        timer.setRunning(false);
                        player.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Timer was reset");
                        player.closeInventory();
                    }
                }
                if ((event.getCurrentItem().getType() == Material.LIME_DYE)) {
                    assert is != null;
                    if ((is.hasItemMeta()) && (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(ChatColor.RED + "Hide Timer"))) {
                        timer.setShow(false);
                        player.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer will now be hidden");
                        player.closeInventory();
                    }
                }
                if ((event.getCurrentItem().getType() == Material.GRAY_DYE)) {
                    assert is != null;
                    if ((is.hasItemMeta()) && (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Show Timer"))) {
                        timer.setShow(true);
                        player.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer will now be shown onscreen");
                        player.closeInventory();
                    }
                }
                if ((event.getCurrentItem().getType() == Material.REDSTONE_LAMP)) {
                    assert is != null;
                    if ((is.hasItemMeta()) && (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Timer forwards"))) {
                        timer.setTimerMode(false);
                        timer.setRunning(false);
                        timer.setTime(0);
                        player.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer is now counting"  + ChatColor.GREEN + " forward");
                        player.closeInventory();
                    }
                }
                if ((event.getCurrentItem().getType() == Material.REDSTONE_LAMP)) {
                    assert is != null;
                    if ((is.hasItemMeta()) && (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Timer backwards"))) {
                        timer.setTimerMode(true);
                        timer.setRunning(false);
                        timer.setTime(3600);
                        player.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer is now counting" + ChatColor.GREEN + " backward");
                        player.closeInventory();
                    }
                }
            }
        }
    }
}
