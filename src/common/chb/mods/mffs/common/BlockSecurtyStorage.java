package chb.mods.mffs.common;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockSecurtyStorage extends BlockMFFSBase {

	public BlockSecurtyStorage(int i, int texturindex) {
		super(i, texturindex);
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

		TileEntitySecStorage tileentity = (TileEntitySecStorage) world.getBlockTileEntity(i, j, k);
		if(tileentity != null)
		{
			if(tileentity.getStackInSlot(0) !=null)
			{
			if(tileentity.getStackInSlot(0).getItem() instanceof ItemCardSecurityLink)
			{
				ItemCardSecurityLink card = (ItemCardSecurityLink) tileentity.getStackInSlot(0).getItem();
				
				if(card.isAccessGranted(tileentity.getStackInSlot(0), entityplayer, world, "OSS", true))
				{
					if (!world.isRemote)
						entityplayer.openGui(ModularForceFieldSystem.instance, ModularForceFieldSystem.GUI_SECSTORAGE, world,
								i, j, k);
					return true;
				}else{
					return false;
				}
			}
			}else{
				
				if (!world.isRemote)
					entityplayer.openGui(ModularForceFieldSystem.instance, ModularForceFieldSystem.GUI_SECSTORAGE, world,
							i, j, k);
				return true;
				
			}
		}
	
		
		return true;
		
		
	}


}
