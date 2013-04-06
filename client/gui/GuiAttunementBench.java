package OpenMDS.client.gui;

import OpenMDS.api.IAttunable;
import OpenMDS.common.Colours;
import OpenMDS.common.gui.ContainerAttunementBench;
import OpenMDS.item.ItemAttunementCrystal;
import OpenMDS.tile.TileAttunementBench;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet132TileEntityData;
import org.lwjgl.opengl.GL11;

public class GuiAttunementBench extends GuiContainer {
	protected TileAttunementBench tile;
	private GuiButton leftcolour,rightcolour,centrecolour;
	private int left,centre,right;
	public GuiAttunementBench(InventoryPlayer playerInv, TileAttunementBench te)
	{
		super(new ContainerAttunementBench(playerInv, te));
		this.tile=te;
		this.xSize=176;
		this.ySize = 171;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		super.initGui();
		// PARAMS: ID, x, y, width, height, text
		leftcolour = new GuiButton(1, (width - xSize)/2+8,(height-ySize)/2+8,96,20,"Left: ");
		leftcolour.enabled = (tile.getStackInSlot(1)!= null);
		centrecolour = new GuiButton(2, (width-xSize)/2+8,(height-ySize)/2+33,96,20,"Centre: ");
		centrecolour.enabled = (tile.getStackInSlot(1)!= null);
		rightcolour = new GuiButton(3, (width-xSize)/2+8,(height-ySize)/2+57,96,20, "Right: ");
		rightcolour.enabled=(tile.getStackInSlot(1)!=null);
		buttonList.add(leftcolour);
		buttonList.add(centrecolour);
		buttonList.add(rightcolour);
	}
	protected void mouseClicked(int x, int y, int keycode)
	{
		super.mouseClicked(x,y,keycode);
		leftcolour.enabled = (tile.getStackInSlot(1)!= null);
		centrecolour.enabled = (tile.getStackInSlot(1)!= null);
		rightcolour.enabled=(tile.getStackInSlot(1)!=null);
		if(tile.getStackInSlot(1)== null || !(tile.getStackInSlot(1).getItem() instanceof IAttunable)) return;
		ItemStack stack = tile.getStackInSlot(1);
		Colours[] tempcolours = ItemAttunementCrystal.attunementToColours(stack.getItemDamage());
		leftcolour.displayString = "Left: "+ tempcolours[0].name().toLowerCase();
		centrecolour.displayString = "Centre: "+ tempcolours[1].name().toLowerCase();
		rightcolour.displayString = "Right: "+ tempcolours[2].name().toLowerCase();
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture("/mods/OpenMDS/textures/gui/guiAttunementBench.png");
		// This is the x value of the picture, it will be used later
		int x = (width - xSize) / 2;
		// This is the y value of the picture, it will be used later
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		fontRenderer.drawString("Attune",116,12,0x000000,false);
	}

	@Override
	public void actionPerformed(GuiButton button)
	{
		if(! (tile.getStackInSlot(1).getItem() instanceof IAttunable)) return;
		ItemStack stack = tile.getStackInSlot(1);
		Colours[] tempcolours = ItemAttunementCrystal.attunementToColours(stack.getItemDamage());
		left=tempcolours[0].ordinal();
		centre=tempcolours[1].ordinal();
		right=tempcolours[2].ordinal();
		switch(button.id)
		{
			case 1:
				if(left == 15) left = 0;
				else left++;
				break;
			case 2:
				if(centre == 15) centre = 0;
				else centre++;
				break;
			case 3:
				if(right == 15) right = 0;
				else right++;
				break;
			default:
				break;
		}
		tempcolours[0] = Colours.fromInt(left);
		tempcolours[1] = Colours.fromInt(centre);
		tempcolours[2] = Colours.fromInt(right);
		stack.setItemDamage(ItemAttunementCrystal.attunementFromColours(tempcolours));
		leftcolour.displayString = "Left: "+ tempcolours[0].name().toLowerCase();
		centrecolour.displayString = "Centre: "+ tempcolours[1].name().toLowerCase();
		rightcolour.displayString = "Right: "+ tempcolours[2].name().toLowerCase();
		tile.setInventorySlotContents(1,stack);
		PacketDispatcher.sendPacketToServer(tile.GetCustomPacket());
	}
}
