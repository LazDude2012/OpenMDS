package OpenMDS.block;

import OpenMDS.common.OpenMDS;
import OpenMDS.item.ItemSpanner;
import OpenMDS.tile.TileAttunementBench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * The Block for the Attunement Bench.
 */
public class BlockAttunementBench extends BlockContainer
{
	public Icon[] icons;
	public BlockAttunementBench(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(OpenMDS.tabMDS);
		this.setUnlocalizedName("BlockAttunementBench");
	}



	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		icons = new Icon[4];
		icons[0]=register.registerIcon("OpenMDS:blockAttunementBenchTop");
		icons[1]=register.registerIcon("OpenMDS:blockAttunementBenchSide");
		icons[2]=register.registerIcon("OpenMDS:blockAttunementBenchTopActive");
		icons[3]=register.registerIcon("OpenMDS:blockAttunementBenchSideActive");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileAttunementBench tile = (TileAttunementBench)world.getBlockTileEntity(x,y,z);
		if(tile.getStackInSlot(0) != null)
		{
			if (side == 1) return icons[2];
			else return icons[3];
		} else {
			if(side == 1) return icons[0];
			else return icons[1];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		if(side == 1) return icons[0];
		else return icons[1];
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileAttunementBench();  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,int side, float hitX, float hitY, float hitZ)
	{
		if(player.getHeldItem() == new ItemStack(OpenMDS.itemDefenceSpanner,1)) return false;
		player.openGui(OpenMDS.instance,OpenMDS.ATTUNEBENCH_GUI,world,x,y,z);
		return true;
	}
}
