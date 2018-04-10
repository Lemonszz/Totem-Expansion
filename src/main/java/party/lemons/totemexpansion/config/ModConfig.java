package party.lemons.totemexpansion.config;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Config;

import java.util.ArrayList;
import java.util.List;

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
	public static String[] TRADE_BLACKLIST_HEADS = new String[]{};

	@Config.RequiresMcRestart
	@Config.Name("Mob Head Trades Blacklist")
	@Config.Comment("Values are metadata of minecraft:skull, values on this list will NOT be added to trades")
	public static int[] TRADE_BLACKLIST_SKULLS = new int[]{

	};

	@Config.Name("Totem Head Drop Blacklist")
	@Config.Comment("Totems on this list will not be dropped by mobs")
	public static String[] TOTEM_HEAD_DROP_BLACKLIST = new String[]{};

	@Config.Name("Totem Blacklist")
	@Config.Comment("Totems on this list will not activate")
	public static String[] TOTEM_BLACKLIST = new String[]{

	};

	@Config.Name("Totem of Spelunking Ore Dictionary")
	@Config.Comment("Ores that can be found via the Totem of Spelunking")
	public static String[] TOTEM_OF_SPELUNKING_ORES_DICT = new String[]{
			"oreIron",
			"oreGold",
			"oreDiamond",
			"oreEmerald",
			"oreCoal",
			"oreLapis",
			"oreRedstone",
			"oreQuartz",
			"oreBauxite",
			"oreCinnabar",
			"oreCopper",
			"oreGalena",
			"oreIridium",
			"oreLead",
			"orePeridot",
			"orePetroleum",
			"orePlatinum",
			"orePyrite",
			"oreRuby",
			"oreSapphire",
			"oreSheldonite",
			"oreSilver",
			"oreSodalite",
			"oreSphalerite",
			"oreSulfur",
			"oreTetrahedrite",
			"oreTin",
			"oreTitanium",
			"oreTungsten",
			"oreUranium",
			"oreInfusedAir",
			"oreInfusedFire",
			"oreInfusedWater",
			"oreInfusedEarth",
			"oreInfusedOrder",
			"oreInfusedEntropy"
	};

	@Config.Name("Spelunking Range")
	@Config.RangeInt(min = 1)
	public static int SPELUNKING_RANGE = 5;

	@Config.Name("Spelunking Cache Time")
	@Config.Comment("How many ticks until the ore cache is remade, lower = faster updates")
	@Config.RangeInt(min = 1)
	public static int SPELUNKING_UPDATE_TIME = 20;

	@Config.Name("Render Bauble On Player")
	public static boolean RENDER_BAUBLE = true;

	@Config.Ignore
	public static List<Block> ORES = new ArrayList<>();
}
