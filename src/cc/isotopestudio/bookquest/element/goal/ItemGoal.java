package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemGoal extends Goal {

    private final Material type;
    private String name = null;
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

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public byte getData() {
        return data;
    }

    @Override
    public String getInfo() {
        return (name == null ? type.name() : name) + (data == 0 ? "" : ":" + data);
    }

    public boolean isItem(ItemStack item) {
        return item.getType() == type
                && item.getDurability() == data
                && !(name != null && item.hasItemMeta()
                && item.getItemMeta().getDisplayName().equals(name));
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
