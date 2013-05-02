package OpenMDS.common;

import OpenMDS.tile.TileAttunementBench;
import OpenMDS.tile.TileAttunementMonitor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class ServerPacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if (packet.channel == "OpenMDS_TAB")
			TileAttunementBench.HandlePacketBytes(packet.data);
		if(packet.channel == "OpenMDS_TAM")
			TileAttunementMonitor.HandlePacketBytes(packet.data);
	}
}
