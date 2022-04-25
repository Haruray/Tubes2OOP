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
    public void getDamage(int damage, CharacterType ct){
        float modifier = 1;
        if (this.type == CharacterType.OVERWORLD){
            if (ct==CharacterType.NETHER)
                modifier = 0.5F;
            else if (ct==CharacterType.END)
                modifier = 2F;
        }
        else if (this.type == CharacterType.NETHER){
            if (ct==CharacterType.OVERWORLD)
                modifier = 2F;
            else if (ct==CharacterType.END)
                modifier = 0.5F;
        }
        else{
            //END
            if (ct==CharacterType.OVERWORLD)
                modifier = 0.5F;
            else if (ct==CharacterType.NETHER)
                modifier = 2F;
        }
        float damageCalculated = damage * modifier;
        this.setHealthPoints(this.getHealthPoints() - (int) Math.floor(damageCalculated));
        System.out.println(this.getCardName()+" is receiving "+(int)damageCalculated+" damage!! Remaining health : "+this.getHealthPoints());
    }
    @Override
    public String toString() {
        String _fromsuper = super.toString().substring(1,super.toString().length()-2)+", ";
        return "{" + _fromsuper+
                "type=" + type +
                ", attackPoints=" + attackPoints +
                ", healthPoints=" + healthPoints +
                '}';
    }
}
