package com.hacman.gomunkulevolv.object;

public class ToxicAtkMainCreature extends MainCreature {

    private int toxicAtkIndex = 4;

    private int toxicAtkValue;

    public ToxicAtkMainCreature(MainCreature mainCreature) {
        super(mainCreature);
        refreshToxicAtkValue();
        this.getCurrentAbilityList().add(getPossibleAbilityList().get(toxicAtkIndex));
    }

    private void refreshToxicAtkValue() {
        toxicAtkValue = getPossibleAbilityList().get(toxicAtkIndex).getLevel() * 5 / 100 * getDamage();
    }


}
