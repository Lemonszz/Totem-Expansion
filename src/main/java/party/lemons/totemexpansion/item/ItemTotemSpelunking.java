package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import party.lemons.totemexpansion.potion.ModPotions;

/**
 * Created by Sam on 8/04/2018.
 */
public class ItemTotemSpelunking extends ItemTotemBase
{
	public ItemTotemSpelunking()
	{
		super("totem_spelunking", TotemType.ACTIVATE);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);

		living.clearActivePotions();
		living.swingArm(EnumHand.MAIN_HAND);
		living.addPotionEffect(new PotionEffect(ModPotions.SPELUNKING, 900, 0));
		return true;
	}
}
