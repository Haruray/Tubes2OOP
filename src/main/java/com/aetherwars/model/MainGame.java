package com.aetherwars.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainGame {
    private Player firstPlayer;
    private Player secondPlayer;
    private int currentTurn = 1;
    private PlayPhase phase = PlayPhase.DRAW;
    private Board board1;
    private Board board2;
    private Scanner scanner;

    public MainGame(Player p1, Player p2){
        this.firstPlayer = p1;
        this.secondPlayer = p2;
        this.board1 = new Board();
        this.board2 = new Board();
        this.scanner = new Scanner(System.in);
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Board getFirstPlayerBoard() {
        return board1;
    }
    public Board getSecondPlayerBoard(){
        return board2;
    }

    public PlayPhase getPhase() {
        return phase;
    }
    public int indexInput(List<Integer> choices, boolean canCancel){
        int _input = scanner.nextInt();
        while (!choices.contains(_input)){
            System.out.print("Wrong input. Try again : ");
            _input = scanner.nextInt();
            if (canCancel && _input==-1){
                break;
            }
        }
        return _input;
    }
    public void startPhase(){
        //ambil 3 kartu
        for (int i = 0 ; i < 3 ; i++){
            try{
                this.firstPlayer.drawCard();
                this.secondPlayer.drawCard();
            }
            catch (Exception e){
                //Tidak mungkin deck habis, sehingga hal ini tidak ditangani.
            }

        }
        this.phase = PlayPhase.DRAW;
    }
    public void drawPhase() throws DeckSizeException {
        Player currPlayer = this.currentTurn==1? this.firstPlayer : this.secondPlayer;
        try{
            currPlayer.drawCard();
            if (currPlayer.getPlayerHand().size() > currPlayer.getMaxHand()){
                //milih kartu
                currPlayer.printPlayerHandCards();
                System.out.print("Select a card to discard (by number): ");
                int idx = indexInput(IntStream.rangeClosed(1, currPlayer.getPlayerHand().size()).boxed().collect(Collectors.toList()), false);
                //terus discard
                System.out.println(currPlayer.getPlayerHand().get(idx).getCardName()+" is discarded.");
                currPlayer.discardCard(idx-1);
            }
        }
        catch (Exception e){
            //kartu sudah habis
            //System.out.println(e);
            endGameScreen();
        }
        currPlayer.resetMana();
        this.phase = PlayPhase.PLANNING;
    }

    public void summon(Player currPlayer,Board currBoard,Card chosenCard){
        //execute character card
        if (chosenCard.getManaRequired() <= currPlayer.getManaPoints()){
            System.out.print("Empty slots on board : ");
            for (int a : currBoard.getEmptySlotsIdx()){
                System.out.print(a);
            }
            System.out.print("\nSelect slot index to summon: ");
            int _slotinput = indexInput(currBoard.getEmptySlotsIdx(), false);
            currBoard.setSlot(_slotinput,chosenCard);
            currPlayer.setManaPoints(currPlayer.getManaPoints()-chosenCard.getManaRequired());
        }
        else{
            System.out.println("Mana not enough.");
        }
    }

    public void useSpell(Player currPlayer, Board currBoard, Board enemyBoard,Card chosenCard){
        if (chosenCard.getManaRequired() <= currPlayer.getManaPoints()){
            System.out.println("Your occupied slots on board: ");
            for (int a : currBoard.getOccupiedSlotsIdx()){
                System.out.println(a + currBoard.getSlot(a).toString());
            }
            System.out.print("Select a card your on board to apply spell to (enter -1 to choose on enemies board instead): ");
            int _slotinput = indexInput(currBoard.getOccupiedSlotsIdx(), true);
            if (_slotinput==-1){
                System.out.println("Enemies occupied slots on board: ");
                for (int b : enemyBoard.getOccupiedSlotsIdx()){
                    System.out.println((b+5) + currBoard.getSlot(b).toString());
                }
                System.out.print("Select a card on enemy board to apply spell to: ");
                _slotinput = indexInput(currBoard.getOccupiedSlotsIdx(), false);
                SpellCard chosenSpellCard = (SpellCard) chosenCard;
                chosenSpellCard.apply((CharacterCard) enemyBoard.getSlot(_slotinput));
            }
            else{
                SpellCard chosenSpellCard = (SpellCard) chosenCard;
                chosenSpellCard.apply((CharacterCard) enemyBoard.getSlot(_slotinput));
            }
        }
        else{
            System.out.println("Mana not enough.");
        }
    }

    public void planningPhase(){
        Player currPlayer = this.currentTurn==1? this.firstPlayer : this.secondPlayer;
        Board currBoard = this.currentTurn==1? this.board1 : this.board2;
        Board enemyBoard = this.currentTurn==1? this.board2:this.board1;
        System.out.println("Available actions:\n" +
                "1. Use card\n" +
                "2. Discard card\n" +
                "3. Throw card from board\n" +
                "4. End turn");
        int _input = indexInput(new ArrayList<>(Arrays.asList(1,2,3,4)), false);
        while (_input!=4){
            if (_input == 1){
                //use card
                currPlayer.printPlayerHandCards();
                System.out.print("Select a card to use: ");
                int idx = indexInput(IntStream.rangeClosed(1, currPlayer.getPlayerHand().size()).boxed().collect(Collectors.toList()), false);
                Card chosenCard = currPlayer.getPlayerHand().get(idx-1);
                if (chosenCard instanceof CharacterCard){
                    summon(currPlayer,currBoard,chosenCard);
                }
                else if (chosenCard instanceof SpellCard){
                    useSpell(currPlayer,currBoard,enemyBoard,chosenCard);
                }
            }
            else if (_input==2){
                //discard card
                //milih kartu
                currPlayer.printPlayerHandCards();
                System.out.print("Select a card to discard (by number): ");
                int idx = indexInput(IntStream.rangeClosed(1, currPlayer.getPlayerHand().size()).boxed().collect(Collectors.toList()), false);
                //terus discard
                System.out.println(currPlayer.getPlayerHand().get(idx).getCardName()+" is discarded.");
                currPlayer.discardCard(idx);
            }
            else if (_input==3){
                //throw card from board
            }
        }

    }

    public void endGameScreen(){}

}
