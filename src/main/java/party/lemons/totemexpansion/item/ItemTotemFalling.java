package party.lemons.totemexpansion.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import party.lemons.totemexpansion.TotemExpansion;
import party.lemons.totemexpansion.network.MessageItemEffect;

/**
 * Created by Sam on 6/04/2018.
 */
public class ItemTotemFalling extends ItemTotemBase
{
	public ItemTotemFalling()
	{
		super("totem_falling", TotemType.FALL_DEATH);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		if(living.getActivePotionEffect(MobEffects.LEVITATION) != null)
			return false;

		super.onActivate(living, stack, source);

		if(source != null)
			living.setHealth(20F);

		living.fallDistance = 0;
		living.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 900, 2));
		return true;
	}
}
