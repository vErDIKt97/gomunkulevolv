package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;
import com.hacman.gomunkulevolv.service.GameService;

import java.util.Random;

public class MainCreature extends Creature implements PlayableCharacter {
    private double exp;
    private double expRatio = 1;
    private double lvlGate;
    private int skillPoint;
    private final double randomModifyHealth = new Random().nextDouble(-20, 20) + 50;
    private double abModifyDamage = 0;

    public MainCreature(MainCreature mainCreature) {
        super(mainCreature);
        this.exp = mainCreature.getCurExp();
        this.lvlGate = mainCreature.getLvlGate();
        this.skillPoint = mainCreature.getSkillPoint();
        this.expRatio = mainCreature.getExpRatio();
        this.abModifyDamage = mainCreature.abModifyDamage;
    }

    public MainCreature(int level, String name) {
        super(level, name);
        this.setDamage(getDamageOnFormulaDamage());
        this.setMaxHealth(getHealthOnFormulaHealth());
        this.setCurHealth(this.getMaxHealth());
        this.exp = 0;
        this.lvlGate = level * 50;
        this.skillPoint = 0;
    }

    private double getRandomModifyHealth() {
        return randomModifyHealth;
    }


    @Override
    public String toString() {
        return super.toString()
                + "\n Exp: " + GameService.getFormatDouble(this.exp)
                + "\\" + GameService.getFormatDouble(this.lvlGate);
    }

    @Override
    public MainCreature getCreature() {
        return this;
    }

    public double getCurExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
        while (this.lvlGate <= exp) {
            this.setExp(this.getCurExp() - this.lvlGate);
            this.lvlUp();
        }
    }

    private void lvlUp() {
        this.setLevel(this.getLevel() + 1);
        this.setLvlGate(this.getLvlGate() * 2);
        this.setDamage(getDamageOnFormulaDamage());
        this.setMaxHealth(getHealthOnFormulaHealth());
        this.restoreHealth(this.getMaxHealth());
        this.addSkillPoint();
    }

    private double getHealthOnFormulaHealth() {
        return Math.pow(this.getLevel() + 10, 2) + 100 + getRandomModifyHealth();
    }

    private double getDamageOnFormulaDamage() {
        return ((2.0 / 25.0) * Math.pow(this.getLevel() + 15, 2)) + getAbModifyDamage();
    }

    @Override
    public void addSkillPoint() {
        this.skillPoint++;
    }

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
    public void learnFromEnemy(Creature enemy) {
        double formulaExp = this.getCurExp() + enemy.getLevel() * 20 * getExpRatio();
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

    public double getAbModifyDamage() {
        return abModifyDamage;
    }

    public void setAbModifyDamage(double abModifyDamage) {
        this.abModifyDamage = abModifyDamage;
        this.setDamage(this.getDamageOnFormulaDamage());
    }

    public void devourEnemy(Creature creature) {
        learnFromEnemy(creature);
        this.restoreHealth(creature.getMaxHealth() * 0.1);
    }

    @Override
    public int getGensForConsume(Creature creature) {
        learnFromEnemy(creature);
        return creature.getLevel()*10;

    }
}
