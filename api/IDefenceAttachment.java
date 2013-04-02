package OpenMDS.api;

import net.minecraft.inventory.IInventory;

public interface IDefenceAttachment
{
	public String[] GetPriorityList();
	public String GetName();
	public boolean hasGui=false;
	public void OpenGui();
}