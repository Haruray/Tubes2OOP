package com.aetherwars;

import java.io.*;
import java.net.URISyntaxException;

public class AsciiArtGenerator {
    private static final String ASCII_ART_FILE_PATH = "card/asciiart/";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String getAsciiArt(String name) throws URISyntaxException, IOException {
        String fullPath = ASCII_ART_FILE_PATH+name+".txt";
        StringBuilder filecontent= new StringBuilder();
        String line;
        File asciiArtFile = new File(AsciiArtGenerator.class.getResource(fullPath).toURI());
        FileReader fileReader = new FileReader(asciiArtFile);
        BufferedReader br = new BufferedReader(fileReader);
        while ((line=br.readLine())!=null){
            filecontent.append(line+"\n");
        }
        return filecontent.toString();
    }

}
