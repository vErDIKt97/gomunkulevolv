package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;
import com.hacman.gomunkulevolv.service.GameService;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Creature {
    private double curHealth;
    private double maxHealth;
    private double damage;
    private static final HashMap<PossibleAbility, Ability> possibleAbilityList = new HashMap<>();
    public static final int possibleAbilityCount = 5;
    private final HashMap<PossibleAbility, Ability> currentAbilityList;
    private int level;
    private final String name;
    private boolean alive;
    private final long atkSpeed;

    private boolean inBattle = false;
    private double levelModify = 1;

    public Creature(Creature creature) {
        this.curHealth = creature.getCurHealth();
        this.maxHealth = creature.getMaxHealth();
        this.atkSpeed = creature.getAtkSpeed();
        this.levelModify = creature.getLevelModify();
        this.level = creature.getLevel();
        this.currentAbilityList = creature.getCurrentAbilityList();
        this.inBattle = creature.isInBattle();
        this.alive = creature.isAlive();
        this.damage = creature.getDamage();
        this.name = creature.getName();
    }

    public long getAtkSpeed() {
        return atkSpeed;
    }

    public Creature(int level, String name) {
        this.level = level;
        this.damage = getDamageOnFormula();
        this.curHealth = getCurHealthOnFormula();
        this.maxHealth = curHealth;
        this.atkSpeed = new Random().nextLong(75, 100) * 10;
        this.currentAbilityList = new HashMap<>();
        this.name = name;
        this.alive = true;
    }

    private double getCurHealthOnFormula() {
        return Math.pow(3 * this.level + 8, 2) + 10 + new Random().nextDouble(-20, 20);
    }

    private double getDamageOnFormula() {
        return Math.pow(2 * this.level + 5, 2) / 9 + 5 + new Random().nextDouble(-5, 5);
    }

    double getLevelModify() {
        return levelModify;
    }

    @Override
    public String toString() {
        String creature;
        String health;
        if (this.isAlive()) {
            health = "\n Health: " + GameService.getFormatDouble(this.curHealth);
        } else {
            health = "\n Health: " + "DEAD";
        }
        creature = "Name: " + this.name +
                "\n LvL: " + this.level +
                health +
                "\\" + GameService.getFormatDouble(maxHealth) +
                "\n Attack: " + GameService.getFormatDouble(damage) +
                "\n Attack Speed:" + this.atkSpeed +
                "\n Alive: " + this.isAlive() +
                "\n Ability: " + this.currentAbilityList.values();
        return creature;

    }



    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getCurHealth() {
        return curHealth;
    }

    public void setCurHealth(double curHealth) {
        this.curHealth = Math.min((curHealth), this.maxHealth);
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public HashMap<PossibleAbility, Ability> getCurrentAbilityList() {
        return currentAbilityList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void takeDamage(int damage) {
        curHealth -= damage;
    }

    public void takeDamage(@NotNull Creature fromCreature) {
        double takenDamage = fromCreature.getDamage();
        takenDamage *= this.useDefAbility(fromCreature);
        curHealth -= takenDamage;
    }

    private float useDefAbility(Creature fromCreature) {
        float damageRatio = 1;
        if (this.getCurrentAbilityList().size() > 0) {
            for (Map.Entry<PossibleAbility, Ability> entry : this.getCurrentAbilityList().entrySet()) {
                damageRatio *= entry.getValue().onDefense(fromCreature);
            }
        }
        return damageRatio;
    }

    private boolean isHitSuccess() {
        boolean hitSuccess = true;
        if (this.getCurrentAbilityList().size() > 0) {
            for (Map.Entry<PossibleAbility, Ability> entry : this.getCurrentAbilityList().entrySet()) {
                hitSuccess = hitSuccess && entry.getValue().isAtkSuccess();
            }
        }
        return hitSuccess;
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

    public boolean attack(@NotNull Creature enemy) {
        if (enemy.isHitSuccess()) {
            enemy.takeDamage(this);
            this.useOnSuccessAttackAbility(enemy);
            return true;
        } else return false;
    }

    private void useOnSuccessAttackAbility(Creature enemy) {
        if (this.getCurrentAbilityList().size() > 0)
            for (Map.Entry<PossibleAbility, Ability> entry :
                    this.getCurrentAbilityList().entrySet()) {
                Ability ability = entry.getValue();
                ability.onSuccessAttack(this, enemy);
            }
    }

    public static void fillDefaultPosAbList() {
        Creature.possibleAbilityList.put(PossibleAbility.EVADE, Ability.newAbility(PossibleAbility.EVADE)); //0
        Creature.possibleAbilityList.put(PossibleAbility.REGENERATION, Ability.newAbility(PossibleAbility.REGENERATION)); //1
        Creature.possibleAbilityList.put(PossibleAbility.SPIKE, Ability.newAbility(PossibleAbility.SPIKE));//2
        Creature.possibleAbilityList.put(PossibleAbility.VAMPIRE, Ability.newAbility(PossibleAbility.VAMPIRE));//3
        Creature.possibleAbilityList.put(PossibleAbility.TOXIC_ATTACK, Ability.newAbility(PossibleAbility.TOXIC_ATTACK));//4
        Creature.possibleAbilityList.put(PossibleAbility.TOXIC_SKIN, Ability.newAbility(PossibleAbility.TOXIC_SKIN));//5
    }

    public static HashMap<PossibleAbility, Ability> getPossibleAbilityList() {
        if (possibleAbilityList.size() < possibleAbilityCount - 1) {
            fillDefaultPosAbList();
        }
        return possibleAbilityList;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        boolean curState = this.inBattle;
        this.inBattle = inBattle;
        if (inBattle != curState && inBattle) useOnStartBattleAbility(this);
    }

    /**
     * Use ability affected on start battle
     *
     * @param creature affected primary creature
     */
    private void useOnStartBattleAbility(Creature creature) {
        if (this.currentAbilityList.size() > 0)
            for (Map.Entry<PossibleAbility, Ability> entry :
                    this.getCurrentAbilityList().entrySet()) {
                Ability ability = entry.getValue();
                ability.onStartBattle(creature);
            }
    }

    public void restoreHealth(double restoredHealth) {
        curHealth +=restoredHealth;
    }
}
