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

import java.util.LinkedList;
import java.util.List;

import chb.mods.mffs.common.api.*;

import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergySink;
import ic2.api.INetworkClientTileEntityEventListener;
import ic2.api.INetworkDataProvider;
import ic2.api.INetworkTileEntityEventListener;
import ic2.api.INetworkUpdateListener;
import ic2.api.NetworkHelper;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityGenerator extends TileEntityMaschines implements
ISidedInventory, IEnergySink,INetworkDataProvider,INetworkUpdateListener,INetworkClientTileEntityEventListener{
	private ItemStack inventory[];
	private int forcepower;
	private int maxforcepower;
	private int transmitrange;
	private int Generator_ID;
	private int SecStation_ID;
	private boolean create;
	private boolean LinkedSecStation;
	private short linketprojektor;
	private boolean addedToEnergyNet;
	private boolean euinjektor;
	private int capacity;
	private int SwitchTyp;
	private boolean OnOffSwitch;

	public TileEntityGenerator() {
		inventory = new ItemStack[5];
		transmitrange = 8;
		SecStation_ID = 0;
		forcepower = 0;
		maxforcepower = 10000000;
		Generator_ID = 0;
		linketprojektor = 0;
		create = true;
		addedToEnergyNet = false;
		euinjektor = false;
		LinkedSecStation = false;
		capacity = 0;
		SwitchTyp = 0;
		OnOffSwitch = false;
	}

	public boolean getOnOffSwitch() {
		return OnOffSwitch;
	}

	public void setOnOffSwitch(boolean a) {
	   this.OnOffSwitch = a;
	}

	public int getswitchtyp() {
		return SwitchTyp;
	}

	public void setswitchtyp(int a) {
	   this.SwitchTyp = a;
	   NetworkHelper.updateTileEntityField(this, "SwitchTyp");
	}

	public int getcapacity(){
		return capacity;
	}

	public void setCapacity(int capacity){
		this.capacity = capacity;
		NetworkHelper.updateTileEntityField(this, "capacity");
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerGenerator(inventoryplayer.player, this);
	}

	public boolean isLinkedSecStation() {
		return LinkedSecStation;
	}

	public void setLinkedSecStation(boolean linkedSecStation) {
		LinkedSecStation = linkedSecStation;
	}

	public void setMaxforcepower(int maxforcepower) {
		this.maxforcepower = maxforcepower;
	}

	public int getMaxforcepower() {
		return maxforcepower;
	}

	public Short getLinketprojektor() {
		return linketprojektor;
	}

	public void setLinketprojektor(Short linketprojektor) {
		this.linketprojektor = linketprojektor;
		NetworkHelper.updateTileEntityField(this, "linketprojektor");
	}

	public int getForcepower() {
		return forcepower;
	}

	public void setForcepower(int f) {
		forcepower = f;
	}

	public int getSecStation_ID() {
		return SecStation_ID;
	}

	public void setTransmitrange(short transmitrange) {
		this.transmitrange = transmitrange;
		NetworkHelper.updateTileEntityField(this, "transmitrange");
	}

	public int getTransmitrange() {
		return transmitrange;
	}

	public int getGenerator_ID() {
		return Generator_ID;
	}

	public int getSizeInventory() {
		return inventory.length;
	}

	private void checkslots() {
		int stacksize = 0;
		short temp_transmitrange = 8;
		int temp_maxforcepower = 10000000;

		if (getStackInSlot(0) != null) {
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemupgradegencap) {
				temp_maxforcepower += (2000000 * getStackInSlot(0).stackSize);
			}
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemupgradegenrange) {
				stacksize = getStackInSlot(0).stackSize;
			}

			if (getStackInSlot(0).getItem() != ModularForceFieldSystem.MFFSitemupgradegencap
					&& getStackInSlot(0).getItem() != ModularForceFieldSystem.MFFSitemupgradegenrange) {
				dropplugins(0,this);
			}
		}

		if (getStackInSlot(1) != null) {
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemupgradegencap) {
				temp_maxforcepower += (2000000 * getStackInSlot(1).stackSize);
			}
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemupgradegenrange) {
				stacksize += getStackInSlot(1).stackSize;
			}

			if (getStackInSlot(1).getItem() != ModularForceFieldSystem.MFFSitemupgradegencap
					&& getStackInSlot(1).getItem() != ModularForceFieldSystem.MFFSitemupgradegenrange) {
				dropplugins(1,this);
			}
		}

		if (getStackInSlot(2) != null) {
			if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemUpgradegenEUInjektor) {
				euinjektor = true;
			}
			if (getStackInSlot(2).getItem() != ModularForceFieldSystem.MFFSitemUpgradegenEUInjektor) {
				dropplugins(2,this);
			}
		} else {
			euinjektor = false;
		}

		if (getStackInSlot(4) != null) {
			if (getStackInSlot(4).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard) {
				if (SecStation_ID != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(4)).getInteger("Secstation_ID")) {
					SecStation_ID = NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(4)).getInteger("Secstation_ID");
				}
				if (SecStation_ID == 0) {
					dropplugins(4,this);
				}
				if(Linkgrid.getWorldMap(worldObj)
				.getSecStation().get(this.getSecStation_ID())!=null)
				{
				setLinkedSecStation(true);
				}
				else
				{
				setLinkedSecStation(false);
				dropplugins(4,this);
				}
			} else {
			    	SecStation_ID = 0;
				    setLinkedSecStation(false);
					dropplugins(4,this);
			}
		} else {
			SecStation_ID = 0;
			setLinkedSecStation(false);
		}

		temp_transmitrange *= (stacksize + 1);

		if (this.getTransmitrange() != temp_transmitrange) {
			this.setTransmitrange(temp_transmitrange);
		}
		if (this.getMaxforcepower() != temp_maxforcepower) {
			this.setMaxforcepower(temp_maxforcepower);
		}
		if (this.getForcepower() > this.maxforcepower) {
			this.setForcepower(maxforcepower);
		}
	}

	public void dropplugins() {
		for (int a = 0; a < this.inventory.length; a++) {
			dropplugins(a,this);
		}
	}

	public void addtogrid() {
		Linkgrid.getWorldMap(worldObj).getGenerator()
				.put(getGenerator_ID(), this);
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getGenerator().remove(getGenerator_ID());
		dropplugins();
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		SwitchTyp = nbttagcompound.getInteger("SwitchTyp");
		OnOffSwitch = nbttagcompound.getBoolean("OnOffSwitch");
		forcepower = nbttagcompound.getInteger("forcepower");
		maxforcepower = nbttagcompound.getInteger("maxforcepower");
		transmitrange = nbttagcompound.getInteger("transmitrange");
		Generator_ID = nbttagcompound.getInteger("Generator_ID");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < inventory.length) {
				inventory[byte0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("SwitchTyp", SwitchTyp);
		nbttagcompound.setBoolean("OnOffSwitch", OnOffSwitch);
		nbttagcompound.setInteger("forcepower", forcepower);
		nbttagcompound.setInteger("maxforcepower", maxforcepower);
		nbttagcompound.setInteger("transmitrange", transmitrange);
		nbttagcompound.setInteger("Generator_ID", Generator_ID);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public void Energylost(int fpcost) {
		if (this.getForcepower() >= 0) {
			this.setForcepower(this.getForcepower() - fpcost);
		}
		if (this.getForcepower() < 0) {
			this.setForcepower(0);
		}
	}

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			if (create) {
				if (Generator_ID == 0) {
					Generator_ID = Linkgrid.getWorldMap(worldObj)
							.newGenerator_ID(this);
					Linkgrid.getWorldMap(worldObj).getGenerator()
							.put(getGenerator_ID(), this);
				} else {
					Linkgrid.getWorldMap(worldObj).getGenerator()
							.put(getGenerator_ID(), this);
				}
				create = false;
			}

			boolean powerdirekt = worldObj.isBlockGettingPowered(xCoord,
					yCoord, zCoord);
			boolean powerindrekt = worldObj.isBlockIndirectlyGettingPowered(
					xCoord, yCoord, zCoord);

			if(this.getswitchtyp()==0)
			{
		    this.setOnOffSwitch((powerdirekt || powerindrekt));
			}

			if (getOnOffSwitch() && euinjektor) {
				if (isActive() != true) {
					setActive(true);
				}
			} else {
				if (isActive() != false) {
					setActive(false);
				}
			}

			if (this.getTicker() == 10) {
				
				setLinketprojektor((short) Linkgrid.getWorldMap(worldObj)
						.condevisec(getGenerator_ID(), xCoord, yCoord, zCoord,
								getTransmitrange()));
				
				this.setCapacity(((getForcepower()/1000)*100)/(getMaxforcepower()/1000));
				checkslots();
				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));

			if (isActive() && getWrenchDropRate() <= 1) {
				setWrenchRate(0);
			}
			if (!isActive() && getWrenchDropRate() >= 0) {
				setWrenchRate(1);
			}

			if (!addedToEnergyNet) {
				EnergyNet.getForWorld(worldObj).addTileEntity(this);
				addedToEnergyNet = true;
			}
		} else {
			if (create) {
				NetworkHelper.requestInitialData(this);
				create = false;
			}
		}
	}

	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public int getInventoryStackLimit() {
		return 9;
	}

	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public String getInvName() {
		return "Generator";
	}

	public void closeChest() {
	}

	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	public void invalidate() {
		if (addedToEnergyNet) {
			EnergyNet.getForWorld(worldObj).removeTileEntity(this);
			addedToEnergyNet = false;
		}

		super.invalidate();
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction) {
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return addedToEnergyNet;
	}

	@Override
	public boolean demandsEnergy() {
		if (this.isActive() && euinjektor) {
			if (this.getForcepower() < this.getMaxforcepower()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		this.setForcepower(this.getForcepower() + (amount * 10));
		int j = 0;
		if (this.getForcepower() > this.getMaxforcepower()) {
			j = this.getForcepower() - this.getMaxforcepower();
			this.setForcepower(this.getMaxforcepower());
		}
		return (int) (j / 10);
	}

	@Override
	public void openChest() {
	}
	
	@Override
	public List<String> getNetworkedFields() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("active");
		NetworkedFields.add("facing");
		NetworkedFields.add("wrenchRate");
		NetworkedFields.add("SwitchTyp");
		NetworkedFields.add("linketprojektor");
		NetworkedFields.add("transmitrange");
		NetworkedFields.add("capacity");
		NetworkedFields.add("SwitchTyp");

		return NetworkedFields;
	}

	
	@Override
	public void onNetworkUpdate(String field) {
		if (field.equals("facing")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("active")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("linketprojektor")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("transmitrange")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("capacity")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		
	}
	


	public ItemStack[] getContents() {
		return inventory;
	}

	public void setMaxStackSize(int arg0) {
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public void onNetworkEvent(EntityPlayer player,int event) {
		switch(event)
		{
		case 0:
			if(this.getswitchtyp() == 0)
			{
				this.setswitchtyp(1);
			}else{
				this.setswitchtyp(0);
			}
		break;

		case 1:
			if(this.getOnOffSwitch())
			{
				this.setOnOffSwitch(false);
			}else{
				this.setOnOffSwitch(true);
			}
		break;
		}
	}


	


}
