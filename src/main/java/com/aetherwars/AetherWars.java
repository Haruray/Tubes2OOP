package com.aetherwars;
//
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
//
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
import com.aetherwars.model.*;
import com.aetherwars.util.CSVReader;
//
public class AetherWars{

    private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
    private static final String SPELLPTN_CSV_FILE_PATH = "card/data/spell_ptn.csv";
    private static final String SPELLSWP_CSV_FILE_PATH = "card/data/spell_swap.csv";

    private static final String PLAYER_ONE_DECK_PATH = "card/data/deck1.csv";
    private static final String PLAYER_TWO_DECK_PATH = "card/data/deck2.csv";
    private static List<Card> cards = new ArrayList<>();
    public void loadCards() throws IOException, URISyntaxException {
        File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
        File potionCSVFile = new File(getClass().getResource(SPELLPTN_CSV_FILE_PATH).toURI());
        File swapCSVFile = new File(getClass().getResource(SPELLSWP_CSV_FILE_PATH).toURI());
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        CSVReader potionReader = new CSVReader(potionCSVFile, "\t");
        CSVReader swapReader = new CSVReader(swapCSVFile, "\t");
        characterReader.setSkipHeader(true);
        potionReader.setSkipHeader(true);
        swapReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            cards.add(CardGenerator.getCard(Integer.parseInt(row[0]),"CHARACTER", row[1], row[3], Integer.parseInt(row[7]), Integer.parseInt(row[5]),Integer.parseInt(row[6]), CharacterType.valueOf(row[2])));
        }
        List<String[]> potionRows = potionReader.read();
        for (String[] row : potionRows) {
            cards.add(CardGenerator.getCard(Integer.parseInt(row[0]),"SPELL_PTN", row[1], row[2], Integer.parseInt(row[6]), Integer.parseInt(row[4]),Integer.parseInt(row[5]), CharacterType.NONE));
        }
        List<String[]> swapRows = swapReader.read();
        for (String[] row : swapRows) {
            cards.add(CardGenerator.getCard(Integer.parseInt(row[0]),"SPELL_SWAP", row[1], row[2], Integer.parseInt(row[4]), 0,0, CharacterType.NONE));
        }
    }

    public Deck loadDeck(int whichDeck) throws IOException, URISyntaxException, DeckSizeException {
        List<Card> cardOnDeck = new ArrayList<>();
        String DECK_PATH = whichDeck==1? PLAYER_ONE_DECK_PATH : PLAYER_TWO_DECK_PATH;
        try {
            File deckFile = new File(getClass().getResource(DECK_PATH).toURI());
            CSVReader deckReader = new CSVReader(deckFile, "\t");
            deckReader.setSkipHeader(true);
            List<String[]> deckRows = deckReader.read();
            for (String[] row : deckRows) {
                String data = row[0];
                if (cards.stream().filter(c -> c.getID() == Integer.parseInt(data)).findAny().get() instanceof CharacterCard)
                    cardOnDeck.add(new CharacterCard((CharacterCard) cards.stream().filter(c -> c.getID() == Integer.parseInt(data)).findAny().get()));
                else if (cards.stream().filter(c -> c.getID() == Integer.parseInt(data)).findAny().get() instanceof PotionSpell)
                    cardOnDeck.add(new PotionSpell((PotionSpell) cards.stream().filter(c -> c.getID() == Integer.parseInt(data)).findAny().get()));
                else if (cards.stream().filter(c -> c.getID() == Integer.parseInt(data)).findAny().get() instanceof SwapSpell)
                    cardOnDeck.add(new SwapSpell((SwapSpell) cards.stream().filter(c -> c.getID() == Integer.parseInt(data)).findAny().get()));
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Deck deck = new Deck(cardOnDeck);
        return deck;
    }

    public static void main(String[] args) throws DeckSizeException, URISyntaxException, IOException {
        AetherWars aetherWars = new AetherWars();
        aetherWars.loadCards();
        Deck deck1= aetherWars.loadDeck(1);
        Deck deck2 = aetherWars.loadDeck(2);
        deck1.shuffle();
        deck2.shuffle();
        Player p1 = new Player("steve",deck1);
        Player p2 = new Player("alex",deck2);
        MainGame ng = new MainGame(p1,p2);
        ng.runPhase();
    }
}
