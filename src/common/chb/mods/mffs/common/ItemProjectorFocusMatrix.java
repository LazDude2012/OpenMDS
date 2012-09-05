package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemProjectorFocusMatrix extends Item  {
	public ItemProjectorFocusMatrix(int i) {
		super(i);
		setIconIndex(66);
		setMaxStackSize(64);
		setTabToDisplayOn(CreativeTabs.tabMaterials);
	}

	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable() {
		return false;
	}
}