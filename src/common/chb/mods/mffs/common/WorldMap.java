package chb.mods.mffs.common;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import net.minecraft.src.World;

public final class WorldMap {
	private static Map ForceFieldWorlds = new HashMap();

	static class ForceFieldWorld {
		private static Map<Integer, ForceFieldBlockStack> ForceFieldStackMap = new Hashtable<Integer, ForceFieldBlockStack>();

		public ForceFieldBlockStack getorcreateFFStackMap(int x, int y, int z) {
			if (ForceFieldStackMap.get(Cordhash( x,  y,  z)) == null) {
					ForceFieldStackMap.put(Cordhash( x,  y,  z), new ForceFieldBlockStack(x,y, z));
            }
			return ForceFieldStackMap.get(Cordhash( x,  y,  z));
		}

		public ForceFieldBlockStack getForceFieldStackMap(Integer hasher) {
			return ForceFieldStackMap.get(hasher);
		}

		public ForceFieldBlockStack getForceFieldStackMap(int x, int y, int z) {
			return ForceFieldStackMap.get(Cordhash( x,  y,  z));
		}

		public int isExistForceFieldStackMap(int x, int y, int z, int counter, int typ)
		{
			switch(typ)
			{
			case 0:
				y += counter;
				break;
			case 1:
				y -= counter;
				break;
			case 2:
				z += counter;
				break;
			case 3:
				z -= counter;
				break;
		    case 4:
		    	x += counter;
		    	break;
		    case 5:
		    	x -= counter;
		    	break;
			}

			ForceFieldBlockStack Map = ForceFieldStackMap.get(Cordhash( x,  y,  z));

			if (Map == null) {
				return 0;
			} else {
				if(Map.isEmpty())
				{
					return 0;
				}

				return Map.getGenratorID();
			}
		}
	}

	public static ForceFieldWorld getForceFieldWorld(World world) {
		if (world != null) {
			if (!ForceFieldWorlds.containsKey(world)) {
				ForceFieldWorlds.put(world, new ForceFieldWorld());
			}
			return (ForceFieldWorld) ForceFieldWorlds.get(world);
		}

		return null;
	}

	public static int Cordhash(int x, int y, int z)
	{
		return new String().concat(String.valueOf(x)).concat(String.valueOf(y)).concat(String.valueOf(z)).hashCode();
	}
}
