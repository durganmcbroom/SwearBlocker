package pluginsminecraft.antiswear;

import java.io.*;
import java.util.*;

import static pluginsminecraft.antiswear.PlayerDataDataBase.BASE_DIR;

public class WordListDatabase {

    public static final String SWEAR_WORD_FILE = BASE_DIR + "swearWordList.dat";
    private static Set<String> swearWordDictionary = new HashSet<String>();

    private static WordListDatabase instance ;

    public synchronized static WordListDatabase getInstance() {
        if (instance == null) {
            instance = new WordListDatabase();
            instance.readFromFile();
        }
        return instance;
    }

    public  void readFromFile() {
        PlayerDataDataBase.testForFile(SWEAR_WORD_FILE);
        // pass the path to the file as a parameter
        File file =
                new File(SWEAR_WORD_FILE);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                swearWordDictionary.add(sc.nextLine());
            }

        } catch (FileNotFoundException e) {
        }
    }

    public void writeToDb() {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(SWEAR_WORD_FILE), "utf-8"));
            for (String word : swearWordDictionary) {
                writer.write(word + System.lineSeparator());
            }
        } catch (IOException ex) {
            // Report

        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }
    }

    public void add(String word) {
        swearWordDictionary.add(word);
    }
    public void remove(String word) {
        swearWordDictionary.remove(word);
    }
    public boolean contains(String word) {
        return swearWordDictionary.contains(word);
    }
    public Set<String> getAll() {
        return swearWordDictionary;
    }
    public void clear() {
        swearWordDictionary.clear();
    }


}
