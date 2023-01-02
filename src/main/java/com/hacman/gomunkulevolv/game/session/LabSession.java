package com.hacman.gomunkulevolv.game.session;

import com.hacman.gomunkulevolv.game.lab.ability.LabAbilities;
import com.hacman.gomunkulevolv.game.lab.ability.LabAbility;
import com.hacman.gomunkulevolv.object.MainCreature;

import java.util.HashMap;
import java.util.Map;

public class LabSession {
    private MainCreature mainCreature;
    private int gensCount = 50;
    private final HashMap<LabAbilities, LabAbility> curLabAbility = new HashMap<>();
    private final HashMap<LabAbilities, LabAbility> possibleLabAbility = new HashMap<>();

    private FightSession curFightSession;

    public LabSession() {
        mainCreature = new MainCreature(1,"Hero");
        fillPosLabAb();
    }

    public MainCreature getMainCreature() {
        return mainCreature;
    }

    private void fillPosLabAb() {
        possibleLabAbility.put(LabAbilities.ATTACK, LabAbility.newLabAbility(LabAbilities.ATTACK));
        possibleLabAbility.put(LabAbilities.DEFENSE, LabAbility.newLabAbility(LabAbilities.DEFENSE));
        possibleLabAbility.put(LabAbilities.ATTACK_SPEED, LabAbility.newLabAbility(LabAbilities.ATTACK_SPEED));
        possibleLabAbility.put(LabAbilities.MAX_HEALTH, LabAbility.newLabAbility(LabAbilities.MAX_HEALTH));
    }

    private void addLabAbility(LabAbilities ability) {
        curLabAbility.put(ability, LabAbility.newLabAbility(ability));
        lvlUpAbility(ability);
    }

    private void lvlUpAbility(LabAbilities ability) {
        getCurrentLabAbilityMap().get(ability).lvlUp();
    }

    public FightSession startNewFightSession() {
        curFightSession = new FightSession(mainCreature);
        return curFightSession;
    }

    public int getGensCount() {
        if (curFightSession!=null) {
            gensCount += curFightSession.getEarnedGens();
        }
        return gensCount;
    }

    public void refreshMainCreature() {
        mainCreature = new MainCreature(1, "Hero");
        appendAbilityModify(mainCreature);
    }

    public void appendAbilityModify(MainCreature mainCreature) {
        for (Map.Entry<LabAbilities, LabAbility> entry :
                getCurrentLabAbilityMap().entrySet()) {
            entry.getValue().modifyCreature(mainCreature);
        }
    }


    public void spendGens(String title) {
        LabAbilities ability = LabAbilities.getLabAbilityByTitle(title);
        gensCount -=getPossibleAbilityMap().get(ability).getCost();
        if (curLabAbility.containsKey(ability))
            curLabAbility.get(ability).lvlUp();
        else addLabAbility(ability);
    }

    public HashMap<LabAbilities, LabAbility> getPossibleAbilityMap() {
        return possibleLabAbility;
    }

    public Map<LabAbilities, LabAbility> getCurrentLabAbilityMap() {
        return curLabAbility;
    }

    public FightSession getCurFightSession() {
        return curFightSession;
    }

    public void clearCurFightSession() {
        curFightSession = null;
    }
}
