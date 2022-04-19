package com.aetherwars.model;

public class MainGame {
    private Player firstPlayer;
    private Player secondPlayer;
    private int currentTurn = 1;
    private PlayPhase phase = PlayPhase.DRAW;
    private Board board;

    public MainGame(Player p1, Player p2){
        this.firstPlayer = p1;
        this.secondPlayer = p2;
        this.board = new Board();
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public PlayPhase getPhase() {
        return phase;
    }
    public void drawPhase() throws DeckSizeException {
        Player currPlayer = this.currentTurn==1? this.firstPlayer : this.secondPlayer;
        try{
            currPlayer.drawCard();
            if (currPlayer.getPlayerHand().size() > currPlayer.getMaxHand()){
                //milih kartu
                //terus discard
            }
        }
        catch (Exception e){
            //kartu sudah habis
            System.out.println(e);
        }

        currPlayer.resetMana();
    }
}
