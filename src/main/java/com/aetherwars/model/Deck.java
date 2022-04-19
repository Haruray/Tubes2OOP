package com.aetherwars.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private final int maxCard = 60;
    private final int minCard = 40;

    public Deck(List<Card> cards) throws DeckSizeException {
        if (cards.size()>=this.minCard && cards.size()<=maxCard){
            this.cards = new ArrayList<>();
            this.cards.addAll(cards);
        }
        else{
            throw new DeckSizeException("Number of cards not fit to the deck.");
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getMaxCard() {
        return maxCard;
    }

    public int getMinCard() {
        return minCard;
    }
    
    public void addCard(Card card) throws DeckSizeException {
        if (cards.size()<maxCard){
            this.cards.add(card);
        }
        else{
            throw new DeckSizeException("Deck is full.");
        }
    }

    public Card popDeck() throws DeckSizeException {
        if (this.cards.size()>0){
            Card c = this.cards.get(0);
            this.cards.remove(0);
            return c;
        }
        else{
            throw new DeckSizeException("Deck is empty.");
        }
    }

    public void removeCard(Card card){
        this.cards.remove(card);
    }
}
