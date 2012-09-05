package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemProjectorModuleBase extends Item  {
	public ItemProjectorModuleBase(int i) {
		super(i);
		setMaxStackSize(1);
		setTabToDisplayOn(CreativeTabs.tabMaterials);
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
				  if(((TileEntityProjector)tileEntity).getaccesstyp()==2)
				  {
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
				 }

				 if(((TileEntityProjector)tileEntity).getaccesstyp()==3)
				 {
						TileEntitySecurityStation SecurityStation  = Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityProjector)tileEntity).getSecStation_ID());

						if(SecurityStation  != null)
						{
							if (!(SecurityStation.isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
								return false;
							}
						}
				}

				if(((TileEntityProjector)tileEntity).getStackInSlot(1)==null)
				{
					((TileEntityProjector)tileEntity).setInventorySlotContents(1,itemstack);
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
					Functions.ChattoPlayer(entityplayer, "[Projector] Success: <Projector Module > installed");
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