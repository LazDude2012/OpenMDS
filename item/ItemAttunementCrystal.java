package OpenMDS.item;

import OpenMDS.api.IAttunable;
import net.minecraft.item.Item;

public class ItemAttunementCrystal extends Item implements IAttunable
{
	int attunement = 0;
	public ItemAttunementCrystal(int i)
	{
		super(i);
		setMaxDamage(4096);
		setMaxStackSize(1);
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
