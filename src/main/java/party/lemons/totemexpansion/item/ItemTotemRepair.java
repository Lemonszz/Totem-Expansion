package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * Created by Sam on 6/04/2018.
 */
public class ItemTotemRepair extends ItemTotemBase
{
	public ItemTotemRepair()
	{
		super("totem_repair", TotemType.TOOL_BREAK);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);

		return true;
	}
}
