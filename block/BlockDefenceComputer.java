package OpenMDS.block;

import OpenMDS.api.I6WayWrenchable;
import OpenMDS.tile.TileDefenceComputer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockDefenceComputer extends BlockContainer
{
	public Icon normalicon,poweredsideicon,unpoweredsideicon;
	public BlockDefenceComputer(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public void registerIcons(IconRegister register)
	{
		this.normalicon=register.registerIcon("OpenMDS:blockDefenceComputerNormal");
		this.poweredsideicon = register.registerIcon("OpenMDS:blockDefenceComputerPoweredSide");
		this.unpoweredsideicon = register.registerIcon("OpenMDS:blockDefenceComputerUnpoweredSide");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileDefenceComputer tile = (TileDefenceComputer)world.getBlockTileEntity(x,y,z);
		ForgeDirection fd = ForgeDirection.getOrientation(side);
		if(tile.isAttached)
		{
			if(fd == tile.currentfacing) return poweredsideicon;
			else return normalicon;
		}
		else if(fd == tile.currentfacing) return unpoweredsideicon;
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
