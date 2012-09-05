package chb.mods.mffs.common;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemProjectorOptionBlockBreaker extends Item  {
	public ItemProjectorOptionBlockBreaker(int i) {
		super(i);
		setIconIndex(38);
		setMaxStackSize(1);
		setTabToDisplayOn(CreativeTabs.tabMaterials);
	}

	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable() {
		return false;
	}

    public void addInformation(ItemStack itemStack, List info)
    {
            String tooltip = "compatible to: <ALL>";
            info.add(tooltip);
    }
}
