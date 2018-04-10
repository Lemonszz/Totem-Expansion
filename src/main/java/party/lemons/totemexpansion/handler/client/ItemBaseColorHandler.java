package party.lemons.totemexpansion.handler.client;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import party.lemons.totemexpansion.misc.IColor;

/**
 * Created by Sam on 10/04/2018.
 */
public class ItemBaseColorHandler implements IItemColor
{
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex)
	{
		if(stack.getItem() instanceof IColor && tintIndex == 0)
		{
			return ((IColor)stack.getItem()).getColor();
		}
		return 0xFFFFF;
	}
}
