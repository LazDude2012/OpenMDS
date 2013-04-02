package OpenMDS.block;

import OpenMDS.tile.TileDefenceComputer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
	public void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, float hitX, float hitY, float hitZ)
	{
		TileDefenceComputer tile = (TileDefenceComputer)world.getBlockTileEntity(x,y,z);
		if(tile.isAttached)
		{
			tile.OpenGui(world,player,x,y,z);
		}else{
			player.addChatMessage("This defence computer doesn't have an attachment.");
		}
	}
}
