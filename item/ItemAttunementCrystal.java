package OpenMDS.item;

import OpenMDS.api.IAttunable;
import OpenMDS.common.Colours;
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
		setUnlocalizedName("ItemAttunementCrystal");
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

	/**
	 * Takes an integer representing attunement, (0 - 4095) and returns the
	 * equivalent Colours array, (3 Colours objects).
	 * Thanks again to retep998 from EsperNet.
	 * @param attunement The integer, 0-4095 inclusive, to convert to colours.
	 * @return 3 Colours objects, neatly bound up in an array.
	 */
	public static Colours[] attunementToColours(int attunement)
	{
		int left = (attunement >> 8) & 0xf;
		int mid = (attunement >> 4) & 0xf;
		int right = (attunement) & 0xf;
		return new Colours[]{Colours.fromInt(left),Colours.fromInt(mid),Colours.fromInt(right)};
	}

	/**
	 * Takes an array of 3 Colours objects and returns the appropriate attunement integer.
	 * Thanks to retep998 from EsperNet; I owe you, buddy!
	 * @param colours The array of 3 Colours objects to convert.
	 * @return The int (0-4095) representing the attunement.
	 */
	public static int attunementFromColours(Colours[] colours)
	{
		int att = colours[0].ordinal() << 8 | colours[1].ordinal() << 4 | colours[2].ordinal();
		return att;
	}
}
