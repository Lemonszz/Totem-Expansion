package party.lemons.totemexpansion.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.handler.PlayerData;

/**
 * Created by Sam on 13/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ActionEvents
{
	@SubscribeEvent
	public static void onTakeDamage(LivingDamageEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			PlayerData data = player.getCapability(PlayerData.CAPABILITY, null);

			ActionState state = data.getActionState();
			boolean damage = state.getAction().onTakeDamage(player, event.getSource(), state);
			event.setCanceled(!damage);
		}
	}

	@SubscribeEvent
	public static void onPlayerUpdate(TickEvent.PlayerTickEvent event)
	{
		PlayerData data = event.player.getCapability(PlayerData.CAPABILITY, null);
		data.getActionState().getAction().onActionUpdate(event.player, data.getActionState());
	}
}