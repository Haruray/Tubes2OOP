package com.aetherwars.model;

public class PotionSpell extends SpellCard implements TargetUse {
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
    public void applyTarget(CharacterCard chCard) {
        chCard.setAttackPoints(chCard.getAttackPoints()-this.attackPointsMod);
        chCard.setHealthPoints(chCard.getHealthPoints()-this.healthPointsMod);
        System.out.println("You've applied the potion spell to "+chCard.getCardName()+"!");
        System.out.println("Applied card information : "+chCard);
    }

    @Override
    public String toString() {
        String _fromsuper = super.toString().substring(1,super.toString().length()-2)+", ";
        return "{" + _fromsuper+
                "attackPointsMod=" + attackPointsMod +
                ", healthPointsMod=" + healthPointsMod +
                '}';
    }
}
