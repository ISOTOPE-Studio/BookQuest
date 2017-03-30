package cc.isotopestudio.bookquest.listener;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TaskListener implements Listener {

    @EventHandler
    public void onClickBook(PlayerInteractEvent event) {

    }

    @EventHandler
    public void onKillMob(EntityDeathEvent event) {
        event.getEntity();
    }

}
