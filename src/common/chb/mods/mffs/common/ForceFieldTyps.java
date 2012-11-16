package chb.mods.mffs.common;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.TileEntity;

public enum ForceFieldTyps {
Default(1),
Zapper(3),
Area(1),
Containment(1),
Camouflage(2);
	
int costmodi;


private ForceFieldTyps(int costmodi) {

	
	this.costmodi = costmodi;

}


}
