package ca.teamdman.tellmemyitems;

import com.mojang.logging.LogUtils;
import net.minecraft.commands.Commands;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

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
										  return SINGLE_SUCCESS;
									  }));
		}
	}
}
