package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

/**
 * Created by Sam on 6/04/2018.
 */
public class ItemTotemBreathing extends ItemTotemBase
{
	public ItemTotemBreathing()
	{
		super("totem_breathing", TotemType.DAMAGE_DROWN);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		if(living.getActivePotionEffect(MobEffects.LEVITATION) != null)
			return false;

		super.onActivate(living, stack, source);

		living.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 900, 2));
		living.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 900, 2));
		return true;
	}
}
