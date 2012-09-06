package chb.mods.mffs.common;

import chb.mods.mffs.common.api.*;
import ic2.api.ElectricItem;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemWrench extends ItemMultitool  {
	protected ItemWrench(int id) {
		super(id, 0);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

		TileEntity tileentity =  world.getBlockTileEntity(x,y,z);

		if(tileentity instanceof IWrenchtool)
		{
			
			if(ElectricItem.canUse(stack, 500))
			{
			
			if(((IWrenchtool)tileentity).WrenchCanSetOrientation(player, side))
			{
				
				
				if(tileentity instanceof TileEntityMaschines)
				{
				
					if(((TileEntityMaschines)tileentity).isActive())
					return false;
				}
				
				if(((IWrenchtool)tileentity).getOrientation() != side )
				{

					((IWrenchtool)tileentity).setOrientation(side);
					ElectricItem.use(stack, 500,player);
					return true;
				}else{
					
					if(((IWrenchtool)tileentity).WrenchCanRemoveBlock(player))
					{

						world.setBlockWithNotify(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 0);
						
						if(!world.isRemote)
						world.spawnEntityInWorld(new EntityItem(world,
								(float) tileentity.xCoord, (float) tileentity.yCoord,
								(float) tileentity.zCoord, new ItemStack(((IWrenchtool)tileentity).getBlocktoDrop())));
						
						ElectricItem.use(stack, 500,player);
						return true;
					}
					
					
				}
			}
			}else{
				 if(world.isRemote)
				Functions.ChattoPlayer(player,"[MultiTool] Fail: not enough EU please charge");
			}
		}
		

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		if(entityplayer.isSneaking())
		{
		int powerleft = ElectricItem.discharge(itemstack, getMaxCharge(), 1, true, true);
		ItemStack hand = entityplayer.inventory.getCurrentItem();
		hand= new ItemStack(ModularForceFieldSystem.MFFSitemSwitch, 1);
		ElectricItem.charge(hand, powerleft, 1, true, false);
		return hand;
		}
		return itemstack;
	}


}
