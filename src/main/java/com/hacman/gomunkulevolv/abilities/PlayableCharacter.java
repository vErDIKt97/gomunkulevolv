package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import com.hacman.gomunkulevolv.object.MainCreature;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public interface PlayableCharacter {
    int getExp();

    void setExp(int exp);

    int getLvlGate();

    void setLvlGate(int lvlGate);

    int getSkillPoint();

    void addSkillPoint();

    void defEnemy(Creature enemy);

    String toString();

    MainCreature getCreature();

    void spendSkillPoint(String title);
    HashMap<PossibleAbility, Ability> getCurrentAbilityList();
}
