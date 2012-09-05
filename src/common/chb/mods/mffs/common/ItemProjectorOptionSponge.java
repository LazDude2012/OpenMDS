package chb.mods.mffs.common;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemProjectorOptionSponge extends Item  {
	public ItemProjectorOptionSponge(int i) {
		super(i);
		setIconIndex(35);
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
            String tooltip = "compatible to: <Tube><Cube><Adv.Cube><Sphere>";
            info.add(tooltip);
    }
}