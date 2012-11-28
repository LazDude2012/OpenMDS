package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class SecurityHelper {

	
	
	
	public static boolean isAccessGranted(TileEntity tileEntity,
			EntityPlayer entityplayer, World world, String right) {
		
		return isAccessGranted( tileEntity,
				 entityplayer,  world,  right,false);
	}
	
	
	public static boolean isAccessGranted(TileEntity tileEntity,
			EntityPlayer entityplayer, World world, String right,boolean suppresswarning) {
		
		

		if (tileEntity instanceof TileEntitySecStorage) {
			
			
			TileEntityAdvSecurityStation sec = ((TileEntitySecStorage)tileEntity).getLinkedSecurityStation();
			
			if(sec!=null){
				if(sec.isAccessGranted(entityplayer.username, right))
					return true;
				
				
				if (!suppresswarning)
					entityplayer.sendChatToPlayer("[Field Security] Fail: access denied");
				return false;
			}else{
				
				if (world.isRemote)
					return false;
				
				return true;
			}
		
		}


		if (tileEntity instanceof TileEntityAdvSecurityStation) {

			if (!(((TileEntityAdvSecurityStation) tileEntity).isAccessGranted(
					entityplayer.username, right))) {

				if (world.isRemote && !suppresswarning)
					Functions.ChattoPlayer(entityplayer,
							"[Field Security] Fail: access denied");
				return false;
			}
		}
		
		
		

		if (tileEntity instanceof TileEntityConverter) {
			
			TileEntityCapacitor cap = ((TileEntityConverter)tileEntity).getLinkedCapacitor();
			if(cap != null)
			{
				TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
				if(sec!=null)
				{
				  if(sec.isAccessGranted(entityplayer.username, right))
				  {
					  return true;
				  }else{
						if (!suppresswarning)
							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Fail: access denied");
					  return false;
				  }
					
				}
			}
			return true;
		}
			
	
		
		if (tileEntity instanceof TileEntityCapacitor) {
			
			TileEntityAdvSecurityStation sec = ((TileEntityCapacitor)tileEntity).getLinkedSecurityStation();
			if(sec!=null)
			{
			  if(sec.isAccessGranted(entityplayer.username, right))
			  {
				  return true;
			  }else{
					if (!suppresswarning)
						Functions.ChattoPlayer(entityplayer,
								"[Field Security] Fail: access denied");
				  return false;
			  }
				
			}
			return true;
		}
			
			

		

		if (tileEntity instanceof TileEntityExtractor) {
			
			TileEntityCapacitor cap = ((TileEntityExtractor)tileEntity).getLinkedCapacitor();
			if(cap != null)
			{
				TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
				if(sec!=null)
				{
				  if(sec.isAccessGranted(entityplayer.username, right))
				  {
					  return true;
				  }else{
						if (!suppresswarning)
							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Fail: access denied");
					  return false;
				  }
					
				}
			}
			return true;
			
			
		}

		if (tileEntity instanceof TileEntityAreaDefenseStation) {
			
				TileEntityAdvSecurityStation sec = ((TileEntityAreaDefenseStation)tileEntity).getLinkedSecurityStation();
				if(sec!=null)
				{
				  if(sec.isAccessGranted(entityplayer.username, right))
				  {
					  return true;
				  }else{
						if (!suppresswarning)
							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Fail: access denied");
					  return false;
				  }
					
				
			}
			return true;

		}
		


		if (tileEntity instanceof TileEntityProjector) {
		
			switch(((TileEntityProjector) tileEntity).getaccesstyp()){
			case 2:
				
				TileEntityCapacitor cap = ((TileEntityProjector)tileEntity).getLinkedCapacitor();
				if(cap != null)
				{
					TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
					if(sec!=null)
					{
					  if(sec.isAccessGranted(entityplayer.username, right))
					  {
						  return true;
					  }else{
							if (!suppresswarning)
								Functions.ChattoPlayer(entityplayer,
										"[Field Security] Fail: access denied");
						  return false;
					  }
						
					}
				}
				
				
			break;
			case 3:
				
				TileEntityAdvSecurityStation sec = ((TileEntityProjector)tileEntity).getLinkedSecurityStation();
				if(sec!=null)
				{
				  if(sec.isAccessGranted(entityplayer.username, right))
				  {
					  return true;
				  }else{
						if (!suppresswarning)
							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Fail: access denied");
					  return false;
				  }
			}
				
			break;
			}
			
			return true;
		}


		return true;
	}

}
