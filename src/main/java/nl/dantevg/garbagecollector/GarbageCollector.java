package nl.dantevg.garbagecollector;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GarbageCollector extends JavaPlugin implements CommandExecutor, TabCompleter {
	@Override
	public void onEnable() {
		getCommand("gc").setExecutor(this);
		getCommand("gc").setTabCompleter(this);
	}

	@Override
	public void onDisable() {
		getLogger().info("GarbageCollector disabled!");
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 0) {
			long before = Runtime.getRuntime().freeMemory();
			System.gc();
			long after = Runtime.getRuntime().freeMemory();
			sender.sendMessage("Freed %s of memory.".formatted(GarbageCollector.formatBytes(after - before)));
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return Collections.emptyList();
	}
	
	public static String formatBytes(long bytes) {
		final String[] prefixes = new String[] { "", "k", "M", "G" };
		int exp = (int) (Math.log10(bytes) / 3);
		return String.format("%.1f %sB", bytes / Math.pow(1000, exp), prefixes[exp]);
	}
}
