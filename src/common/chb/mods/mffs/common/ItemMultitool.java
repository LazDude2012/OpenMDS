package chb.mods.mffs.common;

import java.util.List;

import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public abstract class ItemMultitool extends  Item  implements IElectricItem{
	private int typ;

	protected ItemMultitool(int id,int typ) {
		super(id);
		this.typ = typ;
		setIconIndex(typ);
		setMaxStackSize(1);
		setMaxDamage(20);
		setTabToDisplayOn(CreativeTabs.tabMaterials);
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}

    @Override
    public void addInformation(ItemStack itemStack, List info)
    {
        String tooltip = String.format( "%d EU/%d EU ",ElectricItem.discharge(itemStack, getMaxCharge(), 1, true, true),getMaxCharge());
        info.add(tooltip);
    }

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean isDamageable()
	{
	return true;
	}

	@Override
	public boolean canProvideEnergy() {
		return false;
	}

	@Override
	public int getChargedItemId() {
		return this.shiftedIndex;
	}

	@Override
	public int getEmptyItemId() {
		return this.shiftedIndex;
	}

	@Override
	public int getMaxCharge() {
		return 100000;
	}

	@Override
	public int getTier() {
		return 1;
	}

	@Override
	public int getTransferLimit() {
		return 5000;
	}

	@Override
	public abstract boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ);

	@Override
	public abstract ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer);
}
