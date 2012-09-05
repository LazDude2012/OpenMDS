package chb.mods.mffs.common;

import java.util.LinkedList;
import java.util.List;

import chb.mods.mffs.common.network.INetworkHandlerEventListener;
import chb.mods.mffs.common.network.INetworkHandlerListener;
import chb.mods.mffs.common.network.NetworkHandler;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;


public class TileEntityForceField extends TileEntity implements INetworkHandlerListener {
private int[] texturid = {180,180,180,180,180,180};
private boolean init = true;

	public TileEntityForceField() {
	}

	public int[] getTexturid()
	{
		return texturid;
	}

	public int getTexturid(int l)
	{
		return texturid[l];
	}

	public void  setTexturid(int[] texturid )
	{
		this.texturid = texturid;
		NetworkHandler.updateTileEntityField(this, "texturid");
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		texturid = nbttagcompound.getIntArray("texturid");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setIntArray("texturid", texturid);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote == false ) {
		if(init)
		{
			UpdateTextur();
		   init = false;
		}
		}else{
			if(init)
			{
				NetworkHandler.requestInitialData(this);
			   init = false;
			}
		}
	}

	public void UpdateTextur()
	{
		ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(worldObj).getForceFieldStackMap(WorldMap.Cordhash(this.xCoord, this.yCoord, this.zCoord));

		if(ffworldmap != null)
		{
			if(!ffworldmap.isEmpty())

			{
			 TileEntityProjector projector = Linkgrid.getWorldMap(worldObj).getProjektor().get(ffworldmap.getProjectorID());

				if(projector != null)
				{
					setTexturid(projector.getForcefieldtextur_id());
					worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}else{worldObj.setBlockWithNotify(this.xCoord, this.yCoord,this.zCoord, 0);}
	}else{
		worldObj.setBlockWithNotify(this.xCoord, this.yCoord,this.zCoord, 0);}
	}

	@Override
	public List<String> geFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("texturid");

		return NetworkedFields;
	}

	@Override
	public void onNetworkHandlerUpdate(String field) {
		if (field.equals("texturid")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
	}

	public ItemStack[] getContents() {
		return null;
	}

	public void setMaxStackSize(int arg0) {
	}



}
