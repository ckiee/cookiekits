package me.ronthecookie.cookiekits.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import me.ronthecookie.cookiekits.CookieKits;
import me.ronthecookie.cookiekits.Kit;

@CommandAlias("cookiekits|ck")
public class CookieKitsCommand extends BaseCommand implements Listener {
	private CookieKits ck = CookieKits.getInstance();

	@HelpCommand
	public static void help(CommandSender sender, CommandHelp help) {
		sender.sendMessage(ChatColor.GREEN + "Running CookieKits v1-1.15.2");
		help.showHelp();
	}

	@Subcommand("create")
	@CommandPermission("cookiekits.admin.create")
	public void create(CommandSender sender, String id) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Kit kit = new Kit();
			kit.id = id;
			for (ItemStack item : p.getInventory().getContents()) {
				if (item == null)
					continue;
				kit.items.add(item.clone());
			}
			ck.getConfig().getConfigurationSection("kits").set(kit.id.toUpperCase(), kit);
			ck.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Saved kit.");
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
		}
	}

	@Subcommand("delete")
	@CommandPermission("cookiekits.admin.delete")
	public void delete(CommandSender sender, String id) {
		id = id.toUpperCase();
		ConfigurationSection sec = ck.getConfig().getConfigurationSection("kits");
		if (sec.contains(id)) {
			sec.set(id, null);
			ck.saveConfig();
			sender.sendMessage(ChatColor.GREEN + "Deleted kit");
		} else {
			sender.sendMessage(ChatColor.RED + "Kit not found.");
		}
	}

}
