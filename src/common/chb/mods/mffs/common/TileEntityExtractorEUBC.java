package chb.mods.mffs.common;

import java.util.LinkedList;

import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergySink;
import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

public class TileEntityExtractorEUBC extends TileEntityExtractor implements 
IPowerReceptor,IOverrideDefaultTriggers,IEnergySink{

	private IPowerProvider powerProvider;
	private boolean addedToEnergyNet;
	
public 	TileEntityExtractorEUBC()
{
	super();
	addedToEnergyNet = false;
	EnergyNet.getForWorld(worldObj).addTileEntity(this);
	powerProvider = PowerFramework.currentFramework.createPowerProvider();
	powerProvider.configure(10, 2, (int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5));
	
}


@Override
public void updateEntity() {
	
	if (!addedToEnergyNet) {
		EnergyNet.getForWorld(worldObj).addTileEntity(this);
		addedToEnergyNet = true;
		}
	super.updateEntity();

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
public void converMJtoWorkEnergy(){
	
	if(this.getWorkEnergy() < this.getMaxWorkEnergy())
	{
      float use = powerProvider.useEnergy(1, (float) (this.getMaxWorkEnergy() - this.getWorkEnergy() / 2.5), true);
	  
      if(getWorkEnergy() + (use *2.5) > super.getMaxWorkEnergy())
      {
    	     setWorkEnergy(super.getMaxWorkEnergy()); 
      }else{
             setWorkEnergy((int) (getWorkEnergy() + (use *2.5)));
      }
	  

	}
	
}

@Override
public void setPowerProvider(IPowerProvider provider) {
	this.powerProvider = provider;
}

@Override
public IPowerProvider getPowerProvider() {
	return powerProvider;
}


@Override
public void doWork() {}


@Override
public int powerRequest() {
	
	double workEnergyinMJ = super.getWorkEnergy()  / 2.5;
	double MaxWorkEnergyinMj = super.getMaxWorkEnergy()  / 2.5;
	
	return  (int) (MaxWorkEnergyinMj - workEnergyinMJ) ;
}

@Override
public LinkedList<ITrigger> getTriggers() {
	return null;
}









	
}
