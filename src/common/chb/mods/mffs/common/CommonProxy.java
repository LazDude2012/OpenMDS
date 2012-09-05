package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import chb.mods.mffs.client.*;

public class CommonProxy implements IGuiHandler {
	public static final int GUI_GENERATOR = 1;
	public static final int GUI_PROJECTOR = 2;
	public static final int GUI_SECSTATION = 3;
	public static final int GUI_DEFSTATION = 4;

	public void registerRenderInformation()
{
}
	public void registerTileEntitySpecialRenderer()
{
}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null)
		{
			return null;
		}

		switch (ID) {
		case GUI_GENERATOR:
			return new GuiGenerator(player,
					te == null ? new TileEntityGenerator()
							: ((TileEntityGenerator) te));
		case GUI_PROJECTOR:
			return new GuiProjector(player,
					te == null ? new TileEntityProjector()
							: ((TileEntityProjector) te));
		case GUI_SECSTATION:
			return new GuiSecurityStation(player,
					te == null ? new TileEntitySecurityStation()
							: ((TileEntitySecurityStation) te));
		case GUI_DEFSTATION:
			return new GuiAreaDefenseStation(player,
					te == null ? new TileEntityAreaDefenseStation()
							: ((TileEntityAreaDefenseStation) te));
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
 {			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te == null)
			{
				return null;
			}

			switch (ID) {
			case GUI_GENERATOR:
				return new ContainerGenerator(player,
						te == null ? new TileEntityGenerator()
								: ((TileEntityGenerator) te));
			case GUI_PROJECTOR:
				return new ContainerProjektor(player,
						te == null ? new TileEntityProjector()
								: ((TileEntityProjector) te));
			case GUI_SECSTATION:
				return new ContainerSecurityStation(player,
						te == null ? new TileEntitySecurityStation()
								: ((TileEntitySecurityStation) te));

			case GUI_DEFSTATION:
					return new ContainerAreaDefenseStation(player,
							te == null ? new TileEntityAreaDefenseStation()
									: ((TileEntityAreaDefenseStation) te));
			}
			return null;
		}
	}

	public World getClientWorld() {
		return null;
	}
}
