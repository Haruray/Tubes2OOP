package com.aetherwars.model;

import com.aetherwars.AsciiArtGenerator;

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
    public void printPlayerHandCards(){
        System.out.println("Your hand cards : ");
        for (int i = 0 ; i < this.getPlayerHand().size();i++){
            if (this.getPlayerHand().get(i) instanceof CharacterCard)
                System.out.println(AsciiArtGenerator.ANSI_PURPLE+(i+1)+". "+this.getPlayerHand().get(i).toString()+AsciiArtGenerator.ANSI_RESET);
            else
                System.out.println(AsciiArtGenerator.ANSI_CYAN+(i+1)+". "+this.getPlayerHand().get(i).toString()+AsciiArtGenerator.ANSI_RESET);

        }
    }
    public void discardCard(int idx){
        this.playerHand.remove(idx);
    }
    public void discardCard(Card c){
        this.playerHand.remove(c);
    }
    public void drawCard() throws DeckSizeException {
        this.playerHand.add(this.playerDeck.popDeck());
    }
    public void resetMana(){
        this.manaPoints = this.maxMana;
    }
}
