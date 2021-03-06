package com.aetherwars.model;

import com.aetherwars.AsciiArtGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
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
        if (canCancel){choices.add(-1);}
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
        boolean usedSpell=false;
        if (chosenCard.getManaRequired() <= currPlayer.getManaPoints()){
            System.out.println("Your occupied slots on board: ");
            for (int a : currBoard.getOccupiedSlotsIdx()){
                System.out.println(a +": "+ currBoard.getSlot(a).toString());
            }
            System.out.print("Select a card your on board to apply spell to (enter -1 to choose on enemies board instead): ");
            int _slotinput = indexInput(currBoard.getOccupiedSlotsIdx(), true);
            if (_slotinput==-1){
                if (enemyBoard.getOccupiedSlotsIdx().size()>0){
                    System.out.println("Enemies occupied slots on board: ");
                    for (int b : enemyBoard.getOccupiedSlotsIdx()){
                        System.out.println(b +": " + enemyBoard.getSlot(b).toString());
                    }
                    System.out.print("Select a card on enemy board to apply spell to: ");
                    _slotinput = indexInput(enemyBoard.getOccupiedSlotsIdx(), false);
                    SpellCard chosenSpellCard = (SpellCard) chosenCard;
                    chosenSpellCard.applyTarget((CharacterCard) enemyBoard.getSlot(_slotinput));
                    usedSpell = true;
                }
                else{
                    System.out.println("No cards to apply the effect of the spell.");
                }

            }
            else{
                SpellCard chosenSpellCard = (SpellCard) chosenCard;
                chosenSpellCard.applyTarget((CharacterCard) enemyBoard.getSlot(_slotinput));
                usedSpell = true;
            }
            if (usedSpell){
                currPlayer.setManaPoints(currPlayer.getManaPoints()-chosenCard.getManaRequired());
                currPlayer.discardCard(chosenCard);
            }
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
                System.out.println(currPlayer.getPlayerHand().get(idx-1).getCardName()+" is discarded.");
                currPlayer.discardCard(idx-1);
            }
            else if (_input==3){
                //throw card from board
                System.out.println("Your occupied slots on board: ");
                for (int a : currBoard.getOccupiedSlotsIdx()){
                    System.out.println(a +": "+ currBoard.getSlot(a).toString());
                }
                System.out.print("Select a card your on board to throw away: ");
                int _slotinput = indexInput(currBoard.getOccupiedSlotsIdx(), true);
                currBoard.removeSlot(_slotinput);
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

    public void attackPhase(Player currPlayer, Board currBoard, Player enemyPlayer,Board enemyBoard){
        //TODO
        //the whole thing
        int _input = 0;
        List<Card> _hasAttacked = new ArrayList<>();
        List<CharacterCard> cardChoices = new ArrayList<>();
        CharacterCard target;
        CharacterCard selected;
        int i = 1;
        int j = 1;
        int currCardIdxChoice;
        int enemyCardIdxChoice;
        while (!_hasAttacked.containsAll(currBoard.getExistingCards()) && _input!=-1){
            //milih kartu untuk attack
            System.out.println("Your summoned characters :");
            for(Card c : currBoard.getExistingCards()){
                if (!_hasAttacked.contains(c)){
                    CharacterCard chc = (CharacterCard) c;
                    cardChoices.add(chc);
                    System.out.println(i+". " +chc);
                    i++;
                }
            }
            System.out.print("Select character card (input the number. Enter -1 to skip): ");
            currCardIdxChoice = indexInput(IntStream.rangeClosed(1, i).boxed().collect(Collectors.toList()), true);
            if (currCardIdxChoice == -1){
                break;
            }
            selected = cardChoices.get(currCardIdxChoice-1);
            //memilih musuh yang diserang
            if (enemyBoard.getOccupiedSlotsIdx().size()!=0){
                System.out.println("Enemies cards to attack : ");
                for (Card c : enemyBoard.getExistingCards()){
                    CharacterCard ech = (CharacterCard) c;
                    System.out.println(j+". "+ech);
                    j++;
                }
                System.out.print("Select character card (input the number): ");
                enemyCardIdxChoice = indexInput(IntStream.rangeClosed(1, j).boxed().collect(Collectors.toList()), false);
                target = (CharacterCard) enemyBoard.getExistingCards().get(enemyCardIdxChoice-1);
                //damage calculation
                System.out.println(selected.getCardName()+" is attacking "+target.getCardName()+" !!");
                selected.getDamage(target.getAttackPoints(), target.getType());
                target.getDamage(selected.getAttackPoints(),selected.getType());
                //check if dies
                if (selected.getHealthPoints() <= 0)
                    currBoard.removeSlot(selected);
                if (target.getHealthPoints() <= 0)
                    enemyBoard.removeSlot(target);
            }
            else{
                //damage calculation
                enemyPlayer.setHealthPoints(enemyPlayer.getHealthPoints()- selected.getAttackPoints());
                System.out.println(selected.getCardName()+" is attacking enemy player!! Enemy remaining health : "+enemyPlayer.getHealthPoints());
                if (enemyPlayer.getHealthPoints() <= 0){
                    endGameScreen();
                }
            }
            //repeat
            _hasAttacked.add((Card) selected);
        }
        this.phase = PlayPhase.END;
    }
    public void endPhase(Player currPlayer) {
        // TODO
        // Konfigurasi mana
        this.currentTurn = this.currentTurn==1? 2 : 1;
        this.phase = PlayPhase.DRAW;
        if (this.firstPlayer.getMaxMana()<10)
            this.firstPlayer.setMaxMana(this.firstPlayer.getMaxMana()+1);
        if (this.secondPlayer.getMaxMana()<10)
            this.secondPlayer.setMaxMana(this.secondPlayer.getMaxMana()+1);
        currPlayer.resetMana();
    }
    public void printInformations(){
        System.out.println("\n==="+firstPlayer.getPlayerName()+"'s Information===");
        System.out.println(this.board1);
        System.out.println(AsciiArtGenerator.ANSI_RED+"Health : "+AsciiArtGenerator.ANSI_RESET+ this.firstPlayer.getHealthPoints()+"/80");
        System.out.println(AsciiArtGenerator.ANSI_CYAN+"Mana : "+AsciiArtGenerator.ANSI_RESET+this.firstPlayer.getManaPoints()+"/"+this.firstPlayer.getMaxMana());
        System.out.println("Deck : "+this.firstPlayer.getPlayerDeck().getCards().size()+" cards left");
        System.out.println("\n==="+secondPlayer.getPlayerName()+"'s Information==");
        System.out.println(this.board2);
        System.out.println(AsciiArtGenerator.ANSI_RED+"Health : "+AsciiArtGenerator.ANSI_RESET+ this.secondPlayer.getHealthPoints()+"/80");
        System.out.println(AsciiArtGenerator.ANSI_CYAN+"Mana : "+AsciiArtGenerator.ANSI_RESET+this.secondPlayer.getManaPoints()+"/"+this.secondPlayer.getMaxMana());
        System.out.println("Deck : "+this.secondPlayer.getPlayerDeck().getCards().size()+" cards left\n");
    }
    public void runPhase() throws DeckSizeException, URISyntaxException, IOException {
        Player currPlayer;
        Player enemyPlayer;
        Board currBoard;
        Board enemyBoard;
        while (!gameEnd){
            currPlayer = this.currentTurn==1? this.firstPlayer : this.secondPlayer;
            enemyPlayer = this.currentTurn==1? this.secondPlayer : this.firstPlayer;
            currBoard = this.currentTurn==1? this.board1 : this.board2;
            enemyBoard = this.currentTurn==1? this.board2:this.board1;

            if (this.phase == PlayPhase.START) {
                startPhase();
            }
            else if (this.phase == PlayPhase.DRAW){
                drawPhase(currPlayer);
            }
            else if (this.phase == PlayPhase.PLANNING){
                if (this.currentTurn==1){System.out.println(AsciiArtGenerator.ANSI_BLUE+AsciiArtGenerator.getAsciiArt("steve")+AsciiArtGenerator.ANSI_RESET);}
                else{System.out.println(AsciiArtGenerator.ANSI_YELLOW+AsciiArtGenerator.getAsciiArt("alex")+AsciiArtGenerator.ANSI_RESET);}
                printInformations();
                System.out.println(AsciiArtGenerator.ANSI_GREEN+"=PLANNING PHASE="+AsciiArtGenerator.ANSI_RESET);
                currPlayer.printPlayerHandCards();
                planningPhase(currPlayer,currBoard,enemyBoard);
            }
            else if (this.phase == PlayPhase.ATTACK){
                printInformations();
                System.out.println(AsciiArtGenerator.ANSI_RED+"=ATTACK PHASE="+AsciiArtGenerator.ANSI_RESET);
                attackPhase(currPlayer, currBoard, enemyPlayer,enemyBoard);
            }
            else if (this.phase == PlayPhase.END){
                System.out.println(AsciiArtGenerator.ANSI_WHITE+"=ENDPHASE="+AsciiArtGenerator.ANSI_RESET);
                endPhase(currPlayer);
            }
        }
    }

    public void endGameScreen(){
        //check kondisi menang/kalah
        System.out.println("GAME OVER!");
        if (this.firstPlayer.getHealthPoints()<=0 || this.firstPlayer.getPlayerDeck().getCards().size()==0){
            System.out.println(this.secondPlayer.getPlayerName()+" WINS!!");
        }
        else{
            System.out.println(this.firstPlayer.getPlayerName()+" WINS!!");
        }
        this.gameEnd = true;
    }

}
