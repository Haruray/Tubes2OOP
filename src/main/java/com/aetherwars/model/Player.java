package com.aetherwars.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String playerName;
    private int healthPoints = 80;
    private int maxMana = 1;
    private int manaPoints = maxMana;
    private Deck playerDeck;
    private List<Card> playerHand;
    private final int maxHand = 5;

    public Player(String playerName, Deck deck){
        this.playerName = playerName;
        this.playerDeck = deck;
        this.playerHand = new ArrayList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMaxHand() {
        return maxHand;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }

    public void setPlayerDeck(Deck playerDeck) {
        this.playerDeck = playerDeck;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void discardCard(int idx){
        this.playerHand.remove(idx);
    }
    public void drawCard() throws DeckSizeException {
        this.playerHand.add(this.playerDeck.popDeck());
    }
    public void resetMana(){
        this.manaPoints = this.maxMana;
    }
}
