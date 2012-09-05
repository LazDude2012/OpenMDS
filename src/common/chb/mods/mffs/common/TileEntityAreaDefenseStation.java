package chb.mods.mffs.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import chb.mods.mffs.common.api.*;
import chb.mods.mffs.common.network.INetworkHandlerListener;
import chb.mods.mffs.common.network.NetworkHandler;


import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityAreaDefenseStation extends TileEntityMaschines implements
ISidedInventory, INetworkHandlerListener {
	private ItemStack ProjektorItemStacks[];
	private int Defstation_ID;
	private int linkGenerator_ID;
	private int linkSecStation_ID;
	private int linkPower;
	private int maxlinkPower;
	private boolean linkSecStation;
	private boolean linkGenerator;
	private boolean create;
	private int distance;
	private boolean[] Typ = {false,false};
	private boolean canwork;

	public TileEntityAreaDefenseStation() {
		Random random = new Random();

		ProjektorItemStacks = new ItemStack[4];
		Defstation_ID = random.nextInt();
		linkGenerator_ID = 0;
		linkSecStation = false;
		linkGenerator = false;
		linkPower = 0;
		maxlinkPower = 1000000;
		create = true;
		linkSecStation_ID = 0;
		canwork = false;
	}

	// Start Getter AND Setter

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

	public boolean isLinkGenerator() {
		return linkGenerator;
	}

	public void setLinkGenerator(boolean linkGenerator) {
		this.linkGenerator = linkGenerator;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public int getMaxlinkPower() {
		return maxlinkPower;
	}

	public void setMaxlinkPower(int maxlinkPower) {
		this.maxlinkPower = maxlinkPower;
	}

	public int getSecStation_ID() {
		return linkSecStation_ID;
	}

	public void setSecStation_ID(int linkSecStation_ID) {
		this.linkSecStation_ID = linkSecStation_ID;
	}

	public boolean islinkSecStation() {
		return linkSecStation;
	}

	public void setlinkSecStation(boolean b) {
		this.linkSecStation = b;
		NetworkHandler.updateTileEntityField(this, "linkSecStation");
	}

	public int getLinkPower() {
		return linkPower;
	}

	public void setLinkPower(int linkPower) {
		this.linkPower = linkPower;
	}

	public int getLinkGenerator_ID() {
		return linkGenerator_ID;
	}

	public void setLinkGenerator_ID(int linkGenerator_ID) {
		this.linkGenerator_ID = linkGenerator_ID;
	}

	// End Getter AND Setter

	public void addtogrid() {
		Linkgrid.getWorldMap(worldObj).getDefStation()
				.put(Defstation_ID, this);
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
		if (getStackInSlot(0) != null) {
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemfc) {
				if (getLinkGenerator_ID() != Functions.getTAGfromItemstack(
						getStackInSlot(0)).getInteger("Generator_ID")) {
					setLinkGenerator_ID(Functions.getTAGfromItemstack(
							getStackInSlot(0)).getInteger("Generator_ID"));
					}

				if(Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID())!=null)
				{
				 int transmit =	Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).getTransmitrange();
				 int gen_x=Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).xCoord - this.xCoord;
			     int gen_y=Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).yCoord - this.yCoord;
			     int gen_z=Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).zCoord - this.zCoord;

				 if(Math.sqrt(gen_x * gen_x + gen_y * gen_y + gen_z * gen_z) <= transmit)
				 {
					 setLinkGenerator(true);
				 }
				 else
				 {
					setLinkGenerator(false);
					setLinkGenerator_ID(0);
				 }
				}
				else
				{
	 	    	setLinkGenerator(false);
				 setLinkGenerator_ID(0);
				dropplugins(0,this);
				}
			} else {
				if (getStackInSlot(0).getItem() != ModularForceFieldSystem.MFFSitemfc) {
					dropplugins(0,this);
				}
			}
		} else {
			setLinkGenerator(false);
			setLinkGenerator_ID(0);
		}

		if (getStackInSlot(1) != null) {
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard) {
				if (getSecStation_ID() != Functions.getTAGfromItemstack(
						getStackInSlot(1)).getInteger("Secstation_ID")) {
					setSecStation_ID(Functions.getTAGfromItemstack(
							getStackInSlot(1)).getInteger("Secstation_ID"));
				}
				if (getSecStation_ID() == 0) {
					dropplugins(1,this);
				}

				if(Linkgrid.getWorldMap(worldObj)
				.getSecStation().get(this.getSecStation_ID())!=null)
				{
					setlinkSecStation(true);
				}
				else
				{
					setlinkSecStation(false);
					dropplugins(1,this);
				}
			} else {
				setlinkSecStation(false);
				if (getStackInSlot(1).getItem() != ModularForceFieldSystem.MFFSItemSecLinkCard) {
					dropplugins(1,this);
				}
			}
		} else {
			setlinkSecStation(false);
			setSecStation_ID(0);
		}

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

		if(getStackInSlot(2).getItem() != ModularForceFieldSystem.MFFSProjectorOptionDefenceStation
		&& getStackInSlot(2).getItem() != ModularForceFieldSystem.MFFSProjectorOptionMoobEx		)
		{
			this.dropplugins(2, this);
			setCanwork(false);
		}
		}else{
			setCanwork(false);
		}

		if (getStackInSlot(3) != null) {
			if (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance) {
				setDistance(getStackInSlot(3).stackSize+1);
			} else {
				dropplugins(3,this);
			}
		} else {
			setDistance(0);
		}
	}

	// Stop Slot / Upgrades System

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			if (this.isCreate() && this.getLinkGenerator_ID() != 0) {
				addtogrid();
				checkslots();
				this.setCreate(false);
			}

			if (this.getLinkGenerator_ID() != 0) {
				this.setLinkGenerator(true);
				try {
					this.setLinkPower(Linkgrid.getWorldMap(worldObj)
							.getGenerator().get(this.getLinkGenerator_ID())
							.getForcepower());
					this.setMaxlinkPower(Linkgrid.getWorldMap(worldObj)
							.getGenerator().get(this.getLinkGenerator_ID())
							.getMaxforcepower());
				} catch (java.lang.NullPointerException ex) {
					this.setLinkGenerator(false);
					this.setLinkPower(0);
					this.setMaxlinkPower(1000000);
				}
			} else {
				this.setLinkGenerator(false);
				this.setLinkPower(0);
				this.setMaxlinkPower(1000000);
			}

			boolean securityStationneed = true;

			if(isOptionDefenceStation() && !islinkSecStation())
			{
				securityStationneed = false;
			}

			if ( isCanwork()
					&& securityStationneed
					&& this.isLinkGenerator()
					&& this.getLinkPower() > ModularForceFieldSystem.DefenseStationFPpeerAttack)

			{
				if (isActive() != true) {
					setActive(true);
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}
			if ( !isCanwork()

					|| !securityStationneed
					|| !this.isLinkGenerator()
					|| this.getLinkPower() < ModularForceFieldSystem.DefenseStationFPpeerAttack) {
				if (isActive() != false) {
					setActive(false);
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}

			if (isActive() && getSpannerwork()) {
				setSpannerwork(false);
			}
			if (!isActive() && !getSpannerwork()) {
				setSpannerwork(true);
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
			if (create) {
				NetworkHandler.requestInitialData(this);
				create = false;
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

	public int getInventoryStackLimit() {
		return 32;
	}

	public int getSizeInventory() {
		return ProjektorItemStacks.length;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerAreaDefenseStation(inventoryplayer.player, this);
	}

	@Override
	public List<String> geFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("linkSecStation");
		NetworkedFields.add("active");
		NetworkedFields.add("Orientation");
		NetworkedFields.add("Spannerwork");

		return NetworkedFields;
	}

	@Override
	public void onNetworkHandlerUpdate(String field) {
		if (field.equals("active")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
	}

	public ItemStack[] getContents() {
		return ProjektorItemStacks;
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
	public Block getBlocktoDrop() {
		
		return ModularForceFieldSystem.MFFSDefenceStation;
	}


}
