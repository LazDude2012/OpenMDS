package chb.mods.mffs.common;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerProjektor extends Container {
	private TileEntityProjector projectorentity;
	private int linkPower;
	private int maxlinkPower;
	private EntityPlayer player;

	public ContainerProjektor(EntityPlayer player,
			TileEntityProjector tileentity) {
		this.player = player;
		projectorentity = tileentity;
		linkPower = -1;
		maxlinkPower = -1;

		addSlotToContainer(new Slot(projectorentity, 0, 11, 41)); // Linkcard
		addSlotToContainer(new Slot(projectorentity, 1, 11, 18)); // Typ Slot

		addSlotToContainer(new Slot(projectorentity, 2, 120, 62)); // OptionSlot
		addSlotToContainer(new Slot(projectorentity, 3, 138, 62)); // OptionSlot
		addSlotToContainer(new Slot(projectorentity, 4, 156, 62)); // OptionSlot

		addSlotToContainer(new Slot(projectorentity, 6, 155, 44)); // StreghtSlot
		addSlotToContainer(new Slot(projectorentity, 5, 119, 44)); // DistancetSlot

		addSlotToContainer(new Slot(projectorentity, 7, 137, 8)); // Focus up
		addSlotToContainer(new Slot(projectorentity, 8, 137, 42)); // Focus down
		addSlotToContainer(new Slot(projectorentity, 9, 154, 25)); // Focus right
		addSlotToContainer(new Slot(projectorentity, 10, 120, 25)); // Focus left

		addSlotToContainer(new Slot(projectorentity, 11, 137, 25)); // Centerslot

		addSlotToContainer(new Slot(projectorentity, 12, 92, 18)); // SecCard

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
		return projectorentity.isUseableByPlayer(entityplayer);
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

	@Override
	public void updateCraftingResults() {
		super.updateCraftingResults();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			if (linkPower != projectorentity.getLinkPower()) {
				icrafting.updateCraftingInventoryInfo(this, 0,
						projectorentity.getLinkPower() & 0xffff);
				icrafting.updateCraftingInventoryInfo(this, 1,
						projectorentity.getLinkPower() >>> 16);
			}

			if (maxlinkPower != projectorentity.getMaxlinkPower()) {
				icrafting.updateCraftingInventoryInfo(this, 2,
						projectorentity.getMaxlinkPower() & 0xffff);
				icrafting.updateCraftingInventoryInfo(this, 3,
						projectorentity.getMaxlinkPower() >>> 16);
			}
		}

		linkPower = projectorentity.getLinkPower();
		maxlinkPower = projectorentity.getMaxlinkPower();
	}

	public void updateProgressBar(int i, int j) {
		switch (i) {
		case 0:
			projectorentity
					.setLinkPower((projectorentity.getLinkPower() & 0xffff0000)
							| j);
			break;
		case 1:
			projectorentity
					.setLinkPower((projectorentity.getLinkPower() & 0xffff)
							| (j << 16));
			break;

		case 2:
			projectorentity
					.setMaxlinkPower((projectorentity.getMaxlinkPower() & 0xffff0000)
							| j);
			break;
		case 3:
			projectorentity
					.setMaxlinkPower((projectorentity.getMaxlinkPower() & 0xffff)
							| (j << 16));
			break;
		}
	}
}