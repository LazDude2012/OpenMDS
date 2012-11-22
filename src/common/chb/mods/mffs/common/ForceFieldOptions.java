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

import java.util.List;
import java.util.Map;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public final class ForceFieldOptions {
//----------------------------Player / Mob Attack Function----------------------

	public static void DefenseStation(TileEntity Entity, World world, String Typ,String Target)
	{

		if(Typ.equals("areadefense") && Entity instanceof TileEntityAreaDefenseStation)
		{
	      TileEntityAreaDefenseStation  DefenseStation  =  (TileEntityAreaDefenseStation)Entity;

			int xmin = DefenseStation.xCoord - DefenseStation.getDistance();
			int xmax = DefenseStation.xCoord + DefenseStation.getDistance();
			int ymin = DefenseStation.yCoord - DefenseStation.getDistance(); if(ymin<0){ymin = 0;}
	        int ymax = DefenseStation.yCoord + DefenseStation.getDistance(); if(ymax>255){ymax = 255;}
			int zmin = DefenseStation.zCoord - DefenseStation.getDistance();
			int zmax = DefenseStation.zCoord + DefenseStation.getDistance();

			List<EntityLiving> LivingEntitylist = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xmin, ymin, zmin, xmax, ymax, zmax));

			for (int i = 0; i < LivingEntitylist.size(); i++) {
				EntityLiving entityLiving = LivingEntitylist.get(i);

		if(Target.equals("human") && !(entityLiving instanceof EntityPlayer))
		{continue;}

		if(Target.equals("mobs") && !(entityLiving instanceof EntityMob) && !(entityLiving instanceof EntitySlime) && !(entityLiving instanceof EntityGhast))
		{continue;}

					 if(DefenseStation.getLinkPower() > (ModularForceFieldSystem.DefenseStationFPpeerAttack))
					 {
							if(Target.equals("mobs"))
							{
								Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation.getlinkCapacitors_ID())
								.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation
									.getlinkCapacitors_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));

							entityLiving.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.MobDefenseDamage);
							continue;
							}

							if(Target.equals("human") && Linkgrid.getWorldMap(world).getSecStation().get(DefenseStation.getSecStation_ID()) != null)
							{
								if(!SecurityHelper.isAccessGranted(DefenseStation, (EntityPlayer)entityLiving, world,"SR"))
								{
									Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation.getlinkCapacitors_ID())
									.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation
										.getlinkCapacitors_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));

									Functions.ChattoPlayer((EntityPlayer)entityLiving,"[Defence Area Station] !!! you  are in a restricted area !!! ");
									((EntityPlayer)entityLiving).inventory.dropAllItems();
									entityLiving.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.DefenseStationDamage);
									continue;
									}

							continue;
							}
					}
			}
		}
	}
	
	//-----------------------------------Block Protected by ForceField---------------------------------------------------------------------

		
	public static boolean BlockProtected(World world,int x, int y , int z, EntityPlayer entityplayer)
	{
		
		
		Map<Integer, TileEntityProjector> ProjectorinrangeMap =  Linkgrid.getWorldMap(world).getProjektor();
		for (TileEntityProjector tileentity : ProjectorinrangeMap.values()) {
			
			int dx = tileentity.xCoord - x;
			int dy = tileentity.yCoord - y;
			int dz = tileentity.zCoord - z;

			int dist = (int) Math.round(Math.sqrt(dx * dx + dy * dy + dz * dz));
			
			
			if(dist > 64 || !tileentity.isActive() ||tileentity.getProjektor_Typ() == 1 ||  tileentity.getProjektor_Typ() == 2)
			{continue;}

	Map<Integer, TileEntityProjector> InnerMap = null;
	InnerMap = Linkgrid.getWorldMap(world).getProjektor();
	
	for (TileEntityProjector tileentity2 : InnerMap.values()) {
		
		boolean logicswitch = tileentity2.equals(tileentity);
		
		if (logicswitch && tileentity2.isActive()) {
			
			 if(entityplayer != null)
			 {
				
			    	 if(!SecurityHelper.isAccessGranted(tileentity, entityplayer, world, "RPB",true))
			    	 { 
			         return true;
			    	 }
			    
			 }else{
				
				 return true;	 
			 }
		
		}
		
       	}

		}
		
		return false;
	}

}
