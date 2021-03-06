package party.lemons.totemexpansion.handler.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.item.ModItems;
import party.lemons.totemexpansion.misc.IColor;
import party.lemons.totemexpansion.misc.IModel;

/**
 * Created by Sam on 8/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID, value = Side.CLIENT)
public class ClientModelRegistry
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRegisterModel(ModelRegistryEvent event)
	{
		ModItems.itemList.stream().filter(i -> i instanceof IModel).forEach(i -> registerModel((Item & IModel)i));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRegisterItemColour(ColorHandlerEvent.Item event)
	{
		ModItems.itemList.stream().filter(i -> i instanceof IColor).forEach(i -> event.getItemColors().registerItemColorHandler(new ItemBaseColorHandler(), i));
	}

	@SideOnly(Side.CLIENT)
	public static <ModelItem extends Item & IModel> void registerModel(ModelItem item)
	{
		if(item == Items.AIR || !item.hasModel())
			return;

		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getModelLocation(), "inventory"));
	}
}
