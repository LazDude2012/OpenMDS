package OpenMDS.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class TabMDS extends CreativeTabs{
	public TabMDS(int par1, String par2Str)
	{
		super(par1, par2Str);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex() //The item it displays for your tab
	{
		return OpenMDS.itemAttunementCrystal.itemID; //For this we'll use the ruby
	}
	public String getTranslatedTabLabel()
	{
		return "OpenMDS"; //The name of the tab ingame
	}

}
