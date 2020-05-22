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
	public void onDefault(CommandSender sender, @Optional String kitID, @Optional String pName) {
		if (kitID == null) {
			sender.sendMessage(ChatColor.GREEN + "Kits: ");
			ck.getConfig().getConfigurationSection("kits").getValues(false).forEach((index, kit) -> {
				sender.sendMessage(ChatColor.GREEN + " - " + ChatColor.GOLD + ((Kit) kit).id);
			});
			return;
		}

		if (sender instanceof Player && pName == null)
			pName = sender.getName();
		kitID = kitID.toUpperCase();
		Player p = null;
		if (pName == null) {
			sender.sendMessage(ChatColor.RED + "Execute as player or provide player name.");
			return;
		} else
			p = ck.getServer().getPlayer(pName);

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
