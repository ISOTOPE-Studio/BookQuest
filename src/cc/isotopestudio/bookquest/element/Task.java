package cc.isotopestudio.bookquest.element;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.goal.Goal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

    public static final Map<String, Task> tasks = new HashMap<>();

    private final String name;
    private final List<Goal> goals;
    private final List<String> rewards;
    private final String limit;

    public Task(String name, List<Goal> goals, List<String> rewards, String limit) {
        this.name = name;
        this.goals = goals;
        this.rewards = rewards;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public String getLimit() {
        return limit;
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
}
