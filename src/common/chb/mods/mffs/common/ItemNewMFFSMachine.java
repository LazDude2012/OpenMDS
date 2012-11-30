package chb.mods.mffs.common;

import net.minecraft.src.ItemStack;

public class ItemNewMFFSMachine extends ItemMFFSBase {

	public ItemNewMFFSMachine(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int i) {
		return i;
	}
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return MFFSFutureMachines.values()[itemstack.getItemDamage()].name();
	}

}
