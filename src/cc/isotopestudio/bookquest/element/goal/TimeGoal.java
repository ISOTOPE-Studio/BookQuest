package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

public class TimeGoal extends Goal {

    public TimeGoal(int num) {
        super(num);
    }

    @Override
    public String getInfo() {
        return num +" ����";
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TimeGoal{");
        sb.append("num=").append(num);
        sb.append('}');
        return sb.toString();
    }
}
