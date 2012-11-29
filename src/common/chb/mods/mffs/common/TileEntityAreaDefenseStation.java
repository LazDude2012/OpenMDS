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

import chb.mods.mffs.common.IModularProjector.Slots;
import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandlerClient;
import chb.mods.mffs.network.NetworkHandlerServer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Container;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityLiving;
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
ISidedInventory,INetworkHandlerListener,INetworkHandlerEventListener,ISwitchabel {
	private ItemStack Inventory[];
	private int Defstation_ID;
	private int linkPower;
	private int capacity;
	private boolean create;
	private int distance;
	private int SwitchTyp;
	private boolean OnOffSwitch;
	
	protected Set<EntityPlayer> warnlist = new HashSet<EntityPlayer>();
	protected Set<EntityPlayer> actionlist = new HashSet<EntityPlayer>();
	
	public TileEntityAreaDefenseStation() {
		Random random = new Random();

		Inventory = new ItemStack[40];
		Defstation_ID = 0;
		linkPower = 0;
		capacity = 0;
		create = true;
		SwitchTyp = 0;
		OnOffSwitch = false;
	}
	
	
	

	// Start Getter AND Setter

	public boolean getOnOffSwitch() {
		return OnOffSwitch;
	}

	public void setOnOffSwitch(boolean a) {
		OnOffSwitch = a;
	}

	public int getswitchtyp() {
		return SwitchTyp;
	}

	public void setswitchtyp(int a) {
		SwitchTyp = a;
	}
	
	public int getCapacity(){
		return capacity;
	}

	public void setCapacity(int Capacity){
		this.capacity = Capacity;
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
	
	
	public int getActionDistance() {
	if (getStackInSlot(3) != null) {
		if (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance) {
			return (getStackInSlot(3).stackSize);
		}
	  } 
	return 0;
	}
	
	
	public int getInfoDistance() {
	if (getStackInSlot(2) != null) {
		if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance) {
			return getActionDistance() + (getStackInSlot(2).stackSize+3);
		}
	  } 
	return getActionDistance()+ 3;
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
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		Defstation_ID = nbttagcompound.getInteger("Defstation_ID");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		Inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < Inventory.length) {
				Inventory[byte0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Defstation_ID", Defstation_ID);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < Inventory.length; i++) {
			if (Inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				Inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}


	public void dropplugins() {
		for (int a = 0; a < this.Inventory.length; a++) {
			dropplugins(a,this);
		}
	}


	public void Playerscanner()
	{
		int xmin = xCoord - getInfoDistance();
		int xmax = xCoord + getInfoDistance();
		int ymin = yCoord - getInfoDistance();
		int ymax = yCoord + getInfoDistance();
		int zmin = zCoord - getInfoDistance();
		int zmax = zCoord + getInfoDistance();
		
		List<EntityPlayer> playerlist = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xmin, ymin, zmin, xmax, ymax, zmax));

		for(EntityPlayer player : playerlist)
		{
		int distance = PointXYZ.distance(getMaschinePoint(), new PointXYZ((int)player.posX,(int)player.posY,(int)player.posZ,worldObj));
		
		if(distance <=  getInfoDistance())
		{
			if(!warnlist.contains(player))
			{
				warnlist.add(player);
				player.addChatMessage("!!! [Area Defence] You approach a locked area that's the only warning !!!");
				player.attackEntityFrom(DamageSource.generic,5);

			}
		}
		
		if(distance <=  getActionDistance())
		{
			if(!actionlist.contains(player))
			{
				actionlist.add(player);
				player.addChatMessage("!!! [Area Defence] Defence activities instituted !!!");
			}
		}else{
			
			if(actionlist.contains(player))
			   actionlist.remove(player);
		}
		}
		
		for(EntityPlayer warnplayer : warnlist)
		{
			if(!playerlist.contains(warnplayer))
				warnlist.remove(warnplayer);
		}
		
		for(EntityPlayer actionplayer : actionlist)
		{
			if(!playerlist.contains(actionplayer))
				actionlist.remove(actionplayer);
		}	
	}
	
	
	public void DefenceAction(){
		
		
	}
	

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			if (this.isCreate() && this.getLinkCapacitor_ID() != 0) {
				addtogrid();
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

			
			boolean powerdirekt = worldObj.isBlockGettingPowered(xCoord,
					yCoord, zCoord);
			boolean powerindrekt = worldObj.isBlockIndirectlyGettingPowered(
					xCoord, yCoord, zCoord);

			if (getswitchtyp() == 0)
				setOnOffSwitch(powerdirekt || powerindrekt);

			if (getOnOffSwitch() &&  getLinkedCapacitor()!= null && getLinkedSecurityStation()!=null
					&& !isActive())
				setActive(true);

			if ((!getOnOffSwitch() || getLinkedCapacitor()==null || getLinkedSecurityStation()==null) && isActive())
				setActive(false);


			
			if(this.isActive())
			{
				Playerscanner();				
			}
			

			if (this.getTicker() == 20) {
				if(this.isActive())
				{
					DefenceAction();
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

	
	
	
	
	

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (Inventory[i] != null) {
			if (Inventory[i].stackSize <= j) {
				ItemStack itemstack = Inventory[i];
				Inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = Inventory[i].splitStack(j);
			if (Inventory[i].stackSize == 0) {
				Inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		Inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}


	@Override
	public ItemStack getStackInSlot(int i) {
		return Inventory[i];
	}
	@Override
	public String getInvName() {
		return "Defstation";
	}

	@Override
	public int getSizeInventory() {
		return Inventory.length;
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
	public void onNetworkHandlerEvent(String event) {
		
	
		if (Integer.parseInt(event) == 0) {
			if (this.getswitchtyp() == 0) {
				this.setswitchtyp(1);
			} else {
				this.setswitchtyp(0);
			}
		}

	}
	
	@Override
	public List<String> getFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

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
		case 2: //Distance mod
		case 3:
			return 64;
		}
		return 1;
	}
}
