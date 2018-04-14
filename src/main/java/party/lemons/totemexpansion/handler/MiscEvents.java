package party.lemons.totemexpansion.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.totemexpansion.config.ModConstants;

/**
 * Created by Sam on 13/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class MiscEvents
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void attachCaps(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(new ResourceLocation(ModConstants.MODID, "totem_cap"), new PlayerData.Provider());
		}
	}
}
