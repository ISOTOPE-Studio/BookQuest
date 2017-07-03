package cc.isotopestudio.bookquest.data;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static cc.isotopestudio.bookquest.BookQuest.plugin;

public abstract class PlayerData {

    public static void addRecord(Player player, Task task) {
        String path = task.getName() + "." + player.getName();
        List<Long> list = plugin.playerFile.isSet(path) ? plugin.playerFile.getLongList(path) : new ArrayList<>();
        list.add(System.currentTimeMillis());
        plugin.playerFile.set(path, list);
        plugin.playerFile.save();
    }

    public static List<Long> getRecord(Player player, Task task) {
        List<Long> result = new ArrayList<>();
        String path = task.getName() + "." + player.getName();
        if (plugin.playerFile.isSet(path)) {
            result.addAll(plugin.playerFile.getLongList(path));
            result.sort(Comparator.comparingLong(l -> l));
        }
        return result;
    }

}
