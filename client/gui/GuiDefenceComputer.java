package OpenMDS.client.gui;

import OpenMDS.common.gui.ContainerDefenceComputer;
import OpenMDS.tile.TileDefenceComputer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiDefenceComputer extends GuiContainer {

	protected TileDefenceComputer tile_entity;
	private GuiButton settings;
	public GuiDefenceComputer(InventoryPlayer playerInv, TileDefenceComputer tile)
	{
		super(new ContainerDefenceComputer(playerInv, tile));
		tile_entity = tile;
		xSize = 256;
		ySize = 228;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		settings = new GuiButton(1,(this.width - 256) / 2 + 181, (this.height - 228) / 2 + 117, 64, 16, "Settings...");
		settings.enabled = tile_entity.isAttached;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString("Defence Computer",8,8,0x000000,false);
		for(int i = 0;i < 10; i++)
		{
		    //TEXT STARTS AT 32 x 42 y, SPACING 18 VERTICAL
			if(tile_entity.priorities.length <= i) break;
			fontRenderer.drawString(tile_entity.priorities[i],(i < 5 ? 32 : 149),(i % 5)* 18 + 24, 0x000000,false);
		}
		if(tile_entity.isAttached)
			fontRenderer.drawString("Attached: "+tile_entity.attachedModule.GetName(),32, 117,0x000000, false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i2, int j)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture("/mods/OpenMDS/textures/gui/guiDefenceComputer.png");
		// This is the x value of the picture, it will be used later
		int x = (width - xSize) / 2;
		// This is the y value of the picture, it will be used later
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		for(int i = 0; i < 10; i++)
		{
			if(!tile_entity.slots[i])
			{
				this.drawTexturedModalRect(x + (i < 5 ? 12 : 129),y + (i % 5)* 18 + 24, 0, 240, 16, 16);
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1){
			tile_entity.attachedModule.OpenGui(tile_entity.worldObj.getClosestPlayer(tile_entity.xCoord,tile_entity.yCoord,tile_entity.zCoord,4.0));
		}
	}
}
