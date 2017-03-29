package cc.isotopestudio.bookquest.task;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.element.goal.Goal;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static cc.isotopestudio.bookquest.BookQuest.plugin;

public class UpdateConfigTask extends BukkitRunnable {

    @Override
    public void run() {
        Task.tasks.clear();
        taskLoop:
        for (String taskName : plugin.questFile.getKeys(false)) {
            ConfigurationSection section = plugin.questFile.getConfigurationSection(taskName);
            String displayName = section.getString("name");

            ConfigurationSection goalsConfig = section.getConfigurationSection("goal");
            if (goalsConfig == null) {
                System.out.println("ERROR GOAL SETTINGS");
                continue;
            }
            List<Goal> goals = new ArrayList<>();
            for (String goalName : goalsConfig.getKeys(false)) {
                ConfigurationSection goalSection = goalsConfig.getConfigurationSection(goalName);
                String type = goalSection.getString("type");
                switch (type) {
                    case "mob":

                        break;
                    case "item":

                        break;
                    case "money":

                        break;
                    case "time":

                        break;
                    default:
                        System.out.println("ERROR GOAL TYPE");
                        continue taskLoop;
                }
            }
            if (goals.size() == 0) {
                System.out.println("ERROR GOAL SETTINGS");
                continue;
            }

            if (section.isSet("limit")) {
                String limit = section.getString("limit");

            }
        }
    }

}
