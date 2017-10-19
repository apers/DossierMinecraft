package link.pers.dossierMinecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    if (commandSender instanceof Player) {
      Player player = (Player) commandSender;

      if(s.equals("blocks")) {
        Integer availableBlocks = (Integer) player.getMetadata("availableBlocks").get(0).value();
        player.sendMessage("Available blocks: " + availableBlocks);
      }
    }

    return true;
  }
}
