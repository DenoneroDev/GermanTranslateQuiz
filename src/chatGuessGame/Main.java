package chatGuessGame;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.TextFormat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class Main extends PluginBase {
    
    public static HashMap<String, String> words = new HashMap<>();
    public static Main plugin;
    public static String rightAnswer = "";

    @Override
    public void onEnable() {

        plugin = this;
        registerEvents();

        this.getLogger().info(TextFormat.WHITE + "I've been loaded!");

        this.saveResource("words.txt", false);
        Stream<String> stream = null;

        try {
            
            stream = Files.lines(Paths.get(this.getDataFolder() + File.separator + "words.txt"), StandardCharsets.UTF_8);
            
            stream.forEach(line -> {
                String[] word = line.split("-");
                words.put(word[0].toLowerCase(), word[1].toLowerCase());

            });
            
        } catch (IOException e) {
            
            e.printStackTrace();
            
        }


        Object[] keys = words.keySet().toArray();

        Server.getInstance().getScheduler().scheduleRepeatingTask(Main.plugin, new Runnable() {
            
            @Override
            public void run() {
                
                String key = (String)keys[new java.util.Random().nextInt(keys.length)];
                    
                if (Main.plugin.getServer().getOnlinePlayers().size() >= 1) {
                     
                    rightAnswer = chatGuessGame.Main.words.get(key);
                        
                    Main.plugin.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + "[Server]: Wer " + TextFormat.DARK_PURPLE + key.toUpperCase() + TextFormat.LIGHT_PURPLE + " im Deutschen übersetzt, hat 500.0T gewonnen!");
                        
                }
                
            }
        }, 300);
        
    }
    public void registerEvents() {
        
        PluginManager event = Server.getInstance().getPluginManager();
        event.registerEvents(new events.PlayerChat(), (Plugin) plugin);
        
    }
}