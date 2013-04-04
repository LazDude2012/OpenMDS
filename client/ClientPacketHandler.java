package OpenMDS.client;

import OpenMDS.tile.TileDefenceComputer;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 2/28/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */

@SideOnly(Side.CLIENT)
public class ClientPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if(packet.channel == "OpenMDS_TDC") TileDefenceComputer.HandleUpdatePacketBytes(packet.data);
	}
}
