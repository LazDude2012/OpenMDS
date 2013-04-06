package OpenMDS.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.world.World;

public class CommonProxy
{
	public void registerRenderInfo()
	{

	}
	public World getClientWorld()
	{
		return null;
	}
	public World[] getServerWorlds()
	{
		return FMLServerHandler.instance().getServer().worldServers;
	}
}
