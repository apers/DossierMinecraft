package link.pers.dossierMinecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfigSingleton {
  private static ConfigSingleton instance = null;
  private Map<String, String> usernameToJiraUser = new HashMap<>();
  private int currentAvailableBlocks = 0;

  protected ConfigSingleton() {
  }

  public static ConfigSingleton getInstance() {
    if(instance == null) {
      instance = new ConfigSingleton();
      instance.readConfig();
    }
    return instance;
  }

  public Map<String, String> getUserNameMap() {
    return getInstance().usernameToJiraUser;
  }

  private void readConfig() {
    File file = new File("usernameToJiraUser.conf");

    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("=");
        usernameToJiraUser.put(split[0], split[1]);
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
      System.err.println("Could not find config file usernameToJiraUser.conf");
    }
  }

  public int getCurrentAvailableBlocks() {
    return currentAvailableBlocks;
  }

  public void setCurrentAvailableBlocks(int currentAvailableBlocks) {
    this.currentAvailableBlocks = currentAvailableBlocks;
  }
}
