package chb.mods.mffs.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;
import chb.mods.mffs.common.ContainerSecurityStation;
import chb.mods.mffs.common.TileEntitySecurityStation;

public class GuiSecurityStation extends GuiContainer {
	private TileEntitySecurityStation tileEntity;

	public GuiSecurityStation(EntityPlayer player,
			TileEntitySecurityStation tileentity) {
		super(new ContainerSecurityStation(player, tileentity));
		this.tileEntity = tileentity;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int textur = mc.renderEngine
				.getTexture("/chb/mods/mffs/sprites/GuiSecstation.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
	}
	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("MFFS Security Station. V2", 5, 5, 0x404040);
		fontRenderer.drawString("Master ID-Card:", 15,45, 0x404040);
		fontRenderer.drawString(
				(new StringBuilder()).append(" ").append(tileEntity.getMainUser())
						.toString(), 15, 60, 0x404040);

		fontRenderer.drawString("Full Access", 115, 45, 0x404040);
	}
}
