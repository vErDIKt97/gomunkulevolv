package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.Attackable;

import java.util.ArrayList;
import java.util.Random;

public class Creature implements Attackable {
    private int curHealth;
    private int maxHealth;
    private int damage;
    private static final ArrayList<Ability> possibleAbilityList = new ArrayList<>();
    public static final int possibleAbilityCount = 5;
    private ArrayList<Ability> currentAbilityList;
    private int level;
    private final String name;
    private boolean alive;
    private final long atkSpeed;

    private boolean inBattle = false;

    public long getAtkSpeed() {
        return atkSpeed;
    }

    public Creature(Creature creature) {
        this.curHealth = creature.getCurHealth();
        this.maxHealth = creature.getMaxHealth();
        this.damage = creature.getDamage();
        this.currentAbilityList = creature.getCurrentAbilityList();
        this.level = creature.level;
        this.name = creature.getName();
        this.alive = creature.isAlive();
        this.atkSpeed = creature.getAtkSpeed();
        if (possibleAbilityList.size() < possibleAbilityCount - 1) {
            fillDefaultPosAbList();
        }
    }

    public Creature(int level, String name) {
        this.level = level;
        this.damage = (int) (new Random().nextInt(5, 10) * getLevelModify());
        this.curHealth = (int) (new Random().nextInt(60, 100) * getLevelModify());
        this.maxHealth = curHealth;
        this.atkSpeed = new Random().nextLong(75, 100) * 10;
        this.currentAbilityList = new ArrayList<>();
        this.name = name;
        this.alive = true;
    }

    static double getLevelModify() {
        return 1.2;
    }

    @Override
    public String toString() {
        String creature;
        String health;
        if (this.isAlive()) {
            health ="\n Health: " + this.curHealth;
        } else {
            health ="\n Health: " + "DEAD";
        }
            creature = "Name: " + this.name +
                    "\n LvL: " + this.level +
                    health +
                    "\\" + this.maxHealth +
                    "\n Attack: " + this.damage +
                    "\n Attack Speed:" + this.atkSpeed +
                    "\n Alive: " + this.isAlive() +
                    "\n Ability: " + this.currentAbilityList;
            return creature;

    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurHealth() {
        return curHealth;
    }

    public void setCurHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ArrayList<Ability> getCurrentAbilityList() {
        return currentAbilityList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean takeDamage(Creature enemy) {
        curHealth -= enemy.getDamage();
        return true;
    }

    public boolean takeDamage(int damage) {
        curHealth -= damage;
        return true;
    }

    public boolean isAlive() {
        if (this.getCurHealth() <= 0) {
            alive = false;
        }
        return alive;
    }

    public String getName() {
        return name;
    }

    public boolean attack(Creature enemy) {
        return enemy.takeDamage(this);
    }

    public static void fillDefaultPosAbList() {
        Creature.possibleAbilityList.add(new Ability(Ability.evadeAbility)); //0
        Creature.possibleAbilityList.add(new Ability(Ability.regenerationAbility)); //1
        Creature.possibleAbilityList.add(new Ability(Ability.spikeAbility));//2
        Creature.possibleAbilityList.add(new Ability(Ability.vampireAbility));//3
        Creature.possibleAbilityList.add(new Ability(Ability.toxicAttackAbility));//4
        Creature.possibleAbilityList.add(new Ability(Ability.toxicSkinAbility));//5
    }

    public static ArrayList<Ability> getPossibleAbilityList() {
        if (possibleAbilityList.size() < possibleAbilityCount - 1) {
            fillDefaultPosAbList();
        }
        return possibleAbilityList;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }
}
