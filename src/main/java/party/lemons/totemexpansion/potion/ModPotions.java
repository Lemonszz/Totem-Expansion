package party.lemons.totemexpansion.potion;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.totemexpansion.config.ModConstants;

/**
 * Created by Sam on 8/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class ModPotions
{
	@GameRegistry.ObjectHolder("spelunking")
	public static final Potion SPELUNKING = MobEffects.GLOWING;

	@SubscribeEvent
	public static void onRegisterPotions(RegistryEvent.Register<Potion> event)
	{
		event.getRegistry().register(new PotionSpelunking());
	}
}
