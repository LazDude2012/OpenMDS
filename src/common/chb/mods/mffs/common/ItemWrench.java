/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    Thunderdark - initial implementation
*/

package chb.mods.mffs.common;

import chb.mods.mffs.api.*;
import ic2.api.IWrenchable;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
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
		
		if (world.isRemote)
			return false;
		
		
		TileEntity tileentity =  world.getBlockTileEntity(x,y,z);
		


		if(tileentity instanceof IWrenchable)
		{
		
			
			if(ForceEnergyItems.use(stack, 1000, false,player))
			{
				
		
			
			if(((IWrenchable)tileentity).wrenchCanSetFacing(player, side))
			{
		
				
				if(tileentity instanceof TileEntityMachines)
				{
			
				
					if(((TileEntityMachines)tileentity).isActive())
					return false;
				}
				
				if(((IWrenchable)tileentity).getFacing() != side )
				{


					((IWrenchable)tileentity).setFacing((short) side);
					ForceEnergyItems.use(stack, 1000, true,player);
					return true;
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
			int powerleft = this.getForceEnergy(itemstack);
			ItemStack hand = entityplayer.inventory.getCurrentItem();
			hand= new ItemStack(ModularForceFieldSystem.MFFSitemSwitch, 1);
			ForceEnergyItems.charge(hand, powerleft,entityplayer);
		return hand;
		}
		return itemstack;
	}


}
