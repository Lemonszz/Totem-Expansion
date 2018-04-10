package party.lemons.totemexpansion.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import party.lemons.totemexpansion.handler.ticker.Tickers;

/**
 * Created by Sam on 6/04/2018.
 */
public class ServerProxy implements IProxy
{
	@Override
	public void playActivateAnimation(ItemStack stack, Entity entity)
	{

	}

	@Override
	public void initTickers()
	{
		Tickers.init();
	}
}
