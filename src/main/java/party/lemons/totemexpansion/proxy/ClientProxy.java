package party.lemons.totemexpansion.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Sam on 6/04/2018.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy
{
	@Override
	public void playActivateAnimation(ItemStack stack, Entity entity)
	{
		Minecraft mc = Minecraft.getMinecraft();
		mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.TOTEM, 30);
		mc.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0F, 1.0F, false);

		if (entity == mc.player)
		{
			mc.entityRenderer.displayItemActivation(stack);
		}
	}
}
