
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
    
    Matchlighter
    Thunderdark 

 */

package chb.mods.mffs.common;

import net.minecraft.src.NBTTagCompound;

public class PointXYZ {
	
	public int X = 0;
	public int Y = 0;
	public int Z = 0;
	
	public PointXYZ(int x, int y, int z) {
		X=x;
		Y=y;
		Z=z;
	}
	
	public static PointXYZ fromNBT(NBTTagCompound nbtTag){
		return new PointXYZ(nbtTag.getInteger("X"),nbtTag.getInteger("Y"),nbtTag.getInteger("Z"));
	}
	
	public NBTTagCompound toNBTTagCompound(){
		NBTTagCompound nTag = new NBTTagCompound();
		nTag.setInteger("X", X);
		nTag.setInteger("Y", Y);
		nTag.setInteger("Z", Z);
		return nTag;
	}
	
	public PointXYZ toLocal(PointXYZ localizeTo){
		return new PointXYZ(X-localizeTo.X, Y-localizeTo.Y, Z-localizeTo.Z);
	}
	
	public PointXYZ toWorld(PointXYZ localTo){
		return new PointXYZ(X+localTo.X, Y+localTo.Y, Z+localTo.Z);
	}
	
	
	@Override
	public String toString(){
		return "X: " + X + " Y: " + Y + " Z: " + Z;
	}
	
}
