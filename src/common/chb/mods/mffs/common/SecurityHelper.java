package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class SecurityHelper {
	
	
	public static boolean isAccessGranted(TileEntity tileEntity,EntityPlayer entityplayer,World world)
	{
		if (tileEntity instanceof TileEntitySecurityStation){
			
			if(((TileEntitySecurityStation)tileEntity).isActive())
			{
			 if (!(((TileEntitySecurityStation)tileEntity).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
				 Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
				return false;
			 }
			}
		}
		
		if (tileEntity instanceof TileEntityCapacitor){
			
			if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityCapacitor)tileEntity).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityCapacitor)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
				
						Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					
					return false;
				}
			}
		}

		if (tileEntity instanceof TileEntityExtractor) {
			
			if(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityExtractor)tileEntity).getLinkCapacitors_ID())!= null)
			{
			if(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityExtractor)tileEntity).getLinkCapacitors_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityExtractor)tileEntity).getLinkCapacitors_ID()).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}
		}
		}
		
		if (tileEntity instanceof TileEntityProjector) {
			
			if(((TileEntityProjector)tileEntity).getaccesstyp()== 2)
			{
			if(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityProjector)tileEntity).getLinkCapacitor_ID())!= null)
			{
			if(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityProjector)tileEntity).getLinkCapacitor_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityProjector)tileEntity).getLinkCapacitor_ID()).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}
		}
			}
			if(((TileEntityProjector)tileEntity).getaccesstyp()== 3)
			{
			if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityProjector)tileEntity).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityProjector)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}
		}
			
		}
		
		if (tileEntity instanceof TileEntityAreaDefenseStation) {
			
		       if(tileEntity != null)
		       {
		    	   TileEntitySecurityStation SecurityStation = Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityAreaDefenseStation)tileEntity).getSecStation_ID());
				   if(SecurityStation !=null)
				   {
					   if(!SecurityStation.isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))
					   {
						   Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
							return false;
					   }
				   }
		       }
		}
	

		return true;
	}

}