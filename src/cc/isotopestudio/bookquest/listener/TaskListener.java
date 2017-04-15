package cc.isotopestudio.bookquest.listener;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.element.goal.Goal;
import cc.isotopestudio.bookquest.element.goal.ItemGoal;
import cc.isotopestudio.bookquest.element.goal.MobGoal;
import cc.isotopestudio.bookquest.element.goal.MoneyGoal;
import cc.isotopestudio.bookquest.sql.SqlManager;
import cc.isotopestudio.bookquest.util.S;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

import static cc.isotopestudio.bookquest.BookQuest.econ;
import static cc.isotopestudio.bookquest.BookQuest.plugin;
import static cc.isotopestudio.bookquest.element.Task.tasks;

public class TaskListener implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        boolean allow = false;
        switch (event.getClickedInventory().getType()) {
            case CHEST:
                break;
            case DISPENSER:
                break;
            case DROPPER:
                break;
            case FURNACE:
                break;
            case WORKBENCH:
                break;
            case CRAFTING:
                break;
            case ENCHANTING:
                break;
            case BREWING:
                break;
            case PLAYER:
                allow = true;
                break;
            case CREATIVE:
                allow = true;
                break;
            case MERCHANT:
                break;
            case ENDER_CHEST:
                break;
            case ANVIL:
                break;
            case BEACON:
                break;
            case HOPPER:
                break;
        }
        if (!allow) {
            System.out.println("!allow");
            ItemStack item = event.getCursor();
            if (!(item != null && item.getType() == Material.WRITTEN_BOOK)) {
                return;
            }
            Task task = null;
            for (Task task1 : tasks.values()) {
                if (task1.isBookItem(item)) {
                    task = task1;
                    break;
                }
            }
            if (task == null) return;
            event.setCursor(null);
            HumanEntity player = event.getWhoClicked();
            player.closeInventory();
            player.sendMessage(S.toPrefixRed("任务失败"));
        }
    }

    @EventHandler
    public void onClickBook(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (!(item != null && item.getType() == Material.WRITTEN_BOOK)) {
            return;
        }
        Task task = null;
        for (Task task1 : tasks.values()) {
            if (task1.isBookItem(item)) {
                task = task1;
                break;
            }
        }
        if (task == null) return;
        if (event.getInventory() instanceof PlayerInventory) {
            // MoneyGoal
            int num = -1;
            for (Goal goal : task.getGoals()) {
                if (goal instanceof MoneyGoal) {
                    num = goal.getNum();
                }
            }
            if (num == -1) return;
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            int index = 0;
            for (String s : lore) {
                if (s.contains("金币") && s.contains(String.valueOf(ChatColor.AQUA))) {
                    Player player = (Player) event.getWhoClicked();
                    if (!econ.withdrawPlayer(player, num).transactionSuccess()) {
                        player.sendMessage(S.toPrefixRed("金币不足"));
                        return;
                    }
                    lore.set(index, S.toYellow(s.substring(2, 3) + ": " + "金币 ")
                            + S.toGreen("" + num) + S.toYellow(" / " + num));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    player.getInventory().setItem(event.getSlot(), item);
                    event.setCancelled(true);
                    return;
                }
                index++;
            }
        }
    }

    @EventHandler
    public void onInteractBook(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (!(item != null && item.getType() == Material.WRITTEN_BOOK)) {
            return;
        }
        for (Task task : tasks.values()) {
            if (task.isBookItem(item)) {
                event.setCancelled(true);
                if (task.isFinished(item)) {
                    Player player = event.getPlayer();
                    for (int j = 0; j < player.getInventory().getContents().length; j++) {
                        if (player.getInventory().getContents()[j].equals(item)) {
                            player.getInventory().setItem(j, null);
                            player.sendMessage(S.toPrefixGreen("任务完成"));
                            break;
                        }
                    }
                    task.setRewards(player);
                    SqlManager.addRecord(player, task);
                }
                break;
            }
        }
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent event) {
        LivingEntity dead = event.getEntity();
        Player player = dead.getKiller();
        if (player == null) return;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack item = player.getInventory().getContents()[i];
            Task task = null;
            for (Task task1 : tasks.values()) {
                if (task1.isBookItem(item)) {
                    task = task1;
                    break;
                }
            }
            if (task == null) continue;
            MobGoal goal = null;
            for (Goal goal1 : task.getGoals()) {
                if (goal1 instanceof MobGoal) {
                    MobGoal goal2 = ((MobGoal) goal1);
                    if (goal2.getType() == dead.getType())
                        if (goal2.getName() != null) {
                            if (goal2.getName().equals(dead.getCustomName())) {
                                goal = ((MobGoal) goal1);
                                break;
                            }
                        } else {
                            goal = ((MobGoal) goal1);
                            break;
                        }
                }
            }
            if (goal == null) return;
            int num = goal.getNum();
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            int index = 0;
            for (String s : lore) {
                if (s.contains("杀死") && s.contains(goal.getInfo()) && s.contains(String.valueOf(ChatColor.AQUA))) {
                    int a = s.indexOf(String.valueOf(ChatColor.AQUA));
                    if (a < 0) return;
                    int b = s.indexOf(" /");
                    int killed = Integer.parseInt(s.substring(a + 2, b - 4));
                    if (killed != num) {
                        if (killed + 1 == num) {
                            lore.set(index, s.replace("" + ChatColor.AQUA + killed, "" + ChatColor.GREEN + (killed + 1)));
                        } else {
                            lore.set(index, s.replace("" + ChatColor.AQUA + killed, "" + ChatColor.AQUA + (killed + 1)));
                        }
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        player.getInventory().setItem(i, item);
                    }
                    return;
                }
                index++;
            }
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack pick = event.getItem().getItemStack().clone();
        new BukkitRunnable() {
            @Override
            public void run() {

                if (Arrays.stream(player.getInventory().getContents()).filter(pick::isSimilar).count() < 1) {
                    return;
                }
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    ItemStack item = player.getInventory().getContents()[i];

                    Task task = null;
                    for (Task task1 : tasks.values()) {
                        if (task1.isBookItem(item)) {
                            task = task1;
                            break;
                        }
                    }
                    if (task == null) continue;
                    ItemGoal goal = null;
                    for (Goal goal1 : task.getGoals()) {
                        if (goal1 instanceof ItemGoal) {
                            ItemGoal goal2 = ((ItemGoal) goal1);
                            if (goal2.getType() == pick.getType() && pick.getDurability() == goal2.getData())
                                if (goal2.getName() != null) {
                                    if (pick.hasItemMeta() && pick.getItemMeta().hasDisplayName() &&
                                            goal2.getName().equals(pick.getItemMeta().getDisplayName())) {
                                        goal = goal2;
                                        break;
                                    }
                                } else {
                                    goal = goal2;
                                    break;
                                }
                        }
                    }
                    if (goal == null) return;
                    int num = goal.getNum();
                    int picked = pick.getAmount();
                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = meta.getLore();
                    int index = 0;

                    for (String s : lore) {
                        if (s.contains("收集") && s.contains(goal.getInfo()) && s.contains(String.valueOf(ChatColor.AQUA))) {
                            if (picked >= num) {
                                lore.set(index, s.replace("" + ChatColor.AQUA + 0, "" + ChatColor.GREEN + num));
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                player.getInventory().setItem(i, item);
                                pick.setAmount(picked - num);
                                int slot = -1;
                                for (int j = 0; j < player.getInventory().getContents().length; j++) {
                                    if (player.getInventory().getContents()[j].isSimilar(pick)) {
                                        slot = j;
                                        break;
                                    }
                                }
                                player.getInventory().setItem(slot, pick);
                            }
                            return;
                        }
                        index++;
                    }
                }
            }
        }.runTaskLater(plugin, 2);
    }

    @EventHandler
    public void onThrowItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (!(item != null && item.getType() == Material.WRITTEN_BOOK)) {
            return;
        }
        for (Task task1 : tasks.values()) {
            if (task1.isBookItem(item)) {
                event.getItemDrop().remove();
                event.getPlayer().sendMessage(S.toPrefixRed("任务失败"));
            }
        }
    }
}
