package OpenMDS.client;

import OpenMDS.common.OpenMDS;
import OpenMDS.tile.TileDefenceComputer;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

@SideOnly(Side.CLIENT)
public class ClientPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if(packet.channel == "OpenMDS_TDC")
		{
			World world = OpenMDS.proxy.getClientWorld();
			TileDefenceComputer.HandlePacketBytes(packet.data,world);
		}
	}
}
