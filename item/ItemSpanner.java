package OpenMDS.item;

import OpenMDS.api.I6WayWrenchable;
import OpenMDS.common.OpenMDS;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemSpanner extends Item {
	public ItemSpanner(int id)
	{
		super(id);
		setMaxStackSize(1);
		setCreativeTab(OpenMDS.tabMDS);
		setUnlocalizedName("ItemDefenceSpanner");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister register)
	{
		this.iconIndex = register.registerIcon("OpenMDS:itemDefenceSpanner");
	}

	@Override
	public boolean onItemUse(ItemStack stackUsed, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		ForgeDirection dirToRotate= ForgeDirection.getOrientation(side);
		if(world.blockHasTileEntity(x,y,z))
		{
			TileEntity tile = world.getBlockTileEntity(x,y,z);
			if(tile instanceof I6WayWrenchable)
			{
				((I6WayWrenchable) tile).RotateTo(dirToRotate);
				world.markBlockForUpdate(x,y,z);
				return true;
			}
		}
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		player.addChatMessage("You can't turn that with your spanner.");
		return false;
	}

}
