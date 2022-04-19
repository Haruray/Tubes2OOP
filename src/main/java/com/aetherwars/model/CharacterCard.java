package com.aetherwars.model;

public class CharacterCard extends Card{
    private CharacterType type;
    private int attackPoints;
    private int healthPoints;

    public CharacterCard(int Id,String name, String desc, int mana, CharacterType type, int atk, int hp) {
        super(Id,name,desc,mana);
        this.type = type;
        this.attackPoints = atk;
        this.healthPoints = hp;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public CharacterType getType() {
        return type;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    @Override
    public String toString() {
        String _fromsuper = super.toString().substring(0,super.toString().length()-2)+", ";
        return "{" + _fromsuper+
                "type=" + type +
                ", attackPoints=" + attackPoints +
                ", healthPoints=" + healthPoints +
                '}';
    }
}
