package party.lemons.totemexpansion.misc;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import party.lemons.totemexpansion.config.ModConfig;

/**
 * Created by Sam on 8/04/2018.
 */
public class TotemUtil
{
	private TotemUtil(){}

	public static void updateOreCache()
	{
		for(String s : ModConfig.TOTEM_OF_SPELUNKING_ORES)
		{
			Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s));
			if(block != null)
				ModConfig.ORES.add(block);
		}
	}
}
