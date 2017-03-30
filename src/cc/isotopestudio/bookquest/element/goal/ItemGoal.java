package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.Material;

public class ItemGoal extends Goal {

    private final Material type;
    private String name;
    private byte data = 0;

    public ItemGoal(int num, Material type) {
        super(num);
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemGoal{");
        sb.append("type=").append(type);
        sb.append(", name='").append(name).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
