package OpenMDS.tile;

import OpenMDS.api.I6WayWrenchable;
import OpenMDS.api.IAttunementReader;
import OpenMDS.api.IDefenceAttachment;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileDefenceComputer extends TileEntity implements IAttunementReader,IInventory,I6WayWrenchable
{
	public int[] attunements = new int[0];
	public String[] priorities = new String[0];
	public boolean isAttached = false;
	public IDefenceAttachment attachedModule;
	public ItemStack[] inventory = new ItemStack[0];
	public ForgeDirection currentfacing;

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
		this.xCoord = par1NBTTagCompound.getInteger("x");
		this.yCoord = par1NBTTagCompound.getInteger("y");
		this.zCoord = par1NBTTagCompound.getInteger("z");
		this.isAttached = par1NBTTagCompound.getBoolean("attached");
		this.currentfacing = ForgeDirection.getOrientation(par1NBTTagCompound.getInteger("facing"));
		if(isAttached)
		{
			CheckForAttachment();
		}
	}
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, nbttagcompound);
	}

	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		this.readFromNBT(pkt.customParam1);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setInteger("x", this.xCoord);
		par1NBTTagCompound.setInteger("y", this.yCoord);
		par1NBTTagCompound.setInteger("z", this.zCoord);
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

	}
}
