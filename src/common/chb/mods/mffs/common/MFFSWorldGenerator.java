package chb.mods.mffs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class MFFSWorldGenerator implements IWorldGenerator{
		@Override
		public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
		{
			chunkX = chunkX << 4;
			chunkZ = chunkZ << 4;

	                	WorldGenMinable worldGenMinable = new WorldGenMinable(ModularForceFieldSystem.MFFSMonazitOre.blockID, 0, 8);

	                    for (int i = 0; i < 8; i++)
	                    {
	                        int x = chunkX + rand.nextInt(16);
	                        int y = rand.nextInt(80) + 0;
	                        int z = chunkZ + rand.nextInt(16);

	            			int randAmount = rand.nextInt(8);

	                    	worldGenMinable.generate(world, rand, x, y, z);
	                    }
		}
	}
