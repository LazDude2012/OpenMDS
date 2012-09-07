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

import chb.mods.mffs.common.network.NetworkHandler;
import ic2.api.ElectricItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemSwitch extends ItemMultitool {
	protected ItemSwitch(int id) {
		super(id, 1);
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

		TileEntity tileentity =  world
				.getBlockTileEntity(x,y,z);

		if(tileentity instanceof TileEntityProjector)

		{
		if(((TileEntityProjector)tileentity).getaccesstyp()== 2)
		{
		if(Linkgrid.getWorldMap(world).getGenerator().get(((TileEntityProjector)tileentity).getLinkGenerator_ID())!= null)
		{
		if(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getGenerator().get(((TileEntityProjector)tileentity).getLinkGenerator_ID()).getSecStation_ID()) != null)
		{
			if (!(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getGenerator().get(((TileEntityProjector)tileentity).getLinkGenerator_ID()).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
				if(world.isRemote)
				Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
				return false;
			}
		}
		}
		}

			if(((TileEntityProjector)tileentity).getswitchtyp() == 1)
			{
				if(ElectricItem.canUse(itemstack, 1000))
				{
				ElectricItem.use(itemstack, 1000, entityplayer);

				NetworkHandler.fireTileEntityEvent(tileentity,2);
				
				return true;
				}else{
					if(world.isRemote)
					Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: not enough EU please charge");
					return false;
				}
			}else{
				if(world.isRemote)
				Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: Wrong Mode");
				return false;
			}
		}

		if(tileentity instanceof TileEntityGenerator)

		{
			if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityGenerator)tileentity).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityGenerator)tileentity).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					if(world.isRemote)
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}

			if(((TileEntityGenerator)tileentity).getswitchtyp() == 1)
			{
				if(ElectricItem.canUse(itemstack, 1000))
				{
				ElectricItem.use(itemstack, 1000, entityplayer);
				NetworkHandler.fireTileEntityEvent(tileentity,1);

				return true;
				}else{
					if(world.isRemote)
					Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: not enough EU please charge");
					return false;
				}
			}else{
				if(world.isRemote)
				Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: component in wrong mode");
				return false;
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
		hand= new ItemStack(ModularForceFieldSystem.MFFSitemMFDidtool, 1);
		ElectricItem.charge(hand, powerleft, 1, true, false);
		return hand;
		}
		return itemstack;
	}


}
