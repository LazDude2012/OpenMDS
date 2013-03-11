package OpenMDS.tile;

import OpenMDS.api.IAttunementReader;
import OpenMDS.api.IDefenceAttachment;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileDefenceComputer extends TileEntity implements IAttunementReader, IInventory
{
	public int[] attunements = new int[0];
	public String[] priorities = new String[0];
	public boolean isAttached = false;
	public IDefenceAttachment attachedModule;
	public ItemStack[] inventory = new ItemStack[0];

	@Override
	public int GetAttunementFromPriority(int priority)
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void SetAttunementForPriority(int attunement, int priority)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String GetPriorityName(int priority)
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void Attach(IDefenceAttachment par1)
	{
		attachedModule = par1;
		priorities = par1.GetPriorityList();

	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		if(inventory.length > var1)
		return inventory[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		if(inventory.length> var1)
			inventory[var1] = var2;
	}

	@Override
	public String getInvName()
	{
		return "Defence Computer";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void openChest()
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void closeChest()
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
