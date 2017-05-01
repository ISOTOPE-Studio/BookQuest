package cc.isotopestudio.bookquest.task;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.element.goal.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cc.isotopestudio.bookquest.BookQuest.plugin;
import static cc.isotopestudio.bookquest.element.Task.tasks;

public class UpdateConfigTask extends BukkitRunnable {

    @Override
    public void run() {
        tasks.clear();
        taskLoop:
        for (String taskName : plugin.questFile.getKeys(false)) {
            ConfigurationSection section = plugin.questFile.getConfigurationSection(taskName);
            String displayName = s(section.getString("name"));
            List<String> taskInfo = section.getStringList("info")
                    .stream().map(UpdateConfigTask::s).collect(Collectors.toList());
            ConfigurationSection goalsConfig = section.getConfigurationSection("goal");
            if (goalsConfig == null) {
                System.out.println("ERROR GOAL SETTINGS");
                continue;
            }
            List<Goal> goals = new ArrayList<>();
            for (String goalName : goalsConfig.getKeys(false)) {
                Goal goal;
                ConfigurationSection goalSection = goalsConfig.getConfigurationSection(goalName);
                String goalType = goalSection.getString("type");
                int num = goalSection.getInt("num");
                String info = s(goalSection.getString("info"));
                switch (goalType) {
                    case "mob":
                        EntityType etype;
                        try {
                            etype = EntityType.valueOf(goalSection.getString("mob").toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("ERROR MOB TYPE");
                            continue taskLoop;
                        }
                        String mobName = goalSection.getString("name");
                        goal = mobName == null ?
                                new MobGoal(num, info, etype) : new MobGoal(num, info, etype, s(mobName));
                        break;
                    case "item":
                        Material mtype;
                        try {
                            mtype = Material.valueOf(goalSection.getString("item").toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("ERROR ITEM TYPE");
                            continue taskLoop;
                        }
                        goal = new ItemGoal(num, info, mtype);
                        if (goalSection.isSet("name")) {
                            ((ItemGoal) goal).setName(s(goalSection.getString("name")));
                        }
                        if (goalSection.isSet("data")) {
                            ((ItemGoal) goal).setData((byte) goalSection.getInt("data"));
                        }
                        break;
                    case "money":
                        goal = new MoneyGoal(num, info);
                        break;
                    case "time":
                        goal = new TimeGoal(num, info);
                        break;
                    default:
                        System.out.println("ERROR GOAL TYPE");
                        continue taskLoop;
                }
                goals.add(goal);

            }
            if (goals.size() == 0) {
                System.out.println("ERROR GOAL SETTINGS");
                continue;
            }
            List<String> rewards = section.getStringList("rewards");
            List<String> rewardsinfo = section.getStringList("rewardsinfo")
                    .stream().map(UpdateConfigTask::s).collect(Collectors.toList());

            String limit = null;
            if (section.isSet("limit")) {
                limit = section.getString("limit");
            }
            tasks.put(taskName, new Task(taskName, displayName, taskInfo, goals, rewards, rewardsinfo, limit));
        }

        System.out.println(tasks);

    }

    private static String s(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
