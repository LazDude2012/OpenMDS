package OpenMDS.api;

import OpenMDS.tile.TileDefenceComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

/**
 * IDefenceAttachment is (probably) the only interface addon authors will really need.
 * Remember, your attachments shouldn't deal with attunements at all, merely read them from the attached Defence Computer.
 * This is the interface for the tile entity.
 * Remember, for anything to work right, you should definitely call MDSUtils.CheckForAttachment(this); at some point during your TileEntity's
 * initialisation process.
 */
public interface IDefenceAttachment
{
	/**
	 * A way to store the defence computer providing attunements (and in the future, BC power) to your attachment.
	 */
	public TileDefenceComputer attachedTo = null;

	/**
	 * GetPriorityList() is one of the primary interactions with the Defence Computer. It's the computer's way of displaying what your attachment
	 * will do with each attunement it has. Again, it's your responsibility to get the attunements back from the computer and actually act on them.
	 * The Defence Computer is simply a mediator between attunements and you.
	 * @return A string array, containing the names of your priorities.
	 */
	public String[] GetPriorityList();

	/**
	 * Self explanatory.
	 * @return The name of the attachment.
	 */
	public String GetName();

	/**
	 * hasGui is also self-explanatory. If your attachment needs more than simply an attunement, and has its own GUI, then set hasGUI = true.
	 * Most attachments will have some sort of GUI.
	 */
	public boolean hasGui=true;

	/**
	 * Opens the attachment's GUI.
	 * Why have this? It seems like you could just open the GUI when your attachment's block is right-clicked.
	 * Well, the LazDude recommended approach to an attachment is to have a similar Attach method to the one in TileDefenceComputer,
	 * storing the tile you're attached to, and opening *its* GUI when right-clicked. From the Defence Computer GUI, the user
	 * can click "Attachment Settings" to open your GUI.
	 * @param player The player to display the GUI to.
	 */
	public void OpenGui(EntityPlayer player);

	/**
	 * Attaches to a defence computer.
	 * @param computer The computer to attach to.
	 */
	public void Attach(TileDefenceComputer computer);
}
