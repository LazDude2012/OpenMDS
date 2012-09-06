package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemDebugger extends  ItemMultitool  {
	protected StringBuffer info = new StringBuffer();

	public ItemDebugger(int i) {
		super(i,3);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getBlockTileEntity(x,y,z);



		return false;
	}


	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		return itemstack;
	}
}
