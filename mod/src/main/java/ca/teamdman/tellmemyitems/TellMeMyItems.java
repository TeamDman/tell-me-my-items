package ca.teamdman.tellmemyitems;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.Commands;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ca.teamdman.tellmemyitems.TellMeMyItems.MOD_ID;
import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

@Mod(MOD_ID)
public class TellMeMyItems {
	public static final String MOD_ID = "tellmemyitems";

	private static final Logger LOGGER = LogUtils.getLogger();

	public TellMeMyItems() {}

	public static void dumpItems() throws IOException {
		var items   = gatherItems();
		var gameDir = FMLPaths.GAMEDIR.get();
		var folder  = Paths.get(gameDir.toString(), "tmmi");

		// ensure folder exists
		Files.createDirectories(folder);

		// serialize items
		var mappedItems = items.stream()
				.map(JsonItem::new)
				.toList();
		Gson gson    = new Gson();
		var  content = gson.toJson(mappedItems);

		// write to file
		File itemFile = new File(folder.toFile(), "items.json");
		try (FileOutputStream str = new FileOutputStream(itemFile)) {
			str.write(content.getBytes(StandardCharsets.UTF_8));
		}
		LOGGER.info("Exported item data to {}", itemFile);
	}

	public static NonNullList<ItemStack> gatherItems() {
		var items = NonNullList.<ItemStack>create();
		for (Item item : Registry.ITEM) {
			if (item == null) continue;
			item.fillItemCategory(CreativeModeTab.TAB_SEARCH, items);
		}
		return items;
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onRegisterCommand(final RegisterClientCommandsEvent event) {
			event.getDispatcher()
					.register(Commands.literal("tmmiexport")
									  .executes(context -> {
										  LOGGER.info("Exporting data");
										  try {
											  dumpItems();
										  } catch (IOException e) {
											  LOGGER.error("Error dumping data", e);
										  }
										  return SINGLE_SUCCESS;
									  }));
		}
	}
}
