package chb.mods.mffs.common;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerSecurityStation extends Container {
	private TileEntitySecurityStation SecStation;
	private EntityPlayer player;

	public ContainerSecurityStation(EntityPlayer player,
			TileEntitySecurityStation tileentity) {
		SecStation = tileentity;
		this.player = player;

		addSlotToContainer(new Slot(SecStation, 1, 43, 26)); // MasterCard
		addSlotToContainer(new Slot(SecStation, 2, 137, 26)); // Full Coder
		addSlotToContainer(new Slot(SecStation, 3, 119, 62)); // First Option Slot

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
		return SecStation.isUseableByPlayer(entityplayer);
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