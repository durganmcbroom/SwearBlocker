package pluginsminecraft.antiswear;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerSwearData implements Serializable {
    private String uniqueID;
    private  int swearAmount;
    private Map<String, Integer> swearWordsSaid = new HashMap<>();

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getSwearAmount() {
        return swearAmount;
    }

    public void setSwearAmount(int swearAmount) {
        this.swearAmount = swearAmount;
    }

//    public boolean ContainsSwearword (String word) {
//        Integer HasSwearWord = swearWordsSaid.get(word);
//        if (HasSwearWord != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    public Map<String, Integer> getSwearWordsSaid() {
        return swearWordsSaid;
    }

    public void addSwearWord(String word) {
        Integer count = swearWordsSaid.get(word);
        if (count == null) {
            count = 0;
        }
        count = count + 1;
        swearWordsSaid.put(word, count);

    }
//    public void setSwearWordsSayed(Map<String, Integer> swearWordsSaid) {
//        this.swearWordsSaid = swearWordsSaid;
//    }
}
