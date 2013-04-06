package OpenMDS.tile;

import OpenMDS.api.I6WayWrenchable;
import OpenMDS.api.IAttunementReader;
import OpenMDS.api.IDefenceAttachment;
import OpenMDS.common.OpenMDS;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.io.DataOutputStream;
import java.io.IOException;

public class TileDefenceComputer extends TileEntity implements IAttunementReader,IInventory,I6WayWrenchable
{
	public int[] attunements = new int[0];
	public String[] priorities = new String[0];
	public boolean isAttached = false;
	public IDefenceAttachment attachedModule;
	public ItemStack[] inventory = new ItemStack[5];
	private ForgeDirection currentfacing;
	private boolean deferUpdate;
	public boolean[] slots = new boolean[10];

	@Override
	public int GetAttunementFromPriority(int priority)
	{
		return attunements[priority];
	}

	@Override
	public void SetAttunementForPriority(int attunement, int priority)
	{
		attunements[priority] = attunement;
	}

	@Override
	public String GetPriorityName(int priority)
	{
		return priorities[priority];
	}

	public void Attach(IDefenceAttachment par1)
	{
		attachedModule = par1;
		priorities = par1.GetPriorityList();
		attunements = new int[priorities.length];
		isAttached = true;
		inventory = new ItemStack[priorities.length];
	}

	public void CheckForAttachment()
	{
		for(int x = xCoord-1;x <= xCoord+1; x++)
		{
			for(int y = yCoord-1;y <= yCoord +1; y++)
			{
				for(int z = zCoord-1; z <= zCoord +1; z++)
				{
					TileEntity te = worldObj.getBlockTileEntity(x,y,z);
					if(te instanceof IDefenceAttachment)
					{
						Attach((IDefenceAttachment)te);
					}
				}
			}
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		this.isAttached = par1NBTTagCompound.getBoolean("attached");
		this.currentfacing = ForgeDirection.getOrientation(par1NBTTagCompound.getInteger("facing"));
		if(isAttached) this.deferUpdate = true;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return this.GetCustomPacket();
	}

	public Packet250CustomPayload GetCustomPacket()
	{
		try
		{
			ByteOutputStream bstream = new ByteOutputStream();
			DataOutputStream stream = new DataOutputStream(bstream);
			stream.writeInt(xCoord);
			stream.writeInt(yCoord);
			stream.writeInt(zCoord);
			stream.writeInt(isAttached ? 1 : 0);
			stream.writeInt(currentfacing.ordinal());
			Packet250CustomPayload pkt = new Packet250CustomPayload();
			pkt.channel = "OpenMDS_TDC";
			pkt.data = bstream.toByteArray();
			pkt.length = bstream.size();
			pkt.isChunkDataPacket = true;
			return pkt;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void HandlePacketBytes(byte[] bytes, World world)
	{
		ByteArrayDataInput badi = ByteStreams.newDataInput(bytes);
		int xloc = badi.readInt();
		int yloc = badi.readInt();
		int zloc = badi.readInt();
		boolean attch = (badi.readInt()==1);
		ForgeDirection facing = ForgeDirection.getOrientation(badi.readInt());
		TileDefenceComputer tile = (TileDefenceComputer)world.getBlockTileEntity(xloc,yloc,zloc);
		tile.xCoord = xloc;
		tile.yCoord = yloc;
		tile.zCoord = zloc;
		tile.isAttached = attch;
		tile.RotateTo(facing);
		world.markBlockForUpdate(xloc,yloc,zloc);
	}
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("attached",this.isAttached);
		par1NBTTagCompound.setInteger("facing",this.currentfacing.ordinal());
	}

	@Override
	public int getSizeInventory()
	{
		return priorities.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(inventory.length >= i){
			return inventory[i];
		}
		else throw new RuntimeException("The hell?");
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(inventory.length < i) throw new RuntimeException("The hell?");
		ItemStack temp = inventory[i];
		if(temp.stackSize - j <= 0){
			inventory[i].stackSize = 0;
			return inventory[i];
		}else{
			inventory[i].stackSize -= j;
			return inventory[i];
		}
	}

	@Override
	public void updateEntity()
	{
		if(deferUpdate)
		{
			CheckForAttachment();
			deferUpdate = false;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if(inventory.length>= i)
		{
			return inventory[i];
		}
		throw new RuntimeException("The hell?");
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(inventory.length>= i)
			inventory[i]=itemstack;
		else throw new RuntimeException("The hell?");
	}

	@Override
	public String getInvName()
	{
		if(isAttached) return "Defence Computer: "+ attachedModule.GetName();
		else return "Defence Computer";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest()
	{
		CheckForAttachment();
	}

	@Override
	public void closeChest() {}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}

	@Override
	public void RotateTo(ForgeDirection direction)
	{
		currentfacing = direction;
	}

	@Override
	public ForgeDirection GetCurrentFacing()
	{
		return currentfacing;
	}

	public void OpenGui(World world, EntityPlayer player, int x, int y, int z)
	{
		player.openGui(OpenMDS.instance,OpenMDS.DEFENCECOMP_GUI, world,x,y,z);
	}
}
