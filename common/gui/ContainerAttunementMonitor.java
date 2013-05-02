package OpenMDS.common.gui;

import OpenMDS.tile.TileAttunementMonitor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 5/1/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContainerAttunementMonitor extends Container
{
	protected TileAttunementMonitor tile;

	public ContainerAttunementMonitor(InventoryPlayer playerInv, TileAttunementMonitor te)
	{
		this.tile = te;
		BindTileSlots();
		BindPlayerSlots(playerInv);
	}

	private void BindTileSlots()
	{
		for(int i = 0;i<5;i++)
		{
			Slot slot = new Slot(tile,i,80+i*18,70);
			addSlotToContainer(slot);
		}
	}

	private void BindPlayerSlots(InventoryPlayer playerInv)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				//START OF INVENTORY: 8 x, 93 y; SPACING 18
				addSlotToContainer(new Slot(playerInv, (9*i)+j+9, 8 + 18*j, 93+18*i));
			}
		}
		for(int i = 0;i<9;i++)
		{
			addSlotToContainer(new Slot(playerInv,i, 8+18*i, 151));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotnumber)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(slotnumber);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotnumber < 5)
			{
				if (!mergeItemStack(itemstack1, 5, 40, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (slotnumber >= 5 && slotnumber < 31)
			{
				if (!mergeItemStack(itemstack1, 31, 40, false))
				{
					return null;
				}
			}
			else if (slotnumber >= 31 && slotnumber < 40 && !mergeItemStack(itemstack1, 5, 31, false))
			{
				return null;
			}

			if (itemstack1.stackSize== 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, itemstack);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
}
