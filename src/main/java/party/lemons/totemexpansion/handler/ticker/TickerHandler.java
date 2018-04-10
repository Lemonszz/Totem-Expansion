package party.lemons.totemexpansion.handler.ticker;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.totemexpansion.config.ModConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 10/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class TickerHandler
{
	private static List<ITicker> tickers = new ArrayList<>();
	private static List<Class<ITicker>> registeredTickers = new ArrayList<>();

	public static void addTicker(ITicker ticker)
	{
		tickers.removeIf(t -> t.getClass() == ticker.getClass());

		tickers.add(ticker);
	}

	private static void removeFinishedTickers(World world)
	{
		tickers.removeIf(t -> t.isTaskFinished(world));
	}

	private static void tick(World world)
	{
		tickers.forEach(t -> t.update(world));
	}

	public static <T extends  ITicker> void registerTicker(Class<T> clazz)
	{
		registeredTickers.add((Class<ITicker>) clazz);
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START || event.world.provider.getDimension() != 0)
			return;

		tick(event.world);
		removeFinishedTickers(event.world);
	}
}
