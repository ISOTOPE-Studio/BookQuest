package cc.isotopestudio.bookquest.listener;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static cc.isotopestudio.bookquest.element.Task.tasks;

public class TaskListener implements Listener {

    @EventHandler
    public void onClickBook(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() != Material.WRITTEN_BOOK) {
            return;
        }
        Task task;
        for (Task task1 : tasks.values()) {
            if (task1.isBookItem(event.getItem())) {
                event.setCancelled(true);
                task = task1;
                break;
            }
        }

    }

    @EventHandler
    public void onKillMob(EntityDeathEvent event) {
        event.getEntity();
    }

}
