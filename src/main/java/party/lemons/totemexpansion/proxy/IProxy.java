package party.lemons.totemexpansion.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * Created by Sam on 6/04/2018.
 */
public interface IProxy
{
	void playActivateAnimation(ItemStack stack, Entity entity);
}
