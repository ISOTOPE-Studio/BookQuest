package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

public class MoneyGoal extends Goal {

    public MoneyGoal(int num, String intro) {
        super(num, intro);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MoneyGoal{");
        sb.append("num=").append(num);
        sb.append('}');
        return sb.toString();
    }
}
