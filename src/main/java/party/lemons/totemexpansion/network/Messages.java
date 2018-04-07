package party.lemons.totemexpansion.network;

import net.minecraftforge.fml.relauncher.Side;
import party.lemons.totemexpansion.TotemExpansion;

/**
 * Created by Sam on 6/04/2018.
 */
public class Messages
{
	public static int id = 1;

	public static void init()
	{
		TotemExpansion.NETWORK.registerMessage(MessageItemEffect.Handler.class, MessageItemEffect.class, id++, Side.CLIENT);
	}
}
