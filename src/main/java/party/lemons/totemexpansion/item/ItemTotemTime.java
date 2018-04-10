package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import party.lemons.totemexpansion.handler.ticker.TickerHandler;
import party.lemons.totemexpansion.handler.ticker.TickerTime;

/**
 * Created by Sam on 10/04/2018.
 */
public class ItemTotemTime extends ItemTotemBase
{
	public ItemTotemTime()
	{
		super("totem_time", TotemType.ACTIVATE);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);

		if(!living.world.isRemote)
			TickerHandler.addTicker(new TickerTime(10000, living.world));

		living.swingArm(EnumHand.MAIN_HAND);
		return true;
	}
}
