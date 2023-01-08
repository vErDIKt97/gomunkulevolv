package com.hacman.gomunkulevolv.game.lab.ability;

import com.hacman.gomunkulevolv.object.MainCreature;

public abstract class LabAbility {
    private int levelLabAbility;
    private String title;
    private int cost;
    private int classLabAbility;

    public static LabAbility newLabAbility(LabAbilities ability) {
        switch (ability) {

            case ATTACK -> {
                return new ModifyAttack();
            }
            case DEFENSE -> {
                return new ModifyDefence();
            }
            case ATTACK_SPEED -> {
                return new ModifyAtkSpeed();
            }
            case MAX_HEALTH -> {
                return new ModifyMaxHealth();
            }
            default -> throw new IllegalStateException("Unexpected value: " + ability);
        }
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
    public void modifyCreature(MainCreature creature) {
    }

    @Override
    public String toString() {
        return title + " " + levelLabAbility + " cost: " + cost;
    }

    public void lvlUp() {
        levelLabAbility++;
    }

    public int getClassLabAbility() {
        return classLabAbility;
    }

    public int getLabAbilityLevel() {
        return levelLabAbility;
    }

    public void setAbilityLevel(int labAbilityLevel) {
        this.levelLabAbility = labAbilityLevel;
    }

    public void setClassLabAbility(int classLabAbility) {
        this.classLabAbility = classLabAbility;
    }

    public boolean equals(LabAbility obj) {
        return this.title.equals(obj.title);
    }
}
