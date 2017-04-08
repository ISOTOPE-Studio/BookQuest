package cc.isotopestudio.bookquest.task;
/*
 * Created by Mars Tan on 4/8/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.element.goal.Goal;
import cc.isotopestudio.bookquest.element.goal.TimeGoal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static cc.isotopestudio.bookquest.element.Task.tasks;

public class OnlineTimeTask extends BukkitRunnable {

    @Override
    public void run() {
        loop:
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < player.getInventory().getContents().length; i++) {
                ItemStack item = player.getInventory().getContents()[i];
                if (item == null) continue;
                if (item.getType() != Material.WRITTEN_BOOK) continue;

                Task task = null;
                for (Task task1 : tasks.values()) {
                    if (task1.isBookItem(item)) {
                        task = task1;
                        break;
                    }
                }
                if (task == null) continue;
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                int num = -1;
                for (Goal goal : task.getGoals()) {
                    if (goal instanceof TimeGoal) {
                        num = goal.getNum();
                    }
                }
                if (num == -1) continue;
                int index = 0;
                for (String s : lore) {
                    if (s.contains("ÔÚÏß")) {
                        int a = s.indexOf(String.valueOf(ChatColor.AQUA));
                        if (a < 0) continue loop;
                        int b = s.indexOf(" /");
                        int time = Integer.parseInt(s.substring(a + 2, b - 4));
                        if (time != num) {
                            if (time + 1 == num) {
                                lore.set(index, s.replace("" + ChatColor.AQUA + time, "" + ChatColor.GREEN + (time + 1)));
                            } else {
                                lore.set(index, s.replace("" + ChatColor.AQUA + time, "" + ChatColor.AQUA + (time + 1)));
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            player.getInventory().setItem(i, item);
                        }
                        continue loop;
                    }
                    index++;
                }
                continue loop;
            }
        }
    }
}
