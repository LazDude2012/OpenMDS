package OpenMDS.common;

import OpenMDS.client.ClientPacketHandler;
import OpenMDS.client.ClientProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="OpenMDS",version = "ALPHA 1", name="Open Modular Defence System")
@NetworkMod(clientSideRequired = true,serverSideRequired = true,channels="OpenMDS",
clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels="OpenMDS",packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels="OpenMDS",packetHandler = ServerPacketHandler.class))
public class OpenMDS
{
	@SidedProxy(clientSide="OpenMDS.client.ClientProxy",serverSide="OpenMDS.common.CommonProxy")
	public CommonProxy proxy = new CommonProxy();

	@Mod.Init
	public void Init(FMLInitializationEvent e)
	{
		NetworkRegistry.instance().registerGuiHandler(this,proxy);
	}
}
