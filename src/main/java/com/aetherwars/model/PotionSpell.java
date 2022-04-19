package com.aetherwars.model;

public class PotionSpell extends SpellCard implements Useable{
    private int attackPointsMod;
    private int healthPointsMod;

    public PotionSpell(int Id,String name, String desc, int mana, int atkMod, int hpMod){
        super(Id,name,desc,mana,SpellType.PTN);
        this.attackPointsMod = atkMod;
        this.healthPointsMod = hpMod;
    }

    public int getAttackPointsMod() {
        return attackPointsMod;
    }

    public int getHealthPointsMod() {
        return healthPointsMod;
    }

    @Override
    public void apply(CharacterCard chCard) {
        chCard.setAttackPoints(chCard.getAttackPoints()-this.attackPointsMod);
        chCard.setHealthPoints(chCard.getHealthPoints()-this.healthPointsMod);
    }
}
