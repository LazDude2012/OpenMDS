package OpenMDS.block;

import OpenMDS.tile.TileDefenceComputer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 2/28/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockDefenceComputer extends BlockContainer
{
	public BlockDefenceComputer(int i)
	{
		super(i, Material.rock);
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileDefenceComputer();
	}
}
