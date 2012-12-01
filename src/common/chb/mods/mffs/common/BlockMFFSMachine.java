package chb.mods.mffs.common;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

/**
 * To be used for future MFFS Machines in order to make new machines use only one ID
 * @author Matchlighter
 *
 */
public class BlockMFFSMachine extends BlockMFFSBase {

	public BlockMFFSMachine(int i) {
		super(i);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		MFFSFutureMachines mach = MFFSFutureMachines.values()[meta];
		return null;
	}
	
	@Override
	public String getTextureFile(){
		return "/chb/mods/mffs/sprites/blocks.png";
	}

	@Override
	public int getBlockTexture(IBlockAccess IBA, int x, int y, int z, int l){
		return 1;
	}
	
	@Override
	public int damageDropped(int i){
		return i;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List){
		for (MFFSFutureMachines mach : MFFSFutureMachines.values()) {
			par3List.add(new ItemStack(this, 1, mach.ordinal()));
		}
	}

}
