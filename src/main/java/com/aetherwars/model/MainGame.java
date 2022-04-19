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
    private PlayPhase phase = PlayPhase.START;
    private Board board1;
    private Board board2;
    private Scanner scanner;
    private boolean gameEnd = false;

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
    public void drawPhase(Player currPlayer) throws DeckSizeException {
        try{
            currPlayer.drawCard();
            if (currPlayer.getPlayerHand().size() > currPlayer.getMaxHand()){
                //milih kartu
                currPlayer.printPlayerHandCards();
                System.out.print("Select a card to discard (by number): ");
                int idx = indexInput(IntStream.rangeClosed(1, currPlayer.getPlayerHand().size()).boxed().collect(Collectors.toList()), false);
                //terus discard
                System.out.println(currPlayer.getPlayerHand().get(idx-1).getCardName()+" is discarded.");
                currPlayer.discardCard(idx-1);
            }
        }
        catch (Exception e){
            //kartu sudah habis
            System.out.println(e);
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
                System.out.print(a+" | ");
            }
            System.out.print("\nSelect slot index to summon: ");
            int _slotinput = indexInput(currBoard.getEmptySlotsIdx(), false);
            currBoard.setSlot(_slotinput,chosenCard);
            currPlayer.setManaPoints(currPlayer.getManaPoints()-chosenCard.getManaRequired());
            currPlayer.discardCard(chosenCard);
        }
        else{
            System.out.println("Mana not enough.");
        }
    }

    public void useSpell(Player currPlayer, Board currBoard, Board enemyBoard,Card chosenCard){
        if (chosenCard.getManaRequired() <= currPlayer.getManaPoints()){
            System.out.println("Your occupied slots on board: ");
            for (int a : currBoard.getOccupiedSlotsIdx()){
                System.out.println(a +": "+ currBoard.getSlot(a).toString());
            }
            System.out.print("Select a card your on board to apply spell to (enter -1 to choose on enemies board instead): ");
            int _slotinput = indexInput(currBoard.getOccupiedSlotsIdx(), true);
            if (_slotinput==-1){
                System.out.println("Enemies occupied slots on board: ");
                for (int b : enemyBoard.getOccupiedSlotsIdx()){
                    System.out.println(b +": " + currBoard.getSlot(b).toString());
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
            currPlayer.setManaPoints(currPlayer.getManaPoints()-chosenCard.getManaRequired());
            currPlayer.discardCard(chosenCard);
        }
        else{
            System.out.println("Mana not enough.");
        }
    }

    public void planningPhase(Player currPlayer, Board currBoard, Board enemyBoard){
        System.out.println("Available actions:\n" +
                "1. Use card\n" +
                "2. Discard card\n" +
                "3. Throw card from board\n" +
                "4. End planning phase");
        System.out.print("Select action to do (input the number): ");
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
            System.out.println("Available actions:\n" +
                    "1. Use card\n" +
                    "2. Discard card\n" +
                    "3. Throw card from board\n" +
                    "4. End planning phase");
            System.out.print("Select action to do (input the number): ");
            _input = indexInput(new ArrayList<>(Arrays.asList(1,2,3,4)), false);
        }
        this.phase = PlayPhase.ATTACK;
    }

    public void attackPhase(Player currPlayer, Board enemyBoard){
        //TODO
        //the whole thing
        List<CharacterCard> _hasAttacked = new ArrayList<>();
        this.phase = PlayPhase.END;
    }
    public void endPhase(Player currPlayer) {
        // TODO
        // Konfigurasi mana
        this.currentTurn = this.currentTurn==1? 2 : 1;
        this.phase = PlayPhase.DRAW;
        currPlayer.resetMana();
    }
    public void printInformations(){
        System.out.println("\n===Player 1 Information===");
        System.out.println(this.board1);
        System.out.println("Health : "+ this.firstPlayer.getHealthPoints()+"/80");
        System.out.println("Mana : "+this.firstPlayer.getManaPoints()+"/"+this.firstPlayer.getMaxMana());
        System.out.println("Deck : "+this.firstPlayer.getPlayerDeck().getCards().size()+" cards left");
        System.out.println("\n===Player 2 Information==");
        System.out.println(this.board2);
        System.out.println("Health : "+ this.secondPlayer.getHealthPoints()+"/80");
        System.out.println("Mana : "+this.secondPlayer.getManaPoints()+"/"+this.secondPlayer.getMaxMana());
        System.out.println("Deck : "+this.secondPlayer.getPlayerDeck().getCards().size()+" cards left\n");
    }
    public void runPhase() throws DeckSizeException {
        Player currPlayer = this.currentTurn==1? this.firstPlayer : this.secondPlayer;
        Board currBoard = this.currentTurn==1? this.board1 : this.board2;
        Board enemyBoard = this.currentTurn==1? this.board2:this.board1;
        while (!gameEnd){
            currPlayer = this.currentTurn==1? this.firstPlayer : this.secondPlayer;
            currBoard = this.currentTurn==1? this.board1 : this.board2;
            enemyBoard = this.currentTurn==1? this.board2:this.board1;


            if (this.phase == PlayPhase.START) {
                startPhase();
            }
            else if (this.phase == PlayPhase.DRAW){
                drawPhase(currPlayer);
            }
            else if (this.phase == PlayPhase.PLANNING){
                printInformations();
                if (this.currentTurn==1){System.out.println("Player 1 Turn!!");}
                else{System.out.println("Player 2 Turn!!");}
                currPlayer.printPlayerHandCards();
                planningPhase(currPlayer,currBoard,enemyBoard);
            }
            else if (this.phase == PlayPhase.ATTACK){
                printInformations();
                attackPhase(currPlayer, enemyBoard);
            }
            else if (this.phase == PlayPhase.END){
                endPhase(currPlayer);
            }
        }
    }



    public void endGameScreen(){
        //check kondisi menang/kalah
        this.gameEnd = true;
    }

    public static void main(String[] args) throws DeckSizeException {
        CharacterCard chc1 = new CharacterCard(1,"bruh","jancok",1,CharacterType.NETHER,10,10);
        List<Card> chc = new ArrayList<>(Arrays.asList(chc1));
        for (int i = 0 ; i < 40 ; i++){
            chc.add(new CharacterCard(2,"bruh2","jancok2",1,CharacterType.NETHER,10,10));
        }
        Deck dick = new Deck(chc);
        Player p1 = new Player("cok1",dick);
        Player p2 = new Player("cok2",dick);
        MainGame ng = new MainGame(p1,p2);
        ng.runPhase();
    }
}
