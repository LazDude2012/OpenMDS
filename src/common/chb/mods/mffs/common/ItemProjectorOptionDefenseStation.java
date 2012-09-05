package chb.mods.mffs.common;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemProjectorOptionDefenseStation extends Item  {
	public ItemProjectorOptionDefenseStation(int i) {
		super(i);
		setIconIndex(39);
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

    public void addInformation(ItemStack itemStack, List info)
    {
            String tooltip = "compatible to ProjectorTyp: <Cube><Adv.Cube><Sphere>";
            info.add(tooltip);
            tooltip = "compatible to Area Defense Station";
            info.add(tooltip);
    }
}