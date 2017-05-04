/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.bookquest.gui;

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.util.BookUtil;
import cc.isotopestudio.bookquest.util.S;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static cc.isotopestudio.bookquest.BookQuest.plugin;


public class TaskGUI extends GUI {

    private Map<Integer, String> slotIDMap = new HashMap<>();

    public TaskGUI(Player player) {
        super(S.toBoldGold("任务") + "[" + player.getName() + "]", 4 * 9, player);
        this.page = 0;
        int pos = 0;
        for (Task task : Task.tasks.values()) {
            if (pos >= size) break;
            slotIDMap.put(pos, task.getName());
            ItemStack item = task.getDisplayItem();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(task.getDisplayName());
            List<String> lore = meta.getLore();
            lore.add(task.isAvailable(player) ? S.toItalicYellow("单击接受任务") : S.toRed("无法接受"));
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
                    if (task.isAvailable(player)) {
                        final boolean[] exist = {false};
                        Arrays.stream(player.getInventory().getContents())
                                .filter(Objects::nonNull).forEach(item -> {
                            if (task.isBookItem(item))
                                exist[0] = true;
                        });
                        if (exist[0]) {
                            player.sendMessage(S.toPrefixRed("你现在已经领取了这个任务"));
                        } else {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                                    BookMeta meta = (BookMeta) book.getItemMeta();
                                    List<String> pages = new ArrayList<>();
                                    pages.add(task.getBookJson());
                                    BookUtil.setPages(meta, pages);
                                    book.setItemMeta(meta);
                                    BookUtil.openBook(book, player);
                                }
                            }.runTaskLater(plugin, 2);
                        }
                    } else {
                        player.sendMessage(S.toPrefixRed("你无法领取这个任务"));
                    }
                }
                player.closeInventory();
            }
        }
    }

}
