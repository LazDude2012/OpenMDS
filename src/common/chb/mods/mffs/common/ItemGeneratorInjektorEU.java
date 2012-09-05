package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemGeneratorInjektorEU extends Item {
	public ItemGeneratorInjektorEU(int i) {
		super(i);
		setIconIndex(37);
		setMaxStackSize(1);
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