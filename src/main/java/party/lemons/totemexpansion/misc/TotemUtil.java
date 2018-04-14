package party.lemons.totemexpansion.misc;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import party.lemons.totemexpansion.config.ModConfig;
import party.lemons.totemexpansion.item.ItemTotemBase;
import party.lemons.totemexpansion.item.ItemTotemHead;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sam on 8/04/2018.
 */
public class TotemUtil
{
	private TotemUtil(){}

	/**
	 * Updates spelunking effect ores from config
	 */
	public static void updateOreCache()
	{
		ModConfig.ORES.clear();

		for(String s : ModConfig.TOTEM_OF_SPELUNKING_ORES_DICT)
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

	/**
	 * Chooses a random totem
	 * @param r
	 * @return random totem
	 */
	public static Item randomTotemHead(Random r)
	{
		List<Item> totems = TotemUtil.getListOfTotemHeads();
		totems.removeIf(t -> isTotemOnDropBlacklist(t));
		Item it = totems.get(r.nextInt(totems.size()));

		return it;
	}

	/**
	 * Checks if totem is on config blacklist
	 * @param item
	 * @return blacklisted
	 */
	public static boolean isTotemBlacklisted(Item item)
	{
		if(item instanceof ItemTotemBase)
		{
			for(String s : ModConfig.TOTEM_BLACKLIST)
			{
				if(s.equalsIgnoreCase(item.getRegistryName().toString()))
					return true;
			}
		}

		return false;
	}

	/**
	 * Returns if head is on mob drop blacklist
	 * @param i
	 * @return blacklisted
	 */
	public static boolean isTotemOnDropBlacklist(Item i)
	{
		for(String s : ModConfig.TOTEM_HEAD_DROP_BLACKLIST)
		{
			if(s.equalsIgnoreCase(i.getRegistryName().toString()))
				return true;
		}

		return false;
	}

	/**
	 * Returns a list of totem head items
	 * @return totem heads
	 */
	public static List<Item> getListOfTotemHeads()
	{
		if(headCacheList == null)
		{
			headCacheList = new ArrayList<>();
			for(Item item : ForgeRegistries.ITEMS)
			{
				if((item instanceof ItemTotemHead) && ((ItemTotemHead) item).doesDrop())
				{
					headCacheList.add(item);
				}
			}
		}
		return headCacheList;
	}
	private static List<Item> headCacheList = null;


	/**
	 * Creates a PriceInfo from a Totem HEad
	 * @param head
	 * @return PriceInfo
	 */
	public static EntityVillager.PriceInfo getTotemPrice(ItemTotemHead head)
	{
		int min = (int) (ModConfig.VILLAGER_HEAD_MIN * head.getCostFactor());
		int max = (int) (ModConfig.VILLAGER_HEAD_MAX * head.getCostFactor());

		EntityVillager.PriceInfo info = new EntityVillager.PriceInfo(min, max);

		return info;
	}
}
