package me.minecraftPulgin001;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPulgin001 extends JavaPlugin {

    @Override
    public void onEnable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Get out blyat");
        Bukkit.getLogger().info("A player was told to get out");
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
