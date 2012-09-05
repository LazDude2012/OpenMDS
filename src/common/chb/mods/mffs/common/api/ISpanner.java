package chb.mods.mffs.common.api;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;

public interface ISpanner {

	boolean SpannerCanSetOrientation(EntityPlayer entityPlayer, int side);
	

	int getOrientation();


	void setOrientation(int side);
	

	boolean SpannerCanRemove(EntityPlayer entityPlayer);
	

	boolean getSpannerwork();
	
	void setSpannerwork(boolean b);
	
	Block getBlocktoDrop();

}

