package party.lemons.totemexpansion.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

/**
 * Created by Sam on 13/04/2018.
 */
public class PlayerAction
{
	public static PlayerAction NONE = new PlayerAction("none");
	private final String ID;

	public PlayerAction(String id)
	{
		this.ID = id;
	}

	public boolean onTakeDamage(EntityPlayer player, DamageSource source, ActionState state)
	{
		return true;
	}

	public void onActionUpdate(EntityPlayer player, ActionState state)
	{

	}

	public void onActionStart(EntityPlayer player, ActionState state)
	{

	}

	public void onActionEnd(EntityPlayer player, ActionState state)
	{

	}

	public String getID()
	{
		return ID;
	}
}
