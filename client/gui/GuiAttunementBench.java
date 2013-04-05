package OpenMDS.client.gui;

import OpenMDS.common.gui.ContainerAttunementBench;
import OpenMDS.tile.TileAttunementBench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import org.lwjgl.opengl.GL11;

public class GuiAttunementBench extends GuiContainer {
	protected TileAttunementBench tile;
	public GuiAttunementBench(InventoryPlayer playerInv, TileAttunementBench te)
	{
		super(new ContainerAttunementBench(playerInv, te));
		this.tile=te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{

	}
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture("/mods/OpenMDS/textures/gui/guiDefenceComputer.png");
		// This is the x value of the picture, it will be used later
		int x = (width - xSize) / 2;
		// This is the y value of the picture, it will be used later
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
