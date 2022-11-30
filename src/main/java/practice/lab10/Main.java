package practice.lab10;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Main.class.getName());

    private static HttpURLConnection conn;

    public static void main(String[] args) {

        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/pikachu/");
            conn = (HttpURLConnection) url.openConnection();

            // Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
//            log.info("response code: " + status);
            System.out.println("response code: " + status);
//            System.out.println(responseContent);

            //Read JSON response and print
            JSONObject myResponse = new JSONObject(responseContent.toString());
            System.out.println("result after Reading JSON Response");
            System.out.println("Name: " + myResponse.get("name"));
            System.out.println("Height: " + myResponse.get("height"));
            System.out.println("Weight: " + myResponse.get("weight"));

            List<String> abilities = new ArrayList<>();
            JSONArray jsonArray = myResponse.getJSONArray("abilities");
            for (int i = 0; i < jsonArray.length(); i++) {
                String s = jsonArray.get(i).toString();
                String name = new JSONObject(s).getJSONObject("ability").getString("name");
                abilities.add(name);
            }
            System.out.println("Abilities: " + abilities);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

    }

}
