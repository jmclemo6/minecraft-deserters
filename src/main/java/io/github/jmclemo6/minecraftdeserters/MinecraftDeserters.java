package io.github.jmclemo6.minecraftdeserters;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftDeserters extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("deserters").setExecutor(this::onCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || args.length != 1) return false;
        long distance;
        try {
            distance = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        long distanceSquared = distance * distance;
        Location senderLocation = ((Player) sender).getLocation();
        sender.sendMessage(ChatColor.UNDERLINE + "Possible Deserters:");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == sender) continue;

            Location otherPlayersLocation = player.getLocation();
            double dx = otherPlayersLocation.getX() - senderLocation.getX();
            double dy = otherPlayersLocation.getY() - senderLocation.getY();
            double dz = otherPlayersLocation.getZ() - senderLocation.getZ();

            double dxSquared = dx * dx;
            double dySquared = dy * dy;
            double dzSquared = dz * dz;

            if (dxSquared + dySquared + dzSquared > distanceSquared) {
                String message = String.format(
                        "%s. Current location is (%s, %s, %s).",
                        player.getDisplayName(),
                        otherPlayersLocation.getX(),
                        otherPlayersLocation.getY(),
                        otherPlayersLocation.getZ()
                );
                sender.sendMessage(message);
            }
        }

        return true;
    }
}
