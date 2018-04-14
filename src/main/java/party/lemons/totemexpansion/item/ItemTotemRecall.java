package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import party.lemons.totemexpansion.handler.PlayerData;
import party.lemons.totemexpansion.handler.action.impl.ActionRecall;
import party.lemons.totemexpansion.handler.action.ActionState;
import party.lemons.totemexpansion.handler.action.Actions;

/**
 * Created by Sam on 13/04/2018.
 */
public class ItemTotemRecall extends ItemTotemBase
{
	public ItemTotemRecall()
	{
		super("totem_recalling", TotemType.ACTIVATE);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);

		PlayerData data = living.getCapability(PlayerData.CAPABILITY, null);
		if(!(data.getActionState().getAction() instanceof ActionRecall))
		{
			data.setActionState(living, new ActionState(Actions.RECALL_UP));
		}

		return true;
	}
}
