package party.lemons.totemexpansion.handler.action;

import party.lemons.totemexpansion.handler.action.impl.ActionRecall;

import java.util.ArrayList;

/**
 * Created by Sam on 13/04/2018.
 */
public class Actions
{
	public static PlayerAction RECALL_UP;
	public static PlayerAction RECALL_DOWN;

	public static void init()
	{
		RECALL_UP = createAction(new ActionRecall.RecallUp());
		RECALL_DOWN = createAction(new ActionRecall.RecallDown());
	}

	private static PlayerAction createAction(PlayerAction action)
	{
		actions.add(action);

		return action;
	}

	public static PlayerAction getAction(String id)
	{
		for(PlayerAction action : actions)
		{
			if(action.getID().equalsIgnoreCase(id))
				return action;
		}

		return PlayerAction.NONE;
	}

	private static ArrayList<PlayerAction> actions = new ArrayList<>();
}
