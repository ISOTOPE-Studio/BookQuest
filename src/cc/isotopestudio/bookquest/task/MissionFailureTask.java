package cc.isotopestudio.bookquest.task;
/*
 * Created by Mars Tan on 4/8/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.util.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cc.isotopestudio.bookquest.element.Task.tasks;

public class MissionFailureTask extends BukkitRunnable {

    public final static Map<Player, Set<Task>> playerTaskMap = new HashMap<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Set<Task> currentTask = new HashSet<>();
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
                currentTask.add(task);
            }
            if (player.getItemOnCursor() != null) {
                for (Task task : tasks.values()) {
                    if (task.isBookItem(player.getItemOnCursor())) {
                        currentTask.add(task);
                        break;
                    }
                }
            }
            if (playerTaskMap.containsKey(player)) {
                playerTaskMap.get(player).stream()
                        .filter(task -> !currentTask.contains(task))
                        .forEach(task -> player.sendMessage(
                                S.toPrefixRed(task.getDisplayName()) + S.toRed(" ÈÎÎñÊ§°Ü")));
            }
            if (currentTask.size() > 0) {
                playerTaskMap.put(player, currentTask);
            } else {
                playerTaskMap.remove(player);
            }
        }
    }
}
