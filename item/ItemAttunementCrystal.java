package OpenMDS.item;

import OpenMDS.api.IAttunable;
import OpenMDS.util.Colours;
import OpenMDS.common.OpenMDS;
import OpenMDS.util.MDSUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ItemAttunementCrystal extends Item implements IAttunable
{
	int attunement = 0;
	public ItemAttunementCrystal(int i)
	{
		super(i);
		setMaxStackSize(1);
		setCreativeTab(OpenMDS.tabMDS);
		setUnlocalizedName("ItemAttunementCrystal");
		
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public boolean isDamageable()
	{
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List tooltips, boolean par4)
	{
		int attunement = stack.getItemDamage();
		Colours[] colours = MDSUtils.attunementToColours(attunement);
		tooltips.add(EnumChatFormatting.GRAY + colours[0].name().toLowerCase() + ", " +
		colours[1].name().toLowerCase()+ ", " + colours[2].name().toLowerCase());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister register)
	{
		this.iconIndex = register.registerIcon("OpenMDS:itemAttunementCrystal");
	}
}
