package com.aetherwars.model;

public class SwapSpell extends SpellCard implements Useable{
    public SwapSpell(int Id,String name, String desc, int mana){
        super(Id,name,desc,mana,SpellType.SWAP);
    }

    @Override
    public void apply(CharacterCard chCard) {
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
