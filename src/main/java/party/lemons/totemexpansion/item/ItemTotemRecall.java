package party.lemons.totemexpansion.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import party.lemons.totemexpansion.config.ModConfig;
import party.lemons.totemexpansion.handler.PlayerData;
import party.lemons.totemexpansion.handler.TotemEventHandler;
import party.lemons.totemexpansion.handler.action.impl.ActionRecall;
import party.lemons.totemexpansion.handler.action.ActionState;
import party.lemons.totemexpansion.handler.action.Actions;

import java.util.Arrays;

/**
 * Created by Sam on 13/04/2018.
 */
public class ItemTotemRecall extends ItemTotemBase
{
	public ItemTotemRecall()
	{
		super("totem_recalling", TotemType.ACTIVATE);
	}

	public boolean onActivate(EntityPlayer living, ItemStack stack, DamageSource source)
	{
		super.onActivate(living, stack, source);

		PlayerData data = living.getCapability(PlayerData.CAPABILITY, null);
		if(!(data.getActionState().getAction() instanceof ActionRecall))
		{
			data.setActionState(living, new ActionState(Actions.RECALL_UP));
		}

		return true;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		int dim = worldIn.provider.getDimension();
		boolean allowed = false;

		for(int i = 0; i < ModConfig.ALLOWED_RECALL_DIMENSIONS.length; i++)
		{
			if(ModConfig.ALLOWED_RECALL_DIMENSIONS[i] == dim)
			{
				allowed = true;
			}
		}

		if(!allowed)
		{
			Style style = new Style().setItalic(true).setColor(TextFormatting.RED);
			playerIn.sendStatusMessage(new TextComponentTranslation("item.totemexpansion.totem_recalling.error").setStyle(style), true);
			return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
