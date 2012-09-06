package chb.mods.mffs.common;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.World;
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;

public class ItemPersonalIIDWriter extends ItemMultitool{
	public ItemPersonalIIDWriter(int i) {
		super(i,2);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		if(entityplayer.isSneaking())
		{
		int powerleft = ElectricItem.discharge(itemstack, getMaxCharge(), 1, true, true);
		ItemStack hand = entityplayer.inventory.getCurrentItem();
		hand= new ItemStack(ModularForceFieldSystem.MFFSitemWrench, 1);
		ElectricItem.charge(hand, powerleft, 1, true, false);
		return hand;
		}

			List<Slot> slots = entityplayer.inventorySlots.inventorySlots;
			for (Slot slot : slots) {
				if (slot.getStack() != null) {
					if (slot.getStack().getItem() == ModularForceFieldSystem.MFFSitemcardempty) {
						if(ElectricItem.canUse(itemstack, 1000))
						{
                      
							ElectricItem.use(itemstack, 1000, entityplayer);
                            ItemStack IDCard= new ItemStack(ModularForceFieldSystem.MFFSItemIDCard, 1);
                            ItemCardPersonalID.setOwner(IDCard, entityplayer.username);
                            ItemCardPersonalID.setSeclevel(IDCard, 1);
		                    slot.putStack(IDCard);
							
	                        if(world.isRemote)
							Functions.ChattoPlayer(entityplayer,"[MultiTool] Success: ID-Card create");

							return itemstack;
						}else{
							 if(world.isRemote)
							Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: not enough EU please charge");
							return itemstack;
						}
					}
				}
			}
			if(world.isRemote)
			Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: need MFFS Card <blank> in  Inventory");

		return itemstack;
	}


	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		return false;
	}
}
