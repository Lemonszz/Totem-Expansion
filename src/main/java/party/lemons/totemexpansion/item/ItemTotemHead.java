package party.lemons.totemexpansion.item;

/**
 * Created by Sam on 14/04/2018.
 */
public class ItemTotemHead extends ItemBase
{
	private final ItemTotemBase totem;
	private final float costFactor;

	public ItemTotemHead(String name, ItemTotemBase totem, float costFactor)
	{
		super(name);

		this.totem = totem;
		this.costFactor = costFactor;
	}

	public boolean doesDrop()
	{
		return true;
	}

	public ItemTotemBase getTotem()
	{
		return totem;
	}

	public float getCostFactor()
	{
		return costFactor;
	}
}
