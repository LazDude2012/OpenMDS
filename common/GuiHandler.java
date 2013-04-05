package OpenMDS.common;

import OpenMDS.client.gui.GuiAttunementBench;
import OpenMDS.client.gui.GuiDefenceComputer;
import OpenMDS.common.gui.ContainerAttunementBench;
import OpenMDS.common.gui.ContainerDefenceComputer;
import OpenMDS.tile.TileAttunementBench;
import OpenMDS.tile.TileDefenceComputer;
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
			case OpenMDS.DEFENCECOMP_GUI:
				return new ContainerDefenceComputer(player.inventory,(TileDefenceComputer)world.getBlockTileEntity(x,y,z));
			case OpenMDS.ATTUNEMENT_GUI:
				return new ContainerAttunementBench(player.inventory,(TileAttunementBench)world.getBlockTileEntity(x,y,z));
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			case OpenMDS.DEFENCECOMP_GUI:
				return new GuiDefenceComputer(player.inventory,(TileDefenceComputer)world.getBlockTileEntity(x,y,z));
			case OpenMDS.ATTUNEMENT_GUI:
				return new GuiAttunementBench(player.inventory,(TileAttunementBench)world.getBlockTileEntity(x,y,z));
			default:
				return null;
		}
	}
}
