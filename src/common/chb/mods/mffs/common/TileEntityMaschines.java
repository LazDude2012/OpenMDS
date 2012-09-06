package chb.mods.mffs.common;

import chb.mods.mffs.common.api.*;
import chb.mods.mffs.common.network.NetworkHandler;
import net.minecraft.src.Container;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public abstract class TileEntityMaschines extends TileEntity implements IWrenchtool{
	private boolean active;
	private int Orientation;
	private boolean Wrenchcanwork;
	private short ticker;

	public TileEntityMaschines()

	{
		active = false;
		Orientation = -1;
		Wrenchcanwork = true;
		ticker = 0;
	}


	
	
	
	public  void dropplugins(int slot ,IInventory inventory ) {
		if (inventory.getStackInSlot(slot) != null) {
			if(inventory.getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink
		     || inventory.getStackInSlot(slot).getItem() instanceof ItemCardPowerLink
		     || inventory.getStackInSlot(slot).getItem() instanceof ItemCardPersonalID)
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj,
						(float) this.xCoord, (float) this.yCoord,
						(float) this.zCoord, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty,1)));
			}else
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj,
						(float) this.xCoord, (float) this.yCoord,
						(float) this.zCoord, inventory.getStackInSlot(slot)));
			}

			inventory.setInventorySlotContents(slot, null);
			this.onInventoryChanged();
		}
	}

	public abstract Container getContainer(InventoryPlayer inventoryplayer);

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		Orientation = nbttagcompound.getInteger("Orientation");
		active = nbttagcompound.getBoolean("active");
		Wrenchcanwork = nbttagcompound.getBoolean("Wrenchcanwork");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Orientation", Orientation);
		nbttagcompound.setBoolean("active", active);
		nbttagcompound.setBoolean("Wrenchcanwork", Wrenchcanwork);
	}
	@Override
	public boolean WrenchCanSetOrientation(EntityPlayer entityPlayer, int side) {
		
		   if(this instanceof TileEntityProjector)
		   {
				  if(((TileEntityProjector)this).getaccesstyp()==2)
				  {
					if(Linkgrid.getWorldMap(worldObj).getGenerator().get(((TileEntityProjector)this).getLinkGenerator_ID())!= null)
					{
					if(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getGenerator().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()) != null)
					{
						if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getGenerator().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
							return false;
						}
					}
				}
				  }
				 if(((TileEntityProjector)this).getaccesstyp()==3)
				 {
					if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this).getSecStation_ID()) != null)
					{
					if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this)).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
					}
				}
				  }
		   }

		   if(this instanceof TileEntitySecurityStation)
		   {
				if (!(((TileEntitySecurityStation)this).isAccessGranted(entityPlayer.username,
						ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}
		   }

		   if(this instanceof TileEntityGenerator)
		   {
			   if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityGenerator)this).getSecStation_ID()) != null)
			   {
					if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityGenerator)this)).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
					}
			   }
		   }

		if(this instanceof TileEntityAreaDefenseStation)
		   {
			   return false;
		   }
		
		if (!getWrenchcanwork()) {
			return false;
		}

		return true;
	}

	public short getTicker() {
		return ticker;
	}

	public void setTicker(short ticker) {
		this.ticker = ticker;
	}
	@Override
	public void setOrientation(int i) {
		Orientation = i;
		NetworkHandler.updateTileEntityField(this, "Orientation");
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean flag) {
		active = flag;
		NetworkHandler.updateTileEntityField(this, "active");
	}
	@Override
	public int getOrientation() {
		return Orientation;
	}
	@Override
	public void setWrenchcanwork(boolean b) {
		Wrenchcanwork = b;
		NetworkHandler.updateTileEntityField(this, "Wrenchcanwork");
	}
	@Override
	public boolean WrenchCanRemoveBlock(EntityPlayer entityPlayer) {
	   if(this instanceof TileEntityGenerator)
	   {
		 if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityGenerator)this).getSecStation_ID()) != null)
			{
		      if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityGenerator)this).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}
			}
		 }

	   if(this instanceof TileEntityProjector)
	   {
		  if(((TileEntityProjector)this).getaccesstyp()==2)
		  {
			if(Linkgrid.getWorldMap(worldObj).getGenerator().get(((TileEntityProjector)this).getLinkGenerator_ID())!= null)
			{
			if(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getGenerator().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getGenerator().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}
			}
		}
		  }
		 if(((TileEntityProjector)this).getaccesstyp()==3)
		 {
			if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this).getSecStation_ID()) != null)
			{
			if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this)).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
			return false;
			}
		}
		  }
	   }

	   if(this instanceof TileEntitySecurityStation)
	   {
			if (!(((TileEntitySecurityStation)this).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
				return false;
			}
	   }

	   if(this instanceof TileEntityAreaDefenseStation)
	   {
			if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityAreaDefenseStation)this).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityAreaDefenseStation)this).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}
			}
	   }

		if (!getWrenchcanwork()) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean getWrenchcanwork() {
		return  Wrenchcanwork;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}
	
	
}

