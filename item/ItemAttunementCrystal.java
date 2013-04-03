package OpenMDS.item;

import OpenMDS.api.IAttunable;
import OpenMDS.common.OpenMDS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemAttunementCrystal extends Item implements IAttunable
{
	int attunement = 0;
	public ItemAttunementCrystal(int i)
	{
		super(i);
		setMaxDamage(4096);
		setMaxStackSize(1);
		setCreativeTab(OpenMDS.tabMDS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister register)
	{
		this.iconIndex = register.registerIcon("OpenMDS:itemAttunementCrystal");
	}

	@Override
	public int GetAttunement()
	{
		return attunement;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void SetAttunement(int i)
	{
		attunement = i;
	}
}
