package party.lemons.totemexpansion.handler;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.network.PacketHandler;
import baubles.common.network.PacketSync;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.totemexpansion.config.ModConfig;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.item.ItemTotemBase;
import party.lemons.totemexpansion.item.TotemType;
import party.lemons.totemexpansion.misc.TotemUtil;

import javax.annotation.Nullable;

/**
 * Created by Sam on 6/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
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
					activateTotem(event.player, stack, null);
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

		TotemType totemType = null;

		if(event.getSource() == DamageSource.LAVA)
			totemType = TotemType.DAMAGE_LAVA;

		else if(event.getSource() == DamageSource.DROWN)
			totemType = TotemType.DAMAGE_DROWN;

		if(totemType != null)
		{
			ItemStack stack = findTotem((EntityPlayer) event.getEntityLiving(), totemType);
			if(!stack.isEmpty())
			{
				event.setCanceled(activateTotem((EntityPlayer) event.getEntityLiving(), stack, event.getSource()));
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onDeath(LivingDeathEvent event)
	{
		if(!event.isCanceled() && event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			TotemType type = null;

			if(event.getSource() == DamageSource.FALL)
			{
				ItemStack stack = findTotem(player, TotemType.FALL_DEATH);
				if(!stack.isEmpty())
				{
					event.setCanceled(activateTotem(player, stack, event.getSource()));
					return;
				}
			}

			ItemStack stack = findTotem(player, TotemType.DEATH);
			if(!stack.isEmpty())
			{
				event.setCanceled(activateTotem(player, stack, event.getSource()));
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
					flag = 	activateTotem(p, stack, null);
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
		if(event.getHand() != null && event.getOriginal().getItem() instanceof ItemTool)
		{
			ItemStack stack = findTotem(event.getEntityPlayer(), TotemType.TOOL_BREAK);
			if(!stack.isEmpty())
			{
				//TODO create a real TotemResult or something?
				boolean flag = activateTotem(event.getEntityPlayer(), stack, null);

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
				if(event.getEntityLiving().getRNG().nextInt(ModConfig.HEAD_DROP_RATE - (event.getLootingLevel() * ModConfig.HEAD_DROP_LOOTING_MODIFIER)) == 1)
				{
					ItemStack stack = new ItemStack(TotemUtil.randomTotemHead(event.getEntityLiving().getRNG()));
					EntityItem eI = new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, stack);

					event.getDrops().add(eI);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent event)
	{
		if(event.getModID().equalsIgnoreCase(ModConstants.MODID))
		{
			ConfigManager.sync(ModConstants.MODID, Config.Type.INSTANCE);
			TotemUtil.updateOreCache();
		}
	}

	public static boolean activateTotem(EntityPlayer living, ItemStack stack, @Nullable DamageSource source)
	{
		if(TotemUtil.isTotemBlacklisted(stack.getItem()))
		{
			return false;
		}

		boolean result = ((ItemTotemBase)stack.getItem()).onActivate(living, stack, source);

		if(Loader.isModLoaded("baubles") && !living.world.isRemote)
		{
			PacketHandler.INSTANCE.sendToAllTracking(new PacketSync(living, BaubleType.CHARM.ordinal(), stack), living);
			PacketHandler.INSTANCE.sendTo(new PacketSync(living, BaubleType.CHARM.ordinal(), stack), (EntityPlayerMP) living);
		}
		return result;
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
