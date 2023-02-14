package plugin.plugin.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugin.plugin.Plugin;
import java.util.Collections;

public class TimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            timer(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "run":
            case "start":
            case "resume": {
                start(sender);
                break;
            }
            case "stop":
            case "pause": {
                stop(sender);
                break;
            }
            case "set": {
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + ">> " + ChatColor.RESET + "Use: /timer set <time>");
                    return true;
                }
                try {
                    Timer timer = Plugin.getInstance().getTimer();

                    timer.setRunning(false);
                    timer.setTime(Integer.parseInt(args[1]));
                    sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Timer was set to " + ChatColor.GREEN + args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + ">> " + ChatColor.RESET + "Your second Parameter must be a number");
                }
                break;
            }
            case "reset": {
                reset(sender);
                break;
            }
            case "show": {
                show(sender);
                break;
            }
            case "hide": {
                hide(sender);
                break;
            }
            case "mode": {
                mode(sender);
                break;
            }
            default:
                sendUsage(sender);
                break;
        }
        return false;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + ">> " + ChatColor.RESET + "Use: /timer resume or start or run, /timer pause or stop, /timer set <time>, /timer reset, /timer show, /timer hide, /timer mode");
    }

    private void start(CommandSender sender) {
        Timer timer = Plugin.getInstance().getTimer();
        if (timer.isRunning()) {
            sender.sendMessage(ChatColor.RED + ">> " + ChatColor.RESET + "Timer is already running");
        }
        else {
            timer.setRunning(true);
            sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Timer is now running");
        }
    }

    private void stop(CommandSender sender) {
        Timer timer = Plugin.getInstance().getTimer();
        if (!timer.isRunning()) {
            sender.sendMessage(ChatColor.RED + ">> " + ChatColor.RESET + "Timer is not running");
        } else {
            timer.setRunning(false);
            sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Timer is now paused");
        }
    }

    private void reset(CommandSender sender) {
        Timer timer = Plugin.getInstance().getTimer();
        timer.setTime(0);
        timer.setRunning(false);
        sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Timer was reset");
    }

    private void show(CommandSender sender) {
        Timer timer = Plugin.getInstance().getTimer();
        if (!timer.isShow()) {
            timer.setShow(true);
            sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer will now be shown onscreen");
        } else if (timer.isShow()) {
            sender.sendMessage(ChatColor.RED + ">> " + ChatColor.RESET + "Your timer is already shown");
        }
    }

    private void hide(CommandSender sender) {
        Timer timer = Plugin.getInstance().getTimer();
        if (timer.isShow()) {
            timer.setShow(false);
            sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer will now be hidden");
        } else if (!timer.isShow()) {
            sender.sendMessage(ChatColor.RED + ">> " + ChatColor.RESET + "Your timer is already hidden");
        }
    }

    private void mode(CommandSender sender) {
        Timer timer = Plugin.getInstance().getTimer();
        if (timer.isTimerMode()) {
            timer.setTime(0);
            timer.setTimerMode(false);
            sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer is now counting"  + ChatColor.GREEN + " forward");
            timer.setRunning(false);
        } else if (!timer.isTimerMode()) {
            timer.setTime(3600);
            timer.setTimerMode(true);
            sender.sendMessage(ChatColor.GREEN + ">> " + ChatColor.RESET + "Your timer is now counting" + ChatColor.GREEN + " backward");
            timer.setRunning(false);
        }
    }

    public void timer(CommandSender sender) {
        Inventory inv = Bukkit.createInventory(null, 2*9, "Timer Options");
        Player player = (Player) sender;

        //Player Visibility
        ItemStack is1 = new ItemStack(Material.REDSTONE);
        ItemMeta im1 = is1.getItemMeta();
        assert im1 != null;
        im1.setDisplayName(ChatColor.AQUA + "Start/Stop Timer");
        is1.setItemMeta(im1);

        inv.setItem(1, is1);

        //Player Visibility
        ItemStack is3 = new ItemStack(Material.BEDROCK);
        ItemMeta im3 = is3.getItemMeta();
        assert im3 != null;
        im3.setDisplayName(ChatColor.AQUA + "Reset Timer");
        is3.setItemMeta(im1);

        inv.setItem(3, is3);

        //Player Visibility
        ItemStack is5 = new ItemStack(Material.ENDER_EYE);
        ItemMeta im5 = is5.getItemMeta();
        assert im5 != null;
        im5.setDisplayName(ChatColor.AQUA + "Show/Hide Timer");
        is5.setItemMeta(im5);

        inv.setItem(5, is5);

        //Player Visibility
        ItemStack is7 = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta im7 = is7.getItemMeta();
        assert im7 != null;
        im7.setDisplayName(ChatColor.AQUA + "Timer Mode");
        is7.setItemMeta(im7);

        inv.setItem(7, is7);

        ItemStack is2;
        ItemMeta im2;

        Timer timer = Plugin.getInstance().getTimer();

        if (timer.isRunning()) {
            is2 = new ItemStack(Material.LIME_DYE, 1, (short)10);
            im2 = is2.getItemMeta();
            assert im2 != null;
            im2.setDisplayName(ChatColor.RED + "Stop Timer");
            im2.setLore(Collections.singletonList(ChatColor.GRAY + "Click to stop the timer"));
        } else {
            is2 = new ItemStack(Material.GRAY_DYE, 1, (short)8);
            im2 = is2.getItemMeta();
            assert im2 != null;
            im2.setDisplayName(ChatColor.GREEN + "Start Timer");
            im2.setLore(Collections.singletonList(ChatColor.GRAY + "Click to start the timer"));
        }
        is2.setItemMeta(im2);

        inv.setItem(10, is2);

        ItemStack is4;
        ItemMeta im4;

        is4 = new ItemStack(Material.BARRIER, 1, (short)8);
        im4 = is4.getItemMeta();
        assert im4 != null;
        im4.setDisplayName(ChatColor.GREEN + "Reset Timer");
        im4.setLore(Collections.singletonList(ChatColor.GRAY + "Click to reset the timer"));
        is4.setItemMeta(im4);

        inv.setItem(12, is4);

        ItemStack is6;
        ItemMeta im6;

        if (timer.isShow()) {
            is6 = new ItemStack(Material.LIME_DYE, 1, (short)10);
            im6 = is6.getItemMeta();
            assert im6 != null;
            im6.setDisplayName(ChatColor.RED + "Hide Timer");
            im6.setLore(Collections.singletonList(ChatColor.GRAY + "Click to hide the timer"));
        } else {
            is6 = new ItemStack(Material.GRAY_DYE, 1, (short)8);
            im6 = is6.getItemMeta();
            assert im6 != null;
            im6.setDisplayName(ChatColor.GREEN + "Show Timer");
            im6.setLore(Collections.singletonList(ChatColor.GRAY + "Click to start the timer"));
        }
        is6.setItemMeta(im6);

        inv.setItem(14, is6);

        ItemStack is8;
        ItemMeta im8;

        if (timer.isTimerMode()) {
            is8 = new ItemStack(Material.REDSTONE_LAMP, 1, (short)10);
            im8 = is8.getItemMeta();
            assert im8 != null;
            im8.setDisplayName(ChatColor.GREEN + "Timer forwards");
            im8.setLore(Collections.singletonList(ChatColor.GRAY + "Click to set the timer to count forward"));
        } else {
            is8 = new ItemStack(Material.REDSTONE_LAMP, 1, (short)8);
            im8 = is8.getItemMeta();
            assert im8 != null;
            im8.setDisplayName(ChatColor.GREEN + "Timer backwards");
            im8.setLore(Collections.singletonList(ChatColor.GRAY + "Click to set the timer to count backward"));
        }
        is8.setItemMeta(im8);

        inv.setItem(16, is8);
        player.openInventory(inv);
    }
}