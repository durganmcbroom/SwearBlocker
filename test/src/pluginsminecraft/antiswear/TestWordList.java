package pluginsminecraft.antiswear;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestWordList {

    @Test
    public void GetInstance() {
        WordListDatabase swearWordDictionary = WordListDatabase.getInstance();
    }

    @Test
    public void testGetSet () {
        WordListDatabase swearWordDictionary = WordListDatabase.getInstance();
        String badWord = "BaddfgfdWord1";
        assertFalse(swearWordDictionary.contains(badWord));
        swearWordDictionary.add(badWord);
        assertTrue(swearWordDictionary.contains(badWord));
    }

    @Test
    public void testWrite() {
        WordListDatabase swearWordDictionary = WordListDatabase.getInstance();
        int size = swearWordDictionary.getAll().size();
        swearWordDictionary.add("New_"+Math.random());
        assertEquals(size+1, swearWordDictionary.getAll().size());
        swearWordDictionary.writeToDb();
        swearWordDictionary.clear();
        assertEquals(swearWordDictionary.getAll().size(), 0);
        swearWordDictionary.readFromFile();
        assertEquals(size+1, swearWordDictionary.getAll().size());
    }
//        swearWordDictionary.add("Hello");
//        swearWordDictionary.add("BadWord");



    @Test
    public void testMultipleArgs() {
        List wordList = new ArrayList();
        String[] args;
        int i=1;
        args = new String[4];
        args[i++] = "hello";
        args[i++] = "cow";
        args[i++] = "back";

        if (args.length > 2) {
            for (int count = 0; count < args.length - 1; count++) {
                System.out.println(args[count + 1]);
                wordList.add(args[count + 1]);
            }
            System.out.println("§3SwearBlocker: §rThank you for adding '" + StringUtils.join(wordList, ", ") + "' to the list");
        } else {
            System.out.println("§3SwearBlocker: §rThank you for adding '" + args[2] + "' to the list");
            System.out.println(args[2]);
        }
    }
}




