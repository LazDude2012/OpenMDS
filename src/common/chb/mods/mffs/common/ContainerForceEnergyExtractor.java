/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    Thunderdark - initial implementation
*/

package chb.mods.mffs.common;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerForceEnergyExtractor extends Container {
	
	private TileEntityExtractor Extractor;
	private EntityPlayer player;
	private int WorkEnergy;
	private int WorkCylce;

	public ContainerForceEnergyExtractor(EntityPlayer player,
			TileEntityExtractor tileentity) {
		Extractor = tileentity;
		this.player = player;
		WorkEnergy = -1;
		WorkCylce = -1;

		addSlotToContainer(new Slot(Extractor, 0, 82, 6)); // Forcecium Input 

		int var3;

		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9,
						8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 142));
		}
	}

    public EntityPlayer getPlayer() {
    	return player;
    }

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return Extractor.isUseableByPlayer(entityplayer);
	}
	@Override
	public ItemStack transferStackInSlot(int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(itemstack1);
			} else {
				return null;
			}
		}
		return itemstack;
	}
	
	
	public void updateProgressBar(int i, int j) {
		switch (i) {
		case 1:
			Extractor.setWorkEnergy(j);
			break;

		case 0:
			Extractor.setWorkCylce(j);
			break;
       }
	}
	
	
	@Override
	public void updateCraftingResults() {
		super.updateCraftingResults();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			if (WorkEnergy != Extractor.getWorkEnergy()) {
				icrafting.updateCraftingInventoryInfo(this, 0,
						Extractor.getWorkEnergy());
			}
			if (WorkCylce != Extractor.getWorkCylce()) {
				icrafting.updateCraftingInventoryInfo(this, 1,
						Extractor.getWorkCylce());
			}

		}

		WorkEnergy = Extractor.getWorkEnergy();
		WorkCylce = Extractor.getWorkCylce();
	}
	
	
}