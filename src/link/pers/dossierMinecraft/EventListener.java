package link.pers.dossierMinecraft;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class EventListener implements Listener {

  @EventHandler
  public void onBuildBlock(BlockPlaceEvent event) {
    Integer availableBlocks = (Integer) event.getPlayer().getMetadata("availableBlocks").get(0).value();

    if (availableBlocks <= 0) {
      System.out.println("No more blocks!");
      event.getPlayer().sendMessage("No available blocks! Go test som more!");
      event.setCancelled(true);
    }
    else {
      event.getPlayer().setMetadata("availableBlocks", new FixedMetadataValue(Main.getInstance(), --availableBlocks));

      Block blockPlaced = event.getBlockPlaced();
      blockPlaced.setMetadata("placedBy", new FixedMetadataValue(Main.getInstance(), event.getPlayer().getName()));

      event.getPlayer().sendMessage("Available blocks: " + availableBlocks);
    }
  }

  @EventHandler
  public void onTryingToBreakBlock(BlockBreakEvent event) {
    List<MetadataValue> placedByList = event.getBlock().getMetadata("placedBy");
    if (placedByList.size() != 0) {
      String placedBy = (String) placedByList.get(0).value();

      if(!event.getPlayer().getName().equals(placedBy)) {
        event.setCancelled(true);
        event.getPlayer().sendMessage("Can't destroy. Block placed by: " + placedBy);
      } else {
        Integer availableBlocks = (Integer) event.getPlayer().getMetadata("availableBlocks").get(0).value();
        event.getPlayer().setMetadata("availableBlocks", new FixedMetadataValue(Main.getInstance(), ++availableBlocks));
        event.getPlayer().sendMessage("Available blocks: " + availableBlocks);
      }
    }

  }


  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    Integer availableBlocks = (Integer) player.getMetadata("availableBlocks").get(0).value();
    player.sendMessage("Available blocks: " + availableBlocks);
  }


  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    try {
      String jiraName = ConfigSingleton.getInstance().getUserNameMap().get(event.getPlayer().getName());
      Integer availableBlocks = (Integer) event.getPlayer().getMetadata("availableBlocks").get(0).value();
      JsonApi jsonApi = new JsonApi();
      JsonApi.updateScore(jiraName, availableBlocks);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {
    String playerName = event.getPlayer().getName();

    if (ConfigSingleton.getInstance().getUserNameMap().get(playerName) == null) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, playerName + " is not a registered user");
    }
    else {
      JsonApi jsonApi = new JsonApi();
      Integer usersAvailableBlocks = jsonApi.getUsersAvailableBlocks(
          ConfigSingleton
              .getInstance()
              .getUserNameMap()
              .get(playerName));

      Player player = event.getPlayer();

      if (usersAvailableBlocks == null) {
        player.setMetadata("availableBlocks", new FixedMetadataValue(Main.getInstance(), -1));
      }
      else {
        player.setMetadata("availableBlocks", new FixedMetadataValue(Main.getInstance(), usersAvailableBlocks));
      }

      player.sendMessage("Available blocks: " + usersAvailableBlocks);
    }
  }
}
