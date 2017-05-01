package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.entity.EntityType;

public class MobGoal extends Goal {

    private final EntityType type;
    private String name = null;

    public MobGoal(int num, String intro, EntityType type) {
        super(num, intro);
        this.type = type;
    }

    public MobGoal(int num, String intro, EntityType type, String name) {
        this(num, intro, type);
        this.name = name;
    }

    public EntityType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MobGoal{");
        sb.append("num=").append(num);
        sb.append(", type=").append(type);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
