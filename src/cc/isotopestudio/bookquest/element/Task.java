package cc.isotopestudio.bookquest.element;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.goal.Goal;
import cc.isotopestudio.bookquest.sql.SqlManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class Task {

    public static final Map<String, Task> tasks = new HashMap<>();

    private final String name;
    private final String displayName;
    private final List<Goal> goals;
    private final List<String> rewards;
    private final String limit;

    public Task(String name, String displayName, List<Goal> goals, List<String> rewards, String limit) {
        this.name = name;
        this.displayName = displayName;
        this.goals = goals;
        this.rewards = rewards;
        this.limit = limit;
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
