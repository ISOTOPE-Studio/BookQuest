package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

public abstract class Goal {

    int num;

    public Goal(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public abstract String getInfo();
}
