package OpenMDS.tile;

import OpenMDS.api.IAttunable;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeDirection;

import java.io.DataOutputStream;
import java.io.IOException;

public class TileAttunementBench extends TileEntity implements IInventory
{
	ItemStack inventory;

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inventory;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		--inventory.stackSize;
		return inventory;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return inventory;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		inventory = itemstack;
	}

	@Override
	public String getInvName()
	{
		return "Attunement Bench";
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
	public void openChest(){}

	@Override
	public void closeChest(){}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof IAttunable) return true;
		else return false;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 3, nbt);
	}

	public static Packet250CustomPayload GetCustomPacket(World world, int x, int y, int z, ItemStack stack)
	{
		try
		{
			ByteOutputStream bstream = new ByteOutputStream();
			DataOutputStream stream = new DataOutputStream(bstream);
			stream.writeInt(x);
			stream.writeInt(y);
			stream.writeInt(z);
			stream.writeInt(world.getWorldInfo().getDimension());
			stream.writeInt(stack.getItem().itemID);
			stream.writeInt(stack.getItemDamage());
			stream.writeInt(stack.stackSize);
			Packet250CustomPayload pkt = new Packet250CustomPayload();
			pkt.channel = "OpenMDS_TAB";
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

	public static void HandlePacketBytes(byte[] bytes)
	{
		ByteArrayDataInput badi = ByteStreams.newDataInput(bytes);
		int xloc = badi.readInt();
		int yloc = badi.readInt();
		int zloc = badi.readInt();
		int dim = badi.readInt();
		int id = badi.readInt();
		int meta = badi.readInt();
		int amount = badi.readInt();
		World world = DimensionManager.getWorld(dim);
		TileAttunementBench tile = (TileAttunementBench)world.getBlockTileEntity(xloc,yloc,zloc);
		tile.xCoord = xloc;
		tile.yCoord = yloc;
		tile.zCoord = zloc;
		tile.setInventorySlotContents(1, new ItemStack(id,amount,meta));
		world.markBlockForUpdate(xloc,yloc,zloc);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("hasitem",(inventory != null));
		if(inventory != null)
		{
			nbt.setInteger("itemid",inventory.getItem().itemID);
			nbt.setInteger("meta",inventory.getItemDamage());
			nbt.setInteger("amount",inventory.stackSize);
		}
	}
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.getBoolean("hasitem"))
		{
			int id = nbt.getInteger("itemid");
			int meta = nbt.getInteger("meta");
			int amount = nbt.getInteger("amount");
			inventory = new ItemStack(Item.itemsList[id], meta, amount);
		}
	}
}
