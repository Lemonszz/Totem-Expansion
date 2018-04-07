package party.lemons.totemexpansion.config;

import net.minecraftforge.common.config.Config;

/**
 * Created by Sam on 7/04/2018.
 */
@Config(modid = ModConstants.MODID)
public class ModConfig
{
	@Config.RangeInt(min = 0)
	@Config.Name("Head Drop Rate")
	public static int HEAD_DROP_RATE = 50;

	@Config.RangeInt(min = 0)
	@Config.Name("Head Drop Looting Modifier")
	public static int HEAD_DROP_LOOTING_MODIFIER = 2;

	@Config.RequiresMcRestart
	@Config.Name("Totem Head Trade Blacklist")
	@Config.Comment("Totem heads on this list will NOT be added to trades")
	public static String[] TRADE_BLACKLIST_HEADS = new String[]
			{
			};

	@Config.RequiresMcRestart
	@Config.Name("Mob Head Trades")
	@Config.Comment("Values are metadata of minecraft:skull, values on this list will NOT be added to trades")
	public static int[] TRADE_BLACKLIST_SKULLS = new int[]
			{

			};

	@Config.Name("Totem Head Drop Blacklist")
	@Config.Comment("Totems on this list will not be dropped by mobs")
	public static String[] TOTEM_HEAD_DROP_BLACKLIST = new String[]
			{
			};

	@Config.Name("Totem Blacklist")
	@Config.Comment("Totems on this list will not activate")
	public static String[] TOTEM_BLACKLIST = new String[]
			{

			};
}
