package OpenMDS.tile;

import OpenMDS.api.IAttunable;
import OpenMDS.common.OpenMDS;
import OpenMDS.util.MDSUtils;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class TileAttunementMonitor extends TileEntity implements IInventory
{
	public int radius = 16;
	public boolean isEmitting = false;
	public ItemStack[] inventory = new ItemStack[5];
	public TileAttunementMonitor()
	{
		super();
	}

	public void updateEntity()
	{
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.xCoord-radius,this.yCoord-radius,this.zCoord-radius,this.xCoord+radius,this.yCoord+radius,this.zCoord+radius);
		List playersInRange = worldObj.getEntitiesWithinAABB(EntityPlayer.class,aabb);
		for(Object playerobj : playersInRange)
		{
			EntityPlayer player = (EntityPlayer) playerobj;
			for(ItemStack stack : player.inventory.mainInventory)
			{
				if(stack != null && stack.getItem() instanceof IAttunable)
				{
					int attunement = stack.getItemDamage();
					for(ItemStack crystal : inventory)
					{
						if(crystal != null && attunement == crystal.getItemDamage())
						{
							isEmitting = true;
							UpdateWorld();
							return;
						}
					}
				}
			}
		}
		isEmitting = false;
		UpdateWorld();
	}
	public void UpdateWorld()
	{
		worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,OpenMDS.blockAttunementMonitor.blockID);
		worldObj.notifyBlocksOfNeighborChange(xCoord+1,yCoord,zCoord,OpenMDS.blockAttunementMonitor.blockID);
		worldObj.notifyBlocksOfNeighborChange(xCoord,yCoord-1,zCoord,OpenMDS.blockAttunementMonitor.blockID);
		worldObj.notifyBlocksOfNeighborChange(xCoord,yCoord+1,zCoord,OpenMDS.blockAttunementMonitor.blockID);
		worldObj.notifyBlocksOfNeighborChange(xCoord,yCoord,zCoord-1,OpenMDS.blockAttunementMonitor.blockID);
		worldObj.notifyBlocksOfNeighborChange(xCoord,yCoord,zCoord+1,OpenMDS.blockAttunementMonitor.blockID);
	}

	@Override
	public int getSizeInventory()
	{
		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		ItemStack retStack = inventory[i].splitStack(j);

		if(inventory[i].stackSize == 0)
			inventory[i] = null;

		return retStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return inventory[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		inventory[i]=itemstack;
	}

	@Override
	public String getInvName()
	{
		return "Attunement Monitor";
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

	}

	@Override
	public void closeChest()
	{

	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return (itemstack.getItem() instanceof IAttunable);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("radius",radius);
		NBTTagList invnbt = new NBTTagList();
		for (int i = 0; i < inventory.length; i++)
		{
			ItemStack stack = inventory[i];

			if (stack != null)
			{
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("slot", (byte) i);
				stack.writeToNBT(tag);
				invnbt.appendTag(tag);
			}
		}
		nbt.setTag("inventory",invnbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		radius = nbt.getInteger("radius");
		NBTTagList invnbt = nbt.getTagList("inventory");
		for (int i = 0; i < invnbt.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) invnbt.tagAt(i);

			byte slot = tag.getByte("slot");

			if (slot >= 0 && slot < inventory.length)
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
	}

	public static Packet250CustomPayload GetCustomPacket(TileAttunementMonitor tile)
	{
		try
		{
			ByteArrayOutputStream bstream = new ByteArrayOutputStream();
			DataOutputStream stream = new DataOutputStream(bstream);
			stream.writeInt(tile.xCoord);
			stream.writeInt(tile.yCoord);
			stream.writeInt(tile.zCoord);
			stream.writeInt(tile.worldObj.getWorldInfo().getDimension());
			stream.writeInt(tile.radius);
			stream.writeInt(tile.inventory.length);
			for(int i = 0; i < tile.inventory.length;i++)
			{
				if (tile.inventory[i] != null)
				{
					stream.writeInt(tile.inventory[i].getItem().itemID);
					stream.writeInt(tile.inventory[i].stackSize);
					stream.writeInt(tile.inventory[i].getItemDamage());
				} else
				{
					stream.writeInt(0);
					stream.writeInt(0);
					stream.writeInt(0);
				}
			}
			Packet250CustomPayload pkt = new Packet250CustomPayload();
			pkt.channel = "OpenMDS_TAM";
			pkt.data = bstream.toByteArray();
			pkt.length = bstream.size();
			pkt.isChunkDataPacket = true;
			stream.close();
			return pkt;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void HandlePacketBytes(byte[] bytes)
	{
		ByteArrayDataInput badi = ByteStreams.newDataInput(bytes);
		int x = badi.readInt();
		int y = badi.readInt();
		int z = badi.readInt();
		int dim = badi.readInt();
		int rad = badi.readInt();
		int invsize = badi.readInt();
		ItemStack[] stacks = new ItemStack[invsize];
		for(int i = 0; i < invsize; i++)
		{
			int itemid = badi.readInt();
			int amount = badi.readInt();
			int meta = badi.readInt();
			if(itemid == 0) continue;
			stacks[i] = new ItemStack(Item.itemsList[itemid],amount,meta);
		}
		TileAttunementMonitor tile = (TileAttunementMonitor) DimensionManager.getWorld(dim).getBlockTileEntity(x, y, z);
		tile.radius = rad;
		tile.inventory = stacks;
	}
}
