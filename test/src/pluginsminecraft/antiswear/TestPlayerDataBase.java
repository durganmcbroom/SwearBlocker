package pluginsminecraft.antiswear;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class TestPlayerDataBase {

    @Test
    public void testEndToEnd() {
        PlayerDataDataBase instance = PlayerDataDataBase.getInstance();
        instance.clear();
        testGetWordCount();
        instance.write();
        instance.clear();
        instance.read();
        String userId = "uniquePlayerId123";
        Map<String, Integer> swearWords = instance.getSwearWordsStats(userId);
        for (String word: swearWords.keySet()) {
            assertTrue(instance.getWordCount(word, userId) > 0);
        }

    }

    @Test
    public void testGetWordCount() {
        PlayerDataDataBase instance = PlayerDataDataBase.getInstance();
        String userId = "uniquePlayerId123";
        instance.addNewPlayer(userId);
        instance.addSwearWord(userId, "Word1");
        instance.addSwearWord(userId, "Word2");
        instance.addSwearWord(userId, "Word3");
        Map<String, Integer> swearWords = instance.getSwearWordsStats(userId);
        for (String word: swearWords.keySet()) {
            assertTrue(instance.getWordCount(word, userId) > 0);
        }
        instance.addSwearWord(userId, "Word3");
        assertTrue(instance.getWordCount("Word3", userId) > 1);
    }
    @Test
    public void GetInstance() {
        PlayerDataDataBase instance = PlayerDataDataBase.getInstance();
        assertNotNull("This shouldn't have been null", instance);
    }

    @Test
    public void GetSwearCount() {
        PlayerDataDataBase instance = PlayerDataDataBase.getInstance();
        int chestlyCount = instance.getSwearCount("chestly");
        assertEquals(chestlyCount, 0);
    }

    @Test
    public void write() {
        PlayerDataDataBase instance = PlayerDataDataBase.getInstance();
        instance.write();
    }

    @Test
    public void testWriting() {
        WordListDatabase instance = WordListDatabase.getInstance();
        instance.writeToDb();
    }
}



