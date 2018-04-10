package party.lemons.totemexpansion.handler.ticker;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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

	public static <T extends ITicker> void registerTicker(Class<T> clazz)
	{
		registeredTickers.add((Class<ITicker>) clazz);
	}

	public static NBTTagList writeToNBT()
	{
		NBTTagList list = new NBTTagList();
		for(ITicker ticker : tickers)
		{
			NBTTagCompound tickerTags = ticker.writeToNBT();
			tickerTags.setInteger("key", registeredTickers.indexOf(ticker.getClass()));

			list.appendTag(tickerTags);
		}

		return list;
	}

	public static void readFromNBT(NBTTagList tickerList)
	{
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
		for(int i = 0; i < tickerList.tagCount(); i++)
		{
			NBTTagCompound tag = tickerList.getCompoundTagAt(i);
			Class<ITicker> clazz = registeredTickers.get(tag.getInteger("key"));

			try
			{
				ITicker ticker = clazz.getConstructor(World.class).newInstance(world);
				ticker.readFromNBT(tag);
				addTicker(ticker);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START || event.world.provider.getDimension() != 0)
			return;

		tick(event.world);
		removeFinishedTickers(event.world);
	}

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event)
	{
		event.getWorld().getMapStorage().getOrLoadData(TickerSavedData.class, TickerSavedData.DATA_NAME);
	}
}
