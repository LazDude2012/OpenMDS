package OpenMDS.util;

import OpenMDS.api.IDefenceAttachment;
import OpenMDS.common.OpenMDS;
import OpenMDS.tile.TileDefenceComputer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

public class MDSUtils
{

	/**
	 * Takes an integer representing attunement, (0 - 4095) and returns the
	 * equivalent Colours array, (3 Colours objects).
	 * Thanks again to retep998 from EsperNet.
	 * @param attunement The integer, 0-4095 inclusive, to convert to colours.
	 * @return 3 Colours objects, neatly bound up in an array.
	 */
	public static Colours[] attunementToColours(int attunement)
	{
		int left = (attunement >> 8) & 0xf;
		int mid = (attunement >> 4) & 0xf;
		int right = (attunement) & 0xf;
		return new Colours[]{Colours.fromInt(left),Colours.fromInt(mid),Colours.fromInt(right)};
	}

	/**
	 * Takes an array of 3 Colours objects and returns the appropriate attunement integer.
	 * Thanks to retep998 from EsperNet; I owe you, buddy!
	 * @param colours The array of 3 Colours objects to convert.
	 * @return The int (0-4095) representing the attunement.
	 */
	public static int attunementFromColours(Colours[] colours)
	{
		int att = colours[0].ordinal() << 8 | colours[1].ordinal() << 4 | colours[2].ordinal();
		return att;
	}

	/**
	 * This is a helper method to return the approximate ForgeDirection an entity is facing.
	 * @param entity The EntityLiving to get a ForgeDirection from.
	 * @param inverted Whether to get the direction the entity is facing (useful for in-world GUIs) or the opposite direction. (useful for block placement)
	 * @return The ForgeDirection the entity is facing (or the opposite if needed).
	 */
	public static ForgeDirection GetFDFromEntity(EntityLiving entity, Boolean inverted)
	{
		if(inverted)
		{
			if(entity.rotationPitch > 60) return ForgeDirection.UP;
			if(entity.rotationPitch < -60) return ForgeDirection.DOWN;
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
			if(entity.rotationPitch > 60) return ForgeDirection.DOWN;
			if(entity.rotationPitch < -60) return ForgeDirection.UP;
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
	public static void CheckForAttachment(TileEntity origin)
	{
		if(origin instanceof TileDefenceComputer)
		{
			for(int x = origin.xCoord-1;x <= origin.xCoord+1; x++)
			{
				for(int y = origin.yCoord-1;y <= origin.yCoord +1; y++)
				{
					for(int z = origin.zCoord-1; z <= origin.zCoord +1; z++)
					{
						TileEntity te = origin.worldObj.getBlockTileEntity(x,y,z);
						if(te instanceof IDefenceAttachment)
						{
							((TileDefenceComputer)origin).Attach((IDefenceAttachment)te);
						}
					}
				}
			}
		}
		if(origin instanceof IDefenceAttachment)
		{
			for(int x = origin.xCoord-1;x <= origin.xCoord+1; x++)
			{
				for(int y = origin.yCoord-1;y <= origin.yCoord +1; y++)
				{
					for(int z = origin.zCoord-1; z <= origin.zCoord +1; z++)
					{
						if(origin.worldObj.blockHasTileEntity(x,y,z) == false) continue;
						TileEntity te = origin.worldObj.getBlockTileEntity(x,y,z);
						if(te instanceof TileDefenceComputer)
						{
							((TileDefenceComputer)te).Attach((IDefenceAttachment)origin);
						}
					}
				}
			}
		}
	}
}
