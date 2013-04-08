package OpenMDS.block;

import OpenMDS.common.OpenMDS;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAttunementMonitor extends Block implements ITileEntityProvider
{
	public BlockAttunementMonitor(int i)
	{
		super(i, Material.rock);
		setUnlocalizedName("BlockAttunementMonitor");
		this.setCreativeTab(OpenMDS.tabMDS);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
