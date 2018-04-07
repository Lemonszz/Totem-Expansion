package party.lemons.totemexpansion.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.totemexpansion.TotemExpansion;

/**
 * Created by Sam on 6/04/2018.
 */
public class MessageItemEffect implements IMessage
{
	public ItemStack stack;
	public Entity entity;

	public MessageItemEffect()
	{

	}

	public MessageItemEffect(ItemStack stack, Entity entity)
	{
		this.stack = stack;
		this.entity = entity;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void fromBytes(ByteBuf buf)
	{
		stack = ByteBufUtils.readItemStack(buf);
		entity = Minecraft.getMinecraft().world.getEntityByID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, stack);
		buf.writeInt(entity.getEntityId());
	}

	public static class Handler implements IMessageHandler<MessageItemEffect, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(MessageItemEffect message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(()->
			{
				TotemExpansion.proxy.playActivateAnimation(message.stack, message.entity);
			});
			return null;
		}
	}
}
