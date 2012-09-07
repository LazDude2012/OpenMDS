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

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockProjector extends BlockMFFSBase {
	public BlockProjector(int i, int texturindex) {
		super(i, texturindex);
		setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/projector.png";
	}

	public Integer getGui(World world, int i, int j, int k,
			EntityPlayer entityplayer) {
		return ModularForceFieldSystem.GUI_PROJECTOR;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k,
			EntityLiving entityliving) {
		TileEntityMaschines tileentityblock = (TileEntityMaschines) world
				.getBlockTileEntity(i, j, k);

		int l = MathHelper
				.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		int i1 = Math.round(entityliving.rotationPitch);
		if (i1 >= 65) {
			tileentityblock.setOrientation( 1);
		} else if (i1 <= -65) {
			tileentityblock.setOrientation(0);
		} else if (l == 0) {
			tileentityblock.setOrientation( 2);
		} else if (l == 1) {
			tileentityblock.setOrientation( 5);
		} else if (l == 2) {
			tileentityblock.setOrientation(3);
		} else if (l == 3) {
			tileentityblock.setOrientation(4);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityProjector();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9){
		if (entityplayer.isSneaking())
        {
			return false;
        }

		TileEntityProjector tileentity = (TileEntityProjector) world
				.getBlockTileEntity(i, j, k);

		if(tileentity.getaccesstyp()== 2)
		{
		if(Linkgrid.getWorldMap(world).getGenerator().get(tileentity.getLinkGenerator_ID())!= null)
		{
		if(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getGenerator().get(tileentity.getLinkGenerator_ID()).getSecStation_ID()) != null)
		{
			if (!(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getGenerator().get(tileentity.getLinkGenerator_ID()).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
				Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
				return false;
			}
		}
	}
		}
		if(tileentity.getaccesstyp()== 3)
		{
		if(Linkgrid.getWorldMap(world).getSecStation().get(tileentity.getSecStation_ID()) != null)
		{
			if (!(Linkgrid.getWorldMap(world).getSecStation().get(tileentity.getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
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
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardPowerLink)) {
			return false;
		}

		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemProjectorModuleBase)) {
			return false;
		}

		if(tileentity.isBurnout())
		{
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

	public void randomDisplayTick(World world, int i, int j, int k,
			Random random) {
		TileEntityProjector tileentity = (TileEntityProjector) world
				.getBlockTileEntity(i, j, k);

		if (tileentity.isBurnout()) {
			double d = i + Math.random();
			double d1 = j + Math.random();
			double d2 = k + Math.random();

			world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}
