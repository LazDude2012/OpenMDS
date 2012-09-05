package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemCardSecurityLink extends Item  {
	private StringBuffer info = new StringBuffer();

	public ItemCardSecurityLink(int i) {
		super(i);
		setIconIndex(19);
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
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer,
			World world, int i, int j, int k, int l) {
		TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

		if (!world.isRemote) {
			if (tileEntity instanceof TileEntityGenerator) {
				if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityGenerator)tileEntity).getSecStation_ID()) != null)
				{
					if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityGenerator)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
						return false;
					}
				}

				if(((TileEntityGenerator)tileEntity).getStackInSlot(4)==null)
				{
					((TileEntityGenerator)tileEntity).setInventorySlotContents(4,itemstack);
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
					Functions.ChattoPlayer(entityplayer, "[Generator] Success: <Security Station Link> Card installed");
					return true;
				}
				else
				{
					Functions.ChattoPlayer(entityplayer, "[Generator] Fail: Slot is not empty");
					return false;
				}
			}

			if (tileEntity instanceof TileEntityAreaDefenseStation) {
				if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityAreaDefenseStation)tileEntity).getSecStation_ID()) != null)
				{
					if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityAreaDefenseStation)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
						return false;
					}
				}

				if(((TileEntityAreaDefenseStation)tileEntity).getStackInSlot(1)==null)
				{
					((TileEntityAreaDefenseStation)tileEntity).setInventorySlotContents(1,itemstack);
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
					Functions.ChattoPlayer(entityplayer, "[Defence Station] Success: <Security Station Link> Card installed");
					return true;
				}
				else
				{
					Functions.ChattoPlayer(entityplayer, "[Defence Station] Fail: Slot is not empty");
					return false;
				}
			}

			if (tileEntity instanceof TileEntityProjector) {
				if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityProjector)tileEntity).getSecStation_ID()) != null)
				{
					if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityProjector)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
						return false;
					}
				}

				if(((TileEntityProjector)tileEntity).getStackInSlot(12)==null)
				{
					((TileEntityProjector)tileEntity).setInventorySlotContents(12,itemstack);
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
					Functions.ChattoPlayer(entityplayer, "[Projector] Success: <Security Station Link> Card installed");
					return true;
				}
				else
				{
					Functions.ChattoPlayer(entityplayer, "[Projector] Fail: Slot is not empty");
					return false;
				}
			}
		}

	return false;
}
}