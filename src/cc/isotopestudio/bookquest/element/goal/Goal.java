package cc.isotopestudio.bookquest.element.goal;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

public abstract class Goal {

    int num;
    private String info;

    Goal(int num, String info) {
        this.num = num;
        this.info = info;
    }

    public int getNum() {
        return num;
    }

    public String getInfo() {
        return info;
    }
}
