package com.aetherwars.model;

public class SpellCard extends Card{
    private SpellType type;

    public SpellCard(int Id,String name, String desc, int mana, SpellType type){
        super(Id,name,desc,mana);
        this.type = type;
    }

    public SpellType getType() {
        return type;
    }
}
