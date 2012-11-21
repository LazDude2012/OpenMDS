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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

import chb.mods.mffs.common.modules.Module3DBase;
import chb.mods.mffs.common.modules.ModuleBase;
import chb.mods.mffs.common.options.IChecksOnAll;
import chb.mods.mffs.common.options.IInteriorCheck;
import chb.mods.mffs.common.options.ItemProjectorOptionBase;
import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldManipulator;
import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandlerClient;
import chb.mods.mffs.network.NetworkHandlerServer;

public class TileEntityProjector extends TileEntityMachines implements IModularProjector,
ISidedInventory,INetworkHandlerEventListener,INetworkHandlerListener{
	private ItemStack ProjektorItemStacks[];

	private boolean[] projektoroption = { false, false, false, false, false,false,false,true,false,false,false,false,false};
	private int[] focusmatrix = { 0, 0, 0, 0 }; // Up 7,Down 8,Right 9,Left 10
	private int[] nullgrafik = { 180, 180, 180, 180, 180, 180 };
	private int[] watergrafik = { 205, 205, 207, 222, 223, 223 }; // Vanilla Water Textur
	private int[] lavagrafik = { 237, 237, 239, 254, 255, 255 };  // Vanilla Lava  Textur
	private int[] forcefieldtextur_id= { -10,-10,-10,-10,-10,-10};

	private boolean LinkedSecStation;
	private int SecStation_ID;
	private int switchdelay;
	private short forcefieldblock_meta;
	private int ProjektorTyp;
	private int Projektor_ID;
	private int linkPower;
	private int blockcounter;
	private int ForceField_strength;
	private int ForceField_distance;
	private boolean burnout;
	private boolean camoflage;
	private int accesstyp;
	private int SwitchTyp;
	private boolean OnOffSwitch;
	private int capacity;

	protected Stack<Integer> field_queue = new Stack<Integer>();
	protected Set<PointXYZ> field_interior = new HashSet<PointXYZ>();
	protected Set<PointXYZ> field_def = new HashSet<PointXYZ>();
	
	public TileEntityProjector() {
		Random random = new Random();

		ProjektorItemStacks = new ItemStack[13];
		Projektor_ID = 0;
		linkPower = 0;
		forcefieldblock_meta =  (short) ForceFieldTyps.Default.ordinal();
		ProjektorTyp = 0;
		switchdelay = 0;
		burnout = false;
		camoflage = false;
		ForceField_strength = 0;
		ForceField_distance = 0;
		accesstyp = 0;
		SecStation_ID = 0;
		LinkedSecStation = false;
		SwitchTyp = 0;
		OnOffSwitch = false;
		capacity = 0;
	}

	// Start Getter AND Setter

	public int getCapacity(){
		return capacity;
	}

	public void setCapacity(int Capacity){
		this.capacity = Capacity;
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
	}

	public boolean isLinkedSecStation() {
		return LinkedSecStation;
	}

	public void setLinkedSecStation(boolean linkedSecStation) {
		LinkedSecStation = linkedSecStation;
	}

	public int getSecStation_ID() {
		return SecStation_ID;
	}

	public int getaccesstyp() {
		return accesstyp;
	}

	public void setaccesstyp(int accesstyp) {
		this.accesstyp = accesstyp;
	}

	public int getForcefieldtextur_id(int l) {
		return forcefieldtextur_id[l];
	}

	public int[] getForcefieldtextur_id() {
		return forcefieldtextur_id;
	}

	public void setForcefieldtextur_id(int[] forcefieldtextur_id) {
		this.forcefieldtextur_id = forcefieldtextur_id;
	}

	public int getProjektor_Typ() {
		return ProjektorTyp;
	}

	public int getFocusup() {
		return focusmatrix[0];
	}

	public void setFocusup(int i) {
		focusmatrix[0] = i;
	}

	public int getFocusdown() {
		return focusmatrix[1];
	}

	public void setFocusdown(int i) {
		focusmatrix[1] = i;
	}

	public int getFocusright() {
		return focusmatrix[2];
	}

	public void setFocusright(int i) {
		focusmatrix[2] = i;
	}

	public int getFocusleft() {
		return focusmatrix[3];
	}

	public void setForcusleft(int i) {
		focusmatrix[3] = i;
	}

	public void setProjektor_Typ(int ProjektorTyp) {
		this.ProjektorTyp = ProjektorTyp;

		NetworkHandlerServer.updateTileEntityField(this, "ProjektorTyp");
	}

	public int getForceField_strength() {
		return ForceField_strength;
	}

	public int getForceField_distance() {
		return ForceField_distance;
	}

	public void setForceField_distance(int distance) {
		this.ForceField_distance = distance;
	}

	public void setForceField_strength(int length) {
		this.ForceField_strength = length;
	}
	public int getBlockcounter() {
		return blockcounter;
	}

	public boolean isCreate() {
		return projektoroption[7];
	}

	public void setCreate(boolean create) {
		this.projektoroption[7] = create;
	}

	public int getforcefieldblock_meta() {
		return forcefieldblock_meta;
	}

	public void setforcefieldblock_meta(int ffmeta) {
		this.forcefieldblock_meta =  (short) ffmeta;
	}

	public boolean isLinkCapacitor() {
		return projektoroption[8];
	}

	public void setLinkGenerator(boolean linkGenerator) {
		projektoroption[8] = linkGenerator;
	}

	public int getLinkPower() {
		return linkPower;
	}

	public void setLinkPower(int linkPower) {
		this.linkPower = linkPower;
	}


	public boolean isOptionDamage() {
		return projektoroption[0];
	}

	public void setOptionDamage(boolean b) {
		projektoroption[0] = b;
	}

//	public boolean isOptionSubwater() {
//		return projektoroption[1];
//	}
//
//	public void setOptionSubwater(boolean b) {
//		projektoroption[1] = b;
//	}

	public boolean isOptionFieldcut() {
		return projektoroption[2];
	}

	public void setOptionFieldcut(boolean b) {
		projektoroption[2] = b;
	}

	public boolean isOptionBlockcutter() {
		return projektoroption[3];
	}

	public void setOptionBlockcutter(boolean b) {
		projektoroption[3] = b;
	}

	@Override
	public int getProjektor_ID() {
		return Projektor_ID;
	}

	public boolean isOptionBlockdropper() {
		return projektoroption[4];
	}

	public void setOptionBlockdropper(boolean b) {
		projektoroption[4] = b;
	}

	public boolean isOptionFFJammer() {
		return projektoroption[5];
	}

	public void setOptionFFJammer(boolean b) {
		projektoroption[5] = b;
	}

	public boolean isOptionDefenceStation() {
		return projektoroption[9];
	}

	public void setOptionDefenceStation(boolean b) {
		projektoroption[9] = b;
	}

	public boolean isOptionMobDefense() {
		return projektoroption[10];
	}

	public void setOptionMobDefense(boolean b) {
		projektoroption[10] = b;
	}

	public boolean isBurnout() {
		return burnout;
	}

	public void setBurnedOut(boolean b) {
		burnout = b;
		NetworkHandlerServer.updateTileEntityField(this, "burnout");
	}

	public boolean isJammeractive() {
		return projektoroption[6];
	}

	public void setJammer(boolean b) {
		if (b) {
			projektoroption[6] = true;
			Linkgrid.getWorldMap(worldObj).getJammer()
					.put(getProjektor_ID(), this);
		}
		if (!b) {
			projektoroption[6] = false;
			Linkgrid.getWorldMap(worldObj).getJammer()
					.remove(getProjektor_ID());
		}
	}

	public boolean isOptionFieldFusion() {
		return projektoroption[12];
	}

	public void setOptionFieldFusion(boolean b) {
		projektoroption[12] = b;
	}

	public boolean isFieldFusion() {
		return projektoroption[11];
	}

	public void setFieldFusion(boolean b) {
		if (b) {
			projektoroption[11] = true;
			Linkgrid.getWorldMap(worldObj).getFieldFusion()
					.put(getProjektor_ID(), this);
		}
		if (!b) {
			projektoroption[11] = false;
			Linkgrid.getWorldMap(worldObj).getFieldFusion()
					.remove(getProjektor_ID());
		}
	}

	public void ProjektorBurnout() {
		this.setBurnedOut(true);
		dropplugins();
	}

	public boolean isOptioncamouflage() {
		return camoflage;
	}

	public void setOptioncamouflage(boolean b) {
		camoflage = b;
	}

	// End Getter AND Setter

	// Start NBT Read/ Save

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		SwitchTyp = nbttagcompound.getInteger("SwitchTyp");
		OnOffSwitch = nbttagcompound.getBoolean("OnOffSwitch");
		accesstyp = nbttagcompound.getInteger("accesstyp");
		burnout = nbttagcompound.getBoolean("burnout");
		Projektor_ID = nbttagcompound.getInteger("Projektor_ID");
		ProjektorTyp = nbttagcompound.getInteger("Projektor_Typ");
		forcefieldblock_meta = nbttagcompound.getShort("forcefieldblockmeta");

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

		nbttagcompound.setInteger("SwitchTyp", SwitchTyp);
		nbttagcompound.setBoolean("OnOffSwitch", OnOffSwitch);
		nbttagcompound.setInteger("accesstyp", accesstyp);
		nbttagcompound.setBoolean("burnout", burnout);
		nbttagcompound.setInteger("Projektor_ID", Projektor_ID);
		nbttagcompound.setInteger("Projektor_Typ", ProjektorTyp);
		nbttagcompound.setShort("forcefieldblockmeta", forcefieldblock_meta);

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

	public void checkslots(boolean init) {
		
		
		if (hasValidTypeMod()){
	           
			if(getProjektor_Typ()!= ProjectorTyp.TypfromItem(get_type()).ProTyp) 
			setProjektor_Typ(ProjectorTyp.TypfromItem(get_type()).ProTyp);
			
			if(getforcefieldblock_meta() != get_type().getForceFieldTyps().ordinal())
			setforcefieldblock_meta(get_type().getForceFieldTyps().ordinal());

			setOptionBlockdropper(ProjectorTyp.TypfromItem(get_type()).Blockdropper);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			
		}else{
			if(getProjektor_Typ()!= 0) {setProjektor_Typ(0);}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		
		


		// Focus function

		for (int place = 7; place < 11; place++) {
		if (getStackInSlot(place) != null) {
			if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSitemFocusmatix) {
				switch(this.getProjektor_Typ())
				{
				case 6:
					focusmatrix[place-7] = getStackInSlot(place).stackSize+1;
				break;
				case 7:
					focusmatrix[place-7] = getStackInSlot(place).stackSize+2;
				break;
				default:
					focusmatrix[place-7] = getStackInSlot(place).stackSize;
				break;
				}
			} else {
				dropplugins(place,this);
			}
		} else {
			switch(this.getProjektor_Typ())
			{
			case 6:
				focusmatrix[place-7] = 1;
			break;
			case 7:
				focusmatrix[place-7] = 2;
			break;
			default:
				focusmatrix[place-7] = 0;
			break;
			}
		}
		}

		if (getStackInSlot(11) != null) {
			int[] grafikindex = null;

		if (getStackInSlot(11).getItem() == Item.bucketLava)
				{
			grafikindex=lavagrafik;
				}
		if (getStackInSlot(11).getItem() == Item.bucketWater)
		{
			grafikindex=watergrafik;
		}
		if(grafikindex == null)
		{
			grafikindex = ModularForceFieldSystem.idmetatotextur.get(getStackInSlot(11).itemID
					+ (getStackInSlot(11).getItemDamage() * 1000));
		}

				if(grafikindex != null)
				{
					if(grafikindex != this.getForcefieldtextur_id())
					{
						this.setForcefieldtextur_id(grafikindex);
						UpdateForcefieldTexttur();
					}
				}else{
					dropplugins(11,this);
				}
		} else {
			if(this.getForcefieldtextur_id()!= nullgrafik)
			{
				this.setForcefieldtextur_id(nullgrafik);
				UpdateForcefieldTexttur();
		    }
		}

		if (getStackInSlot(12) != null) {
			if (getStackInSlot(12).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard) {
				if (SecStation_ID != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(12)).getInteger("Secstation_ID")) {
					SecStation_ID = NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(12)).getInteger("Secstation_ID");
				}
				if (SecStation_ID == 0) {
					dropplugins(12,this);
				}
				if(Linkgrid.getWorldMap(worldObj)
				.getSecStation().get(this.getSecStation_ID())!=null)
				{
				setLinkedSecStation(true);
				this.setaccesstyp(3);
				}
				else
				{
				setLinkedSecStation(false);
				dropplugins(12,this);
				if(getaccesstyp()==3)
				{setaccesstyp(0);}
				}
			} else {
			    	SecStation_ID = 0;
				    setLinkedSecStation(false);
					if(getaccesstyp() ==3)
					{setaccesstyp(0);}
					dropplugins(12,this);
			}
		} else {
			SecStation_ID = 0;
			setLinkedSecStation(false);
			if(getaccesstyp() ==3)
				{setaccesstyp(0);}
		}
				
		

		if (getStackInSlot(2) != null || getStackInSlot(3) != null
				|| getStackInSlot(4) != null) {
			boolean OptionFieldcut = false;

			if (isOptionBlockcutter()) {
				setOptionBlockcutter(false);
			}
			if (isOptionDamage()) {
				setOptionDamage(false);
				setforcefieldblock_meta((short) 0);
			}

//			if (isOptionSubwater()) {
//				setOptionSubwater(false);
//			}

			if (isOptionFFJammer()) {
				setOptionFFJammer(false);
			}

			if (this.isOptionFieldFusion()) {
				setOptionFieldFusion(false);
			}

			if (isOptioncamouflage()) {
				setOptioncamouflage(false);
			}

			if(isOptionDefenceStation()){
				setOptionDefenceStation(false);
			}

			if(isOptionMobDefense()){
				setOptionMobDefense(false);
			}

			for (int place = 2; place < 5; place++) {
				if (getStackInSlot(place) != null) {
					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionFieldFusion) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6 ) {
							dropplugins(place,this);
						} else {
							setOptionFieldFusion(true);

							if (!isFieldFusion()) {
								this.setFieldFusion(true);
							}
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6 ) {
							dropplugins(place,this);
						} else {
							setOptionFFJammer(true);

							if (!isJammeractive()) {
								this.setJammer(true);
							}
						}
						continue;
					}

//					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionSubwater) {
//						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6) {
//							dropplugins(place,this);
//						} else {
//							setOptionSubwater(true);
//						}
//						continue;
//					}
					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionDome) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6) {
							dropplugins(place,this);
						} else {
							OptionFieldcut=true;
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionZapper) {
						if (this.getProjektor_Typ() == 4 || this.getProjektor_Typ() == 5 || this.getProjektor_Typ() == 6 || this.getProjektor_Typ() == 7) {
							dropplugins(place,this);
						} else {
							setOptionDamage(true);
							setforcefieldblock_meta((short) 1);
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionCutter) {
						setOptionBlockcutter(true);
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionCamouflage) {
	                     if(!this.isOptionDamage())
	                     {
	 						setforcefieldblock_meta((short) ForceFieldTyps.Camouflage.ordinal());
	 						this.setOptioncamouflage(true);
	                     }else{
	                    	 dropplugins(place,this);
	                     }
							continue;
						}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionDefenceStation) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 3 || this.getProjektor_Typ() == 6  ) {
							dropplugins(place,this);
							continue;
						} else {
							this.setOptionDefenceStation(true);
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionMoobEx) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 3 || this.getProjektor_Typ() == 6  ) {
							dropplugins(place,this);
							continue;
						} else {
							this.setOptionMobDefense(true);
						}
						continue;
					}

					if (getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionSubwater
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionDome
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionZapper
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionCutter
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionDefenceStation
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionMoobEx
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionCamouflage

							) {
						dropplugins(place,this);
						continue;
					}

					if (getProjektor_Typ() == 0) {
						dropplugins(place,this);
					}
				}
			}

			if (isJammeractive() && !isOptionFFJammer()) {
				this.setJammer(false);
			}

			if (isFieldFusion() && !isOptionFieldFusion()) {
				this.setFieldFusion(false);
			}

			if (isOptionFieldcut() != OptionFieldcut) {
				setOptionFieldcut(OptionFieldcut);
			}
		} else {
			if(isOptionDefenceStation()){
				setOptionDefenceStation(false);
			}

			if (isOptioncamouflage()) {
				setOptioncamouflage(false);
			}

			if(isOptionMobDefense()){
				setOptionMobDefense(false);
			}

			if (isOptionBlockcutter()) {
				setOptionBlockcutter(false);
			}
			if (isOptionDamage()) {
				setOptionDamage(false);
				setforcefieldblock_meta((short) 0);
			}
			if (isOptionFieldcut()) {
				setOptionFieldcut(false);
			}
//			if (isOptionSubwater()) {
//				setOptionSubwater(false);
//			}

			if (isOptionFFJammer()) {
				setOptionFFJammer(false);
				if (isJammeractive()) {
					this.setJammer(false);
				}
			}

			if (isOptionFieldFusion()) {
				setOptionFieldFusion(false);
				if (isFieldFusion()) {
					this.setFieldFusion(false);
				}
			}
		}

		// --------------------------
		// Drop plugin by typ
		
		if (hasValidTypeMod()) {
			ModuleBase modTyp = get_type();				
			
			if (!modTyp.supportsStrength)
				dropplugins(6,this);
			if (!modTyp.supportsDistance)
				dropplugins(5,this);
			
			if (!modTyp.supportsMatrixRight)
				dropplugins(9,this);
			if (!modTyp.supportsMatrixLeft)
				dropplugins(10,this);
			if (!modTyp.supportsMatrixUp)
				dropplugins(7,this);
			if (!modTyp.supportsMatrixDown)
				dropplugins(8,this);
			
			if(!this.isOptioncamouflage())
				dropplugins(11,this);
			
		} else {
			for (int spot = 2; spot <= 10; spot++) {
				dropplugins(spot, this);
			}
		}
		
		
		

	}

	private void UpdateForcefieldTexttur() {
		if(this.isActive() && this.isOptioncamouflage())
		{
		for (Integer hasher : field_queue) {
			ForceFieldBlockStack ffb = WorldMap.getForceFieldWorld(worldObj).getForceFieldStackMap(hasher);

			if(ffb!=null){
		     if (worldObj.getChunkFromBlockCoords(ffb.getX(), ffb.getZ()).isChunkLoaded) {
			if (ffb.getProjectorID() == getProjektor_ID()){
				TileEntity tileEntity = worldObj.getBlockTileEntity(ffb.getX(), ffb.getY(), ffb.getZ());

				if(tileEntity != null && tileEntity instanceof TileEntityForceField )
				{
					((TileEntityForceField)tileEntity).UpdateTextur();
				}
			}
		    }
	    	}
		}
	  }
	}

	// Stop Slot / Upgrades System

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			if (this.isCreate() && this.getLinkCapacitor_ID() != 0) {
				addtogrid();
				checkslots(true);
				if (this.isActive()) {
					calculateField(true);
				}
				this.setCreate(false);
			}

			if (this.getLinkCapacitor_ID() != 0) {
				this.setLinkGenerator(true);
				try {
					this.setLinkPower(Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitor_ID())
							.getForcePower());
					this.setCapacity(Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitor_ID())
							.getCapacity());
				} catch (java.lang.NullPointerException ex) {
					this.setLinkGenerator(false);
					this.setLinkPower(0);
					this.setCapacity(0);
				}
			} else {
				this.setLinkGenerator(false);
				this.setLinkPower(0);
				this.setCapacity(0);
			}

			boolean powerdirekt = worldObj.isBlockGettingPowered(xCoord,
					yCoord, zCoord);
			boolean powerindrekt = worldObj.isBlockIndirectlyGettingPowered(
					xCoord, yCoord, zCoord);

			if(this.getswitchtyp()==0)
			{
		    this.setOnOffSwitch((powerdirekt || powerindrekt));
			}

			if ((getOnOffSwitch() && (switchdelay >= 40))
					&& getProjektor_Typ() != 0 && this.isLinkCapacitor()
					&& this.getLinkPower() > Forcepowerneed(5)) {
				if (isActive() != true) {
					setActive(true);
					switchdelay = 0;
					if(calculateField(true))
					{
					FieldGenerate(true);
					}
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			if ((!getOnOffSwitch() && switchdelay >= 40)
					|| getProjektor_Typ() == 0 || !this.isLinkCapacitor() || burnout
					|| this.getLinkPower() <= Forcepowerneed(1)) {
				if (isActive() != false) {
					setActive(false);
					switchdelay = 0;
					destroyField();
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}

			if (this.getTicker() == 20) {
				checkslots(false);

				if (isActive()) {
					FieldGenerate(false);
					if(this.isOptionDefenceStation())
					{ ForceFieldOptions.DefenseStation(this, worldObj,"projector","human");}

					if(this.isOptionMobDefense())
					{ForceFieldOptions.DefenseStation(this, worldObj,"projector","mobs");}
				}

				this.setTicker((short) 0);
			}

			this.setTicker((short) (this.getTicker() + 1));
		}else{
			if(this.getProjektor_ID()==0)
			{
				if (this.getTicker() >= 20+random.nextInt(20)) {
					NetworkHandlerClient.requestInitialData(this,true);

					this.setTicker((short) 0);
				}

				this.setTicker((short) (this.getTicker() + 1));
			}
		}

		switchdelay++;
	}
	
	private boolean calculateField(boolean addtoMap){ //Should only be called when being turned on after setting changes or on first on.
		field_def.clear();
		field_interior.clear();
		if (hasValidTypeMod()){
			Set<PointXYZ> tField = new HashSet<PointXYZ>();
			Set<PointXYZ> tFieldInt = new HashSet<PointXYZ>();
			
			if (get_type() instanceof Module3DBase){
				((Module3DBase)get_type()).calculateField(this, tField, tFieldInt);
			}else{
				get_type().calculateField(this, tField);
			}
			
			for (PointXYZ pnt : tField){
				
				PointXYZ tp  = new PointXYZ(pnt.X+this.xCoord,pnt.Y+this.yCoord,pnt.Z+this.zCoord);
				
				if (Forcefielddefine(tp,addtoMap))
					{
					field_def.add(tp);
					}else{return false;}
			}
			for (PointXYZ pnt : tFieldInt){
				
				PointXYZ tp  = new PointXYZ(pnt.X+this.xCoord,pnt.Y+this.yCoord,pnt.Z+this.zCoord);
				
				if (calculateBlock(tp))
					{field_interior.add(tp);}else{return false;}
			}
			
			
			return true;
		}
		return false;
	}

	
	
	
	public boolean calculateBlock(PointXYZ pnt){

		for(ItemProjectorOptionBase opt : getOptions())
		{
			if(opt instanceof IInteriorCheck)
				((IInteriorCheck)opt).checkInteriorBlock(pnt, worldObj, this);	
						
		}
		return true;
	}
	



	public boolean Forcefielddefine(PointXYZ png,boolean addtoMap)
	{
		
		for(ItemProjectorOptionBase opt : getOptions())
		{
			if(opt instanceof IChecksOnAll){
				
				if(opt instanceof ItemProjectorOptionForceFieldJammer){
					if(((ItemProjectorOptionForceFieldJammer)opt).CheckJammerinfluence(png, worldObj, this))
			            return false;
				}
				if(opt instanceof ItemProjectorOptionFieldFusion){
					if(((ItemProjectorOptionFieldFusion)opt).checkFieldFusioninfluence(png, worldObj, this))
			            return true;
				}
				
				
				
			}			
		}
		
		
		ForceFieldBlockStack ffworldmap = WorldMap
				.getForceFieldWorld(worldObj)
				.getorcreateFFStackMap(png.X, png.Y, png.Z);

		if(!ffworldmap.isEmpty())
		{
			if(ffworldmap.getProjectorID() != getProjektor_ID()){
			    ffworldmap.removebyProjector(getProjektor_ID());
				ffworldmap.add(getLinkCapacitor_ID(), getProjektor_ID(), getforcefieldblock_meta());
			    }
		}else{
			ffworldmap.add(getLinkCapacitor_ID(), getProjektor_ID(), getforcefieldblock_meta());
			ffworldmap.setSync(false);
		}

		field_queue.push(WorldMap.Cordhash(png.X, png.Y, png.Z));

		return true;
	}
	


	public void FieldGenerate(boolean init) {
			TileEntity tileEntity = Linkgrid.getWorldMap(worldObj)
					.getCapacitor().get(this.getLinkCapacitor_ID());
			if (tileEntity instanceof TileEntityCapacitor && tileEntity != null) {
				int cost = 0;

				if (init) {
					cost = ModularForceFieldSystem.forcefieldblockcost
							* ModularForceFieldSystem.forcefieldblockcreatemodifier;
				} else {
					cost = ModularForceFieldSystem.forcefieldblockcost;
				}

				if (getforcefieldblock_meta() == 1) {
					cost *= ModularForceFieldSystem.forcefieldblockzappermodifier;
				}

				((TileEntityCapacitor) tileEntity).Energylost(cost
						* field_def.size());
			}

		blockcounter = 0;

		for (PointXYZ pnt : field_def) {
			if (blockcounter == ModularForceFieldSystem.forcefieldmaxblockpeerTick) {
				break;
			}
			ForceFieldBlockStack ffb = WorldMap.getForceFieldWorld(worldObj).getForceFieldStackMap(pnt.X, pnt.Y, pnt.Z);


			
			if(ffb!=null){
		     if (worldObj.getChunkFromBlockCoords(ffb.getX(), ffb.getZ()).isChunkLoaded) {
		    	 if(!ffb.isEmpty()){
		            	if (ffb.getProjectorID() == getProjektor_ID()){
					if (isOptionBlockcutter()) {
						int blockid = worldObj.getBlockId(ffb.getX(),
								ffb.getY(), ffb.getZ());
						TileEntity entity = worldObj.getBlockTileEntity(ffb.getX(),
								ffb.getY(), ffb.getZ());
						
						if (blockid != ModularForceFieldSystem.MFFSFieldblock.blockID
								&& blockid != 0
								&& blockid != Block.bedrock.blockID
								&& entity == null) 
						
						{
							ArrayList<ItemStack> stacks = Functions
									.getItemStackFromBlock(worldObj,
											ffb.getX(), ffb.getY(), ffb.getZ());
							worldObj.setBlockWithNotify(ffb.getX(), ffb.getY(),
									ffb.getZ(), 0);

							if (isOptionBlockdropper() && stacks != null) {
								IInventory inventory = InventoryHelper.findAttachedInventory(worldObj,this.xCoord,this.yCoord,this.zCoord);
								if (inventory != null) {
									if (inventory.getSizeInventory() > 0) {
										InventoryHelper.addStacksToInventory(inventory, stacks);
									}
								}
							}
						}
					}

						if (worldObj.getBlockMaterial(ffb.getX(), ffb.getY(),
								ffb.getZ()).isLiquid()
								|| worldObj.getBlockId(ffb.getX(), ffb.getY(),
										ffb.getZ()) == 0 || worldObj.getBlockId(ffb.getX(), ffb.getY(),
												ffb.getZ()) == ModularForceFieldSystem.MFFSFieldblock.blockID) {
						if (worldObj.getBlockId(ffb.getX(), ffb.getY(),
								ffb.getZ()) != ModularForceFieldSystem.MFFSFieldblock.blockID) {
							worldObj.setBlockAndMetadataWithNotify(
									ffb.getX(),
									ffb.getY(),
									ffb.getZ(),
									ModularForceFieldSystem.MFFSFieldblock.blockID,
									ffb.getTyp());
							blockcounter++; // Count create blocks...
						}
						ffb.setSync(true);
					}
				}
			}
			}
		}
		}
	}



	public void destroyField() {
		while(!field_queue.isEmpty()){
			ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(
					worldObj).getForceFieldStackMap(field_queue.pop());

			if(!ffworldmap.isEmpty())
			{
				if (ffworldmap.getProjectorID() == getProjektor_ID()) {
					ffworldmap.removebyProjector(getProjektor_ID());

					if(ffworldmap.isSync())
					{
						worldObj.removeBlockTileEntity(ffworldmap.getX(),
								ffworldmap.getY(), ffworldmap.getZ());
						worldObj.setBlockWithNotify(ffworldmap.getX(),
								ffworldmap.getY(), ffworldmap.getZ(), 0);
					}

					ffworldmap.setSync(false);
				}else{
					ffworldmap.removebyProjector(getProjektor_ID());
				}
		  }
		}

		Map<Integer, TileEntityProjector> FieldFusion = Linkgrid.getWorldMap(worldObj).getFieldFusion();
		for (TileEntityProjector tileentity : FieldFusion.values()) {
		 if(tileentity.getLinkCapacitor_ID() == this.getLinkCapacitor_ID())
		 {
			 if(tileentity.isActive())
			 {
				 tileentity.calculateField(false);
			 }
		 }
	  }
	}

	public void addtogrid() {
		if (Projektor_ID == 0) {
			Projektor_ID = Linkgrid.getWorldMap(worldObj)
					.newID(this);
		}
		Linkgrid.getWorldMap(worldObj).getProjektor().put(Projektor_ID, this);
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getProjektor().remove(getProjektor_ID());
		dropplugins();
		destroyField();
	}

	public int Forcepowerneed(int factor) {
		if (!field_queue.isEmpty()) {
			return field_queue.size()
					* ModularForceFieldSystem.forcefieldblockcost;
		}

		int forcepower = 0;
		int blocks = 0;

		int tmplength = 1;

		if (this.getForceField_strength() != 0) {
			tmplength = this.getForceField_strength();
		}

		switch (this.getProjektor_Typ()) {
		case 1:
			blocks = ((this.getFocusdown() + this.getFocusleft()
					+ this.getFocusright() + this.getFocusup()) + 1)
					* tmplength;
			break;
		case 2:
			blocks = (this.getFocusdown() + this.getFocusup() + 1)
					* (this.getFocusleft() + this.getFocusright() + 1);
			break;
		case 3:
			blocks = (((this.getForceField_distance() + 2 + this.getForceField_distance() + 2) * 4) + 4)
					* (this.getForceField_strength() + 1);
			break;
		case 4:
		case 5:
			blocks = (this.getForceField_distance() * this.getForceField_distance()) * 6;
			break;
		}

		forcepower = blocks * ModularForceFieldSystem.forcefieldblockcost;
		if (factor != 1) {
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
		return "Projektor";
	}

	public int getInventoryStackLimit() {
		return 64;
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
		return new ContainerProjector(inventoryplayer.player, this);
	}

	public ItemStack[] getContents() {
		return ProjektorItemStacks;
	}

	public void setMaxStackSize(int arg0) {
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 11;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public void onNetworkHandlerEvent(String event) {
		
		
		switch(Integer.parseInt(event))
		   {
		   case 0:

			   if(getaccesstyp()!=3)
			   {
			   if(getaccesstyp() == 2)
			   {
				   setaccesstyp(0);
		       }else{
		    	   setaccesstyp(getaccesstyp()+1);
		       }
			   }

		   break;
		   case 1:
			if(this.getswitchtyp() == 0)
			{
				this.setswitchtyp(1);
			}else{
				this.setswitchtyp(0);
			}

		   break;
		   }
	}

	@Override
	public void onNetworkHandlerUpdate(String field) {
		if (field.equals("side")) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("active")) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("ProjektorTyp")) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public List<String> getFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("ProjektorTyp");
		NetworkedFields.add("active");
		NetworkedFields.add("side");
		NetworkedFields.add("burnout");
		NetworkedFields.add("camoflage");
		NetworkedFields.add("Projektor_ID");

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
			if(par1ItemStack.getItem() instanceof ModuleBase )
			return true;
		break;

		case 2:
		case 3:
		case 4:
			if(par1ItemStack.getItem() instanceof ItemProjectorOptionBlockBreaker ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionCamoflage ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionFieldFusion ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionDefenseStation ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionFieldManipulator ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionForceFieldJammer ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionMobDefence ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionSponge ||
			   par1ItemStack.getItem() instanceof ItemProjectorOptionTouchDamage )
			return true;
		break;

		case 5:
			if(par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorDistance )

				if(this.getProjektor_Typ() != 0 &&  this.getProjektor_Typ() != 7 )
				return true;
		break;

		case 6:
			if(par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorStrength )

				if(this.getProjektor_Typ() != 0 &&  this.getProjektor_Typ() != 2 && this.getProjektor_Typ() != 4)
				return true;
		break;

		case 7:
		case 8:
		case 9:
		case 10:
			if(par1ItemStack.getItem() instanceof ItemProjectorFocusMatrix )
			{
				if(this.getProjektor_Typ() != 0 &&  this.getProjektor_Typ() != 3 && this.getProjektor_Typ() != 5)
				return true;
			}

		break;

		case 12:
			if(par1ItemStack.getItem() instanceof ItemCardSecurityLink )
			return true;
		break;
		}

		if(Slot == 11 && this.isOptioncamouflage())
		return true;

		return false;
	}
	@Override
	public int getSlotStackLimit(int Slot){
		switch(Slot){
		case 5: //Distance Slot
		case 6: //Strength Slot
			return 64;

		case 7: //Focus Up
		case 8: //Focus Down
		case 9: //Focus right
		case 10: //Focus left
			return 64;
		}

		return 1;
	}
	
	public boolean hasValidTypeMod(){
		if (this.getStackInSlot(1) != null && getStackInSlot(1).getItem() instanceof ModuleBase)
			return true;
		return false;
	}
	
	public ModuleBase get_type(){
		if (hasValidTypeMod())
			return (ModuleBase)this.getStackInSlot(1).getItem();
		
		return null;
	}

	
	public int countItemsInSlot(Slots slt){
		if (this.getStackInSlot(slt.slot) != null)
			return this.getStackInSlot(slt.slot).stackSize;
		return 0;
	}

	@Override
	public Set<PointXYZ> getInteriorPoints() {
		return field_interior;
	}
	
	public Stack<Integer> getfield_queue()
	{
		return field_queue;
	}

	
	public TileEntityCapacitor getLinkedCapacitor(){
		int capid = getLinkCapacitor_ID();
		TileEntityCapacitor cap = Linkgrid.getWorldMap(getWorldObj()).getCapacitor().get(capid);
		if (cap != null){
			if (cap.getTransmitRange() >= Math.sqrt((cap.xCoord-xCoord)^2 + (cap.yCoord-yCoord)^2 + (cap.zCoord-zCoord)^2))
				setLinkGenerator(true);
				return cap;
		}else{
		  if (getStackInSlot(0) != null)
			  this.setInventorySlotContents(0, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
		}
		setLinkGenerator(false);
		return null;
	}
	
	

	
	@Override
	public int getLinkCapacitor_ID(){
		if (getStackInSlot(0) != null)
			return NBTTagCompoundHelper.getTAGfromItemstack(getStackInSlot(0)).getInteger("CapacitorID");
		return 0;
	}
	

		
	
	public List<ItemProjectorOptionBase> getOptions(){
		List<ItemProjectorOptionBase> ret = new ArrayList<ItemProjectorOptionBase>();
		for (int place = 2; place < 5; place++) {
			if (getStackInSlot(place) != null) {
				ret.add((ItemProjectorOptionBase)(getStackInSlot(place).getItem()));
			}
			
			for (ItemProjectorOptionBase opt : ItemProjectorOptionBase.get_instances()) {
				if (opt instanceof IChecksOnAll && !ret.contains(opt))
					ret.add(opt);
			}
			
		}

		return ret;
	}
	

	
}
