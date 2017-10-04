package link.pers.dossierMinecraft;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


  @Override
  public void onEnable() {
    System.out.println("\n\n=======================\n\n");
    getServer().getPluginManager().registerEvents(new EventListener(), this);
    System.out.println(ConfigSingleton.getInstance().getUserNameMap());
    System.out.println("\n\n=======================\n\n");
  }

  @Override
  public void onDisable() {

  }



}
