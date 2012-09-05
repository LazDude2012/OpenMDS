package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemCardPowerLink extends Item  {
	private StringBuffer info = new StringBuffer();

	public ItemCardPowerLink(int i) {
		super(i);
		setIconIndex(17);
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
			if (tileEntity instanceof TileEntityProjector) {
				TileEntityGenerator Generator = Linkgrid.getWorldMap(world).getGenerator().get(((TileEntityProjector)tileEntity).getLinkGenerator_ID());

				if(Generator!= null)
				{
				TileEntitySecurityStation SecurityStation  = Linkgrid.getWorldMap(world).getSecStation().get(Generator.getSecStation_ID());

				if(SecurityStation  != null)
				{
					if (!(SecurityStation.isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
						return false;
					}
				}
			}

				if(((TileEntityProjector)tileEntity).getStackInSlot(0)==null)
				{
					((TileEntityProjector)tileEntity).setInventorySlotContents(0,itemstack);
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
					Functions.ChattoPlayer(entityplayer, "[Projector] Success: <Power-Link> Card installed");
					return true;
				}
				else
				{
					Functions.ChattoPlayer(entityplayer, "[Projector] Fail: Slot is not empty");
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

				if(((TileEntityAreaDefenseStation)tileEntity).getStackInSlot(0)==null)
				{
					((TileEntityAreaDefenseStation)tileEntity).setInventorySlotContents(0,itemstack);
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
					Functions.ChattoPlayer(entityplayer, "[Defence Station] Success: <Power-Link> Card installed");
					return true;
				}
				else
				{
					Functions.ChattoPlayer(entityplayer, "[Defence Station] Fail: Slot is not empty");
					return false;
				}
			}
		}
		return false;
	}
}
