package party.lemons.totemexpansion.misc;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Sam on 8/04/2018.
 */
public interface IModel
{
	default boolean hasModel(){
		return true;
	}

	ResourceLocation getModelLocation();
}
