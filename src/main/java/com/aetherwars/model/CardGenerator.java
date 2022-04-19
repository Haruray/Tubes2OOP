package com.aetherwars.model;

public class CardGenerator {
    public static Card getCard(int id,String type, String name, String desc, int manaRequired, int atk, int hp, CharacterType t1){
        if ("CHARACTER".equalsIgnoreCase(type)){
            return new CharacterCard(id, name, desc, manaRequired, t1,atk,hp);
        }
        else if ("SPELL_PTN".equalsIgnoreCase(type)){
            return new PotionSpell(id,name,desc,manaRequired,atk,hp);
        }
        else if ("SPELL_SWAP".equalsIgnoreCase(type)){
            return new SwapSpell(id,name,desc,manaRequired);
        }
        return null;
    }
}
