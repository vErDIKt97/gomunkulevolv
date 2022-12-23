package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import org.jetbrains.annotations.NotNull;

public class MainCreature extends Creature implements PlayableCharacter {
    private int exp;
    private int lvlGate;
    private int skillPoint;

    public MainCreature(MainCreature creature) {
        super(creature);
        this.exp = 0;
        this.lvlGate = creature.getLevel() * 100;
        this.skillPoint = 0;
    }

    public MainCreature(int level, String name) {
        super(level, name);
        float stronger = (float) 3;
        this.setDamage((int) (this.getDamage() * stronger));
        this.setCurHealth((int) (this.getCurHealth() * stronger));
        this.setMaxHealth(this.getCurHealth());
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
    public MainCreature spendSkillPoint(String title) {
        this.skillPoint--;
        title = title.split(" ",2)[0];
        return addAbility(title);
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

    @Override
    public MainCreature addAbility(@NotNull String abilityTitle) {
        switch (abilityTitle) {
            case Ability.evadeAbility -> {
                getPossibleAbilityList().get(0).lvlUp();
                return addAbilityEvade();
            }
            case Ability.regenerationAbility -> {
                getPossibleAbilityList().get(1).lvlUp();
                return addAbilityRegen();
            }
            case Ability.spikeAbility -> {
                getPossibleAbilityList().get(2).lvlUp();
                return addAbilitySpike();
            }
            case Ability.vampireAbility -> {
                getPossibleAbilityList().get(3).lvlUp();
                return addAbilityVampire();
            }
            case Ability.toxicAttackAbility -> {
                getPossibleAbilityList().get(4).lvlUp();
                return addAbilityToxicAttack();
            }
            case Ability.toxicSkinAbility -> {
                getPossibleAbilityList().get(5).lvlUp();
                return addAbilityToxicSkin();
            }
        }
        return null;
    }

   private ToxicSkinMainCreature addAbilityToxicSkin() {
        return new ToxicSkinMainCreature(this);
    }

    private ToxicAtkMainCreature addAbilityToxicAttack() {
        return new ToxicAtkMainCreature(this);
    }

    private SpikeMainCreature addAbilitySpike() {
        return new SpikeMainCreature(this);
    }

    private RegenMainCreature addAbilityRegen() {
        return new RegenMainCreature(this);
    }

    public VampireMainCreature addAbilityVampire() {
        return new VampireMainCreature(this);
    }

    public EvadeMainCreature addAbilityEvade() {
        return new EvadeMainCreature(this);
    }
}

