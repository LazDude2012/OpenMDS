package OpenMDS.client.gui;

import OpenMDS.common.gui.ContainerAttunementMonitor;
import OpenMDS.tile.TileAttunementMonitor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiAttunementMonitor extends GuiContainer
{
	protected TileAttunementMonitor tile;
	private GuiButton increase, decrease;

	public GuiAttunementMonitor(InventoryPlayer playerInv, TileAttunementMonitor te)
	{
		super(new ContainerAttunementMonitor(playerInv, te));
		this.tile = te;
		this.xSize = 176;
		this.ySize = 171;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		super.initGui();
		// PARAMS: ID, x, y, width, height, text
		decrease = new GuiButton(1, (width - xSize)/2+21,(height-ySize)/2+35,42,20,"-");
		increase = new GuiButton(2, (width - xSize)/2+115,(height-ySize)/2+35,42,20,"+");
		decrease.enabled = (tile.radius > 1);
		increase.enabled = (tile.radius <= 50);
		buttonList.add(decrease);
		buttonList.add(increase);
	}

	protected void mouseClicked(int x, int y, int keycode)
	{
		super.mouseClicked(x,y,keycode);
		decrease.enabled = (tile.radius > 1);
		increase.enabled = (tile.radius <= 50);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture("/mods/OpenMDS/textures/gui/guiAttunementMonitor.png");
		// This is the x value of the picture, it will be used later
		int x = (width - xSize) / 2;
		// This is the y value of the picture, it will be used later
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		int radiuswidth = fontRenderer.getStringWidth(String.valueOf(tile.radius))/2;
		int radlabelwidth = fontRenderer.getStringWidth("Radius")/2;
		int invlabelwidth = fontRenderer.getStringWidth("Attunements");

		fontRenderer.drawString(String.valueOf(tile.radius),64+(25 - radiuswidth),41,0xffffff);
		fontRenderer.drawString("Radius",64+(25-radlabelwidth),22,0x000000);
		fontRenderer.drawString("Attunements",76 - invlabelwidth,74,0x000000);
	}

	@Override
	public void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
			case 1:
				tile.radius --;
				PacketDispatcher.sendPacketToServer(TileAttunementMonitor.GetCustomPacket(tile));
				break;
			case 2:
				tile.radius ++;
				PacketDispatcher.sendPacketToServer(TileAttunementMonitor.GetCustomPacket(tile));
				break;
		}
	}
}
