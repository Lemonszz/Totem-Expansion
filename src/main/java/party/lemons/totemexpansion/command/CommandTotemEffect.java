package party.lemons.totemexpansion.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import party.lemons.totemexpansion.TotemExpansion;
import party.lemons.totemexpansion.item.ItemTotemBase;
import party.lemons.totemexpansion.network.MessageItemEffect;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sam on 9/04/2018.
 */
public class CommandTotemEffect extends CommandBase
{
	/**
	 * Gets the name of the command
	 */
	public String getName()
	{
		return "totemeffect";
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
		return "commands.totemeffect.usage";
	}

	/**
	 * Callback for when the command is executed
	 */
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 2)
		{
			throw new WrongUsageException("commands.totemeffect.usage");
		}
		else
		{
			EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
			Item item = getItemByText(sender, args[1]);
			int i = 1;
			int j = 0;

			ItemStack itemstack = new ItemStack(item, i, j);
			TotemExpansion.NETWORK.sendTo(new MessageItemEffect(itemstack, entityplayer), (EntityPlayerMP) entityplayer);
			TotemExpansion.NETWORK.sendToAllTracking(new MessageItemEffect(itemstack, entityplayer), entityplayer);


			notifyCommandListener(sender, this, "commands.totemeffect.success", itemstack.getTextComponent(), i, entityplayer.getName());
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
			return getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys());
		}

		return Collections.EMPTY_LIST;
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}
}
