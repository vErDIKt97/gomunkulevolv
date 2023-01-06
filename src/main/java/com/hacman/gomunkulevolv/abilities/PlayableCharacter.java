package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import com.hacman.gomunkulevolv.object.MainCreature;

import java.util.HashMap;

@SuppressWarnings("unused")
public interface PlayableCharacter {
    double getExp();

    void setExp(double exp);

    double getLvlGate();

    void setLvlGate(double lvlGate);

    int getSkillPoint();

    void addSkillPoint();

    void defEnemy(Creature enemy);

    String toString();

    MainCreature getCreature();

    void spendSkillPoint(String title);

    HashMap<PossibleAbility, Ability> getCurrentAbilityList();
}
