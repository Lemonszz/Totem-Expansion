package party.lemons.totemexpansion.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.totemexpansion.config.ModConstants;

/**
 * Created by Sam on 8/04/2018.
 */
public class PotionSpelunking extends Potion
{
	ResourceLocation loc = new ResourceLocation(ModConstants.MODID, "textures/gui/spelunky_icon.png");

	protected PotionSpelunking()
	{
		super(false, 0xf4d942);

		this.setPotionName("effects.totemexpansion.spelunking");
		this.setRegistryName(ModConstants.MODID, "spelunking");
	}

	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
	{
		drawIcon(x,y, 8, 8, effect, mc, 1);
	}

	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha)
	{
		drawIcon(x,y, 4, 4, effect, mc, alpha);
	}

	@SideOnly(Side.CLIENT)
	public void drawIcon(int x, int y, int offsetX, int offsetY, PotionEffect effect, Minecraft mc, float alpha)
	{
		GlStateManager.pushMatrix();
		mc.getTextureManager().bindTexture(loc);

		GlStateManager.color(1,1, 1, alpha);
		Gui.drawModalRectWithCustomSizedTexture(x + offsetX, y + offsetY, 0, 0, 18, 18, 18, 18);

		GlStateManager.popMatrix();
	}
}
