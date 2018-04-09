package party.lemons.totemexpansion.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Created by Sam on 9/04/2018.
 */
public class ModCommands
{
	public static void init(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandTotem());
		event.registerServerCommand(new CommandTotemEffect());
	}
}
