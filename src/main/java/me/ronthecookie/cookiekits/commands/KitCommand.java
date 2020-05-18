package me.ronthecookie.cookiekits.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import me.ronthecookie.cookiekits.CookieKits;
import me.ronthecookie.cookiekits.Kit;

@CommandAlias("kit|ckit|kits")
public class KitCommand extends BaseCommand implements Listener {
	private CookieKits ck = CookieKits.getInstance();

	@Default
	@Subcommand("give")
	@CatchUnknown
	public void onDefault(CommandSender sender, @Optional String kitID, @Optional Player p) {
		if (kitID == null) {
			sender.sendMessage(ChatColor.GREEN + "Kits: ");
			ck.getConfig().getConfigurationSection("kits").getValues(false).forEach((index, kit) -> {
				sender.sendMessage(ChatColor.GREEN + " - " + ChatColor.GOLD + ((Kit) kit).id);
			});
			return;
		}

		kitID = kitID.toUpperCase();

		if (p == null && !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You have to either pass in a player name or execute this as a player.");
			return;
		} else if (p == null)
			p = (Player) sender;

		if (!ck.getConfig().contains("kits." + kitID)) {
			sender.sendMessage(ChatColor.RED + "Kit not found.");
			return;
		}

		if (!p.hasPermission("cookiekits.kit." + kitID)) {
			sender.sendMessage(ChatColor.RED + "No permission for that kit.");
			return;
		}

		Kit kit = (Kit) ck.getConfig().get("kits." + kitID);

		for (ItemStack item : kit.items) {
			if (item == null)
				continue;
			p.getInventory().addItem(item);
		}
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have recieved the kit &6" + kitID + "&a."));
	}
}
