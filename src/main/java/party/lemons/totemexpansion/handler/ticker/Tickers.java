package party.lemons.totemexpansion.handler.ticker;

/**
 * Created by Sam on 10/04/2018.
 */
public class Tickers
{
	public static void init()
	{
		TickerHandler.registerTicker(TickerTime.class);
	}
}
