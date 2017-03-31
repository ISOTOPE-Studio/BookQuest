package cc.isotopestudio.bookquest.element;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.BookQuest;
import cc.isotopestudio.bookquest.element.goal.*;
import cc.isotopestudio.bookquest.sql.SqlManager;
import cc.isotopestudio.bookquest.util.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.*;

public class Task {

    public static final Map<String, Task> tasks = new HashMap<>();

    private final String name;
    private final String displayName;
    private final List<Goal> goals;
    private final List<String> rewards;
    private final String limit;

    private final List<String> lore = new ArrayList<>();
    private final ItemStack bookItem = new ItemStack(Material.WRITTEN_BOOK);

    public Task(String name, String displayName, List<Goal> goals,
                List<String> rewards, List<String> rewardsinfo, String limit) {
        this.name = name;
        this.displayName = displayName;
        this.goals = goals;
        this.rewards = rewards;
        List<String> rewardsinfo1 = rewardsinfo;
        this.limit = limit;

        lore.add(S.toBoldGold("任务目标: "));
        int i = 1;
        for (Goal goal : goals) {
            if (goal instanceof ItemGoal) {
                ItemGoal itemGoal = (ItemGoal) goal;
                lore.add(S.toYellow(i + ": " + "收集 " + itemGoal.getInfo()));
            } else if (goal instanceof MobGoal) {
                MobGoal mobGoal = (MobGoal) goal;
                lore.add(S.toYellow(i + ": " + "杀死 " + mobGoal.getInfo()));
            } else if (goal instanceof MoneyGoal) {
                MoneyGoal moneyGoal = (MoneyGoal) goal;
                lore.add(S.toYellow(i + ": " + "金币 " + moneyGoal.getInfo()));
            } else if (goal instanceof TimeGoal) {
                TimeGoal timeGoal = (TimeGoal) goal;
                lore.add(S.toYellow(i + ": " + "在线 " + timeGoal.getInfo()));
            }
            i++;
        }
        lore.add(S.toBoldGold("任务奖励"));
        lore.addAll(rewardsinfo);
        lore.add(S.toGray("————————————"));
        if (limit != null) {
            if (limit.equalsIgnoreCase("daily")) {
                lore.add(S.toGreen("每日任务"));
            } else if (limit.endsWith("h")) {
                lore.add(S.toGreen("每 " + limit.replaceAll("h", "小时")));
            } else {
                lore.add(S.toGreen("可做 " + limit + " 次"));
            }
        } else {
            lore.add(S.toGreen("无限制"));
        }
        BookMeta meta = (BookMeta) bookItem.getItemMeta();
        meta.setAuthor(BookQuest.prefix);
        meta.addPage("");
        meta.setDisplayName(displayName);
        meta.setLore(getLore());
        bookItem.setItemMeta(meta);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setRewards(Player player) {
        rewards.forEach(s -> Bukkit.dispatchCommand(console,
                s.replace("<player>", player.getName())));
    }

    private static final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public boolean isAvailable(Player player) {
        if (limit == null) return true;
        List<Long> record = SqlManager.getRecord(player, this);
        if (record.size() == 0) return true;
        if (limit.equalsIgnoreCase("daily")) {
            return !isToday(new Date(record.get(record.size() - 1)));
        } else if (limit.endsWith("h")) {
            int hour = Integer.parseInt(limit.replaceAll("h", ""));
            long diff = System.currentTimeMillis() - record.get(record.size() - 1);
            return diff > hour * 60 * 60 * 1000;
        } else {
            return record.size() < Integer.parseInt(limit);
        }
    }

    public List<String> getLore() {
        return new ArrayList<>(lore);
    }

    public ItemStack getBookItem() {
        return bookItem.clone();
    }

    public boolean isBookItem(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName()
                .equals(bookItem.getItemMeta().getDisplayName());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Task{");
        sb.append("name='").append(name).append('\'');
        sb.append(", goals=").append(goals);
        sb.append(", rewards=").append(rewards);
        sb.append(", limit='").append(limit).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private static boolean isToday(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH) + 1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        return year1 == year2 && month1 == month2 && day1 == day2;
    }

}
