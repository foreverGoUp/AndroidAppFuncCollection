package com.kc.module_home.rv.mvvmRvAdapter;

public class Commodity {

    public Commodity(String name) {
        this.name = name;
    }

    private String name;
    private int buyNum;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}
