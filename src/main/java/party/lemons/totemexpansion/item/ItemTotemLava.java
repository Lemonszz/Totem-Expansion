package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

/**
 * Created by Sam on 6/04/2018.
 */
public class ItemTotemLava extends ItemTotemBase
{
	public ItemTotemLava()
	{
		super("totem_lava", TotemType.DAMAGE_LAVA);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		if(living.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) != null)
			return false;

		super.onActivate(living, stack, source);

		living.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 900, 2));
		return true;
	}
}
