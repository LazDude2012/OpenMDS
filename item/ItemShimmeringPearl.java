package OpenMDS.item;

import OpenMDS.common.OpenMDS;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemShimmeringPearl extends Item
{
	public ItemShimmeringPearl(int par1)
	{
		super(par1);
		setMaxStackSize(16);
		setUnlocalizedName("ItemShimmeringPearl");
		setCreativeTab(OpenMDS.tabMDS);
	}
	public void updateIcons(IconRegister register)
	{
		iconIndex = register.registerIcon("OpenMDS:itemShimmeringPearl");
	}
}
