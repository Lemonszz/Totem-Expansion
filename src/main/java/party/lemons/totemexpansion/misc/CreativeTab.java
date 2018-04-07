package party.lemons.totemexpansion.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import party.lemons.totemexpansion.config.ModConstants;

/**
 * Created by Sam on 7/04/2018.
 */
public class CreativeTab extends CreativeTabs
{
	public static CreativeTab tab = new CreativeTab();

	public CreativeTab()
	{
		super(ModConstants.MODID);
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(Items.TOTEM_OF_UNDYING);
	}
}
