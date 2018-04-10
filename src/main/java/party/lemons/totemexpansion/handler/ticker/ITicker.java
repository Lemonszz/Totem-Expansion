package party.lemons.totemexpansion.handler.ticker;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by Sam on 10/04/2018.
 */
public interface ITicker
{
	/*
		ITickers MUST have a constructor that ONLY takes a world
	 */

	void update(World world);
	NBTTagCompound writeToNBT();
	void readFromNBT(NBTTagCompound tagCompound);
	boolean isTaskFinished(World world);
}
