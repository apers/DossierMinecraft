package link.pers.dossierMinecraft;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

  private static Main instance = null;

  public static Main getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    this.getCommand("blocks").setExecutor(new Commands());
    getServer().getPluginManager().registerEvents(new EventListener(), this);

    System.out.println("\n\n=======================\n\n");
    System.out.println(ConfigSingleton.getInstance().getUserNameMap());
    System.out.println("\n\n=======================\n\n");
  }

  @Override
  public void onDisable() {

  }



}
