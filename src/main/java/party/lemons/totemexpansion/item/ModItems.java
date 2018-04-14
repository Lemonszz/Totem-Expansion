package party.lemons.totemexpansion.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.totemexpansion.config.ModConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 6/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class ModItems
{
	@GameRegistry.ObjectHolder("minecraft:totem_of_undying")
	public static final Item TOTEM_OF_UNDYING = Items.TOTEM_OF_UNDYING;

	@GameRegistry.ObjectHolder("totem_base")
	public static final Item TOTEM_BASE = Items.AIR;

	@GameRegistry.ObjectHolder("totem_falling")
	public static final Item TOTEM_PLUMMETING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_undying")
	public static final Item TOTEM_HEAD_UNDYING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_plummeting")
	public static final Item TOTEM_HEAD_PLUMMETING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_lava")
	public static final Item TOTEM_HEAD_LAVA = Items.AIR;

	@GameRegistry.ObjectHolder("totem_lava")
	public static final Item TOTEM_LAVA = Items.AIR;

	@GameRegistry.ObjectHolder("totem_breathing")
	public static final Item TOTEM_BREATHING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_breathing")
	public static final Item TOTEM_HEAD_BREATHING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_explode")
	public static final Item TOTEM_EXPLODE = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_explode")
	public static final Item TOTEM_HEAD_EXPLODE = Items.AIR;

	@GameRegistry.ObjectHolder("totem_repair")
	public static final Item TOTEM_REPAIR = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_repair")
	public static final Item TOTEM_HEAD_REPAIR = Items.AIR;

	@GameRegistry.ObjectHolder("totem_spelunking")
	public static final Item TOTEM_SPELUNKING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_spelunking")
	public static final Item TOTEM_HEAD_SPELUNKING = Items.AIR;

	@GameRegistry.ObjectHolder("totem_time")
	public static final Item TOTEM_TIME= Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_time")
	public static final Item TOTEM_HEAD_TIME = Items.AIR;

	@GameRegistry.ObjectHolder("totem_recalling")
	public static final Item TOTEM_RECALL = Items.AIR;

	@GameRegistry.ObjectHolder("totem_head_recalling")
	public static final Item TOTEM_HEAD_RECALLING= Items.AIR;

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> r = event.getRegistry();
		registerTotem(r, new ItemTotemUndying(), "undying");
		registerTotem(r, new ItemTotemFalling(), "plummeting");
		registerTotem(r, new ItemTotemLava(), "lava");
		registerTotem(r, new ItemTotemBreathing(), "breathing");
		registerTotem(r, new ItemTotemExplode(), "explode");
		registerTotem(r, new ItemTotemRepair(), "repair");
		registerTotem(r, new ItemTotemSpelunking(), "spelunking");
		registerTotem(r, new ItemTotemTime(), "time");
		registerTotem(r, new ItemTotemRecall(), "recalling", 1.4F);

		r.register(new ItemBase("totem_base"));
	}

	private static void registerTotem(IForgeRegistry<Item> registry, ItemTotemBase totem, String name, float costFactor)
	{
		registry.registerAll(totem, new ItemTotemHead("totem_head_" + name, totem, costFactor));
	}

	private static void registerTotem(IForgeRegistry<Item> registry, ItemTotemBase totem, String name)
	{
		registerTotem(registry, totem, name, 1F);
	}

	public static List<Item> itemList = new ArrayList<>();
}
