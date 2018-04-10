package party.lemons.totemexpansion.handler.ticker;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.world.World;

/**
 * Created by Sam on 10/04/2018.
 */
public class TickerTime implements ITicker
{
	private final long TIME_STEP = 100;

	private long target;

	public TickerTime(World world){}
	public TickerTime(long target, World world)
	{
		this.target = target + world.getWorldTime();
	}

	@Override
	public void update(World world)
	{
		if(world.getWorldTime() > target)
		{
			world.setWorldTime(world.getWorldTime() - TIME_STEP);
		}
		else
		{
			world.setWorldTime(world.getWorldTime() + TIME_STEP);
			if(world.getWorldTime() > target)
				world.setWorldTime(target);
		}

		for(EntityPlayerMP playerMP : world.getMinecraftServer().getPlayerList().getPlayers())
		{
			playerMP.connection.sendPacket(new SPacketTimeUpdate(world.getTotalWorldTime(), world.getWorldTime(), world.getGameRules().getBoolean("doDaylightCycle")));
		}

	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setLong("target", target);

		return tags;
	}

	@Override
	public void readFromNBT(NBTTagCompound tags)
	{
		target = tags.getLong("target");
	}

	@Override
	public boolean isTaskFinished(World world)
	{
		return world.getWorldTime() == target;
	}
}
