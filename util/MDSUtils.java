package OpenMDS.util;

import OpenMDS.common.OpenMDS;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

public class MDSUtils
{
	public static ForgeDirection GetFDFromEntity(EntityLiving entity, Boolean inverted)
	{
		if(inverted)
		{
			if(entity.rotationPitch > 45) return ForgeDirection.UP;
			if(entity.rotationPitch < -45) return ForgeDirection.DOWN;
			int facing = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			switch(facing)
			{
				case 0:
					return ForgeDirection.NORTH;
				case 1:
					return ForgeDirection.EAST;
				case 2:
					return ForgeDirection.SOUTH;
				case 3:
					return ForgeDirection.WEST;
			}
		}
		else
		{
			if(entity.rotationPitch > 45) return ForgeDirection.DOWN;
			if(entity.rotationPitch < -45) return ForgeDirection.UP;
			int facing = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			switch(facing)
			{
				case 0:
					return ForgeDirection.SOUTH;
				case 1:
					return ForgeDirection.WEST;
				case 2:
					return ForgeDirection.NORTH;
				case 3:
					return ForgeDirection.EAST;
			}
		}
		throw new RuntimeException("The hell?");
	}
}
