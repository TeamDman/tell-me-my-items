package ca.teamdman.tellmemyitems;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class JsonItem {
	public String data;
	public String id;

	public JsonItem(ItemStack stack) {
		this.id = ForgeRegistries.ITEMS.getKey(stack.getItem())
				.toString();
		if (stack.getShareTag() != null) {
			data = stack.getShareTag()
					.getAsString();
		}
	}
}
