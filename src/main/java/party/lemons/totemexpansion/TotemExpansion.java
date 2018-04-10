package party.lemons.totemexpansion;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import party.lemons.totemexpansion.command.ModCommands;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.misc.TotemUtil;
import party.lemons.totemexpansion.network.Messages;
import party.lemons.totemexpansion.proxy.IProxy;

/**
 * Created by Sam on 6/04/2018.
 */
@Mod(modid = ModConstants.MODID, name = ModConstants.NAME, version = ModConstants.VERSION)
public class TotemExpansion
{
	@SidedProxy(clientSide = "party.lemons.totemexpansion.proxy.ClientProxy", serverSide = "party.lemons.totemexpansion.proxy.ServerProxy")
	public static IProxy proxy;

	public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(ModConstants.MODID);

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		Messages.init();
		proxy.initTickers();
	}

	@Mod.EventHandler
	public static void loadComplete(FMLLoadCompleteEvent event)
	{
		TotemUtil.updateOreCache();
	}

	@Mod.EventHandler
	public static void serverStart(FMLServerStartingEvent event)
	{
		ModCommands.init(event);
	}
}
