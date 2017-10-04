package link.pers.dossierMinecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventListener implements Listener {
  @EventHandler
  public void onTryingToPlaceBlock(BlockCanBuildEvent event) {
    event.setBuildable(false);
  }

  @EventHandler
  public void onTryingToPlaceBlock(BlockBreakEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onTryingToPlaceBlock(BlockDamageEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {
    String playerName = event.getPlayer().getName();

    if (ConfigSingleton.getInstance().getUserNameMap().get(playerName) == null) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, playerName + " is not a registered user");
    }

    JsonApi jsonApi = new JsonApi();
    int usersAvailableBlocks = jsonApi.getUsersAvailableBlocks(playerName);
    System.out.println("Available blocks: " + usersAvailableBlocks);
  }



}
