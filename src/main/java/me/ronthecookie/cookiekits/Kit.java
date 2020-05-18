package me.ronthecookie.cookiekits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Kit implements ConfigurationSerializable {
	public Kit() {
	}

	public Kit(Map<String, Object> map) {
		this.id = (String) map.get("id");
		this.items = new ArrayList<>();
		int itemsSize = (int) map.get("items.size");
		for (int i = 0; i < itemsSize; i++) {
			this.items.add((ItemStack) map.get("items." + i));
		}
	}

	public String id;
	public ArrayList<ItemStack> items = new ArrayList<>();

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();

		map.put("id", this.id);
		map.put("items.size", this.items.size());
		int i = 0;
		for (ItemStack item : this.items) {
			map.put("items." + i, item);
			i++;
		}

		return map;
	}

}