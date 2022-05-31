package Obot;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class LifeQuote{
    File file = null;
    ArrayList<String> quoteList;

    public LifeQuote() throws IOException {
        this.file = new File("lifeQuotes.txt");
        quoteList = new ArrayList<>();
        initQuotes();
    }

    private void initQuotes() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = br.readLine();
        while(str != null){
            quoteList.add(str);
            str = br.readLine();
        }
    }

    public String getLifeQuoteRandom(){
        return quoteList.get(makeRandomValue());
    }

    private int makeRandomValue() {
        Random random = new Random();
        return random.nextInt(quoteList.size());
    }
}
