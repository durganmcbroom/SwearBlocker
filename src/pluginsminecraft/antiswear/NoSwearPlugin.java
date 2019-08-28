package pluginsminecraft.antiswear;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class NoSwearPlugin extends JavaPlugin {


    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new SwearChatEventListener(), this);
    }


    public void onDisable() {
        WordListDatabase instance = WordListDatabase.getInstance();
        instance.writeToDb();
        PlayerDataDataBase instance2 = PlayerDataDataBase.getInstance();
        instance2.write();

    }

    WordListDatabase instanceWordList = WordListDatabase.getInstance();
    PlayerDataDataBase instancePlayerData = PlayerDataDataBase.getInstance();
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        if (command.getName().equalsIgnoreCase("SwearBlocker")) {


            if (args.length >= 2) {
                String commandStr = args[0];
                String commandArg = args[1].toLowerCase().trim();

                if (commandStr != null && !commandStr.isEmpty()) {


                    if (commandStr.equalsIgnoreCase("AddWord")) {
                        List wordList = new ArrayList();


                        if (args.length > 2) {
                            for (int count = 1; count < args.length; count++) {

                                instanceWordList.add(args[count]);
                                wordList.add(args[count]);
                            }
                            sender.sendMessage("§3SwearBlocker: §rThank you for adding '" + StringUtils.join(wordList, ", ") + "' to the list");
                        } else {
                            sender.sendMessage("§3SwearBlocker: §rThank you for adding '" + args[1] + "' to the list");
                            instanceWordList.add(args[1]);
                        }
                    } else if (commandStr.equalsIgnoreCase("clear")) {
                        if (commandArg.equalsIgnoreCase("playerdata")) {
                            instancePlayerData.clear();
                            sender.sendMessage("§3SwearBlocker:§r PlayerData DataBase has been Cleared");
                        } else if (commandArg.equalsIgnoreCase("wordlist")) {
                            instanceWordList.clear();
                            sender.sendMessage("§3SwearBlocker:§r WordList DataBase has been Cleared");
                        }


                    } else if (commandStr.equalsIgnoreCase("DelWord")) {
                        if (commandArg.equalsIgnoreCase("*all")){
                            instanceWordList.clear();
                            sender.sendMessage("§3SwearBlocker: §rall words have been cleared from the list");
                        } else {
                            if (instanceWordList.contains(commandArg)) {
                                instanceWordList.remove(commandArg);
                                sender.sendMessage("§3SwearBlocker: §rWord '" + commandArg + "' Has been removed from the list");
                            } else {
                                sender.sendMessage("§3SwearBlocker: §4'" + commandArg + "' Is not currently in the list.");
                            }
                        }
                    } else if (commandStr.equalsIgnoreCase("PlayerSwearAmount")) {
                        PlayerDataDataBase playerDataDataBase = PlayerDataDataBase.getInstance();
                        if (Bukkit.getServer().getPlayer(commandArg) != null) {

                            sender.sendMessage("§3SwearBlocker:§r player '" + commandArg + "' has sweared " + playerDataDataBase.getSwearCount(Bukkit.getServer().getPlayer(commandArg).getUniqueId().toString()) + " times");

                            String userId = Bukkit.getServer().getPlayer(commandArg).getUniqueId().toString();
                            Map<String, Integer> swearWords = playerDataDataBase.getSwearWordsStats(userId);
                            sender.sendMessage("§3SwearBlocker:§r player '" + commandArg + "' has Said These Banned Words");
                            for (String word : swearWords.keySet()) {
                                sender.sendMessage("§3SwearBlocker:§r " + word + " " + playerDataDataBase.getWordCount(word, userId) + " Times");
                            }
                            //               args[1] is the player, then check if the player is online, and then gt number from list
                        } else if (Bukkit.getServer().getOfflinePlayer(commandArg) != null) {
                            OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(commandArg);
                            sender.sendMessage("§3SwearBlocker:§r player '" + commandArg + "' has sweared " + playerDataDataBase.getSwearCount(player.getUniqueId().toString()) + " times");

                            String userId = player.getUniqueId().toString();
                            Map<String, Integer> swearWords = playerDataDataBase.getSwearWordsStats(userId);
                            sender.sendMessage("§3SwearBlocker:§r player '" + commandArg + "' has Said These Banned Words");
                            for (String word : swearWords.keySet()) {
                                sender.sendMessage("§3SwearBlocker:§r " + word + " " + playerDataDataBase.getWordCount(word, userId) + " Times");
                            }
                        } else {
                            sender.sendMessage("§3SwearBlocker: §4This is not a valid player. please make sure the username is typed correctly");
                        }


                    } else {
                        sender.sendMessage("§3SwearBlocker: §4You must provide a command.  Please see /swearblocker help");
                    }
                }

            } else if (args.length == 1) {
                String commandStr = args[0];
                if (commandStr.equalsIgnoreCase("help")) {
                    sender.sendMessage("                  ---- §3SwearBlocker Help§r ----                      ");
                    sender.sendMessage("§3/swearblocker §7help§r - Displays This List");
                    sender.sendMessage("§3/swearblocker §7addword §3<Param>§r - Adds Specified word to DataBase");
                    sender.sendMessage("§3/swearblocker §7delword §3<Param/*all>§r - deletes Specified word from the DataBase or deletes all words from databse");
                    sender.sendMessage("§3/swearblocker §7PlayerSwearAmount §3<Param>§r - Shows times player has sweared");
                    sender.sendMessage("§3/swearblocker §7list§r - lists all banned words in the data base");
                    sender.sendMessage("§3/swearblocker §7clear§r §3<playerdata, wordlist>§r - Deletes all playerData");
                    sender.sendMessage("§3SwearBlocker: §6Made By Chestly");
                } else if (commandStr.equalsIgnoreCase("list")) {
                    sender.sendMessage("§3SwearBlocker: §rCurrent Words In data Base: " + StringUtils.join(instanceWordList.getAll(), ", "));
                } else {
                    sender.sendMessage("§3SwearBlocker: §4Please Provide an action and Params. Do '/SwearBlocker help' for help");
                }

            } else {
                sender.sendMessage("§3SwearBlocker: §4Please Provide an action and Params. Do '/SwearBlocker help' for help");
            }
            return false;
        }
        return true;

    }
}

