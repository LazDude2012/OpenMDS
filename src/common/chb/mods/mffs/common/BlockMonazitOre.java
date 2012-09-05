package chb.mods.mffs.common;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockMonazitOre extends Block {
	public BlockMonazitOre(int i) {
		super(i, Material.rock);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundStoneFootstep);
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/forciciumore.png";
	}

    @Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
    	return 0;
	}
}
