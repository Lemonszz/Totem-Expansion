package party.lemons.totemexpansion.item;

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
public class ItemTotemUndying extends ItemTotemBase
{
	public ItemTotemUndying(String name, TotemType type)
	{
		super(name, type);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);


		living.setHealth(1.0F);
		living.clearActivePotions();
		living.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
		living.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
		return true;
	}
}
