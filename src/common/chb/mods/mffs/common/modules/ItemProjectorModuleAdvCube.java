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
import java.util.List;
import java.util.Set;

import chb.mods.mffs.common.IModularProjector.Slots;
import chb.mods.mffs.common.modules.ModuleBase;
import chb.mods.mffs.common.modules.Module3DBase;
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.PointXYZ;
import chb.mods.mffs.common.TileEntityProjector;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemProjectorModuleAdvCube extends Module3DBase {
	public ItemProjectorModuleAdvCube(int i) {
		super(i);
		setIconIndex(55);
	}
	
	public boolean supportsDistance = false;
	

	
	@Override
	public void calculateField(IModularProjector projector, Set<PointXYZ> ffLocs, Set<PointXYZ> ffInterior) {
		
		int xMout = projector.countItemsInSlot(Slots.FocusLeft) + 1;
		int xPout = projector.countItemsInSlot(Slots.FocusRight) + 1;
		
		int yMout = projector.countItemsInSlot(Slots.Strength) + 1;
		int yPout = projector.countItemsInSlot(Slots.Strength) + 1;
		
		int zMout = projector.countItemsInSlot(Slots.FocusDown) + 1;
		int zPout = projector.countItemsInSlot(Slots.FocusUp) + 1;
		
		for (int y1 = -yMout; y1 <= yPout; y1++) {
			for (int x1 = -xMout; x1 <= xPout; x1++) {
		    	for (int z1 = -zMout; z1 <= zPout; z1++) {
		    		if ((x1==xPout || x1==-xMout) || (y1==yPout || y1==-yMout) || (z1==zPout || z1==-zMout))
		    			ffLocs.add(new PointXYZ(x1, y1, z1));
		    		else
		    			ffInterior.add(new PointXYZ(x1, y1, z1));
		    	}
			}
		}
		
	}


	
}