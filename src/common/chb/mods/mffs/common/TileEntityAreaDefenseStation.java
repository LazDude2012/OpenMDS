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

import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandlerClient;
import chb.mods.mffs.network.NetworkHandlerServer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityAreaDefenseStation extends TileEntityMachines implements
ISidedInventory,INetworkHandlerListener {
	private ItemStack ProjektorItemStacks[];
	private int Defstation_ID;
	private int linkPower;
	private int capacity;
	private boolean create;
	private int distance;
	private boolean[] Typ = {false,false};
	private boolean canwork;

	public TileEntityAreaDefenseStation() {
		Random random = new Random();

		ProjektorItemStacks = new ItemStack[4];
		Defstation_ID = 0;
		linkPower = 0;
		capacity = 0;
		create = true;
		canwork = false;
	}

	// Start Getter AND Setter

	public int getCapacity(){
		return capacity;
	}

	public void setCapacity(int Capacity){
		this.capacity = Capacity;
	}
	
	public boolean isOptionDefenceStation() {
		return Typ[0];
	}

	public void setOptionDefenceStation(boolean b) {
		Typ[0] = b;
	}

	public boolean isOptionMobDefense() {
		return Typ[1];
	}

	public void setOptionMobDefense(boolean b) {
		Typ[1] = b;
	}

	public boolean isCanwork() {
		return canwork;
	}

	public void setCanwork(boolean canwork) {
		this.canwork = canwork;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}


	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}



	public int getLinkPower() {
		return linkPower;
	}

	public void setLinkPower(int linkPower) {
		this.linkPower = linkPower;
	}
	
	
	public TileEntityAdvSecurityStation getLinkedSecurityStation()
	{
		
		if (getStackInSlot(1) != null)
		{
			if(getStackInSlot(1).getItem() instanceof ItemCardSecurityLink)
			{
				ItemCardSecurityLink card = (ItemCardSecurityLink) getStackInSlot(1).getItem();
				PointXYZ png = card.getCardTargetPoint(getStackInSlot(1));
				if(png != null)
				{
					if(png.dimensionId != worldObj.provider.dimensionId) return null;
				
					if(worldObj.getBlockTileEntity(png.X, png.Y, png.Z) instanceof TileEntityAdvSecurityStation)
				{
						TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation) worldObj.getBlockTileEntity(png.X, png.Y, png.Z);
				if (sec != null){
					
				  if(sec.getSecurtyStation_ID()== card.getTargetID("Secstation_ID",getStackInSlot(1))&&  card.getTargetID("Secstation_ID",getStackInSlot(1)) != 0 )
				  {
                    return sec;
				   }
				}
			  }
			  if(worldObj.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded)
					this.setInventorySlotContents(1, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
			}
		   }
		}
		
		return null;
	}
	
	
	
	public int getSecStation_ID(){
		TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
		if(sec != null)
			return sec.getSecurtyStation_ID();
		return 0;	
	}
	
	public TileEntityCapacitor getLinkedCapacitor()
	{
		
		if (getStackInSlot(0) != null)
		{
			if(getStackInSlot(0).getItem() instanceof ItemCardPowerLink)
			{
				ItemCardPowerLink card = (ItemCardPowerLink) getStackInSlot(0).getItem();
				PointXYZ png = card.getCardTargetPoint(getStackInSlot(0));
				if(png != null)
				{
					if(png.dimensionId != worldObj.provider.dimensionId) return null;
					if(worldObj.getBlockTileEntity(png.X, png.Y, png.Z) instanceof TileEntityCapacitor)
				{
				TileEntityCapacitor cap = (TileEntityCapacitor) worldObj.getBlockTileEntity(png.X, png.Y, png.Z);
				if (cap != null){
					
				  if(cap.getCapacitor_ID()== card.getTargetID("CapacitorID",getStackInSlot(0))&&  card.getTargetID("CapacitorID",getStackInSlot(0)) != 0 )
				  {
					if (cap.getTransmitRange() >=PointXYZ.distance(cap.getMaschinePoint(), this.getMaschinePoint()))
					{
						return cap;
					}else{
						return null;
					}
				   }
				}
			  }
			  if(worldObj.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded)
					this.setInventorySlotContents(0, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
			}
			}
		}
		
		return null;
	}
	
	public int getLinkCapacitor_ID(){
		TileEntityCapacitor cap = getLinkedCapacitor();
		if(cap != null)
			return cap.getCapacitor_ID();
		return 0;	
	}


	// End Getter AND Setter

	public void addtogrid() {
		if (Defstation_ID == 0) {
			Defstation_ID = Linkgrid.getWorldMap(worldObj)
					.newID(this);
		}
		Linkgrid.getWorldMap(worldObj).getDefStation().put(Defstation_ID, this);

		registerChunkLoading();
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getDefStation().remove(Defstation_ID);
		dropplugins();
	}

	// Start NBT Read/ Save

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		Defstation_ID = nbttagcompound.getInteger("Defstation_ID");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		ProjektorItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < ProjektorItemStacks.length) {
				ProjektorItemStacks[byte0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Defstation_ID", Defstation_ID);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < ProjektorItemStacks.length; i++) {
			if (ProjektorItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				ProjektorItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	// Stop NBT Read/ Save

	// Start Slot / Upgrades System

	public void dropplugins() {
		for (int a = 0; a < this.ProjektorItemStacks.length; a++) {
			dropplugins(a,this);
		}
	}

	public void checkslots() {

//		if (getStackInSlot(1) != null) {
//			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard) {
//				if (getSecStation_ID() != NBTTagCompoundHelper.getTAGfromItemstack(
//						getStackInSlot(1)).getInteger("Secstation_ID")) {
//					setSecStation_ID(NBTTagCompoundHelper.getTAGfromItemstack(
//							getStackInSlot(1)).getInteger("Secstation_ID"));
//				}
//				if (getSecStation_ID() == 0) {
//					dropplugins(1,this);
//				}
//
//				if(Linkgrid.getWorldMap(worldObj)
//				.getSecStation().get(this.getSecStation_ID())!=null)
//				{
//					if(!this.islinkSecStation())
//					    setlinkSecStation(true);
//				}
//				else
//				{
//					if(this.islinkSecStation())
//					    setlinkSecStation(false);
//					dropplugins(1,this);
//				}
//			} else {
//				if(this.islinkSecStation())
//				    setlinkSecStation(false);
//			}
//		} else {
//			if(this.islinkSecStation())
//			    setlinkSecStation(false);
//			setSecStation_ID(0);
//		}

		if(getStackInSlot(2)!= null)
		{
		if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSProjectorOptionDefenceStation) {
				this.setOptionDefenceStation(true);
				setCanwork(true);
            }else{
            	this.setOptionDefenceStation(false);
            }

		if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSProjectorOptionMoobEx) {
			this.setOptionMobDefense(true);
			setCanwork(true);
        }else{
        	this.setOptionMobDefense(false);
        }
		}else{
			setCanwork(false);
		}

		if (getStackInSlot(3) != null) {
			if (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance) {
				setDistance(getStackInSlot(3).stackSize+1);
			}
		} else {
			setDistance(0);
		}
	}

	// Stop Slot / Upgrades System

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			if (this.isCreate() && this.getLinkCapacitor_ID() != 0) {
				addtogrid();
				checkslots();
				this.setCreate(false);
			}

			if (getLinkCapacitor_ID() != 0) {

				TileEntityCapacitor remotecap = getLinkedCapacitor();
				if(remotecap != null)
				{
					setCapacity(remotecap.getCapacity());
					setLinkPower(remotecap.getForcePower());
				}else{
					setCapacity(0);
					setLinkPower(0);
				}
				
			} else {
				setCapacity(0);
				setLinkPower(0);
			}

			boolean securityStationneed = true;

			if(isOptionDefenceStation() && this.getSecStation_ID() == 0)
			{
				securityStationneed = false;
			}

			if ( isCanwork()
					&& securityStationneed
					&& getLinkedCapacitor() != null
					&& getLinkPower() > ModularForceFieldSystem.DefenseStationFPpeerAttack)

			{
				if (isActive() != true) {
					setActive(true);
					worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
				}
			}
			if ( !isCanwork()

					|| !securityStationneed
					|| getLinkedCapacitor() == null
					|| getLinkPower() < ModularForceFieldSystem.DefenseStationFPpeerAttack) {
				if (isActive() != false) {
					setActive(false);
					worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
				}
			}

			if (this.getTicker() == 20) {
				checkslots();
				if(this.isActive())
				{
					if(this.isOptionDefenceStation())
					{ ForceFieldOptions.DefenseStation(this, worldObj,"areadefense","human");}

					if(this.isOptionMobDefense())
					{ForceFieldOptions.DefenseStation(this, worldObj,"areadefense","mobs");}
				}
				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));
		} else {
			if(Defstation_ID==0)
			{
				if (this.getTicker() >= 20+random.nextInt(20)) {
					NetworkHandlerClient.requestInitialData(this,true);

					this.setTicker((short) 0);
				}

				this.setTicker((short) (this.getTicker() + 1));
			}
		}
	}

	public int Forcepowerneed(int blocks, boolean init) {
		int forcepower;
		forcepower = blocks * ModularForceFieldSystem.forcefieldblockcost;
		if (init) {
			forcepower = (forcepower * ModularForceFieldSystem.forcefieldblockcreatemodifier)
					+ (forcepower * 5);
		}
		return forcepower;
	}

	public ItemStack decrStackSize(int i, int j) {
		if (ProjektorItemStacks[i] != null) {
			if (ProjektorItemStacks[i].stackSize <= j) {
				ItemStack itemstack = ProjektorItemStacks[i];
				ProjektorItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = ProjektorItemStacks[i].splitStack(j);
			if (ProjektorItemStacks[i].stackSize == 0) {
				ProjektorItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		ProjektorItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistanceSq((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	public ItemStack getStackInSlot(int i) {
		return ProjektorItemStacks[i];
	}

	public String getInvName() {
		return "Defstation";
	}


	public int getSizeInventory() {
		return ProjektorItemStacks.length;
	}


	@Override
	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerAreaDefenseStation(inventoryplayer.player, this);
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
	public void onNetworkHandlerUpdate(String field){ 
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public List<String> getFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("linkSecStation");
		NetworkedFields.add("active");
		NetworkedFields.add("side");
		NetworkedFields.add("Defstation_ID");

		return NetworkedFields;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack, int Slot) {
		switch(Slot)
		{
		case 0:
		     if(par1ItemStack.getItem() instanceof ItemCardPowerLink)
	        	return true;
		break;
		case 1:
			if(par1ItemStack.getItem() instanceof ItemCardSecurityLink)
				return true;
		break;
		case 2:
			if(par1ItemStack.getItem() instanceof ItemProjectorOptionDefenseStation || par1ItemStack.getItem() instanceof ItemProjectorOptionMobDefence)
				return true;
		break;
		case 3:
			if(par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorDistance)
				return true;
		break;
		}

		return false;
	}

	@Override
	public int getSlotStackLimit(int Slot){
		switch(Slot){
		case 3: //Distance mod
			return 64;
		}
		return 1;
	}
}
