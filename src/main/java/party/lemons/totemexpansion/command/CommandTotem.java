package party.lemons.totemexpansion.command;

import net.minecraft.command.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import party.lemons.totemexpansion.item.ItemTotemBase;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sam on 9/04/2018.
 */
public class CommandTotem extends CommandBase
{
	/**
	 * Gets the name of the command
	 */
	public String getName()
	{
		return "totem";
	}

	/**
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	/**
	 * Gets the usage string for the command.
	 */
	public String getUsage(ICommandSender sender)
	{
		return "commands.totem.usage";
	}

	/**
	 * Callback for when the command is executed
	 */
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 2)
		{
			throw new WrongUsageException("commands.totem.usage");
		}
		else
		{
			EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
			Item item = getItemByText(sender, args[1]);
			int i = 1;
			int j = 0;
			if(!(item instanceof ItemTotemBase))
			{
				throw new WrongUsageException("command.totem.nototem");
			}

			ItemStack itemstack = new ItemStack(item, i, j);
			((ItemTotemBase)item).onActivate(entityplayer, itemstack, null);


			notifyCommandListener(sender, this, "commands.totem.success", itemstack.getTextComponent(), i, entityplayer.getName());
		}
	}

	/**
	 * Get a list of options for when the user presses the TAB key
	 */
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		if(args.length == 1)
		{
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}else if(args.length == 2)
		{
			System.out.println(getListOfTotemString());

			return getListOfTotemString();
		}

		return Collections.EMPTY_LIST;
	}

	public static List<String> getListOfTotemString()
	{
		if(cacheList == null)
		{
			cacheList = new ArrayList<>();
			for(Item item : ForgeRegistries.ITEMS)
			{
				if(item instanceof ItemTotemBase)
				{
					cacheList.add(item.getRegistryName().toString());
				}
			}
		}

		return cacheList;
	}
	private static List<String> cacheList = null;

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}
