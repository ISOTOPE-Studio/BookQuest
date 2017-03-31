package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.entity.EntityType;

public class MobGoal extends Goal {

    private final EntityType type;
    private String name = null;

    public MobGoal(int num, EntityType type) {
        super(num);
        this.type = type;
    }

    public MobGoal(int num, EntityType type, String name) {
        this(num, type);
        this.name = name;
    }

    @Override
    public String getInfo() {
        return (name == null ? type.name() : name + "(" + type.name() + ")") + " ¡Á" + num;
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
