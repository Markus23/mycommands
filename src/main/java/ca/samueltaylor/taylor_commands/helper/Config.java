package ca.samueltaylor.taylor_commands.helper;


import ca.samueltaylor.taylor_commands.TaylorCommands;
import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.Map;

/**
 * A Config helper class for creating and reading from json configs
 */
public class Config {

    private String name;
    private File configFile;
    private JsonObject config;
    private Gson gson;

    /**
     * Constructor for creating a config using properties in a map
     * @param name - The name of the config file
     * @param properties - The properties to append to the config
     */
    public Config(String name, Map<String, Object> properties) {
        this(name);
        if(configFile.exists()) {
            this.loadConfig();
            return;
        }else {
            write(properties);

               
        }
    }
    
    /**
     * Constructor for defining a new config using a name
     * Append properties using the add methods from this class
     * Save config once properties are added using saveConfig
     * @param name - The name of the config file
     */
    public Config(String name) {
        this.name = name;
        this.configFile = new File(FabricLoader.getInstance().getConfigDirectory() + "/" + name + ".json");
        this.config = new JsonObject();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    public void write( Map<String, Object> properties) {
    	{
            try(FileWriter writer = new FileWriter(configFile)) {
                for (Map.Entry<String, Object> property : properties.entrySet()) {
                    if (property.getValue() instanceof JsonElement) {
                        config.add(property.getKey(), (JsonElement) property.getValue());
                    }else if (property.getValue() instanceof Integer) {
                        config.addProperty(property.getKey(), (int) property.getValue());
                    }else if (property.getValue() instanceof Boolean) {
                        config.addProperty(property.getKey(), (boolean) property.getValue());
                    }else if (property.getValue() instanceof String) {
                        config.addProperty(property.getKey(), (String) property.getValue());
                    }else {
                        TaylorCommands.LOGGER.error("Unable to parse json config property! File: " + name + ".json with Property: " + property.getKey());
                        continue;
                    }
                }

                this.saveConfig(writer);
            } catch (IOException e) {
                TaylorCommands.LOGGER.fatal("You done borked something with your config", e);
            }
        }
    }
    /**
     * Add an integer to the config file
     * @param key - The name of the key
     * @param value - The {@link Integer} value
     */
    public void addInt(String key, int value) {
        this.config.addProperty(key, value);
    }

    /**
     * Add a boolean to the config file
     * @param key - The name of the key
     * @param value - The {@link Boolean} value
     */
    public void addBoolean(String key, boolean value) {
        this.config.addProperty(key, value);
    }

    /**
     * Add a string to the config file
     * @param key - The name of the key
     * @param value - The {@link String} value
     */
    public void addString(String key, String value) {
        this.config.addProperty(key, value);
    }

    /**
     * Add a JsonElement to the config file
     * @param key - The name of the key
     * @param value - The {@link JsonElement} value
     */
    public void addJsonElement(String key, JsonElement value) {
        this.config.add(key, value);
    }

    /**
     * Get an {@link Integer} from the config
     * @param key - The name of the entry
     * @return - The return value
     */
    public int getInt(String key) {
        return this.config.has(key) ? this.config.get(key).getAsInt() : 0;
    }

    /**
     * Get a {@link Boolean} from the config
     * @param key - The name of the entry
     * @return - The return value
     */
    public boolean getBoolean(String key) {
        return this.config.has(key) ? this.config.get(key).getAsBoolean() : false;
    }

    /**
     * Get a {@link String} from the config
     * @param key - The name of the entry
     * @return - The return value
     */
    public String getString(String key) {
        
    	return this.config.has(key) ? this.config.get(key).getAsString() : "";
        
    }

    /**
     * Get a {@link JsonElement} from the config
     * @param key - The name of the entry
     * @return - The return value
     */
    public JsonElement getJsonElement(String key) {
        return this.config.has(key) ? this.config.get(key) : null;
    }

    /**
     * Get the {@link JsonObject} config
     * @return - The set {@link JsonObject}
     */
    public JsonObject getConfig() {
        return config;
    }

    /**
     * Get the {@link File} for the config
     * @return - The config {@link File}
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * Load an existing config file
     */
    private void loadConfig() {
        if(!configFile.exists()) {
            TaylorCommands.LOGGER.error("Unable to load config file " + name + ".json, File not found!");
            return;
        }

        try {
            TaylorCommands.LOGGER.info("Loading config file " + name + ".json");
            JsonParser parser = new JsonParser();
            config = parser.parse(new FileReader(configFile)).getAsJsonObject();
        } catch (FileNotFoundException e) {
            TaylorCommands.LOGGER.fatal("You done borked something with your config", e);
        }
    }

    /**
     * Save a config file
     * Called in the constructor after properties are added
     * @param writer - The writer to to write with
     */
    private void saveConfig(FileWriter writer) {
        TaylorCommands.LOGGER.info("Creating config file " + name + ".json");
        gson.toJson(config, writer);
    }

    /**
     * Save a config file if it doesnt exist, otherwise attempt to load it
     * Called after adding all properties to the config manually
     */
    public void saveConfig() {
        if(!configFile.exists()) {
            try {
                TaylorCommands.LOGGER.info("Creating config file " + name + ".json");
                gson.toJson(config, new FileWriter(configFile));
            } catch (IOException e) {
                TaylorCommands.LOGGER.fatal("You done borked something with your config", e);
            }
        }
    }
}
