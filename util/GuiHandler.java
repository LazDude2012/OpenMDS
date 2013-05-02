package OpenMDS.util;

import OpenMDS.client.gui.GuiAttunementBench;
import OpenMDS.client.gui.GuiAttunementMonitor;
import OpenMDS.common.OpenMDS;
import OpenMDS.common.gui.ContainerAttunementBench;
import OpenMDS.common.gui.ContainerAttunementMonitor;
import OpenMDS.tile.TileAttunementBench;
import OpenMDS.tile.TileAttunementMonitor;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			case OpenMDS.ATTUNEBENCH_GUI:
				return new ContainerAttunementBench(player.inventory,(TileAttunementBench)world.getBlockTileEntity(x,y,z));
			case OpenMDS.ATTUNEMONITOR_GUI:
				return new ContainerAttunementMonitor(player.inventory,(TileAttunementMonitor)world.getBlockTileEntity(x,y,z));
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			case OpenMDS.ATTUNEBENCH_GUI:
				return new GuiAttunementBench(player.inventory,(TileAttunementBench)world.getBlockTileEntity(x,y,z));
			case OpenMDS.ATTUNEMONITOR_GUI:
				return new GuiAttunementMonitor(player.inventory,(TileAttunementMonitor)world.getBlockTileEntity(x,y,z));
			default:
				return null;
		}
	}
}
