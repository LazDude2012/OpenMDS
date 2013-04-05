package OpenMDS.client;

import OpenMDS.common.*;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy
{
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
