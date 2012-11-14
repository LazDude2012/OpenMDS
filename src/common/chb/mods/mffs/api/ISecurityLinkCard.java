package chb.mods.mffs.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface ISecurityLinkCard {

	/**
	 * Simple Access to the Security System of MFFS
	 *
	 *Input:  itemstack    = instanceof SecurityLinkCard(Item) use getStackInSlot() for get
	 *        entityplayer = Player who Request Access
	 *        world        = the current world ;-)
	 *       
	 *       accessmode
	 *       ForceField Bypass =  "FFB"
     *       Edit MFFS Block = "EB"
     *       Config Security Rights = "CSR)"
     *       Stay Right =  "SR"
     *       Open Secure Storage = "OSS"
     *       Remote Protected Block = "RRB"
     *       
	 *        AccessErrorMessage =  if true write Error Massage on function return false
	 *        
	 *Output:  True =  Access Granted or (!on Error in Function!) // use isSecurityCardValidity for Check SecurityLinkCard Validity
	 *         False = Access denied return from Security Station
	 */
	

	 public boolean isAccessGranted(ItemStack itemstack, EntityPlayer entityplayer,World world, String accessmode,boolean AccessErrorMessage);
	
		/**
		 * Simple Check of SecurtiyCard Validity
		 *
		 *Input:  itemstack    = instanceof SecurityLinkCard(Item) use getStackInSlot() for get
		 *        world        = the current world ;-)
		 *        
		 *Output:  True = everything i.o. linked Security Station exists
		 *         False = Error :linked Security Station no longer exists  his  card belongs in the garbage
         */

	 public boolean  isSecurityCardValidity(ItemStack itemstack,World world); 
}
