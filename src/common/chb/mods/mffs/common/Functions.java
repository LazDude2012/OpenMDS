package chb.mods.mffs.common;

import java.util.ArrayList;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class Functions {
	public static Block getBlock(int x,int y,int z,World world)
	{
		return Block.blocksList[world.getBlockId(x,y,z)];
	}

	public static ArrayList<ItemStack> getItemStackFromBlock(World world,
			int i, int j, int k) {
		Block block = Block.blocksList[world.getBlockId(i, j, k)];

		if (block == null)
			return null;

		int meta = world.getBlockMetadata(i, j, k);

		return block.getBlockDropped(world, i, j, k, meta, 0);
	}

	  public static World getClientWorld() {
      return FMLClientHandler.instance().getClient().theWorld;
}

	public static String getValuefor(String key,String category, Object defvalue)
	{
		if (ModularForceFieldSystem.MFFSconfig == null) {
	//		return String.valueOf(defvalue);
		}

		try {
		if(defvalue instanceof String)
		{
			String value = ModularForceFieldSystem.MFFSconfig.getOrCreateProperty(key, category, defvalue.toString()).value;
//			System.out.println(defvalue+"->"+value);
			return value;
		}

		if(defvalue instanceof Integer)
		{
			String value = ModularForceFieldSystem.MFFSconfig.getOrCreateIntProperty(key, category, (Integer) defvalue).value;
	//		System.out.println(defvalue+"->"+value);
			return value;
		}

	    if(defvalue instanceof Boolean)
		{
			String value = ModularForceFieldSystem.MFFSconfig.getOrCreateBooleanProperty(key, category,  (Boolean) defvalue).value;
	//		System.out.println(defvalue+"->"+value);
			return value;
		}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	    return String.valueOf(defvalue);
	}

	public static int getConfigIdFor(String s, int i, int typ) {
		if (ModularForceFieldSystem.MFFSconfig == null) {
			return i;
		}

		try {
			switch(typ)
			{
			case 1: // Block int
				return new Integer(ModularForceFieldSystem.MFFSconfig.getOrCreateIntProperty(s, "block", i).value);
			case 2: // general items
				return new Integer(ModularForceFieldSystem.MFFSconfig.getOrCreateIntProperty(s, "general", i).value);
			case 3: // Items int
				return new Integer(ModularForceFieldSystem.MFFSconfig.getOrCreateIntProperty(s, "item", i).value);
			}
		} catch (Exception exception) {
			System.out
					.println((new StringBuilder())
							.append("[ModularForceFieldSystem V2.0] Error while trying to access ID-List, config wasn't loaded properly!")
							.toString());
		}
		return i;
	}

	public static void ChattoPlayer(EntityPlayer player, String Message) {
		player.addChatMessage(Message);
	}

	public static NBTTagCompound getTAGfromItemstack(ItemStack itemStack) {
		if (itemStack != null) {
			NBTTagCompound tag = itemStack.getTagCompound();
			if (tag == null) {
				tag = new NBTTagCompound();
				itemStack.setTagCompound(tag);
			}
			return tag;
		}
		return null;
	}

	public static IInventory searchinventory(TileEntityMaschines tileEntity, World worldObj,boolean faceinginventory) {
		int facing = tileEntity.getOrientation();

		if(faceinginventory)
		{
			switch(facing)
			{
			case 0:facing=1;break;
			case 1:facing=0;break;
			case 2:facing=3;break;
			case 3:facing=2;break;
			case 4:facing=5;break;
			case 5:facing=4;break;
			}
		}

		switch (facing) {
		case 0:
			if (worldObj.getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord + 1, tileEntity.zCoord) instanceof IInventory) {
				TileEntity inventory = worldObj.getBlockTileEntity(tileEntity.xCoord,
						tileEntity.yCoord + 1, tileEntity.zCoord);
				return (IInventory) inventory;
			}
			break;
		case 1:
			if (worldObj.getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord - 1, tileEntity.zCoord) instanceof IInventory) {
				TileEntity inventory = worldObj.getBlockTileEntity(tileEntity.xCoord,
						tileEntity.yCoord - 1, tileEntity.zCoord);
				return (IInventory) inventory;
			}
			break;
		case 2:
			if (worldObj.getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord + 1) instanceof IInventory) {
				TileEntity inventory = worldObj.getBlockTileEntity(tileEntity.xCoord,
						tileEntity.yCoord, tileEntity.zCoord + 1);
				return (IInventory) inventory;
			}
			break;
		case 3:
			if (worldObj.getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord - 1) instanceof IInventory) {
				TileEntity inventory = worldObj.getBlockTileEntity(tileEntity.xCoord,
						tileEntity.yCoord, tileEntity.zCoord - 1);
				return (IInventory) inventory;
			}
			break;
		case 4:
			if (worldObj.getBlockTileEntity(tileEntity.xCoord + 1, tileEntity.yCoord, tileEntity.zCoord) instanceof IInventory) {
				TileEntity inventory = worldObj.getBlockTileEntity(tileEntity.xCoord + 1,
						tileEntity.yCoord, tileEntity.zCoord);
				return (IInventory) inventory;
			}
			break;
		case 5:
			if (worldObj.getBlockTileEntity(tileEntity.xCoord - 1, tileEntity.yCoord, tileEntity.zCoord) instanceof IInventory) {
				TileEntity inventory = worldObj.getBlockTileEntity(tileEntity.xCoord - 1,
						tileEntity.yCoord, tileEntity.zCoord);
				return (IInventory) inventory;
			}
			break;
		}
		return null;
	}
}
