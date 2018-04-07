package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by Sam on 6/04/2018.
 */
public class ItemTotemExplode extends ItemTotemBase
{
	public ItemTotemExplode()
	{
		super("totem_explode", TotemType.EXPLODE);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);
		return true;
	}
}
