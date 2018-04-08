package party.lemons.totemexpansion.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.totemexpansion.TotemExpansion;
import party.lemons.totemexpansion.config.ModConfig;

import party.lemons.totemexpansion.handler.TotemEventHandler;
import party.lemons.totemexpansion.network.MessageItemEffect;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 6/04/2018.
 */
@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
@Optional.Interface(iface = "baubles.api.render.IRenderBauble", modid = "baubles")
public class ItemTotemBase extends ItemBase implements IBauble, IRenderBauble
{
	private TotemType type;

	public ItemTotemBase(String name, TotemType type)
	{
		super(name);

		this.type = type;
		this.setMaxStackSize(1);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, @Nullable DamageSource source)
	{
		if (!living.world.isRemote && living instanceof EntityPlayerMP)
		{
			EntityPlayerMP entityplayermp = (EntityPlayerMP)living;
			entityplayermp.addStat(StatList.getObjectUseStats(this));
			CriteriaTriggers.USED_TOTEM.trigger(entityplayermp, stack);
			TotemExpansion.NETWORK.sendTo(new MessageItemEffect(stack, living), (EntityPlayerMP) living);
			TotemExpansion.NETWORK.sendToAllTracking(new MessageItemEffect(stack, living), living);
		}

		stack.shrink(1);
		living.clearActivePotions();

		return false;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(type == TotemType.ACTIVATE)
		{
			TotemEventHandler.activateTotem(playerIn, playerIn.getHeldItem(handIn), null);
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}

		return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(TextFormatting.DARK_PURPLE + I18n.format(this.getUnlocalizedName() + ".desc"));
	}

	public TotemType getType()
	{
		return type;
	}


	@Override
	public BaubleType getBaubleType(ItemStack itemStack)
	{
		return BaubleType.CHARM;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onPlayerBaubleRender(ItemStack itemStack, EntityPlayer entityPlayer, RenderType renderType, float v)
	{
		if(!ModConfig.RENDER_BAUBLE || renderType != RenderType.BODY)
			return;

		double outAmount = !entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() ? -0.2F : -0.14D;

		IRenderBauble.Helper.rotateIfSneaking(entityPlayer);
		GlStateManager.translate(-0.15D, 0.3D, outAmount);
		GlStateManager.rotate(180F,-0.0F, 0F, 1F);
		GlStateManager.scale(0.23D, 0.23D, 0.23D);
		Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);
	}
}
