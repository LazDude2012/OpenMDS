package OpenMDS.block;

import OpenMDS.tile.TileAttunementBench;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAttunementBench extends BlockContainer
{
	public BlockAttunementBench(int i)
	{
		super(i, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileAttunementBench();  //To change body of implemented methods use File | Settings | File Templates.
	}
}
