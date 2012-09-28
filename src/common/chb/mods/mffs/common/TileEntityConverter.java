package chb.mods.mffs.common;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandler;

public class TileEntityConverter extends TileEntityMachines implements ISidedInventory
,INetworkHandlerListener{

	private ItemStack inventory[];
    private boolean create;
	private int Converter_ID;
	private int LinkCapacitor_ID;
	private int capacity;
	private int linkPower;

	
	public TileEntityConverter() {
		
		inventory = new ItemStack[1];
		create = true;
		Converter_ID = 0;
		LinkCapacitor_ID = 0;
		capacity = 0;
		linkPower = 0;
    }

	
	public int getLinkPower() {
		return linkPower;
	}

	public void setLinkPower(int linkPower) {
		this.linkPower = linkPower;
	}
	
	
	public int getCapacity(){
		return capacity;
	}

	
	public void setCapacity(int Capacity){
		this.capacity = Capacity;
	}
	
	public int getLinkCapacitors_ID() {
		return LinkCapacitor_ID;
	}
	
	public void setLinkCapacitor_ID(int id){
		this.LinkCapacitor_ID = id;
	}
	
	public int getConverter_ID() {
		return Converter_ID;
	}
	
	public void updateEntity() {
		if (worldObj.isRemote == false) {
			
			if (create) {
				if (Converter_ID == 0) {
					Converter_ID = Linkgrid.getWorldMap(worldObj)
							.newConverter_ID(this);
				}
				
				
				if (this.getLinkCapacitors_ID() != 0) {
					try {
						this.setLinkPower(Linkgrid.getWorldMap(worldObj)
								.getCapacitor().get(this.getLinkCapacitors_ID())
								.getForcePower());
						this.setCapacity(Linkgrid.getWorldMap(worldObj)
								.getCapacitor().get(this.getLinkCapacitors_ID())
								.getCapacity());
					} catch (java.lang.NullPointerException ex) {
						this.setLinkPower(0);
						this.setCapacity(0);
					}
				} else {
					this.setLinkPower(0);
					this.setCapacity(0);
				}
				
				addtogrid();
				checkslots(true);
				create = false;
			}
		
			if (this.getTicker() >= 20) {
				

				checkslots(false);

				
				this.setTicker((short) 0);
			}
		
			this.setTicker((short) (this.getTicker() + 1));
			
				

		}else{
			
			if (create) {
				NetworkHandler.requestInitialData(this);
				create = false;
			}
		}
	}
	
	
	public void checkslots(boolean init) {
		if (getStackInSlot(0) != null) {
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemfc) {
				if (getLinkCapacitors_ID() != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(0)).getInteger("CapacitorID")) {
					setLinkCapacitor_ID(NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(0)).getInteger("CapacitorID"));
				}

				if (Linkgrid.getWorldMap(worldObj).getCapacitor()
						.get(this.getLinkCapacitors_ID()) != null) {
					int transmit = Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitors_ID())
							.getTransmitRange();
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
						this.setInventorySlotContents(0, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
					}
				}
			} else {
				if (getStackInSlot(0).getItem() != ModularForceFieldSystem.MFFSitemcardempty) {
					dropplugins(0,this);
				}
			}
		} else {
		    this.setLinkCapacitor_ID(0);
		}
		
	}
	
	
	public void addtogrid() {
		Linkgrid.getWorldMap(worldObj).getConverter()
				.put(getConverter_ID(), this);
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getConverter().remove(getConverter_ID());
		dropplugins();
	}
	
	public void dropplugins() {
		for (int a = 0; a < this.inventory.length; a++) {
			dropplugins(a,this);
		}
	}
	
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}
	
	
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		Converter_ID = nbttagcompound.getInteger("Converter_ID");

		 
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

		nbttagcompound.setInteger("Converter_ID", Converter_ID);

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
	
	
	public ItemStack[] getContents() {
		return inventory;
	}
	
	@Override
	public void openChest() {
	}

	@Override
	public void closeChest(){ 
	}
	
	
	@Override
	public void onNetworkHandlerUpdate(String field) {
		if (field.equals("side")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("active")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}

	}





	@Override
	public List<String> geFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("active");
		NetworkedFields.add("side");

		return NetworkedFields;
	}


	@Override
	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerConverter(inventoryplayer.player,this);
	}
	
	
}
