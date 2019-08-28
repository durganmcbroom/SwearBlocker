package pluginsminecraft.antiswear;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Set;



public class SwearChatEventListener implements Listener {
    public void  onEnable(){

    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        WordListDatabase instance = WordListDatabase.getInstance();
        PlayerDataDataBase instancePlayer = PlayerDataDataBase.getInstance();

        Set<String> wordDictionary = instance.getAll();
        for (String currentword : wordDictionary) {
            String message = event.getMessage();
            if (message.toLowerCase().contains(currentword)) {
                event.getPlayer().sendMessage("§4DO NOT SWEAR");
                message = message.toLowerCase().replace(currentword, "§k" + currentword + "§r");
                event.setMessage(message);
                instancePlayer.addSwearWord(event.getPlayer().getUniqueId().toString(), currentword);
            }
        }

    }
}
