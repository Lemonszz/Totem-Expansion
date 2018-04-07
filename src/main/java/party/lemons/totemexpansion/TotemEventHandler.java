package party.lemons.totemexpansion;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.totemexpansion.item.ItemTotemBase;
import party.lemons.totemexpansion.item.ModItems;
import party.lemons.totemexpansion.item.TotemType;

import java.util.Random;

/**
 * Created by Sam on 6/04/2018.
 */
@Mod.EventBusSubscriber
public class TotemEventHandler
{
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			if(event.player.posY < -5)
			{
				ItemStack stack = findTotem(event.player, TotemType.FALL_DEATH);
				if(!stack.isEmpty())
				{
					((ItemTotemBase) stack.getItem()).onActivate(event.player, stack, null);
					return;
				}
			}

			if(event.player.isSneaking() && event.player.getActivePotionEffect(MobEffects.LEVITATION) != null)
			{
				event.player.fallDistance = 0;
				event.player.motionY = -0.10;
			}
		}

	}

	@SubscribeEvent
	public static void onPlayerDamage(LivingDamageEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		//TODO: fix dupe code
		if(event.getSource() == DamageSource.LAVA)
		{
			ItemStack stack = findTotem((EntityPlayer) event.getEntityLiving(), TotemType.DAMAGE_LAVA);
			if(!stack.isEmpty())
			{
				event.setCanceled(((ItemTotemBase)stack.getItem()).onActivate((EntityPlayer) event.getEntityLiving(), stack, event.getSource()));
				return;
			}
		}

		if(event.getSource() == DamageSource.DROWN)
		{
			ItemStack stack = findTotem((EntityPlayer) event.getEntityLiving(), TotemType.DAMAGE_DROWN);
			if(!stack.isEmpty())
			{
				event.setCanceled(((ItemTotemBase)stack.getItem()).onActivate((EntityPlayer) event.getEntityLiving(), stack, event.getSource()));
				return;
			}
		}
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event)
	{

		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			if(event.getSource() == DamageSource.FALL)
			{
				ItemStack stack = findTotem(player, TotemType.FALL_DEATH);
				if(!stack.isEmpty())
				{
					event.setCanceled(((ItemTotemBase)stack.getItem()).onActivate(player, stack, event.getSource()));
					return;
				}
			}

			ItemStack stack = findTotem(player, TotemType.DEATH);
			if(!stack.isEmpty())
			{
				event.setCanceled(((ItemTotemBase) stack.getItem()).onActivate(player, stack, event.getSource()));
				return;
			}
		}
	}

	@SubscribeEvent
	public static void onExplode(ExplosionEvent.Detonate event)
	{
		boolean flag = false;
		for(Entity e : event.getAffectedEntities())
		{
			if(e instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer) e;
				ItemStack stack = findTotem(p, TotemType.EXPLODE);
				if(!stack.isEmpty())
				{
					flag = ((ItemTotemBase) stack.getItem()).onActivate(p, stack, null);
					if(flag)
						break;
				}
			}
		}

		if(flag)
		{
			event.getAffectedEntities().clear();
			event.getAffectedBlocks().clear();
		}
	}

	@SubscribeEvent
	public static void onToolBreak(PlayerDestroyItemEvent event)
	{
		if(event.getHand() != null)
		{
			ItemStack stack = findTotem(event.getEntityPlayer(), TotemType.TOOL_BREAK);
			if(!stack.isEmpty())
			{
				//TODO create a real TotemResult or something?
				boolean flag = ((ItemTotemBase) stack.getItem()).onActivate(event.getEntityPlayer(), stack, null);

				if(flag)
				{
					ItemStack newStack = event.getOriginal().copy();
					newStack.setItemDamage(0);
					System.out.println(newStack);
					event.getEntityPlayer().inventory.addItemStackToInventory( newStack);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingKill(LivingDropsEvent event)
	{
		if(!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof IMob)
		{
			if(event.getSource().getTrueSource() instanceof EntityPlayer)
			{
				if(event.getEntityLiving().getRNG().nextInt(50 - (event.getLootingLevel() * 2)) == 1)
				{
					ItemStack stack = new ItemStack(randomTotem(event.getEntityLiving().getRNG()));
					EntityItem eI = new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, stack);

					event.getDrops().add(eI);
				}
			}
		}
	}

	private static Item randomTotem(Random r)
	{
		Item[] items = new Item[] {
				ModItems.TOTEM_HEAD_LAVA,
				ModItems.TOTEM_HEAD_PLUMMETING,
				ModItems.TOTEM_HEAD_UNDYING,
				ModItems.TOTEM_HEAD_BREATHING,
				ModItems.TOTEM_HEAD_EXPLODE,
				ModItems.TOTEM_HEAD_REPAIR
		};

		return items[r.nextInt(items.length)];
	}

	public static ItemStack findTotem(EntityPlayer player, TotemType type)
	{
		if(Loader.isModLoaded("baubles"))
		{
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for(int i = 0 ; i < baubles.getSlots(); i++)
			{
				ItemStack stack = baubles.getStackInSlot(i);
				if(!stack.isEmpty() && stack.getItem() instanceof ItemTotemBase)
				{
					ItemTotemBase totem = (ItemTotemBase) stack.getItem();
					if(totem.getType() == type)
						return stack;
				}
			}
		}

		for(ItemStack stack : player.inventory.mainInventory)
		{
			if(!stack.isEmpty() && stack.getItem() instanceof ItemTotemBase)
			{
				ItemTotemBase totem = (ItemTotemBase) stack.getItem();
				if(totem.getType() == type)
					return stack;
			}
		}

		return ItemStack.EMPTY;
	}
}
