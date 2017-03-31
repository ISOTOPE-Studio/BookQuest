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

import java.util.*;


public class TaskGUI extends GUI {

    private Map<Integer, String> slotIDMap = new HashMap<>();

    public TaskGUI(Player player) {
        super(S.toBoldGold("����") + "[" + player.getName() + "]", 4 * 9, player);
        this.page = 0;
        int pos = 0;
        for (Task task : Task.tasks.values()) {
            if (pos >= size) break;
            slotIDMap.put(pos, task.getName());
            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(task.getDisplayName());
            List<String> lore = task.getLore();
            lore.add(task.isAvailable(player) ? S.toItalicYellow("������������") : S.toRed("�޷�����"));
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
                    player.sendMessage(S.toPrefixRed("���񲻴���"));
                } else {
                    if (task.isAvailable(player)) {
                        ItemStack bookItem = task.getBookItem();
                        final boolean[] exist = {false};
                        Arrays.stream(player.getInventory().getContents())
                                .filter(Objects::nonNull).forEach(item -> {
                            if (task.isBookItem(item))
                                exist[0] = true;
                        });
                        if (exist[0]) {
                            player.sendMessage(S.toPrefixRed("�������Ѿ���ȡ���������"));
                        } else {
                            player.getInventory().addItem(bookItem);
                            player.sendMessage(S.toPrefixGreen("�ɹ���ȡ����"));
                        }
                    } else {
                        player.sendMessage(S.toPrefixRed("���޷���ȡ�������"));
                    }
                }
                player.closeInventory();
            }
        }
    }

}
