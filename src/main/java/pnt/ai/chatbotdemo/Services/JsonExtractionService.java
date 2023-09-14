package pnt.ai.chatbotdemo.Services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class JsonExtractionService {

    // This class is used to convert JSON data in particular JSON Strings
    // into HashMaps or an ArrayList of HashMaps

    Gson gson;

    public JsonExtractionService() {
        this.gson = new Gson();
    }

    public HashMap<String, String> extractJsonObject(String jsonString, String key) {
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class).getAsJsonObject(key);
        HashMap<String, String> result = new HashMap<>();
        for (HashMap.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getAsString());
        }
        return result;
    }

    public ArrayList<HashMap<String, String>> extractJsonArray(String jsonString, String key) {
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray(key);
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            HashMap<String, String> hashMap = new HashMap<>();
            for (HashMap.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                hashMap.put(entry.getKey(), entry.getValue().getAsString());
            }
            result.add(hashMap);
        }
        return result;
    }
}

