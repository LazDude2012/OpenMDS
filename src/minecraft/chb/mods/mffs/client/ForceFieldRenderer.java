package chb.mods.mffs.client;

import chb.mods.mffs.common.ModularForceFieldSystem;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraftforge.client.ForgeHooksClient;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class ForceFieldRenderer implements ISimpleBlockRenderingHandler {
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if(block == ModularForceFieldSystem.MFFSFieldblock) {
			if(world.getBlockMetadata(x, y, z) != ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE)
			{
				if(world.getBlockMetadata(x, y, z)==ModularForceFieldSystem.FORCEFIELBOCKMETA_DEFAULT)
				{
					ForgeHooksClient.bindTexture("/chb/mods/mffs/sprites/blocks.png", 0);
					renderer.renderStandardBlock(block, x, y, z);
				}else{
				ForgeHooksClient.bindTexture("/chb/mods/mffs/sprites/blocks.png", 0);
				renderer.renderStandardBlock(block, x, y, z);
				}
			}else
			{
				ForgeHooksClient.bindTexture("/terrain.png", 0);
				renderer.renderStandardBlock(block, x, y, z);
			}

			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		return ModularForceFieldSystem.FORCEFIELDRENDER_ID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}
}
