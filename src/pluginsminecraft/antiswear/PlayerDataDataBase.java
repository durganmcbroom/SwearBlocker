package pluginsminecraft.antiswear;

        import java.io.*;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Set;

public class PlayerDataDataBase {

    public static final String BASE_DIR = new File("").getAbsoluteFile() + File.separator + "SwearBlocker" + File.separator + "plugins" + File.separator;
    public static final String PLAYER_DATA_FILE_LOCATION = BASE_DIR + "playerData.dat";
    private Map<String, PlayerSwearData> playerSwearMap = new HashMap();

    private static PlayerDataDataBase instance ;
    
    public synchronized static PlayerDataDataBase getInstance() {
        if (instance == null) {
            instance = new PlayerDataDataBase();
            instance.read();
        }
        return instance;
    }

    public void clear() {
        playerSwearMap.clear();
    }

    public void addNewPlayer(String uniqueID) {
        playerSwearMap.put(uniqueID, new PlayerSwearData());
    }

    public int getSwearCount(String uniqueID) {
        PlayerSwearData swearData = playerSwearMap.get(uniqueID);
            if (swearData != null) {
                return swearData.getSwearAmount();
            } else {
                addNewPlayer(uniqueID);
                PlayerSwearData data = new PlayerSwearData();
                playerSwearMap.put(uniqueID, data);
                return 0;
            }
    }

    public Integer getWordCount (String word, String uniqueID) {
        return playerSwearMap.get(uniqueID).getSwearWordsSaid().get(word);
    }

    public Map<String, Integer> getSwearWordsStats(String uniqueID) {
        return playerSwearMap.get(uniqueID).getSwearWordsSaid();

    }

    public void addSwearWord(String uniqueID, String word) {
        PlayerSwearData playerSwearData;
        if (playerSwearMap.containsKey(uniqueID)) {
            playerSwearData = playerSwearMap.get(uniqueID);
            playerSwearData.setSwearAmount(playerSwearData.getSwearAmount() + 1);
            playerSwearData.addSwearWord(word);
            //if playerSwearData.
        } else {
            addNewPlayer(uniqueID);
            playerSwearData = playerSwearMap.get(uniqueID);
            playerSwearData.setSwearAmount(playerSwearData.getSwearAmount() + 1);
            playerSwearData.addSwearWord(word);
        }
        playerSwearMap.put(uniqueID, playerSwearData);
    }
//    private PlayerSwearData getPlayerData(String uniqueID) {
//        return null;
//    }

    public void write() {
        FileOutputStream fos = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            testForFile(PLAYER_DATA_FILE_LOCATION);
            fos = new FileOutputStream(PLAYER_DATA_FILE_LOCATION);
            objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(playerSwearMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void testForFile(String playerDataFileLocation) {
        File file = new File(playerDataFileLocation);
        file.getParentFile().mkdirs();
    }

    public void read() {
        FileInputStream fos = null;
        ObjectInputStream objectInputStream = null;
        try {
            testForFile(PLAYER_DATA_FILE_LOCATION);
            playerSwearMap = new HashMap<>();
            fos = new FileInputStream(PLAYER_DATA_FILE_LOCATION);
            objectInputStream = new ObjectInputStream(fos);
            Object o = objectInputStream.readObject();
            playerSwearMap = (Map<String, PlayerSwearData>) o;
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }


    }
}
