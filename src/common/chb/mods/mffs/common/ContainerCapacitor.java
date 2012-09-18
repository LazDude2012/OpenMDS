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

public class ContainerCapacitor extends Container {
	private TileEntityCapacitor generatorentity;

	private int forcepower;
	private int maxforcepower;
	private short transmitrange;
	private short linketprojektor;
	private EntityPlayer player;

	public ContainerCapacitor(EntityPlayer player,
			TileEntityCapacitor tileentity) {
		forcepower = -1;
		maxforcepower = -1;
		transmitrange = -1;
		linketprojektor = -1;

		generatorentity = tileentity;
		this.player = player;

		addSlotToContainer(new Slot(generatorentity, 4, 154, 47));
		addSlotToContainer(new Slot(generatorentity, 0, 154, 5));
		addSlotToContainer(new Slot(generatorentity, 1, 154, 26));
		addSlotToContainer(new Slot(generatorentity, 2, 107, 47));
	

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

			if (transmitrange != generatorentity.getTransmitrange()) {
				icrafting.updateCraftingInventoryInfo(this, 0,
						generatorentity.getTransmitrange());
			}
			if (linketprojektor != generatorentity.getLinketprojektor()) {
				icrafting.updateCraftingInventoryInfo(this, 1,
						generatorentity.getLinketprojektor());
			}

			if (forcepower != generatorentity.getForcepower()) {
				icrafting.updateCraftingInventoryInfo(this, 2,
						generatorentity.getForcepower() & 0xffff);
				icrafting.updateCraftingInventoryInfo(this, 3,
						generatorentity.getForcepower() >>> 16);
			}

			if (maxforcepower != generatorentity.getMaxforcepower()) {
				icrafting.updateCraftingInventoryInfo(this, 4,
						generatorentity.getMaxforcepower() & 0xffff);
				icrafting.updateCraftingInventoryInfo(this, 5,
						generatorentity.getMaxforcepower() >>> 16);
			}
		}

		transmitrange = (short) generatorentity.getTransmitrange();
		linketprojektor = generatorentity.getLinketprojektor();
		forcepower = generatorentity.getForcepower();
		maxforcepower = generatorentity.getMaxforcepower();
	}

	public void updateProgressBar(int i, int j) {
		switch (i) {
		case 0:

			generatorentity.setTransmitrange((short) j);
			break;

		case 1:

			generatorentity.setLinketprojektor((short) j);
			break;

		case 2:
			generatorentity
					.setForcepower((generatorentity.getForcepower() & 0xffff0000)
							| j);
			break;
		case 3:
			generatorentity
					.setForcepower((generatorentity.getForcepower() & 0xffff)
							| (j << 16));
			break;

		case 4:
			generatorentity.setMaxforcepower((generatorentity
					.getMaxforcepower() & 0xffff0000) | j);
			break;
		case 5:
			generatorentity.setMaxforcepower((generatorentity
					.getMaxforcepower() & 0xffff) | (j << 16));
			break;
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return generatorentity.isUseableByPlayer(entityplayer);
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
}