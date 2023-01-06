package com.hacman.gomunkulevolv.game.session;

import com.hacman.gomunkulevolv.game.lab.ability.LabAbilities;
import com.hacman.gomunkulevolv.game.lab.ability.LabAbility;
import com.hacman.gomunkulevolv.object.MainCreature;

import java.util.HashMap;
import java.util.Map;

public class LabSession {
    private MainCreature mainCreature;
    private int gensCount = 0;
    private final HashMap<LabAbilities, LabAbility> curLabAbility = new HashMap<>();
    private final HashMap<LabAbilities, LabAbility> possibleLabAbility = new HashMap<>();

    private FightSession curFightSession;

    private MainCreature noModifyMainCreature;

    public LabSession() {
        mainCreature = new MainCreature(1,"Hero");
        noModifyMainCreature = mainCreature;
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
        return gensCount;
    }

    public void setGensCount(int gensCount) {
        this.gensCount = gensCount;
    }

    public void refreshMainCreature() {
        mainCreature = new MainCreature(1, "Hero");
        noModifyMainCreature = mainCreature;
    }

    public void appendAbilityModify() {
        MainCreature tempCreature = noModifyMainCreature.getCopy();
        for (Map.Entry<LabAbilities, LabAbility> entry :
                getCurrentLabAbilityMap().entrySet()) {
            entry.getValue().modifyCreature(tempCreature);
        }
        mainCreature = tempCreature;
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

    public void getGensFromCurFightSession() {
        gensCount += curFightSession.getEarnedGens();
    }
}
