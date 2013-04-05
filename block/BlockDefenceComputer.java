package OpenMDS.block;

import OpenMDS.api.I6WayWrenchable;
import OpenMDS.common.MDSUtils;
import OpenMDS.common.OpenMDS;
import OpenMDS.tile.TileDefenceComputer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockDefenceComputer extends BlockContainer
{
	public Icon normalicon,poweredsideicon,unpoweredsideicon;
	public BlockDefenceComputer(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(OpenMDS.tabMDS);
		this.setUnlocalizedName("BlockDefenceComputer");
	}

	@Override
	public void registerIcons(IconRegister register)
	{
		this.normalicon=register.registerIcon("OpenMDS:blockDefenceComputerNormal");
		this.poweredsideicon = register.registerIcon("OpenMDS:blockDefenceComputerPoweredSide");
		this.unpoweredsideicon = register.registerIcon("OpenMDS:blockDefenceComputerUnpoweredSide");
	}

	@Override
	/**
	 * Called when the block is placed in the world.
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer, ItemStack placedItemStack)
	{
		TileDefenceComputer tile = (TileDefenceComputer)world.getBlockTileEntity(x,y,z);
		tile.RotateTo(MDSUtils.GetFDFromEntity(placer,true));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileDefenceComputer tile = (TileDefenceComputer)world.getBlockTileEntity(x,y,z);
		ForgeDirection fd = ForgeDirection.getOrientation(side);
		if(tile.isAttached)
		{
			if(fd == tile.GetCurrentFacing()) return poweredsideicon;
			else return normalicon;
		}
		else if(fd == tile.GetCurrentFacing()) return unpoweredsideicon;
		else return normalicon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return this.normalicon;
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileDefenceComputer();
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,int side, float hitX, float hitY, float hitZ)
	{
		if(player.getHeldItem()==new ItemStack(OpenMDS.itemDefenceSpanner, 1)) return false;
		TileDefenceComputer tile = (TileDefenceComputer)world.getBlockTileEntity(x,y,z);
		tile.OpenGui(world, player, x,y,z);
		return true;
	}
}
