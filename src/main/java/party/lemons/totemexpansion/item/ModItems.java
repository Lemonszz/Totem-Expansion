package party.lemons.totemexpansion.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.misc.CreativeTab;

/**
 * Created by Sam on 6/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class ModItems
{
	@GameRegistry.ObjectHolder("minecraft:totem_of_undying")
	public static Item TOTEM_OF_UNDYING = Items.TOTEM_OF_UNDYING;

	@GameRegistry.ObjectHolder("totem_base")
	public static Item TOTEM_BASE = Items.AIR;

	@GameRegistry.ObjectHolder("totem_falling")
	public static Item TOTEM_PLUMMETING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_undying")
	public static Item TOTEM_HEAD_UNDYING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_plummeting")
	public static Item TOTEM_HEAD_PLUMMETING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_lava")
	public static Item TOTEM_HEAD_LAVA = Items.AIR;

	@GameRegistry.ObjectHolder("totem_lava")
	public static Item TOTEM_LAVA = Items.AIR;

	@GameRegistry.ObjectHolder("totem_breathing")
	public static Item TOTEM_BREATHING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_breathing")
	public static Item TOTEM_HEAD_BREATHING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_explode")
	public static Item TOTEM_EXPLODE = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_explode")
	public static Item TOTEM_HEAD_EXPLODE = Items.AIR;

	@GameRegistry.ObjectHolder("totem_repair")
	public static Item TOTEM_REPAIR = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_repair")
	public static Item TOTEM_HEAD_REPAIR = Items.AIR;

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemTotemUndying("minecraft:totem_of_undying", TotemType.DEATH).setUnlocalizedName("totem"),
				new ItemTotemFalling(),
				new ItemTotemLava(),
				new ItemTotemBreathing(),
				new ItemTotemExplode(),
				new ItemTotemRepair(),
				createItem(new Item(), "totem_base"),
				createItem(new Item(), "totem_head_undying"),
				createItem(new Item(), "totem_head_plummeting"),
				createItem(new Item(), "totem_head_lava"),
				createItem(new Item(), "totem_head_breathing"),
				createItem(new Item(), "totem_head_explode"),
				createItem(new Item(), "totem_head_repair")
		);
	}

	public static Item createItem(Item item, String name)
	{
		item.setCreativeTab(CreativeTab.tab);
		item.setRegistryName(ModConstants.MODID, name);
		item.setUnlocalizedName(ModConstants.MODID + "." + name);

		return item;
	}

	//TODO: move to client only event subscriber

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRegisterModel(ModelRegistryEvent event)
	{
		//TODO: automatically do this
		registerModels(TOTEM_BASE, TOTEM_HEAD_PLUMMETING, TOTEM_HEAD_UNDYING, TOTEM_PLUMMETING, TOTEM_HEAD_LAVA, TOTEM_LAVA,
		TOTEM_HEAD_BREATHING, TOTEM_BREATHING, TOTEM_EXPLODE, TOTEM_HEAD_EXPLODE, TOTEM_HEAD_REPAIR, TOTEM_REPAIR);

		ModelLoader.setCustomModelResourceLocation(TOTEM_OF_UNDYING, 0, new ModelResourceLocation("minecraft:totem", "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Item item)
	{
		if(item == Items.AIR)
			return;

		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels(Item... items)
	{
		for(Item i : items)
		{
			registerModel(i);
		}
	}
}
