package OpenMDS.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

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
		return true;
	}
}
