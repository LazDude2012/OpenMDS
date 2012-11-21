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
		if(Typ.equals("projector") && Entity instanceof TileEntityProjector)
		{
		 TileEntityProjector  projector =  (TileEntityProjector) Entity;

		if(projector.isActive())
		{
			int xmin=0;int xmax=0;int ymin=0;int ymax=0;int zmin=0;int zmax=0;

			if(projector.getProjektor_Typ() == 4 || projector.getProjektor_Typ() == 5)
			{
				 xmin = projector.xCoord - projector.getForceField_distance();
				 xmax = projector.xCoord + projector.getForceField_distance();
				 ymin = projector.yCoord - projector.getForceField_distance();if(ymin<0){ymin = 0;}
		         ymax = projector.yCoord + projector.getForceField_distance(); if(ymax>255){ymax = 255;}
				 zmin = projector.zCoord - projector.getForceField_distance();
				 zmax = projector.zCoord + projector.getForceField_distance();
			}

			if(projector.getProjektor_Typ() == 7)
			{
				switch(projector.getSide())
				{
				case 0:
					ymax = projector.yCoord +1;
					ymin = projector.yCoord - projector.getForceField_strength();
					xmax = projector.xCoord + projector.getFocusleft();
					xmin = projector.xCoord - projector.getFocusright();
					zmin = projector.zCoord - projector.getFocusup();
					zmax = projector.zCoord	+ projector.getFocusdown();
				break;
				case 1:
					ymin = projector.yCoord -1;
					ymax = projector.yCoord + projector.getForceField_strength();
					xmin = projector.xCoord - projector.getFocusleft();
					xmax = projector.xCoord + projector.getFocusright();
					zmin = projector.zCoord - projector.getFocusup();
					zmax = projector.zCoord	+ projector.getFocusdown();
				break;
				case 2:
					zmax = projector.zCoord +1;
					zmin = projector.zCoord - projector.getForceField_strength();
					xmax = projector.xCoord + projector.getFocusleft();
					xmin = projector.xCoord - projector.getFocusright();
					ymax = projector.yCoord + projector.getFocusup();
					ymin = projector.yCoord	- projector.getFocusdown();
				break;
				case 3:
					zmin = projector.zCoord -1;
					zmax = projector.zCoord + projector.getForceField_strength();
					xmax = projector.xCoord + projector.getFocusleft();
					xmin = projector.xCoord - projector.getFocusright();
					ymax = projector.yCoord + projector.getFocusup();
					ymin = projector.yCoord	- projector.getFocusdown();
				break;
				case 4:
					xmax = projector.xCoord +1;
					xmin = projector.xCoord - projector.getForceField_strength();
					zmin = projector.zCoord - projector.getFocusleft();
					zmax = projector.zCoord + projector.getFocusright();
					ymin = projector.yCoord - projector.getFocusup();
					ymax = projector.yCoord	+ projector.getFocusdown();
				break;
				case 5:
					xmin = projector.xCoord -1;
					xmax = projector.xCoord + projector.getForceField_strength();
					zmax = projector.zCoord + projector.getFocusleft();
					zmin = projector.zCoord - projector.getFocusright();
					ymin = projector.yCoord - projector.getFocusup();
					ymax = projector.yCoord	+ projector.getFocusdown();
				break;
				}
			}

			List<EntityLiving> LivingEntitylist = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xmin, ymin, zmin, xmax, ymax, zmax));

				for (int i = 0; i < LivingEntitylist.size(); i++) {
					EntityLiving entityLiving = LivingEntitylist.get(i);

			if(Target.equals("human") && !(entityLiving instanceof EntityPlayer))
			{continue;}

			if(Target.equals("mobs") && !(entityLiving instanceof EntityMob) && !(entityLiving instanceof EntitySlime) && !(entityLiving instanceof EntityGhast))
			{continue;}

			if (projector.getProjektor_Typ()==5  && entityLiving.getDistance(projector.xCoord, projector.yCoord,
					projector.zCoord) > projector.getForceField_distance()) {
				continue;
			}

			if((projector.getProjektor_Typ()==4 || projector.getProjektor_Typ()==5) && projector.isOptionFieldcut() && entityLiving.posY  < projector.yCoord)
			{
			   continue;
			}

			if(projector.getProjektor_Typ()==7 && projector.isOptionFieldcut())
			{
				switch(projector.getSide())
				{
				case 0:
					if(entityLiving.posY > projector.yCoord+2 )
					{
					continue;}
				break;
				case 1:
					if(entityLiving.posY <  projector.yCoord )
					{
					continue;}
				break;
				case 2:
					if(entityLiving.posZ > projector.zCoord +1 )
					{
					continue;}
				break;
				case 3:
					if(entityLiving.posZ < projector.zCoord )
					{
					continue;}
				break;
				case 4:
					if(entityLiving.posX > projector.xCoord+1 )
					{
					continue;}
				break;
				case 5:
					if(entityLiving.posX < projector.xCoord )
					{
					continue;}
				break;
				}
			}

        		if (projector.getLinkPower() > ModularForceFieldSystem.DefenseStationFPpeerAttack) {
					if(Target.equals("mobs"))
					{
						Linkgrid.getWorldMap(world).getCapacitor().get(projector.getLinkCapacitor_ID())
						.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(projector
							.getLinkCapacitor_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));

					
						
					entityLiving.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.MobDefenseDamage);
					continue;
					}

					if(Target.equals("human"))
					{
						boolean killswitch = false;

						if(projector.getaccesstyp()==2)
						{
							TileEntityCapacitor Generator = Linkgrid.getWorldMap(world).getCapacitor().get(projector.getLinkCapacitor_ID());
							if(Generator != null)
							{
							TileEntityAdvSecurityStation SecurityStation = Linkgrid.getWorldMap(world).getSecStation().get(Generator.getSecStation_ID());

							if(SecurityStation != null)
							{
							killswitch = !SecurityStation.isAccessGranted(((EntityPlayer)entityLiving).username,"SR");
							}
							}
							}
						if(projector.getaccesstyp()==3)
						{
							TileEntityAdvSecurityStation SecurityStation = Linkgrid.getWorldMap(world).getSecStation().get(projector.getSecStation_ID());
							if(SecurityStation != null)
							{
							killswitch = !SecurityStation.isAccessGranted(((EntityPlayer)entityLiving).username,"SR");
							}
						}

						if (killswitch)
							{
							Linkgrid.getWorldMap(world).getCapacitor().get(projector.getLinkCapacitor_ID())
							.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(projector
								.getLinkCapacitor_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));
//							EntityPlayer mainuser = world.getPlayerEntityByName(Linkgrid.getWorldMap(world).getSecStation().get(projector.getSecStation_ID()).getMainUser());
//							if(mainuser != null)
//							{
//							 Functions.ChattoPlayer(mainuser,"[Defence Area Station] Attack " + ((EntityPlayer)entityLiving).username+ " at x:" +  ((EntityPlayer)entityLiving).posX + " y:" + 
//							           ((EntityPlayer)entityLiving).posY+ " z:"+((EntityPlayer)entityLiving).posZ);	
//							}
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
