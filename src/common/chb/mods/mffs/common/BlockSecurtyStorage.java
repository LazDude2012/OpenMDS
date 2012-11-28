package chb.mods.mffs.common;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockSecurtyStorage extends BlockMFFSBase {

	public BlockSecurtyStorage(int i) {
		super(i);
		setRequiresSelfNotify();
		setBlockUnbreakable();	
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
	   return new TileEntitySecStorage();

	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/SecStorage.png";
	}

	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9) {
		
		if(world.isRemote)
			return true;

		TileEntitySecStorage tileentity = (TileEntitySecStorage) world.getBlockTileEntity(i, j, k);
		if(tileentity != null)
		{
			
			if(SecurityHelper.isAccessGranted(tileentity, entityplayer, world, "OSS"))
			{
				if (!world.isRemote)
					entityplayer.openGui(ModularForceFieldSystem.instance, 0, world,i, j, k);
				return true;
			}else{
				return false;
				}
			
		}else{
			
			return false;
		}
	  }
}
