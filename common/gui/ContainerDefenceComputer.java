package OpenMDS.common.gui;

import OpenMDS.tile.TileDefenceComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerDefenceComputer extends Container
		{
protected TileDefenceComputer tile;

public ContainerDefenceComputer(InventoryPlayer playerInv, TileDefenceComputer te)
		{
		this.tile = te;
BindTileSlots();
BindPlayerSlots(playerInv);
}

private void BindTileSlots()
		{
		int numSlots = tile.inventory.length - 1;
for(int i = 0; i < 9; ++i)
		{
		if(numSlots < i) tile.slots[i]=false;
else
		{
		Slot temp = new Slot(tile,i,(i < 5 ? 12 : 129),(i % 5)* 18 + 24);
addSlotToContainer(temp);
tile.slots[i] = true;
}
		}

		}

private void BindPlayerSlots(InventoryPlayer playerInv)
		{
		for(int i = 0; i < 3; i++)
		{
		for(int j = 0; j < 9; j++)
		{
		//START OF INVENTORY: 48 x, 146 y; SPACING 18
		addSlotToContainer(new Slot(playerInv, (9*i)+j+9, 48 + 18*j, 146+18*i));
}
		}
		for(int i = 0;i<9;i++)
		{
		addSlotToContainer(new Slot(playerInv,i, 48+18*i, 204));
}
		}

@Override
public boolean canInteractWith(EntityPlayer entityplayer)
		{
		return true;
}
		}
