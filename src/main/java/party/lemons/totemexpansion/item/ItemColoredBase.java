package party.lemons.totemexpansion.item;

import party.lemons.totemexpansion.misc.IColor;

/**
 * Created by Sam on 10/04/2018.
 */
public class ItemColoredBase extends ItemBase implements IColor
{
	private int color = 0xFFFFFF;

	public ItemColoredBase(String name)
	{
		super(name);
	}

	public ItemColoredBase setColor(int color)
	{
		this.color = color;

		return this;
	}

	public int getColor()
	{
		return color;
	}
}
