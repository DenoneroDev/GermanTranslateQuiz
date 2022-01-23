package events;

import Hagbrain.Bank;
import chatGuessGame.Main;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.TextFormat;

public class PlayerChat implements Listener {

    @EventHandler
    public void on(PlayerChatEvent event) {
        
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();
        String rightAnswer = Main.rightAnswer;
        
        if (!Main.rightAnswer.equals("")) {
            
            Main.plugin.getLogger().info(rightAnswer);
            
            if (message.contains(rightAnswer)) {
                
                chatGuessGame.Main.plugin.getServer().broadcastMessage(TextFormat.BOLD + "" + TextFormat.GOLD + rightAnswer.toUpperCase() + TextFormat.RESET + "" + TextFormat.GOLD + " wurde von " + TextFormat.BOLD + player.getName() + TextFormat.RESET + " " + TextFormat.GOLD + "richtig erraten, er hat 500.0T erhalten!");
                
                Main.rightAnswer = "";
                
                Bank.bearbeiteKontostand(player.getUniqueId(), 500.0); //economy API
                
                event.setCancelled();
            }
            
        }
        
    }
}
