package chb.mods.mffs.common.network;

import java.util.List;

import net.minecraft.src.EntityPlayer;

public interface INetworkHandlerListener {
	
	public void onNetworkHandlerUpdate(String field);

	public List<String> geFieldsforUpdate();
	
	
}
