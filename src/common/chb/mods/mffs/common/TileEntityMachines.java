/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    Thunderdark - initial implementation
*/

package chb.mods.mffs.common;

import ic2.api.IWrenchable;
import ic2.api.NetworkHelper;
import net.minecraft.src.Container;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public abstract class TileEntityMachines extends TileEntity implements IWrenchable{
	
	private boolean active;
	private short facing;
	private float wrenchRate;
	private short ticker;

	public TileEntityMachines()

	{
		active = false;
		facing = -1;
		wrenchRate = 1;
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
		facing = nbttagcompound.getShort("facing");
		active = nbttagcompound.getBoolean("active");
		wrenchRate = nbttagcompound.getFloat("wrenchRate");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("facing", facing);
		nbttagcompound.setBoolean("active", active);
		nbttagcompound.setFloat("wrenchRate", wrenchRate);
	}
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		
		   if(side == facing)
		   {
			   return false;
		   }



		   if(this instanceof TileEntityProjector)
		   {
				  if(((TileEntityProjector)this).getaccesstyp()==2)
				  {
					  
					  
					if(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityProjector)this).getLinkGenerator_ID())!= null)
					{
					
					if(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()) != null)
					{
						if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
							return false;
						}
					}
				}
				  }
				 if(((TileEntityProjector)this).getaccesstyp()==3)
				 {
					 
					if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this).getSecStation_ID()) != null)
					{
					if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
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

		   if(this instanceof TileEntityCapacitor)
		   {
			   if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityCapacitor)this).getSecStation_ID()) != null)
			   {
					if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityCapacitor)this).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
					}
			   }
		   }
		   
		   
			  if(this instanceof TileEntityExtractor)
			  {
				if(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityExtractor)this).getLinkCapacitors_ID())!= null)
				{
				if(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityExtractor)this).getLinkCapacitors_ID()).getSecStation_ID()) != null)
				{
					if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityExtractor)this).getLinkCapacitors_ID()).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
						return false;
					}
				}
			}
			  }
		   
		   

		if(this instanceof TileEntityAreaDefenseStation)
		   {
			   return false;
		   }
		
				
		
		
		if (getWrenchDropRate() <= 0) {
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
	public void setFacing(short i) {
		facing = i;
		NetworkHelper.updateTileEntityField(this, "facing");
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean flag) {
		active = flag;
		NetworkHelper.updateTileEntityField(this, "active");
	}
	
	@Override
	public short getFacing() {
		return facing;
	}
	
	
	public void setWrenchRate(float i) {
		wrenchRate = i;
		NetworkHelper.updateTileEntityField(this, "wrenchRate");
	}
	
	@Override
	public float getWrenchDropRate() {
		return wrenchRate;
	}
	
	
	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		
		
		
	   if(this instanceof TileEntityCapacitor)
	   {
		 if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityCapacitor)this).getSecStation_ID()) != null)
			{
		      if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityCapacitor)this).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}
			}
		 }

	   if(this instanceof TileEntityProjector)
	   {
		  if(((TileEntityProjector)this).getaccesstyp()==2)
		  {
			if(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityProjector)this).getLinkGenerator_ID())!= null)
			{
			if(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityProjector)this).getLinkGenerator_ID()).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}
			}
		}
		  }
		 if(((TileEntityProjector)this).getaccesstyp()==3)
		 {
			if(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this).getSecStation_ID()) != null)
			{
			if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(((TileEntityProjector)this).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
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
	   
	   
		  if(this instanceof TileEntityExtractor)
		  {
			if(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityExtractor)this).getLinkCapacitors_ID())!= null)
			{
			if(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityExtractor)this).getLinkCapacitors_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(worldObj).getSecStation().get(Linkgrid.getWorldMap(worldObj).getCapacitor().get(((TileEntityExtractor)this).getLinkCapacitors_ID()).getSecStation_ID()).isAccessGranted(entityPlayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}
			}
		}
		  }
		  

		if (getWrenchDropRate() <= 0) {
			return false;
		}
		return true;
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

