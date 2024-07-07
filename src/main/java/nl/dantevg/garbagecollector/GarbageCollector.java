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
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 0) {
			long before = Runtime.getRuntime().freeMemory();
			System.gc();
			long after = Runtime.getRuntime().freeMemory();
			long used = Runtime.getRuntime().totalMemory() - after;
			sender.sendMessage("Freed %s of memory (%s in use).".formatted(
					formatBytes(after - before),
					formatBytes(used)));
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("query")) {
			long total = Runtime.getRuntime().totalMemory();
			long free = Runtime.getRuntime().freeMemory();
			long max = Runtime.getRuntime().maxMemory();
			sender.sendMessage("%s of %s total in use (%s max).".formatted(formatBytes(total - free),
					formatBytes(total),
					formatBytes(max)));
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return Collections.singletonList("query");
	}
	
	/**
	 * Format a number of bytes into a human-readable string. Will use the
	 * lowest SI-prefix that results in a number greater than or equal to 1.
	 *
	 * @param bytes The number of bytes to format.
	 * @return A human-readable string representing the number of bytes.
	 */
	public static String formatBytes(long bytes) {
		final String[] prefixes = new String[]{"", "k", "M", "G"};
		int exp = (int) (Math.log10(Math.abs(bytes)) / 3);
		return String.format("%.3g %sB", bytes / Math.pow(1000, exp), prefixes[exp]);
	}
}
