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

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemTotemUndying().setUnlocalizedName("totem"),
				new ItemTotemFalling(),
				new ItemTotemLava(),
				new ItemTotemBreathing(),
				new ItemTotemExplode(),
				new ItemTotemRepair(),
				new ItemTotemSpelunking(),
				new ItemBase("totem_base"),
				new ItemBase("totem_head_undying"),
				new ItemBase("totem_head_plummeting"),
				new ItemBase("totem_head_lava"),
				new ItemBase("totem_head_breathing"),
				new ItemBase("totem_head_explode"),
				new ItemBase("totem_head_repair"),
				new ItemBase("totem_head_spelunking")
		);
	}

	public static List<Item> itemList = new ArrayList<>();
}
