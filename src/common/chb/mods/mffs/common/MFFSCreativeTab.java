package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class MFFSCreativeTab extends CreativeTabs{
	
	public MFFSCreativeTab (int par1, String par2Str) {
		super(par1, par2Str);
	}

	public ItemStack getIconItemStack() {
		return new ItemStack(ModularForceFieldSystem.MFFSCapacitor);
	}
		
}
