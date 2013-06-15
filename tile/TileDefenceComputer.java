package OpenMDS.tile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import OpenMDS.api.IAttunementStorage;
import OpenMDS.util.MDSUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import OpenMDS.api.I6WayWrenchable;
import OpenMDS.api.IDefenceAttachment;
import OpenMDS.common.OpenMDS;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class TileDefenceComputer extends TileEntity implements IAttunementStorage,IInventory,I6WayWrenchable
{
	public String[] priorities = new String[0];
	public boolean isAttached = false;
	public IDefenceAttachment attachedModule;
	public ItemStack[] inventory = new ItemStack[5];
	private ForgeDirection currentfacing;
	private boolean deferUpdate;
	public boolean[] slots = new boolean[10];
	private int ticksSinceUpdate = 0;

	@Override
	public int GetAttunementFromPriority(int priority)
	{
		if(inventory[priority] != null) return inventory[priority].getItemDamage();
		else return 0;
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
		isAttached = true;
		inventory = new ItemStack[priorities.length];
		attachedModule.Attach(this);
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
			ByteArrayOutputStream bstream = new ByteArrayOutputStream();
			DataOutputStream stream = new DataOutputStream(bstream);
			stream.writeInt(xCoord);
			stream.writeInt(yCoord);
			stream.writeInt(zCoord);
			stream.writeInt(isAttached ? 1 : 0);
			stream.writeInt(currentfacing.ordinal());
			stream.writeInt(inventory.length);
			for(int i = 0; i < inventory.length; i++)
			{
				stream.writeInt(inventory[i] != null ? inventory[i].getItem().itemID : 0);
				stream.writeInt(inventory[i] != null ? inventory[i].getItemDamage() : 0);
			}
			Packet250CustomPayload pkt = new Packet250CustomPayload();
			pkt.channel = "OpenMDS_TDC";
			pkt.data = bstream.toByteArray();
			pkt.length = bstream.size();
			pkt.isChunkDataPacket = true;
			stream.close();
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
		int invsize = badi.readInt();
		ItemStack[] stacks = new ItemStack[invsize];
		for(int i = 0;i<invsize;i++)
		{
			Item item = Item.itemsList[badi.readInt()];
			if(item != null)
			stacks[i]=new ItemStack(item,1,badi.readInt());
			else
				stacks[i] = null;
		}
		TileDefenceComputer tile = (TileDefenceComputer)world.getBlockTileEntity(xloc,yloc,zloc);
		tile.xCoord = xloc;
		tile.yCoord = yloc;
		tile.zCoord = zloc;
		tile.inventory = stacks;
		tile.RotateTo(facing);
		MDSUtils.CheckForAttachment(tile);
		world.markBlockForUpdate(xloc,yloc,zloc);
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
		inventory = new ItemStack[par1NBTTagCompound.getInteger("invsize")];
		NBTTagList tagList = par1NBTTagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
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
		NBTTagList itemList = new NBTTagList();
		par1NBTTagCompound.setInteger("invsize",this.getSizeInventory());
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];

			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		par1NBTTagCompound.setTag("Inventory",itemList);
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(inventory.length >= i){
			return inventory[i];
		}
		else throw new RuntimeException();
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		ItemStack retStack = inventory[i].splitStack(j);
		
		if(inventory[i].stackSize == 0)
			inventory = null;
		
		return retStack;
	}

	@Override
	public void updateEntity()
	{
		if(deferUpdate)
		{
			MDSUtils.CheckForAttachment(this);
			deferUpdate = false;
		}
		if(ticksSinceUpdate==20)
		{
			ticksSinceUpdate = 0;
			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if(inventory.length>= i)
		{
			return inventory[i];
		}
		throw new RuntimeException();
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(getSizeInventory() >= i)
			inventory[i]=itemstack;
		else throw new RuntimeException();
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
		MDSUtils.CheckForAttachment(this);
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
