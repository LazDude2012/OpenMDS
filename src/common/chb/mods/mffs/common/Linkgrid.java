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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.World;

public final class Linkgrid {
	private static Map WorldpowernetMap = new HashMap();

	static class Worldlinknet {
		private Map<Integer, TileEntityProjector> Projektor = new Hashtable<Integer, TileEntityProjector>();
		private Map<Integer, TileEntityGenerator> Generator = new Hashtable<Integer, TileEntityGenerator>();
		private Map<Integer, TileEntitySecurityStation> SecStation = new Hashtable<Integer, TileEntitySecurityStation>();
		private Map<Integer, TileEntityAreaDefenseStation> DefStation = new Hashtable<Integer, TileEntityAreaDefenseStation>();
		private Map<Integer, TileEntityProjector> Jammer = new Hashtable<Integer, TileEntityProjector>();
		private Map<Integer, TileEntityProjector> FieldFusion = new Hashtable<Integer, TileEntityProjector>();

		public Map<Integer, TileEntityProjector> getProjektor() {
			return Projektor;
		}

		public Map<Integer, TileEntityGenerator> getGenerator() {
			return Generator;
		}

		public Map<Integer, TileEntitySecurityStation> getSecStation() {
			return SecStation;
		}

		public Map<Integer, TileEntityAreaDefenseStation> getDefStation() {
			return DefStation;
		}

		public Map<Integer, TileEntityProjector> getJammer() {
			return Jammer;
		}

		public Map<Integer, TileEntityProjector> getFieldFusion() {
			return FieldFusion;
		}

		public int newGenerator_ID(TileEntityGenerator tileEntityGeneratorCore) {
			Random random = new Random();
			int tempGenerator_ID = random.nextInt();

			while (Generator.get(tempGenerator_ID) != null) {
				tempGenerator_ID = random.nextInt();
			}
			Generator.put(tempGenerator_ID, tileEntityGeneratorCore);
			return tempGenerator_ID;
		}

		public int newSecStation_ID(TileEntitySecurityStation tileEntity) {
			Random random = new Random();
			int tempSecStation_ID = random.nextInt();

			while (SecStation.get(tempSecStation_ID) != null) {
				tempSecStation_ID = random.nextInt();
			}
			SecStation.put(tempSecStation_ID, tileEntity);
			return tempSecStation_ID;
		}

		public int newDefStation_ID(TileEntityAreaDefenseStation tileEntity) {
			Random random = new Random();
			int tempDefStation_ID = random.nextInt();

			while (DefStation.get(tempDefStation_ID) != null) {
				tempDefStation_ID = random.nextInt();
			}
			DefStation.put(tempDefStation_ID, tileEntity);
			return tempDefStation_ID;
		}

		public static int myRandom(int low, int high) {
			return (int) (Math.random() * (high - low) + low);
		}

		public int condevisec(int Generator_ID, int xCoordr, int yCoordr,
				int zCoordr, int i) {
			int counter = 0;
			for (TileEntityProjector tileentity : Projektor.values()) {
				if (tileentity.getLinkGenerator_ID() == Generator_ID) {
					int dx = tileentity.xCoord - xCoordr;
					int dy = tileentity.yCoord - yCoordr;
					int dz = tileentity.zCoord - zCoordr;

					if (i >= Math.sqrt(dx * dx + dy * dy + dz * dz)) {
						counter++;
					}
				}
			}

			for (TileEntityAreaDefenseStation tileentity : DefStation.values()) {
				if (tileentity.getLinkGenerator_ID() == Generator_ID) {
					int dx = tileentity.xCoord - xCoordr;
					int dy = tileentity.yCoord - yCoordr;
					int dz = tileentity.zCoord - zCoordr;

					if (i >= Math.sqrt(dx * dx + dy * dy + dz * dz)) {
						counter++;
					}
				}
			}

			return counter;
		}
	}

	public static Worldlinknet getWorldMap(World world) {
		if (world != null) {
			if (!WorldpowernetMap.containsKey(world)) {
				WorldpowernetMap.put(world, new Worldlinknet());
			}
			return (Worldlinknet) WorldpowernetMap.get(world);
		}

		return null;
	}
}