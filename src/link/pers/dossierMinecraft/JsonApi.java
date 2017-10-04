package link.pers.dossierMinecraft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonApi {
  public int getUsersAvailableBlocks(String jiraUser) {
    String json = "";
    JsonElement userScoreElement = null;
    try {
      json = getJson("http://do.pers.link/stats");
      JsonElement root = new JsonParser().parse(json);
      JsonArray userArray = root.getAsJsonObject().get("Users").getAsJsonArray();

      for(JsonElement userElement : userArray) {
        JsonObject userObj = userElement.getAsJsonObject();
        userScoreElement = userObj.get(jiraUser);
        if (userScoreElement != null) {
          break;
        }
      }
    }
    catch (Exception e) {
      System.err.println("Could not access 'do.pers.link/stats'");
      e.printStackTrace();
    }
    return userScoreElement.getAsInt();
  }

  private static String getJson(String urlToRead) throws Exception {
    StringBuilder result = new StringBuilder();
    URL url = new URL(urlToRead);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line;
    while ((line = rd.readLine()) != null) {
      result.append(line);
    }
    rd.close();
    return result.toString();
  }
}
