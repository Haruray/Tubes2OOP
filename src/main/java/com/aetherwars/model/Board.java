package com.aetherwars.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Card> slot;

    public Board(){
        this.slot=new ArrayList<>();
        for(int i=0 ; i<5;i++){
            this.slot.add(new Card());
        }
    }

    public List<Card> getSlot() {
        return slot;
    }
    public Card getSlot(int idx){
        return this.slot.get(idx);
    }
    public void setSlot(int idx, Card card){
        this.slot.set(idx,card);
    }
    public void removeSlot(int idx){
        this.slot.set(idx, new Card());
    }
    public void removeSlot(Card card){
        int idx=0;
        for (Card c : this.slot){
            if (c.getIDInitialized() == card.getIDInitialized()){
                this.removeSlot(idx);
            }
            idx++;
        }
    }
    public List<Integer> getEmptySlotsIdx(){
        List<Integer> _empties= new ArrayList<>();
        for (int i = 0 ; i < this.slot.size() ; i++){
            if (this.slot.get(i).equals(new Card())){
                _empties.add(i);
            }
        }
        return _empties;
    }

    public List<Integer> getOccupiedSlotsIdx(){
        List<Integer> _notempties= new ArrayList<>();
        for (int i = 0 ; i < this.slot.size() ; i++){
            if (this.slot.get(i).getID()!=-1){
                _notempties.add(i);
            }
        }
        return _notempties;
    }

    public List<Card> getExistingCards(){
        List<Card> _notempties= new ArrayList<>();
        for (int i = 0 ; i < this.slot.size() ; i++){
            if (!this.slot.get(i).equals(new Card())){
                _notempties.add(this.slot.get(i));
            }
        }
        return _notempties;
    }

    @Override
    public String toString() {
        String _tostring="";
        for(Card c : this.slot){
            if (c.equals(new Card())){
                System.out.print("|   |");
            }
            else{
                System.out.print("| "+c.getCardName()+" |");
            }
        }
        return _tostring;
    }
}
