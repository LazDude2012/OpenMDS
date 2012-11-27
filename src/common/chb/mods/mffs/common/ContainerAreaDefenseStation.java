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

public class ContainerAreaDefenseStation extends Container {
	private TileEntityAreaDefenseStation defstation;

	 private int linkPower;
    private int capacity;
	private EntityPlayer player;

	public ContainerAreaDefenseStation(EntityPlayer player,
			TileEntityAreaDefenseStation tileentity) {
		linkPower = -1;
		capacity = -1;

		defstation = tileentity;
		this.player = player;

		addSlotToContainer(new SlotHelper(defstation, 0, 10, 44)); //Power Link
		addSlotToContainer(new SlotHelper(defstation, 1, 10, 19)); //Security Link

		addSlotToContainer(new SlotHelper(defstation, 2, 128, 13)); //Defense mod
		addSlotToContainer(new SlotHelper(defstation, 3, 128, 44)); //Distance mod

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

	@Override
	public void updateCraftingResults() {
		super.updateCraftingResults();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			if (linkPower != defstation.getLinkPower()) {
				icrafting.sendProgressBarUpdate(this, 0,
						defstation.getLinkPower() & 0xffff);
				icrafting.sendProgressBarUpdate(this, 1,
						defstation.getLinkPower() >>> 16);
			}

            if(capacity != defstation.getCapacity())
            	icrafting.sendProgressBarUpdate(this, 2, defstation.getCapacity());
		}

		linkPower = defstation.getLinkPower();
		capacity = defstation.getCapacity();
	}

	public void updateProgressBar(int i, int j) {
		switch (i) {
		case 0:
			defstation
					.setLinkPower((defstation.getLinkPower() & 0xffff0000)
							| j);
			break;
		case 1:
			defstation
					.setLinkPower((defstation.getLinkPower() & 0xffff)
							| (j << 16));
			break;

        case 2:
        	defstation.setCapacity(j);
            break;
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return defstation.isUseableByPlayer(entityplayer);
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p,int i) {
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
				slot.onSlotChanged();
			} else {
				return null;
			}
		}
		return itemstack;
	}
}