package OpenMDS.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public interface IDefenceAttachment
{
	/*
	Documentation time! IDefenceAttachment is likely the only interface addon authors will need.
	'Tis fairly simple, but some things could use a bit of explaining.

	GetPriorityList() is the key bit. Basically, it's how the Defence Computer knows how to organise its GUI,
	how it knows how many slots it has, and what those slots are named. As the attachment, you'll have to handle the logic of getting
	attunements back from the defence computer and acting upon them.

	GetName() is what it says on the tin.

	The next two are for those attachments that have details the Defence Computer can't handle, like an ammunition inventory
	or such. Most attachments will probably have some sort of GUI.

	The LazDude recommended approach to an attachment is to have a similar Attach method to the one in TileDefenceComputer,
	storing the tile you're attached to, and opening *its* GUI when right-clicked. From the Defence Computer GUI, the user
	can click "Attachment Settings" to open your GUI.
	 */
	public String[] GetPriorityList();
	public String GetName();
	public boolean hasGui=false;
	public void OpenGui(EntityPlayer player);
}