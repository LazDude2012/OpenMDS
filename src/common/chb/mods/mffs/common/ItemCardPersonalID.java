package chb.mods.mffs.common;

import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ItemCardPersonalID extends Item {
	public ItemCardPersonalID(int i) {
		super(i);
		setIconIndex(18);
		setMaxStackSize(1);
	}
	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean isDamageable()
	{
	return true;
	}

    public static void setOwner(ItemStack itemStack, EntityPlayer entityplayer)
    {
        NBTTagCompound nbtTagCompound = Functions.getTAGfromItemstack(itemStack);
        nbtTagCompound.setString("name", entityplayer.username);
    }

    public static void setSeclevel(ItemStack itemStack,int SecLevel)
    {
        NBTTagCompound nbtTagCompound = Functions.getTAGfromItemstack(itemStack);
        nbtTagCompound.setInteger("SecLevel", SecLevel);
    }

    public static int getSecLevel(ItemStack itemStack)
    {
    	NBTTagCompound nbtTagCompound = Functions.getTAGfromItemstack(itemStack);
    	if(nbtTagCompound != null)
    	{
    		return nbtTagCompound.getInteger("SecLevel");
    	}
       return 0;
    }

    @Override
    public void addInformation(ItemStack itemStack, List info)
    {
            String tooltip = String.format( "Owner: %s ", Functions.getTAGfromItemstack(itemStack).getString("name") );
            info.add(tooltip);

            int SecLevel = Functions.getTAGfromItemstack(itemStack).getInteger("SecLevel");
            String SecLeveldesc ="";

            switch(SecLevel)
            {
            case ModularForceFieldSystem.PERSONALID_LIMITEDACCESS:
            	SecLeveldesc = "Restricted ForceField Enter Only";
            break;
            case ModularForceFieldSystem.PERSONALID_FULLACCESS:
            	SecLeveldesc = "Full Access";
            break;
            default:
            	SecLeveldesc = "ERROR Please re-coding";
            break;
            }
            tooltip = String.format( "Security Level: %s ", SecLeveldesc );
            info.add(tooltip);
    }
}
