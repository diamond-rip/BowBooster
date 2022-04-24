package me.goodestenglish.bowbooster;

import lombok.Getter;
import me.goodestenglish.bowbooster.listener.BowBoostListener;
import me.goodestenglish.core.utilities.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BowBooster extends JavaPlugin {

    /*
     * Credit: https://www.spigotmc.org/resources/straightarrows.91935/
     */

    public static BowBooster INSTANCE;

    @Getter private ConfigFile configFile;

    @Override
    public void onEnable() {
        INSTANCE = this;
        configFile = new ConfigFile(this, "config.yml");
        Bukkit.getPluginManager().registerEvents(new BowBoostListener(), this);
    }


}
