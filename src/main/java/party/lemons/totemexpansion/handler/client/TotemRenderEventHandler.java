package party.lemons.totemexpansion.handler.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.totemexpansion.config.ModConfig;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.item.ModItems;
import party.lemons.totemexpansion.potion.ModPotions;

import java.util.ArrayList;

/**
 * Created by Sam on 8/04/2018.
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModConstants.MODID)
@SideOnly(Side.CLIENT)
public class TotemRenderEventHandler
{
	private static EntityItem itemInst = null;
	private static ArrayList<BlockPos> oreCache = new ArrayList<>();

	@SubscribeEvent
	public static void onRenderTick(RenderWorldLastEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.player == null || mc.world == null || mc.player.getActivePotionEffect(ModPotions.SPELUNKING) == null)
			return;

		if(itemInst == null)
		{
			itemInst = new EntityItem(mc.world, 0, 0, 0, new ItemStack(ModItems.TOTEM_HEAD_SPELUNKING));
			itemInst.setInfinitePickupDelay();
			itemInst.setNoDespawn();
		}

		int size = ModConfig.SPELUNKING_RANGE;

		EntityPlayer player = mc.player;
		RenderManager renderManager = mc.getRenderManager();

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(0,0,0);
		if(mc.world.getTotalWorldTime() % ModConfig.SPELUNKING_UPDATE_TIME == 0)
		{
			oreCache.clear();
			AxisAlignedBB bb = new AxisAlignedBB(-size, -size, -size, size, size, size).offset(player.getPosition());
			for(int x = (int) bb.minX; x < (int) bb.maxX; x++)
			{
				for(int y = (int) bb.minY; y < (int) bb.maxY; y++)
				{
					for(int z = (int) bb.minZ; z < (int) bb.maxZ; z++)
					{
						pos.setPos(x, y, z);
						IBlockState state = mc.world.getBlockState(pos);
						if(ModConfig.ORES.contains(state.getBlock()))
						{
							oreCache.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
						}
					}
				}
			}
		}

		double playerX = player.prevPosX + (player.posX - player.prevPosX) * event.getPartialTicks();
		double playerY = player.prevPosY + (player.posY - player.prevPosY) * event.getPartialTicks();
		double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.getPartialTicks();
		GlStateManager.translate(-playerX, -playerY, -playerZ);
		oreCache.forEach(bp ->
		{
			GlStateManager.pushMatrix();
			GlStateManager.disableDepth();

			itemInst.setPosition((double) bp.getX() + 0.5D, (double) bp.getY(), (double) bp.getZ() + 0.5D);
			renderManager.renderEntity(itemInst, itemInst.posX, itemInst.posY, itemInst.posZ, 0, event.getPartialTicks(), false);

			GlStateManager.enableDepth();
			GlStateManager.popMatrix();
		});
		GlStateManager.translate(0,0,0);
	}

	@SubscribeEvent
	public static void onWorldTickClient(TickEvent.WorldTickEvent event)
	{
		if(event.world.provider.getDimension() != 0)
			return;

		if(itemInst != null)
			itemInst.onUpdate();
	}
}
