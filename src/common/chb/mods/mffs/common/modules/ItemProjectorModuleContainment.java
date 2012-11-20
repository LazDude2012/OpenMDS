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
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.PointXYZ;
import chb.mods.mffs.common.IModularProjector.Slots;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemProjectorModuleContainment extends Module3DBase {
	public ItemProjectorModuleContainment(int i) {
		super(i);
		setIconIndex(54);
	}
	

	
	@Override
	public void calculateField(IModularProjector projector, Set<PointXYZ> ffLocs, Set<PointXYZ> ffInterior) {
		
		int out = projector.countItemsInSlot(Slots.Strength) + 1;
		
		for (int y1 = 0; y1 <= out+2; y1++) {
			for (int x1 = -out; x1 <= out; x1++) {
		    	for (int z1 = -out; z1 <= out; z1++) {
		    		if ((x1==out || x1==-out) || (y1==out || y1==0) || (z1==out || z1==-out))
		    			ffLocs.add(new PointXYZ(x1, y1+projector.countItemsInSlot(Slots.Distance)+1, z1));
		    		else
		    			ffInterior.add(new PointXYZ(x1, y1+projector.countItemsInSlot(Slots.Distance)+1, z1));
		    	}
			}
		}
	}
	


}