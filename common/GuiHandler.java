package OpenMDS.common;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
