package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;

import java.util.Random;

public class MainCreature extends Creature implements PlayableCharacter {
    private double exp;
    private double expRatio = 1;
    private double lvlGate;
    private int skillPoint;
    private final double modifyDamage = new Random().nextDouble(-5, 5);
    private final double modifyHealth = new Random().nextDouble(-20, 20);

    public MainCreature(MainCreature mainCreature) {
        super(mainCreature);
        this.exp = mainCreature.getExp();
        this.lvlGate = mainCreature.getLvlGate();
        this.skillPoint = mainCreature.getSkillPoint();
        this.expRatio = mainCreature.getExpRatio();
    }

    public MainCreature(int level, String name) {
        super(level, name);
        this.setDamage(getDamageOnFormulaDamage());
        this.setMaxHealth(getHealthOnFormulaHealth());
        this.setCurHealth(this.getMaxHealth());
        this.exp = 0;
        this.lvlGate = level * 50;
        this.skillPoint = 1;
    }

    private double getModifyDamage() {
        return modifyDamage;
    }

    private double getModifyHealth() {
        return modifyHealth;
    }


    @Override
    public String toString() {
        return super.toString() + "\n Exp: " + this.exp + "\\" + this.lvlGate;
    }

    @Override
    public MainCreature getCreature() {
        return this;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
        while (this.lvlGate <= exp) {
            this.setExp(this.getExp() - this.lvlGate);
            this.lvlUp();
        }
    }

    private void lvlUp() {
        this.setLevel(this.getLevel() + 1);
        this.setLvlGate(this.getLvlGate() * 2);
        this.setDamage(getDamageOnFormulaDamage());
        this.setMaxHealth(getHealthOnFormulaHealth());
        this.addSkillPoint();
    }

    private double getHealthOnFormulaHealth() {
        return Math.pow(this.getLevel() + 10, 2) + 100 + getModifyHealth();
    }

    private double getDamageOnFormulaDamage() {
        return ((2.0 / 25.0) * Math.pow(this.getLevel() + 15, 2)) + getModifyDamage();
    }

    @Override
    public void addSkillPoint() {
        this.skillPoint++;
    }

    @SuppressWarnings("unused")
    @Override
    public void spendSkillPoint(String title) {
        this.skillPoint--;
        PossibleAbility ability = PossibleAbility.getAbilityByTitle(title);
        if (!getCurrentAbilityList().containsKey(ability)) {
            try {
                addAbility(ability);
            } catch (RuntimeException e) {
                throw new RuntimeException("Chosen Ability not exist");
            }
        } else lvlUpAbility(ability);
    }

    private void addAbility(PossibleAbility ability) throws RuntimeException {
        this.getCurrentAbilityList().put(ability, Ability.newAbility(ability));
        lvlUpAbility(ability);
    }

    private void lvlUpAbility(PossibleAbility ability) {
        getCurrentAbilityList().get(ability).lvlUp();
    }

    @Override
    public double getLvlGate() {
        return lvlGate;
    }

    @Override
    public void setLvlGate(double lvlGate) {
        this.lvlGate = lvlGate;
    }

    @SuppressWarnings("unused")
    @Override
    public void defEnemy(Creature enemy) {
        double formulaExp = this.getExp() + enemy.getLevel() * 20 * getExpRatio();
        this.setExp(formulaExp);
    }

    @SuppressWarnings("unused")
    @Override
    public int getSkillPoint() {
        return skillPoint;
    }

    public MainCreature getCopy() {
        return new MainCreature(this);
    }

    public double getExpRatio() {
        return expRatio;
    }

    public void setExpRatio(double expRatio) {
        this.expRatio = expRatio;
    }
}
