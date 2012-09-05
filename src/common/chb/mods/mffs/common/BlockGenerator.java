package chb.mods.mffs.common;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockGenerator extends BlockMFFSBase {
	public BlockGenerator(int i, int texturindex) {
		super(i, texturindex);
		setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/generator.png";
	}

	public Integer getGui(World world, int i, int j, int k,
			EntityPlayer entityplayer) {
		return ModularForceFieldSystem.GUI_GENERATOR;
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
		return new TileEntityGenerator();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9){
		if (entityplayer.isSneaking())
        {
			return false;
        }

		TileEntityGenerator tileentity = (TileEntityGenerator) world
				.getBlockTileEntity(i, j, k);

		if(Linkgrid.getWorldMap(world).getSecStation().get(tileentity.getSecStation_ID()) != null)
		{
			if (!(Linkgrid.getWorldMap(world).getSecStation().get(tileentity.getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
				Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
				return false;
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
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardEmpty)) {
			return false;
		}

		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardSecurityLink)) {
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
