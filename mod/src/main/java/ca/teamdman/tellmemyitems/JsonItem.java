package ca.teamdman.tellmemyitems;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class JsonItem {
	public String id;
	public String data;

	public JsonItem(ItemStack stack) {
		this.id = Registry.ITEM.getKey(stack.getItem()).toString();
		var item = stack.getItem();
		if (stack.getShareTag() != null) {
			data = stack.getShareTag().getAsString();
		}
	}
}
