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
	
	public static final int MAXWORKCYLCE = 125;

	private ItemStack inventory[];
	private boolean addedToEnergyNet;
	private boolean create;
	private int Extractor_ID;
	private int WorkEnergy;
	private int MaxWorkEnergy;
	private int ForceEnergybuffer;
	private int MaxForceEnergyBuffer;
	private int WorkCylce;
	private int LinkCapacitor_ID;
	
	
	public TileEntityExtractor() {
		
		inventory = new ItemStack[2];
		create = true;
		addedToEnergyNet = false;
		Extractor_ID = 0;
		LinkCapacitor_ID = 0;
		WorkEnergy = 0;
		MaxWorkEnergy = 4000;
		ForceEnergybuffer = 0;
		MaxForceEnergyBuffer = 1000000;
		WorkCylce = 0;
	}
	
	
	
	public int getLinkCapacitors_ID() {
		return LinkCapacitor_ID;
	}
	
	public void setLinkCapacitor_ID(int id){
		this.LinkCapacitor_ID = id;
	}
	
	public int getExtractor_ID() {
		return Extractor_ID;
	}




	public int getMaxForceEnergyBuffer() {
		return MaxForceEnergyBuffer;
	}



	public void setMaxForceEnergyBuffer(int maxForceEnergyBuffer) {
		MaxForceEnergyBuffer = maxForceEnergyBuffer;
	}
	
	public int getForceEnergybuffer() {
		return ForceEnergybuffer;
	}



	public void setForceEnergybuffer(int forceEnergybuffer) {
		ForceEnergybuffer = forceEnergybuffer;
		NetworkHelper.updateTileEntityField(this, "ForceEnergybuffer");
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
	
	
	public void addtogrid() {
		Linkgrid.getWorldMap(worldObj).getExtractor()
				.put(getExtractor_ID(), this);
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getExtractor().remove(getExtractor_ID());
		dropplugins();
	}
	
	public void dropplugins() {
		for (int a = 0; a < this.inventory.length; a++) {
			dropplugins(a,this);
		}
	}
	
	
	public void checkslots(boolean init) {
		if (getStackInSlot(1) != null) {
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemfc) {
				if (getLinkCapacitors_ID() != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(1)).getInteger("Generator_ID")) {
					setLinkCapacitor_ID(NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(1)).getInteger("Generator_ID"));
				}

				if (Linkgrid.getWorldMap(worldObj).getCapacitor()
						.get(this.getLinkCapacitors_ID()) != null) {
					int transmit = Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitors_ID())
							.getTransmitrange();
					int gen_x = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitors_ID()).xCoord
							- this.xCoord;
					int gen_y = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitors_ID()).yCoord
							- this.yCoord;
					int gen_z = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitors_ID()).zCoord
							- this.zCoord;

					if (Math.sqrt(gen_x * gen_x + gen_y * gen_y + gen_z * gen_z) <= transmit) {

					} else {
						setLinkCapacitor_ID(0);
					}
				} else {
					setLinkCapacitor_ID(0);
					if (!init) {
						dropplugins(1,this);
					}
				}
			} else {
				if (getStackInSlot(1).getItem() != ModularForceFieldSystem.MFFSitemfc) {
					dropplugins(1,this);
				}
			}
		} else {
		    this.setLinkCapacitor_ID(0);
		}
	}
	
	
	private boolean hasPowertoConvert()
	{
		if(WorkEnergy == MaxWorkEnergy)
		{
		 setWorkEnergy(0);
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
				
		    	  setWorkCylce(MAXWORKCYLCE);
			      decrStackSize(0, 1);
			      return true;
					
				}
			}
		}
		
		return false;
	}
	
	public void transferForceEnergy()
	{
		if(this.getForceEnergybuffer() >= 8000)
		{	
		if(LinkCapacitor_ID!=0)
		{
			TileEntityCapacitor Cap =Linkgrid.getWorldMap(worldObj).getCapacitor().get(LinkCapacitor_ID);	
			if(Cap != null)
			{
				if((Cap.getForcepower() + 8000) < Cap.getMaxforcepower())
				{
					Cap.setForcepower(Cap.getForcepower() + 8000);
					setForceEnergybuffer(this.getForceEnergybuffer()-8000);
				}
			}
		}
		}
	}
	
	public void updateEntity() {
		if (worldObj.isRemote == false) {
			
			if (create) {
				if (Extractor_ID == 0) {
					Extractor_ID = Linkgrid.getWorldMap(worldObj)
							.newExtractor_ID(this);
				}
				addtogrid();
				checkslots(true);
				create = false;
			}
		
			if (this.getTicker() == 20) {

				checkslots(false);
				if(this.hasfreeForceEnergyStorage() && this.hasStufftoConvert() && this.hasPowertoConvert())
				{
				
							if (isActive() != true) {
								setActive(true);
							}
							
						  setWorkEnergy(0);
						  setWorkCylce(getWorkCylce()-1);
						  setForceEnergybuffer(getForceEnergybuffer()+ 8000);	
		
				}else{
					
					if (isActive() != false) {
						setActive(false);
					}
					
				}
				
				
				
				transferForceEnergy();
				
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
		if(this.MaxWorkEnergy > this.WorkEnergy)
		{
			return true;
		}
		return false;
	}


	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
	 if(this.MaxWorkEnergy > this.WorkEnergy)
	 {
		 WorkEnergy =  WorkEnergy + amount;
		 if(WorkEnergy > MaxWorkEnergy)
		 {
			 int rest = WorkEnergy - MaxWorkEnergy;
			 WorkEnergy = WorkEnergy - rest;
			 return rest;
		 }
	 } 
	   return 0;
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
		NetworkedFields.add("ForceEnergybuffer");
		NetworkedFields.add("WorkCylce");
		NetworkedFields.add("WorkEnergy");
		
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
		 ForceEnergybuffer = nbttagcompound.getInteger("ForceEnergybuffer");
		 WorkEnergy = nbttagcompound.getInteger("WorkEnergy");
		 WorkCylce = nbttagcompound.getInteger("WorkCylce");
		 
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

		nbttagcompound.setInteger("WorkCylce", WorkCylce);
		nbttagcompound.setInteger("WorkEnergy", WorkEnergy);
		nbttagcompound.setInteger("ForceEnergybuffer",ForceEnergybuffer);
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
