/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.bookquest.gui;

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.util.S;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskGUI extends GUI {

    private Map<Integer, String> slotIDMap = new HashMap<>();

    public TaskGUI(Player player) {
        super(S.toBoldGold("加入游戏") + "[" + player.getName() + "]", 4 * 9, player);
        this.page = 0;
        int pos = 0;
        for (Task task : Task.tasks.values()) {
            if (pos >= size) break;
            slotIDMap.put(pos, task.getName());
            ItemStack item = new ItemStack(Material.WOOL);
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            meta.setLore(lore);
            item.setItemMeta(meta);
            setOption(pos, item);
            pos++;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name) && playerName.equals(event.getWhoClicked().getName())) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot < 0 || slot >= size) {
                return;
            }

            if (optionIcons[slot] != null) {
                Task task = Task.tasks.get(slotIDMap.get(slot));
                if (task == null) {
                    player.sendMessage(S.toPrefixRed("任务不存在"));
                } else {

                }
                player.closeInventory();
            }
        }
    }

}
