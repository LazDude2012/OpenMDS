package chb.mods.mffs.common;

import java.util.LinkedList;
import java.util.List;

import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergySink;
import ic2.api.INetworkDataProvider;
import ic2.api.INetworkUpdateListener;
import ic2.api.NetworkHelper;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityExtractor extends TileEntityMaschines implements ISidedInventory
,IEnergySink,INetworkDataProvider,INetworkUpdateListener{

	private ItemStack inventory[];
	private boolean addedToEnergyNet;
	private boolean create;
	private int Extractor_ID;
	private int WorkEnergy;
	private int MaxWorkEnergy;
	private int ForceEnergybuffer;
	private int MaxForceEnergyBuffer;
	private int WorkCylce;
	
	
	public TileEntityExtractor() {
		
		inventory = new ItemStack[1];
		create = true;
		addedToEnergyNet = false;
		Extractor_ID = 0;
		WorkEnergy = 0;
		MaxWorkEnergy = 2048;
		ForceEnergybuffer = 0;
		MaxForceEnergyBuffer = 1000000;
		WorkCylce = 0;
	}
	
	public void setWorkCylce(int i)
	{
		this.WorkCylce = i;
	}
	
	public int getWorkCylce(){
		return WorkCylce;
	}
	
	public int getWorkEnergy() {
		return WorkEnergy;
	}

	public void setWorkEnergy(int workEnergy) {
		WorkEnergy = workEnergy;
	}

	public int getMaxWorkEnergy() {
		return MaxWorkEnergy;
	}


	public void setMaxWorkEnergy(int maxWorkEnergy) {
		MaxWorkEnergy = maxWorkEnergy;
	}

	private boolean hasPowertoConvert()
	{
		if(WorkEnergy == MaxWorkEnergy)
		{
		 WorkEnergy = 0;
		 return true;
		}
		return false;
	}
	
	private boolean hasfreeForceEnergyStorage()
	{
		if(this.MaxForceEnergyBuffer > this.ForceEnergybuffer)
		 return true;
		return false;
	}
	
	
	private boolean hasStufftoConvert()
	{
		if (WorkCylce > 0)
		{
			return true;
			
		}else{
		
		if (getStackInSlot(0) != null) {
				if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemForcicium) {
				
			      WorkCylce = 10;
			      decrStackSize(0, 1);
			      return true;
					
				}
			}
		}
		
		return false;
	}
	
	public void updateEntity() {
		if (worldObj.isRemote == false) {
			
			
			if (this.getTicker() == 20) {

				if(this.hasfreeForceEnergyStorage())
				{
					if(this.hasStufftoConvert())
					{
						if(this.hasPowertoConvert())
						{
						  this.WorkEnergy = 0;
						  this.WorkCylce--;
						  this.ForceEnergybuffer += 100000;	
							
						}
					}
				}
				
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
		}else{
			
			if (create) {
				NetworkHelper.requestInitialData(this);
				create = false;
			}
		}
	}
	
	
	@Override
	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerForceEnergyExtractor(inventoryplayer.player,this);
	}

	
	@Override
	public boolean demandsEnergy() {
		if(this.MaxWorkEnergy != this.WorkEnergy)
		{
			return true;
		}
		return false;
	}


	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
	   WorkEnergy =+ amount;
	   return  WorkEnergy - MaxWorkEnergy;
	}
	
	@Override
	public void invalidate() {
		if (addedToEnergyNet) {
			EnergyNet.getForWorld(worldObj).removeTileEntity(this);
			addedToEnergyNet = false;
		}

		super.invalidate();
	}
	
	@Override
	public boolean isAddedToEnergyNet() {
		return addedToEnergyNet;
	}
	
	@Override
	public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction) {
		return true;
	}
	
	@Override
	public List<String> getNetworkedFields() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("active");
		NetworkedFields.add("wrenchRate");

		return NetworkedFields;
	}

	@Override
	public void onNetworkUpdate(String field) {

		if (field.equals("active")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}

	}
	
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		 Extractor_ID = nbttagcompound.getInteger("Extractor_ID");

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

		nbttagcompound.setInteger("Extractor_ID", Extractor_ID);

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
	
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public String getInvName() {
		return "Extractor";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public int getSizeInventory() {
		return inventory.length;
	}
	
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
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
	
	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}
	

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest(){ 
	}
	

	

}
