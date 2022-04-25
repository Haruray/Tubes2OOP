package com.aetherwars.model;

import java.util.Objects;

public class Card{
    private int ID;
    private int IDInitialized;
    protected static int cardsInitialized=0;
    private String cardName;
    private String cardDescription;
    private int manaRequired;

    public Card(){
        this.ID = -1;
        this.cardName="";
        this.cardDescription = "";
        this.manaRequired=0;
        cardsInitialized++;
        this.IDInitialized=cardsInitialized;
    }
    public Card(int Id,String name, String desc, int mana){
        this.ID = Id;
        this.cardName = name;
        this.cardDescription = desc;
        this.manaRequired = mana;
        cardsInitialized++;
        this.IDInitialized = cardsInitialized;
    }
    public int getID(){
        return this.ID;
    }
    public String getCardName(){
        return this.cardName;
    }
    public String getCardDescription(){
        return this.cardDescription;
    }
    public int getManaRequired(){
        return this.manaRequired;
    }
    public int getIDInitialized(){
        return this.IDInitialized;
    }
    public void setCardName(String name){
        this.cardName = name;
    }
    public void setCardDescription(String desc){
        this.cardDescription = desc;
    }
    public void setManaRequired(int mana){
        this.manaRequired = mana;
    }

    public boolean checkIdInitialized(Card c){
        return c.getIDInitialized() == this.getIDInitialized();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getID() == card.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    @Override
    public String toString() {
        return "{" +
                "ID=" + ID +
                ", cardName='" + cardName + '\'' +
                ", cardDescription='" + cardDescription + '\'' +
                ", manaRequired=" + manaRequired +
                '}';
    }
}