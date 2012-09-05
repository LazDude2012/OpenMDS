package chb.mods.mffs.common.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;

import chb.mods.mffs.common.*;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToFindFieldException;

public class NetworkHandler implements IPacketHandler{
	
	private static final boolean DEBUG = false;
	
	@Override
	public void onPacketData(NetworkManager manager,Packet250CustomPayload packet, Player player) {

		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		int typ = dat.readInt(); 
		

		switch(typ)
		{
		case 1:
			
			String fieldname = dat.readUTF();
			World world = ModularForceFieldSystem.proxy.getClientWorld();
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

			 if(tileEntity instanceof TileEntityMaschines)
			 {
				 try{
				 Field f = ReflectionHelper.findField(TileEntityMaschines.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
				 }catch(Exception e)
				 {   
					 if(DEBUG)
					 System.out.println(e.getLocalizedMessage());
				 }
			 }
			
			 if(tileEntity instanceof TileEntityGenerator)
			 {
				 try{
				 Field f = ReflectionHelper.findField(TileEntityGenerator.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 }
			 }

			 
			 if(tileEntity instanceof TileEntityProjector)
			 {
				 try{
				 Field f = ReflectionHelper.findField(TileEntityProjector.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 }
			 }
			
			
		break;
		case 2:
			
			int dimension = dat.readInt() ;
			String daten = dat.readUTF(); 
			World serverworld = DimensionManager.getWorld(dimension);
			TileEntity servertileEntity = serverworld.getBlockTileEntity(x, y, z);
			
			for(String varname : daten.split("/"))
			{
				updateTileEntityField(servertileEntity,  varname);
			}
		break;
		
		case 3:
			int dimension2 = dat.readInt() ;
			int evt = dat.readInt() ;
			
			World serverworld2 = DimensionManager.getWorld(dimension2);
			TileEntity servertileEntity2 = serverworld2.getBlockTileEntity(x, y, z);
			
			if(servertileEntity2 instanceof INetworkHandlerEventListener)
			{
				((INetworkHandlerEventListener)servertileEntity2).onNetworkHandlerEvent(evt);
				
			}
			
			
		break;		
		}
		

		 
	}
	
	public static void reflectionsetvalue(Field f,TileEntity tileEntity,ByteArrayDataInput dat,String fieldname) 
	{
		try{
			 if(f.getType().equals(java.lang.Integer.TYPE)){f.setInt(tileEntity, Integer.parseInt(dat.readUTF()));}
			 if(f.getType().equals(java.lang.Boolean.TYPE)){f.setBoolean(tileEntity, Boolean.parseBoolean(dat.readUTF()));}
			 if(f.getType().equals(java.lang.Short.TYPE)){f.setShort(tileEntity, Short.parseShort(dat.readUTF()));}

			 if(tileEntity instanceof INetworkHandlerListener )
			 {
				 ((INetworkHandlerListener)tileEntity).onNetworkHandlerUpdate(fieldname);
			 }
		 }catch(Exception e)
		 {
			 if(DEBUG)
			 System.out.println(e.getLocalizedMessage());
		 }
	}
	
	
	
	
	

	
	public static void updateTileEntityField(TileEntity tileEntity, String varname)
	{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
			DataOutputStream dos = new DataOutputStream(bos);
			int x = tileEntity.xCoord;
			int y = tileEntity.yCoord;
			int z = tileEntity.zCoord;
			int typ = 1; // Server -> Client

			 try {
				dos.writeInt(x);
				dos.writeInt(y);
				dos.writeInt(z);
				dos.writeInt(typ);
				dos.writeUTF(varname);
				
				} catch (Exception e) {
					if(DEBUG)
					System.out.println(e.getLocalizedMessage());
				}
			
	 if(tileEntity instanceof TileEntityMaschines)
	  {
		 try {
		        Field f = ReflectionHelper.findField(TileEntityMaschines.class, varname);
		        f.get(tileEntity);
		    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
			} catch (Exception e) {
				if(DEBUG)
				System.out.println(e.getLocalizedMessage());
			}
	  }
			
			
	 if(tileEntity instanceof TileEntityProjector)
	 {
		
		 try {	
	        Field f = ReflectionHelper.findField(TileEntityProjector.class, varname);
	        f.get(tileEntity);
	    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
		} catch (Exception e) {
			if(DEBUG)
			System.out.println(e.getLocalizedMessage());
		}
	 }
	 
	 if(tileEntity instanceof TileEntityGenerator)
	 {
		 try {	
	        Field f = ReflectionHelper.findField(TileEntityGenerator.class, varname);
	        f.get(tileEntity);
	    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
		} catch (Exception e) {
			if(DEBUG)
			System.out.println(e.getLocalizedMessage());
		}
	 }
	 

	 
	 

			Packet250CustomPayload pkt = new Packet250CustomPayload();
			pkt.channel = "MFFS";
			pkt.data = bos.toByteArray();
			pkt.length = bos.size();
			pkt.isChunkDataPacket = true;

			PacketDispatcher.sendPacketToAllAround(x, y, z, 60, tileEntity.worldObj.getWorldInfo().getDimension(), pkt);
		}

	public static void requestInitialData(TileEntity tileEntity){
		

		if(tileEntity instanceof INetworkHandlerListener)
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
			DataOutputStream dos = new DataOutputStream(bos);
			int x = tileEntity.xCoord;
			int y = tileEntity.yCoord;
			int z = tileEntity.zCoord;
			int typ = 2; // Client -> Server
			int Dimension = tileEntity.worldObj.getWorldInfo().getDimension();
		   
			StringBuilder str = new StringBuilder();
			
			for(String fields : ((INetworkHandlerListener)tileEntity).geFieldsforUpdate())
			{
				str.append(fields);
				str.append("/");
			}
		
			
			 try {
				dos.writeInt(x);
				dos.writeInt(y);
				dos.writeInt(z);
				dos.writeInt(typ);
				dos.writeInt(Dimension);
				dos.writeUTF(str.toString());
		
				
				} catch (Exception e) {
					if(DEBUG)
					System.out.println(e.getLocalizedMessage());
				}
			
			Packet250CustomPayload pkt = new Packet250CustomPayload();
			pkt.channel = "MFFS";
			pkt.data = bos.toByteArray();
			pkt.length = bos.size();
			pkt.isChunkDataPacket = true;
			
			PacketDispatcher.sendPacketToServer(pkt);
		}
	}
	
	
	public static void fireTileEntityEvent(TileEntity tileEntity,int event){
		if(tileEntity instanceof INetworkHandlerEventListener)
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
			DataOutputStream dos = new DataOutputStream(bos);
			int x = tileEntity.xCoord;
			int y = tileEntity.yCoord;
			int z = tileEntity.zCoord;
			int typ = 3; // Client -> Server
			
			int Dimension = tileEntity.worldObj.getWorldInfo().getDimension();
			
			 try {
				dos.writeInt(x);
				dos.writeInt(y);
				dos.writeInt(z);
				dos.writeInt(typ);
				dos.writeInt(Dimension);
				dos.writeInt(event);
			
				} catch (Exception e) {
					if(DEBUG)
					System.out.println(e.getLocalizedMessage());
				}
			 
				Packet250CustomPayload pkt = new Packet250CustomPayload();
				pkt.channel = "MFFS";
				pkt.data = bos.toByteArray();
				pkt.length = bos.size();
				pkt.isChunkDataPacket = true;
				
				PacketDispatcher.sendPacketToServer(pkt);
			
		}
	}
	}
