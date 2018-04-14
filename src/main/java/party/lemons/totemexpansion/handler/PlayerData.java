package party.lemons.totemexpansion.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import party.lemons.totemexpansion.handler.action.ActionState;
import party.lemons.totemexpansion.handler.action.PlayerAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Sam on 13/04/2018.
 */
public interface PlayerData
{
	@CapabilityInject(PlayerData.class)
	Capability<PlayerData> CAPABILITY = null;

	ActionState getActionState();
	void setActionState(EntityPlayer player, ActionState state);
	void setActionStateFromLoad(ActionState state);

	class Impl implements PlayerData
	{
		private ActionState actionState = new ActionState(PlayerAction.NONE);

		@Override
		public ActionState getActionState()
		{
			return actionState;
		}

		@Override
		public void setActionState(EntityPlayer player, ActionState state)
		{
			actionState.getAction().onActionEnd(player,  this.actionState);
			this.actionState = state;
			actionState.getAction().onActionStart(player,  this.actionState);
		}

		@Override
		public void setActionStateFromLoad(ActionState state)
		{
			this.actionState = state;
		}
	}

	class Storage implements Capability.IStorage<PlayerData>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side)
		{
			NBTTagCompound tags = new NBTTagCompound();
			tags.setTag("action", instance.getActionState().writeToNBT());
			return tags;
		}

		@Override
		public void readNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tags = (NBTTagCompound) nbt;

			NBTTagCompound actionTags = (NBTTagCompound) tags.getTag("action");
			ActionState state = new ActionState(actionTags);
			if(state.getAction() != null)
				instance.setActionStateFromLoad(state);
		}
	}

	class Provider implements ICapabilitySerializable<NBTBase>
	{
		private PlayerData instance = CAPABILITY.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
		{
			return capability == CAPABILITY;
		}

		@Nullable
		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
		{
			return capability == CAPABILITY ? CAPABILITY.cast(instance) : null;
		}

		@Override
		public NBTBase serializeNBT()
		{
			return CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt)
		{
			CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
		}
	}
}