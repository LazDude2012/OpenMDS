package chb.mods.mffs.client;


import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;

import org.lwjgl.opengl.GL11;

import chb.mods.mffs.common.ContainerProjektor;
import chb.mods.mffs.common.TileEntityProjector;
import chb.mods.mffs.common.network.NetworkHandler;

public class GuiProjector extends GuiContainer {
	private TileEntityProjector projector;

	public GuiProjector(EntityPlayer player,
			TileEntityProjector tileentity) {
		super(new ContainerProjektor(player, tileentity));
		projector = tileentity;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int textur = mc.renderEngine
				.getTexture("/chb/mods/mffs/sprites/GuiProjector.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);

		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
		int i1 = (79 * (projector.getLinkPower()/1000)) / (projector.getMaxlinkPower()/1000);
		drawTexturedModalRect(w + 8, k + 71, 176, 0, i1 + 1, 79);

		if (projector.getProjektor_Typ() != 0 ) {
			if (projector.getProjektor_Typ() != 7){
				drawTexturedModalRect(w + 119, k + 43, 177, 143, 16, 16); // distance
			}

			if (projector.getProjektor_Typ() != 4   && projector.getProjektor_Typ() != 2 ) {
				drawTexturedModalRect(w + 155, k + 43, 177, 143, 16, 16); // stärke
			}

			if (projector.getProjektor_Typ() == 1 || projector.getProjektor_Typ() == 2 || projector.getProjektor_Typ() == 6|| projector.getProjektor_Typ() == 7) {
				drawTexturedModalRect(w + 137, k + 8, 177, 143, 16, 16); // Focus
																		// up
				drawTexturedModalRect(w + 137, k + 42, 177, 143, 16, 16); // Focus
																			// down
				drawTexturedModalRect(w + 154, k + 25, 177, 143, 16, 16); // Focus
																			// rechts
				drawTexturedModalRect(w + 120, k + 25, 177, 143, 16, 16); // Focus
																			// left
			}

			if (projector.isOptioncamouflage()) {
				drawTexturedModalRect(w + 137, k + 25, 177, 143, 16, 16); // center
			}
		}
	}

	protected void actionPerformed(GuiButton guibutton) {
		NetworkHandler.fireTileEntityEvent(projector, guibutton.id);
	}

	public void initGui() {
		controlList.add(new GuigrafikButton(0, (width / 2) + 4, (height / 2) - 47,projector,1));
		controlList.add(new GuigrafikButton(1, (width / 2) + 67, (height / 2) -76,projector,2));

		super.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("MFFS Projector V2", 5, 5, 0x404040);
		fontRenderer.drawString(
				(new StringBuilder()).append(" ")
						.append(projector.getLinkPower()).toString(), 30, 60,
				0x404040);
	}
}
