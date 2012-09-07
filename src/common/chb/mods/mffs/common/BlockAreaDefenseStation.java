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

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockAreaDefenseStation extends BlockMFFSBase {
	public BlockAreaDefenseStation(int i, int texturindex) {
		super(i, texturindex);
		setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/DefenceStation.png";
	}

	public Integer getGui(World world, int i, int j, int k,
			EntityPlayer entityplayer) {
		return ModularForceFieldSystem.GUI_DEFSTATION;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAreaDefenseStation();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9){
		if (entityplayer.isSneaking())
        {
			return false;
        }

		TileEntity tileentity = world.getBlockTileEntity(i, j, k);

       if(tileentity != null)
       {
    	   TileEntitySecurityStation SecurityStation = Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityAreaDefenseStation)tileentity).getSecStation_ID());
		   if(SecurityStation !=null)
		   {
			   if(!SecurityStation.isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))
			   {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
			   }
		   }
       }

		if (entityplayer.getCurrentEquippedItem() != null
				&& entityplayer.getCurrentEquippedItem().itemID == Block.lever.blockID) {
			return false;
		}

		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemMultitool)) {
			return false;
		}

		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardSecurityLink)) {
			return false;
		}

		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardPowerLink)) {
			return false;
		}

		if (world.isRemote)
			return false;

		int gui = getGui(world, i, j, k, entityplayer);

		if (gui < 0)
			return false;

		entityplayer.openGui(ModularForceFieldSystem.instance, gui, world,
				i, j, k);
		return true;
	}
}
