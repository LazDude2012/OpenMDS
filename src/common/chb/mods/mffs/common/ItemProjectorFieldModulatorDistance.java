package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemProjectorFieldModulatorDistance extends Item  {
	public ItemProjectorFieldModulatorDistance(int i) {
		super(i);
		setIconIndex(64);
		setMaxStackSize(64);
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