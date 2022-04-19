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
    }
}
