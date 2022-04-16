package fr.ostix.questCreator.json;

import com.google.gson.*;
import fr.ostix.questCreator.quest.*;
import fr.ostix.questCreator.quest.serialization.*;

import java.io.*;

public class JsonUtils {

    public static Gson gsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(Rewards.class, new RewardsTypeAdapter());
        return gsonBuilder.create();
    }

    public static String loadJson(String jsonFile) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(jsonFile)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
