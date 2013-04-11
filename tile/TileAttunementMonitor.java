package OpenMDS.tile;

import OpenMDS.api.IAttunable;
import OpenMDS.api.IDefenceAttachment;
import OpenMDS.util.MDSUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class TileAttunementMonitor extends TileEntity implements IDefenceAttachment
{
	public int radius = 16;
	public boolean isEmitting = false;
	private boolean deferredUpdate;
	public TileAttunementMonitor()
	{
		super();
		deferredUpdate=true;
	}
	public TileDefenceComputer attachedTo;
	public boolean hasGui = true;
	@Override
	public String[] GetPriorityList()
	{
		return new String[]{"Emit signal","Emit signal","Emit signal","Emit signal","Emit signal"};  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String GetName()
	{
		return "Attunement Monitor";  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void updateEntity()
	{
		if(deferredUpdate)
		{
			MDSUtils.CheckForAttachment(this);
			deferredUpdate = false;
		}
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.xCoord-radius,this.yCoord-radius,this.zCoord-radius,this.xCoord+radius,this.yCoord+radius,this.zCoord+radius);
		List playersInRange = worldObj.getEntitiesWithinAABB(EntityPlayer.class,aabb);
		if(attachedTo ==null) return;
		for(Object playerobj : playersInRange)
		{
			EntityPlayer player = (EntityPlayer) playerobj;
			for(ItemStack stack : player.inventory.mainInventory)
			{
				if(stack != null && stack.getItem() instanceof IAttunable)
				{
					int attunement = stack.getItemDamage();
					if(attunement == attachedTo.GetAttunementFromPriority(0))
					{
						isEmitting = true;
						UpdateWorld();
						return;
					}
					if(attunement == attachedTo.GetAttunementFromPriority(1))
					{
						isEmitting = true;
						UpdateWorld();
						return;
					}
					if(attunement == attachedTo.GetAttunementFromPriority(2))
					{
						isEmitting = true;
						UpdateWorld();
						return;
					}
					if(attunement == attachedTo.GetAttunementFromPriority(3))
					{
						isEmitting = true;
						UpdateWorld();
						return;
					}
					if(attunement == attachedTo.GetAttunementFromPriority(4))
					{
						isEmitting = true;
						UpdateWorld();
						return;
					}
				}
			}
		}
		isEmitting = false;
		worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
	}

	@Override
	public void OpenGui(EntityPlayer player)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void Attach(TileDefenceComputer computer)
	{
		this.attachedTo = computer;
	}
	public void UpdateWorld()
	{
		worldObj.markBlockForUpdate(xCoord-1,yCoord,zCoord);
		worldObj.markBlockForUpdate(xCoord+1,yCoord,zCoord);
		worldObj.markBlockForUpdate(xCoord,yCoord-1,zCoord);
		worldObj.markBlockForUpdate(xCoord,yCoord+1,zCoord);
		worldObj.markBlockForUpdate(xCoord,yCoord,zCoord-1);
		worldObj.markBlockForUpdate(xCoord,yCoord,zCoord+1);
		worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
	}
}
