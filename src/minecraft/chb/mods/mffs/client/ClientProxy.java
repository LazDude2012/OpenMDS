package chb.mods.mffs.client;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import chb.mods.mffs.common.CommonProxy;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.TileEntityGenerator;

public class ClientProxy extends CommonProxy {
	@Override

	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/DefenceStation.png");
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/projector.png");
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/SecurtyStation.png");
    	MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/generator.png");
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/items.png");
 		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/blocks.png");
 		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/forciciumore.png");
	}

	@Override
     public void registerTileEntitySpecialRenderer() {
     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGenerator.class, new TileEntityGeneratorRenderer());
     RenderingRegistry.registerBlockHandler(new ForceFieldRenderer());
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}