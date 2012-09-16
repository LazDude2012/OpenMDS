/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    Thunderdark - initial implementation
*/

package chb.mods.mffs.client;


import ic2.api.NetworkHelper;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import chb.mods.mffs.common.ContainerCapacitor;
import chb.mods.mffs.common.TileEntityCapacitor;


public class GuiCapacitor extends GuiContainer {
	private TileEntityCapacitor Core;

	public GuiCapacitor(EntityPlayer player,
			TileEntityCapacitor tileentity) {
		super(new ContainerCapacitor(player, tileentity));
		Core = tileentity;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int textur = mc.renderEngine
				.getTexture("/chb/mods/mffs/sprites/GuiCapacitor.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
		int i1 = (79 * (Core.getForcepower()/1000)) / (Core.getMaxforcepower()/1000);
		drawTexturedModalRect(w + 8, k + 71, 176, 0, i1+1, 79);
	}
	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Force Energy Capacitor", 5, 5, 0x404040);
		fontRenderer.drawString("Force Energy", 15, 50, 0x404040);
		fontRenderer.drawString(
				(new StringBuilder()).append(" ").append(Core.getForcepower())
						.toString(), 30, 60, 0x404040);

		fontRenderer.drawString("transmit range:", 10, 20, 0x404040);
		fontRenderer.drawString(
				(new StringBuilder()).append(" ")
						.append(Core.getTransmitrange()).toString(), 90, 20,
				0x404040);
		fontRenderer.drawString("linked device:", 10, 35, 0x404040);
		fontRenderer.drawString(
				(new StringBuilder()).append(" ")
						.append(Core.getLinketprojektor()).toString(), 90, 35,
				0x404040);
	}

	protected void actionPerformed(GuiButton guibutton) {
		NetworkHelper.initiateClientTileEntityEvent(Core, guibutton.id);
	}

	public void initGui() {
		controlList.add(new GuigrafikButton(0, (width / 2) + 33, (height / 2) - 36,Core,2));

		super.initGui();
	}
}