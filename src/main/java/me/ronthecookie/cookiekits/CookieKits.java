package me.ronthecookie.cookiekits;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.ronthecookie.cookiekits.commands.CookieKitsCommand;
import me.ronthecookie.cookiekits.commands.KitCommand;

public final class CookieKits extends JavaPlugin implements Listener {

	@Getter
	private static CookieKits instance;

	@Override
	public void onEnable() {
		ConfigurationSerialization.registerClass(Kit.class);
		CookieKits.instance = this;

		// FileConfiguration config = this.getConfig();
		PaperCommandManager manager = new PaperCommandManager(this);
		manager.enableUnstableAPI("help");
		manager.registerCommand(new CookieKitsCommand());
		manager.registerCommand(new KitCommand());
		this.saveDefaultConfig();

	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	public void registerEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

}
