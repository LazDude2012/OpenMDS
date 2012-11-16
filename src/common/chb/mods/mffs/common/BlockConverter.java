package chb.mods.mffs.common;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockConverter extends BlockMFFSBase {
	
	public BlockConverter(int i) {
		super(i);
		setRequiresSelfNotify();
	}

	
	@Override
	public String getTextureFile() {

		return "/chb/mods/mffs/sprites/Converter.png";
	}
	
	
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		
         return new TileEntityConverter();
		
	}

}
