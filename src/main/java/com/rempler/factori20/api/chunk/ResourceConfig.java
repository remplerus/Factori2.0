package com.rempler.factori20.api.chunk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceConfig {
    private static final Map<String, Map<String, Integer>> resourceTypes = new ConcurrentHashMap<>();
    private final File configFile;

    public ResourceConfig(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configFile = new File(path.toFile(), "resource_config.json");
        if (!configFile.exists()) {
            createDefaultConfig();
        } else {
            loadConfig();
        }
    }

    private void createDefaultConfig() {
        try {
            if (configFile.createNewFile())
                saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            JsonObject json = JsonParser.parseReader(new FileReader(configFile)).getAsJsonObject();

            if (json.has("resources") && json.get("resources").isJsonArray()) {
                JsonArray resources = json.getAsJsonArray("resources");
                for (JsonElement resource : resources) {
                    if (resource.isJsonObject()) {
                        JsonObject resourceObject = resource.getAsJsonObject();
                        if (resourceObject.has("item") && resourceObject.has("amount") && resourceObject.has("rarity")) {
                            String item = resourceObject.get("item").getAsString();
                            int amount = resourceObject.get("amount").getAsInt();
                            int rarity = resourceObject.get("rarity").getAsInt();

                            // Überprüfen, ob die Ressource bereits in der Map ist
                            if (!resourceTypes.containsKey(item)) {
                                resourceTypes.put(item, new HashMap<>());
                            }

                            // Hinzufügen der Menge (Amount) und Seltenheit (Rarity) zur Ressource
                            resourceTypes.get(item).put("amount", amount);
                            resourceTypes.get(item).put("rarity", rarity);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        Map<String, Map<String, Integer>> map = getMap();

        if (resourceTypes.isEmpty()) {
            resourceTypes.putAll(map);
        }

        for (String resourceName : resourceTypes.keySet()) { // Iterieren Sie über die Ressourcen-Namen (String)
            JsonObject resourceObject = new JsonObject();
            resourceObject.addProperty("item", resourceName); // Verwenden Sie den Ressourcen-Namen (String)
            resourceObject.addProperty("amount", resourceTypes.get(resourceName).get("amount"));
            resourceObject.addProperty("rarity", resourceTypes.get(resourceName).get("rarity"));
            jsonArray.add(resourceObject);
        }

        jsonObject.add("resources", jsonArray);

        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private static Map<String, Map<String, Integer>> getMap() {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("amount", 10000);
        map2.put("rarity", 0);
        map.put("minecraft:coal_ore", map2);
        map.put("minecraft:iron_ore", map2);
        map.put("minecraft:gold_ore", map2);
        map.put("minecraft:redstone_ore", map2);
        map.put("minecraft:lapis_ore", map2);
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("amount", 250);
        map3.put("rarity", 1);
        map.put("minecraft:diamond_ore", map3);
        map.put("minecraft:emerald_ore", map3);
        Map<String, Integer> map4 = new HashMap<>();
        map4.put("amount", 10000);
        map4.put("rarity", 100);
        map.put("minecraft:nether_quartz_ore", map4);
        map.put("minecraft:glowstone", map4);
        Map<String, Integer> map5 = new HashMap<>();
        map5.put("amount", 50);
        map5.put("rarity", 2);
        map.put("minecraft:ancient_debris", map5);
        return map;
    }

    public static Map<String, Map<String, Integer>> getResourceTypes() { // Ändern Sie den Rückgabetyp auf String
        return resourceTypes;
    }
}