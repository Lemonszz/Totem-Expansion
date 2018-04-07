package party.lemons.totemexpansion.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.misc.CreativeTab;
import party.lemons.totemexpansion.misc.IModel;

/**
 * Created by Sam on 8/04/2018.
 */
public class ItemBase extends Item implements IModel
{
	public ItemBase(String name)
	{
		this.setUnlocalizedName(ModConstants.MODID + "." + name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTab.tab);

		ModItems.itemList.add(this);
	}

	@Override
	public ResourceLocation getModelLocation()
	{
		return this.getRegistryName();
	}
}
