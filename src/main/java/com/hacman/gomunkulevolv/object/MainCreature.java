package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;

public class MainCreature extends Creature implements PlayableCharacter {
    private int exp;
    private int lvlGate;
    private int skillPoint;

    private double levelModify = 1;

    public MainCreature(MainCreature creature) {
        super(creature);
        this.exp = 0;
        this.lvlGate = creature.getLevel() * 100;
        this.skillPoint = 0;
    }

    public double getLevelModify() {
        return this.levelModify;
    }

    public MainCreature(int level, String name) {
        super(level, name);
        float stronger = (float) 3;
        this.setDamage((int) (this.getDamage() * stronger/2));
        this.setMaxHealth((int) (this.getCurHealth() * stronger));
        this.setCurHealth(this.getMaxHealth());
        this.exp = 0;
        this.lvlGate = level * 100;
        this.skillPoint = 0;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Exp: " + this.exp + "\\" + this.lvlGate;
    }

    @Override
    public MainCreature getCreature() {
        return this;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        while (this.lvlGate <= exp) {
            this.setExp(this.getExp() - this.lvlGate);
            this.lvlUp();
        }
    }

    private void lvlUp() {
        this.setLevel(this.getLevel() + 1);
        this.setLvlGate(this.getLvlGate() * 2);
        this.setDamage((int) (this.getDamage() * getLevelModify()));
        this.setMaxHealth((int) (this.getMaxHealth() * getLevelModify()));
        this.setCurHealth((int) (this.getCurHealth() * getLevelModify()));
        this.addSkillPoint();
    }

    @Override
    public void addSkillPoint() {
        this.skillPoint++;
    }

    @Override
    public void spendSkillPoint(String title) {
        this.skillPoint--;
        title = title.split(" ", 2)[0];
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
    public int getLvlGate() {
        return lvlGate;
    }

    @Override
    public void setLvlGate(int lvlGate) {
        this.lvlGate = lvlGate;
    }

    @Override
    public void defEnemy(Creature enemy) {
        int formulaExp = this.getExp() + enemy.getLevel() * 20;
        this.setExp(formulaExp);
    }

    @Override
    public int getSkillPoint() {
        return skillPoint;
    }
}
