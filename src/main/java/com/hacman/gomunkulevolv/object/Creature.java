package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.Attackable;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Creature implements Attackable {
    private int curHealth;
    private int maxHealth;
    private int damage;
    private static final HashMap<PossibleAbility, Ability> possibleAbilityList = new HashMap<>();
    public static final int possibleAbilityCount = 5;
    private final HashMap<PossibleAbility, Ability> currentAbilityList;
    private int level;
    private final String name;
    private boolean alive;
    private final long atkSpeed;

    private boolean inBattle = false;
    private final double levelModify = 1;

    public long getAtkSpeed() {
        return atkSpeed;
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public Creature(@NotNull Creature creature) {
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
        this.damage = (int) (new Random().nextInt(5, 10) * this.getLevelModify());
        this.curHealth = (int) (new Random().nextInt(60, 100) * this.getLevelModify());
        this.maxHealth = curHealth;
        this.atkSpeed = new Random().nextLong(75, 100) * 10;
        this.currentAbilityList = new HashMap<>();
        this.name = name;
        this.alive = true;
    }

    private double getLevelModify() {
        return levelModify;
    }

    @Override
    public String toString() {
        String creature;
        String health;
        if (this.isAlive()) {
            health = "\n Health: " + this.curHealth;
        } else {
            health = "\n Health: " + "DEAD";
        }
        creature = "Name: " + this.name +
                "\n LvL: " + this.level +
                health +
                "\\" + this.maxHealth +
                "\n Attack: " + this.damage +
                "\n Attack Speed:" + this.atkSpeed +
                "\n Alive: " + this.isAlive() +
                "\n Ability: " + this.currentAbilityList.values();
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
        this.curHealth = Math.min((curHealth), this.maxHealth);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
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

    public boolean takeDamage(int damage) {
        curHealth -= damage;
        return true;
    }

    public void takeDamage(@NotNull Creature fromCreature) {
        float takenDamage = fromCreature.getDamage();
        takenDamage *= this.useDefAbility(fromCreature);
        curHealth -= takenDamage;
    }

    private float useDefAbility(Creature fromCreature) {
        float damageRatio = 1;
        if (this.getCurrentAbilityList().size() > 0) {
            for (Map.Entry<PossibleAbility, Ability> entry : this.getCurrentAbilityList().entrySet()) {
                damageRatio *= entry.getValue().onDefense(this, fromCreature);
            }
        }
        return damageRatio;
    }

    private boolean isHitSuccess(Creature fromCreature) {
        boolean hitSuccess = true;
        if (this.getCurrentAbilityList().size() > 0) {
            for (Map.Entry<PossibleAbility, Ability> entry : this.getCurrentAbilityList().entrySet()) {
                hitSuccess = hitSuccess && entry.getValue().isAtkSuccess(this, fromCreature);
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
        if (enemy.isHitSuccess(this)) {
            enemy.takeDamage(this);
            this.useOnSuccessAttackAbility(this, enemy);
            return true;
        } else return false;
    }

    private void useOnSuccessAttackAbility(Creature creature, @NotNull Creature enemy) {
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
}
