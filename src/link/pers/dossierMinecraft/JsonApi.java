package link.pers.dossierMinecraft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonApi {
  public Integer getUsersAvailableBlocks(String jiraUser) {
    System.out.println("jiraUser: " + jiraUser);
    String json = "";
    JsonElement userScoreElement = null;
    try {
      json = getJson("http://do.pers.link/stats");
      JsonElement root = new JsonParser().parse(json);
      JsonArray userArray = root.getAsJsonObject().get("Users").getAsJsonArray();

      for (JsonElement userElement : userArray) {
        JsonObject userObj = userElement.getAsJsonObject();

        if (userObj.get("Username").getAsString().equals(jiraUser)) {
          userScoreElement = userObj.get("Tasks");
        }

        if (userScoreElement != null) {
          break;
        }
      }
    }
    catch (Exception e) {
      System.err.println("Could not access 'do.pers.link/stats'");
      e.printStackTrace();
    }
    if (userScoreElement != null) {
      return userScoreElement.getAsInt();
    }
    else {
      return null;
    }
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

  public static void placeBlock(String username, String material, int x, int y, int z) {
    sendPostRequest(
        "http://do.pers.link/blocks",
        "{ \"username\": \"" + username +
            "\", \"material\": " + material +
            "\", \"x\": " + x +
            "\", \"y\": " + y +
            "\", \"z\": " + z + "}");
  }

  public static void updateScore(String jiraName, int availableBlocks) throws Exception {
    sendPostRequest("http://do.pers.link/stats", "{ \"username\": \"" + jiraName + "\", \"availableBlocks\": " + availableBlocks + "}");
  }

  private static void sendPostRequest(String requestUrl, String payload) {
    try {
      URL url = new URL(requestUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
      writer.write(payload);
      writer.close();
      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder jsonString = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        jsonString.append(line);
      }
      br.close();
      connection.disconnect();
      jsonString.toString();
    }
    catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
