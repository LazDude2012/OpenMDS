package chb.mods.mffs.common.api;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;

public interface IWrenchtool {

	boolean WrenchCanSetOrientation(EntityPlayer entityPlayer, int side);
	

	int getOrientation();


	void setOrientation(int side);
	

	boolean WrenchCanRemoveBlock(EntityPlayer entityPlayer);
	

	boolean getWrenchcanwork();
	
	
	void setWrenchcanwork(boolean b);
	
	
	Block getBlocktoDrop();

}

