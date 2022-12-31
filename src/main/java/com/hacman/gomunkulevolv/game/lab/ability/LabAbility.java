package com.hacman.gomunkulevolv.game.lab.ability;

import com.hacman.gomunkulevolv.object.Creature;

@SuppressWarnings("unused")
public abstract class LabAbility {
    private int level;
    private String title;
    private int cost;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @SuppressWarnings({"unused", "EmptyMethod"})
    public void modifyCreature(Creature creature) {
    }

}
