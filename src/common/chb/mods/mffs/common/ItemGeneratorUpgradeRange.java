package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemGeneratorUpgradeRange extends Item  {
	public ItemGeneratorUpgradeRange(int i) {
		super(i);
		setIconIndex(33);
		setMaxStackSize(9);
		setTabToDisplayOn(CreativeTabs.tabMaterials);
	}

	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable() {
		return false;
	}
}