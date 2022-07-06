package ca.teamdman.tellmemyitems;

import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

public class JsonItem {
	public String data;
	public String id;

	public JsonItem(ItemStack stack) {
		this.id = Registry.ITEM.getKey(stack.getItem())
				.toString();
		if (stack.getShareTag() != null) {
			data = stack.getShareTag()
					.getAsString();
		}
	}
}
