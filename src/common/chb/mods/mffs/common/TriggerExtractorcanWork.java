package chb.mods.mffs.common;

import net.minecraft.src.TileEntity;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;

public class TriggerExtractorcanWork implements ITrigger{

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTextureFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndexInTexture() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasParameter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTriggerActive(TileEntity tile, ITriggerParameter parameter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITriggerParameter createParameter() {
		// TODO Auto-generated method stub
		return null;
	}

}
