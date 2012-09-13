package chb.mods.mffs.common;

import java.util.LinkedList;

import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

public class TileEntityExtractorBC extends TileEntityExtractor implements IPowerReceptor,IOverrideDefaultTriggers{

	private IPowerProvider powerProvider;

public TileEntityExtractorBC(){
	
	super();
	
	powerProvider = PowerFramework.currentFramework.createPowerProvider();
	powerProvider.configure(10, 2, (int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5));
	
}
	
	
	@Override
	public void convertMJtoWorkEnergy(){
		
		if(this.getWorkEnergy() < this.getMaxWorkEnergy())
		{
	      float use = powerProvider.useEnergy(1, (float) (this.getMaxWorkEnergy() - this.getWorkEnergy() / 2.5), true);
		  
	      if(getWorkEnergy() + (use *2.5) > super.getMaxWorkEnergy())
	      {
	    	     setWorkEnergy(super.getMaxWorkEnergy()); 
	      }else{
	             setWorkEnergy((int) (getWorkEnergy() + (use *2.5)));
	      }
		  
		  System.out.println(super.getWorkEnergy());
	     
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
