package me.goodestenglish.bowbooster.util;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFile extends YamlConfiguration {

    @Getter
    private final File file;

    public ConfigFile(JavaPlugin plugin, String name) {
        this.file = new File(plugin.getDataFolder(), name);

        if (!this.file.exists()) {
            plugin.saveResource(name, false);
        }

        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public ConfigFile(JavaPlugin plugin, String name, boolean ignored) {
        this.file = new File(plugin.getDataFolder(), name);

        if (!this.file.exists()) {
            plugin.saveResource(name, false);
        }

        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getInt(String path) {
        if (!contains(path)) throw new NullPointerException("Path at '" + path + "' not found");
        return super.getInt(path, 0);
    }

    @Override
    public double getDouble(String path) {
        if (!contains(path)) throw new NullPointerException("Path at '" + path + "' not found");
        return super.getDouble(path, 0.0);
    }

    @Override
    public boolean getBoolean(String path) {
        if (!contains(path)) throw new NullPointerException("Path at '" + path + "' not found");
        return super.getBoolean(path, false);
    }

    public String getUnColoredString(String path) {
        if (!contains(path)) throw new NullPointerException("Path at '" + path + "' not found");
        return super.getString(path, null);
    }

    @Override
    public String getString(String path) {
        if (!contains(path)) throw new NullPointerException("Path at '" + path + "' not found");
        return ChatColor.translateAlternateColorCodes('&', super.getString(path, "&bString at path &7'&3" + path + "&7' &bnot found.").replace("{0}", "\n"));
    }

    @Override
    public List<String> getStringList(String path) {
        if (!contains(path)) throw new NullPointerException("Path at '" + path + "' not found");
        return super.getStringList(path).stream().map(str -> ChatColor.translateAlternateColorCodes('&', str)).collect(Collectors.toList());
    }

}