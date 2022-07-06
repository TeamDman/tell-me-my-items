package ca.teamdman.tellmemyitems;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.system.CallbackI;
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

	public TellMeMyItems() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get()
				.getModEventBus()
				.addListener(this::setup);

		// Register ourselves for server and other game events we are interested in
		//		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		// some preinit code
		//		LOGGER.info("HELLO FROM PREINIT");
		//		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onRegisterCommand(final RegisterClientCommandsEvent event) {
			event.getDispatcher()
					.register(Commands.literal("tmmiexport")
									  .executes(context -> {
										  System.out.println("Writing item data...");
										  dumpItems();
										  return SINGLE_SUCCESS;
									  }));
		}
	}

	public static void dumpItems() {
		var searchableItems = NonNullList.<ItemStack>create();
		for (Item item : Registry.ITEM) {
			if (item == null) continue;
			item.fillItemCategory(CreativeModeTab.TAB_SEARCH, searchableItems);
//			for (CreativeModeTab tab : CreativeModeTab.TABS) {
//				item.fillItemCategory(tab, searchableItems);
//			}
		}
		var folder = Paths.get(FMLPaths.GAMEDIR.get()
									   .toString(), "tmmi");
		try {
			Files.createDirectories(folder);
			File itemFile = new File(folder.toFile(), "items.json");
			Gson gson     = new Gson();
			var  content  = gson.toJson(searchableItems.stream()
												.map(JsonItem::new)
												.toList());
			try (FileOutputStream str = new FileOutputStream(itemFile)) {
				str.write(content.getBytes(StandardCharsets.UTF_8));
			}


		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
