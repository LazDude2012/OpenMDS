package chb.mods.mffs.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemCardEmpty extends Item {
	public ItemCardEmpty(int i) {
		super(i);
		setIconIndex(16);
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
			if (tileEntity instanceof TileEntityGenerator) {
				if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityGenerator)tileEntity).getSecStation_ID()) != null)
				{
					if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityGenerator)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,2))) {
						return false;
					}
				}

				ItemStack newcard =  new ItemStack(ModularForceFieldSystem.MFFSitemfc);
				Functions.getTAGfromItemstack(newcard).setInteger("Generator_ID", ((TileEntityGenerator)tileEntity).getGenerator_ID());
				entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = newcard;
				Functions.ChattoPlayer(entityplayer, "[Generator] Success: <Power-Link> Card create");
				return true;
			}

			if (tileEntity instanceof TileEntitySecurityStation) {
				if (!(((TileEntitySecurityStation)tileEntity).isAccessGranted(entityplayer.username,ModularForceFieldSystem.PERSONALID_FULLACCESS))) {
					return false;
				}

				ItemStack newcard =   new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard);
				Functions.getTAGfromItemstack(newcard).setInteger("Secstation_ID", ((TileEntitySecurityStation)tileEntity).getSecurtyStation_ID());
				entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = newcard;
				Functions.ChattoPlayer(entityplayer, "[Security Station] Success: <Security Station Link>  Card create");
				return true;
			}
		}
		return false;
	}
}
