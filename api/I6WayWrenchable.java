package OpenMDS.api;


import net.minecraftforge.common.ForgeDirection;

public interface I6WayWrenchable
{
	ForgeDirection currentfacing = ForgeDirection.UNKNOWN;
	public void RotateTo(ForgeDirection direction);
	public ForgeDirection GetCurrentFacing();
}
