package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemProjectorFieldModulatorStrength extends Item {
	public ItemProjectorFieldModulatorStrength(int i) {
		super(i);
		setIconIndex(65);
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