package com.aetherwars.model;

public class SwapSpell extends SpellCard implements TargetUse {
    public SwapSpell(int Id,String name, String desc, int mana){
        super(Id,name,desc,mana,SpellType.SWAP);
    }
    public SwapSpell(SwapSpell ss){
        super(ss.getID(), ss.getCardName(), ss.getCardDescription(), ss.getManaRequired(), SpellType.SWAP);
    }
    @Override
    public void applyTarget(CharacterCard chCard) {
        int initialHealth = chCard.getHealthPoints();
        chCard.setHealthPoints(chCard.getAttackPoints());
        chCard.setAttackPoints(initialHealth);
        System.out.println("You've applied the swap spell to "+chCard.getCardName()+"!");
        System.out.println("Applied card information : "+chCard);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
