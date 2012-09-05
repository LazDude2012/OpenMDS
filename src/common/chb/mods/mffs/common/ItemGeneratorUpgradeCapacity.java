package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemGeneratorUpgradeCapacity extends Item  {
	public ItemGeneratorUpgradeCapacity(int i) {
		super(i);
		setIconIndex(32);
		setMaxStackSize(9);
		setTabToDisplayOn(CreativeTabs.tabMaterials);
	}
	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}
	@Override
	public boolean isRepairable() {
		return false;
	}
}