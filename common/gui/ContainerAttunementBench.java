package OpenMDS.common.gui;

import OpenMDS.tile.TileAttunementBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerAttunementBench extends Container
{
	protected TileAttunementBench tile;
	public ContainerAttunementBench(InventoryPlayer playerInv, TileAttunementBench te)
	{
		this.tile = te;
		BindTileSlots();
		BindPlayerSlots(playerInv);
	}

	private void BindTileSlots()
	{
		addSlotToContainer(new Slot(tile,0,134,38));
	}

	private void BindPlayerSlots(InventoryPlayer playerInv)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				//START OF INVENTORY: 8 x, 89 y; SPACING 18
				addSlotToContainer(new Slot(playerInv, (9*i)+j+1, 8 + 18*j, 89+18*i));
			}
		}
		for(int i = 0;i<9;i++)
		{
			addSlotToContainer(new Slot(playerInv,28 + i, 8+18*i, 147));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
}
