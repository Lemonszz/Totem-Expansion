package party.lemons.totemexpansion.proxy;

import party.lemons.totemexpansion.handler.ticker.Tickers;

/**
 * Created by Sam on 6/04/2018.
 */
public class ServerProxy implements IProxy
{
	@Override
	public void initTickers()
	{
		Tickers.init();
	}
}
