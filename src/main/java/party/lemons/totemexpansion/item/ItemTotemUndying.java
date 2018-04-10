package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Sam on 6/04/2018.
 */
public class ItemTotemUndying extends ItemTotemBase
{
	public ItemTotemUndying()
	{
		super("minecraft:totem_of_undying", TotemType.DEATH);
		setUnlocalizedName("totem");
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);


		living.setHealth(1.0F);
		living.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
		living.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
		return true;
	}

	@Override
	public boolean clearPotions()
	{
		return true;
	}

	@Override
	public ResourceLocation getModelLocation()
	{
		return new ResourceLocation("minecraft", "totem");
	}
}
