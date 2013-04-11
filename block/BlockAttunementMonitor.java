package OpenMDS.block;

import OpenMDS.common.OpenMDS;
import OpenMDS.tile.TileAttunementMonitor;
import OpenMDS.util.MDSUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockAttunementMonitor extends Block implements ITileEntityProvider
{
	public Icon[] icons = new Icon[4];
	public BlockAttunementMonitor(int i)
	{
		super(i, Material.rock);
		setUnlocalizedName("BlockAttunementMonitor");
		this.setCreativeTab(OpenMDS.tabMDS);
		setBlockBounds(0,0,0,1,0.5f,1);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileAttunementMonitor();
	}

	@Override
	public void registerIcons(IconRegister register)
	{
		icons[0]=register.registerIcon("OpenMDS:blockAttunementMonitorTop");
		icons[1]=register.registerIcon("OpenMDS:blockAttunementMonitorSide");
		icons[2]=register.registerIcon("OpenMDS:blockAttunementMonitorTopActive");
		icons[3]=register.registerIcon("OpenMDS:blockAttunementMonitorSideActive");
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {return true;}

	@Override
	public boolean canProvidePower() {return true;}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		if(side == ForgeDirection.DOWN) return true;
		else return false;
	}

	@Override
	public boolean isOpaqueCube() {return false;}


	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileAttunementMonitor tile = (TileAttunementMonitor)world.getBlockTileEntity(x,y,z);
		if(side==0 || side == 1)
		{
			if(tile.isEmitting) return icons[2];
			else return icons[0];
		}
		else
		{
			if(tile.isEmitting) return icons[3];
			else return icons[1];
		}
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		if(side == 0 || side == 1)
		{
			return icons[0];
		}
		else return icons[1];
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side)
	{
		TileAttunementMonitor tile = (TileAttunementMonitor)world.getBlockTileEntity(x,y,z);
		if(tile.isEmitting) return 15;
		else return 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
	{
		return isProvidingStrongPower(world,x,y,z,side);
	}

	public boolean isBlockNormalCube(World world, int x, int y, int z)
	{
		return false;
	}
}
