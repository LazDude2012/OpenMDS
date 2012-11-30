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

package chb.mods.mffs.common.modules;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.src.Item;
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.PointXYZ;
import chb.mods.mffs.common.TileEntityProjector;
import chb.mods.mffs.common.IModularProjector.Slots;
import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldManipulator;
import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemProjectorModuleContainment extends Module3DBase {
	public ItemProjectorModuleContainment(int i) {
		super(i);
		setIconIndex(54);
	}
	

	
	@Override
	public void calculateField(IModularProjector projector, Set<PointXYZ> ffLocs, Set<PointXYZ> ffInterior) {
		
		int tpx = 0;
		int tpy = 0;
		int tpz = 0;
		
		int xMout = projector.countItemsInSlot(Slots.FocusLeft);
		int xPout = projector.countItemsInSlot(Slots.FocusRight);
		int zMout = projector.countItemsInSlot(Slots.FocusDown);
		int zPout = projector.countItemsInSlot(Slots.FocusUp);
		int distance = projector.countItemsInSlot(Slots.Distance);
		int Strength =  projector.countItemsInSlot(Slots.Strength) + 1;
		
		for (int y1 = 0; y1 <= Strength; y1++) {
		for (int x1 = 0 - xMout; x1 < xPout + 1; x1++) {
	    	for (int z1 = 0 - zPout; z1 < zMout + 1; z1++) {
				if (((TileEntityProjector)projector).getSide() == 0) {

					tpy = y1 - y1 - y1 - distance-1;
					tpx = x1;
					tpz = z1;
				}

				if (((TileEntityProjector)projector).getSide() == 1) {

					tpy = y1 + distance+1;
					tpx = x1;
					tpz = z1;
				}

				if (((TileEntityProjector)projector).getSide() == 2) {

					tpz = y1 - y1 - y1 - distance-1;
					tpy = z1 - z1 - z1;
					tpx = x1 - x1 - x1;
				}

				if (((TileEntityProjector)projector).getSide() == 3) {

					tpz = y1 + distance+1;
					tpy = z1 - z1 - z1;
					tpx = x1;
				}

				if (((TileEntityProjector)projector).getSide() == 4) {
                
					tpx = y1 - y1 - y1 - distance-1;
                    tpy = z1 - z1 - z1;
					tpz = x1;
				}
				if (((TileEntityProjector)projector).getSide() == 5) {

					tpx = y1 + distance+1;
                    tpy = z1 - z1 - z1;
					tpz = x1 -x1 - x1;
				}

				if(y1==0 || y1 == Strength || x1== 0 - xMout || x1==  xPout  || z1 == 0 - zPout || z1 == zMout)
				{
					ffLocs.add(new PointXYZ(tpx, tpy, tpz,0));
					
				}else {
					
					ffInterior.add(new PointXYZ(tpx, tpy, tpz,0));
				}
	    	}
		}
	}
	
   }
	
	@Override
	public boolean supportsOption(Item item) {
		

		if(item instanceof ItemProjectorOptionCamoflage) return true;
		if(item instanceof ItemProjectorOptionDefenseStation) return true;
		if(item instanceof ItemProjectorOptionFieldFusion) return true;
		if(item instanceof ItemProjectorOptionFieldManipulator) return true;
		if(item instanceof ItemProjectorOptionForceFieldJammer) return true;
		if(item instanceof ItemProjectorOptionMobDefence) return true;
		if(item instanceof ItemProjectorOptionSponge) return true;
		if(item instanceof ItemProjectorOptionBlockBreaker) return true;

		
		return false;
	}
	
}
