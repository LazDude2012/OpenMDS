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
		
	public TileEntityExtractor() {
		
		inventory = new ItemStack[1];
		create = true;
		addedToEnergyNet = false;
		Extractor_ID = 0;
	}
	
	public void updateEntity() {
		if (worldObj.isRemote == false) {
			
			
			
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
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getStartInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean demandsEnergy() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		// TODO Auto-generated method stub
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

	
	

}
