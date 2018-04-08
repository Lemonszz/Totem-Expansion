package party.lemons.totemexpansion.misc;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import party.lemons.totemexpansion.config.ModConfig;

/**
 * Created by Sam on 8/04/2018.
 */
public class TotemUtil
{
	private TotemUtil(){}

	public static void updateOreCache()
	{
		ModConfig.ORES.clear();

		for(String s : ModConfig.TOTEM_OF_SPELUNKING_ORES)
		{
			NonNullList<ItemStack> blocks = OreDictionary.getOres(s);
			for(ItemStack stack : blocks)
			{
				if(stack.getItem() instanceof ItemBlock)
				{
					ItemBlock ib = (ItemBlock) stack.getItem();
					ModConfig.ORES.add(ib.getBlock());

				}

			}
		}
	}
}
