package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class SecurityHelper {

	
	
	
	public static boolean isAccessGranted(TileEntity tileEntity,
			EntityPlayer entityplayer, World world, String right) {
		
		return isAccessGranted( tileEntity,
				 entityplayer,  world,  right,false);
	}
	
	
	public static boolean isAccessGranted(TileEntity tileEntity,
			EntityPlayer entityplayer, World world, String right,boolean suppresswarning) {

		if (tileEntity instanceof TileEntitySecStorage) {

			if (((TileEntitySecStorage) tileEntity).getStackInSlot(0) != null) {
				if (((TileEntitySecStorage) tileEntity).getStackInSlot(0)
						.getItem() instanceof ItemCardSecurityLink) {
					ItemCardSecurityLink card = (ItemCardSecurityLink) ((TileEntitySecStorage) tileEntity)
							.getStackInSlot(0).getItem();

					if (card.isAccessGranted(
							((TileEntitySecStorage) tileEntity)
									.getStackInSlot(0), entityplayer, world,
							right, true)) {
						return true;
					}
				}
			} else {

				return true;

			}

			return false;

		}

		if (tileEntity instanceof TileEntityAdvSecurityStation) {

			if (!(((TileEntityAdvSecurityStation) tileEntity).isAccessGranted(
					entityplayer.username, right))) {

				if (world.isRemote && !suppresswarning)
					Functions.ChattoPlayer(entityplayer,
							"[Field Security] Fail: access denied");
				return false;
			}
		}

		if (tileEntity instanceof TileEntityConverter) {

			if (Linkgrid
					.getWorldMap(world)
					.getCapacitor()
					.get(((TileEntityConverter) tileEntity)
							.getLinkCapacitor_ID()) != null) {
				if (Linkgrid
						.getWorldMap(world)
						.getSecStation()
						.get(Linkgrid
								.getWorldMap(world)
								.getCapacitor()
								.get(((TileEntityConverter) tileEntity)
										.getLinkCapacitor_ID())
								.getSecStation_ID()) != null) {
					if (!(Linkgrid
							.getWorldMap(world)
							.getSecStation()
							.get(Linkgrid
									.getWorldMap(world)
									.getCapacitor()
									.get(((TileEntityConverter) tileEntity)
											.getLinkCapacitor_ID())
									.getSecStation_ID()).isAccessGranted(
							entityplayer.username, right))) {
						
						if (!suppresswarning)
							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Fail: access denied");
						return false;
					}
				}
			}
		}

		
		if (tileEntity instanceof TileEntityCapacitor) {
			
			if (((TileEntityCapacitor) tileEntity).getSecStation_ID() != 0) {

				TileEntityAdvSecurityStation SecStation = Linkgrid
						.getWorldMap(world)
						.getSecStation()
						.get(((TileEntityCapacitor) tileEntity)
								.getSecStation_ID());

				if (SecStation != null) {

					if (!(SecStation.isAccessGranted(entityplayer.username,
							right))) {
						if (!suppresswarning)
						Functions.ChattoPlayer(entityplayer,
								"[Field Security] Fail: access denied");

						return false;
					}
				} else {
					return false;
				}

			} else {
				return true;
			}

		}
		
		

		if (tileEntity instanceof TileEntityExtractor) {

			if (Linkgrid
					.getWorldMap(world)
					.getCapacitor()
					.get(((TileEntityExtractor) tileEntity)
							.getLinkCapacitor_ID()) != null) {
				if (Linkgrid
						.getWorldMap(world)
						.getSecStation()
						.get(Linkgrid
								.getWorldMap(world)
								.getCapacitor()
								.get(((TileEntityExtractor) tileEntity)
										.getLinkCapacitor_ID())
								.getSecStation_ID()) != null) {
					if (!(Linkgrid
							.getWorldMap(world)
							.getSecStation()
							.get(Linkgrid
									.getWorldMap(world)
									.getCapacitor()
									.get(((TileEntityExtractor) tileEntity)
											.getLinkCapacitor_ID())
									.getSecStation_ID()).isAccessGranted(
							entityplayer.username, right))) {
						if (!suppresswarning)
							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Fail: access denied");
						return false;
					}
				}
			}
		}

		if (tileEntity instanceof TileEntityProjector) {

			if (((TileEntityProjector) tileEntity).getaccesstyp() == 2) {
				if (Linkgrid
						.getWorldMap(world)
						.getCapacitor()
						.get(((TileEntityProjector) tileEntity)
								.getLinkCapacitor_ID()) != null) {
					if (Linkgrid
							.getWorldMap(world)
							.getSecStation()
							.get(Linkgrid
									.getWorldMap(world)
									.getCapacitor()
									.get(((TileEntityProjector) tileEntity)
											.getLinkCapacitor_ID())
									.getSecStation_ID()) != null) {
						if (!(Linkgrid
								.getWorldMap(world)
								.getSecStation()
								.get(Linkgrid
										.getWorldMap(world)
										.getCapacitor()
										.get(((TileEntityProjector) tileEntity)
												.getLinkCapacitor_ID())
										.getSecStation_ID()).isAccessGranted(
								entityplayer.username, right))) {
							if (!suppresswarning)
								Functions.ChattoPlayer(entityplayer,
										"[Field Security] Fail: access denied");
							return false;
						}
					}
				}
			}
			if (((TileEntityProjector) tileEntity).getaccesstyp() == 3) {
				if (Linkgrid
						.getWorldMap(world)
						.getSecStation()
						.get(((TileEntityProjector) tileEntity)
								.getSecStation_ID()) != null) {
					if (!(Linkgrid
							.getWorldMap(world)
							.getSecStation()
							.get(((TileEntityProjector) tileEntity)
									.getSecStation_ID()).isAccessGranted(
							entityplayer.username, right))) {
						if (!suppresswarning)
							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Fail: access denied");
						return false;
					}
				}
			}

		}

		if (tileEntity instanceof TileEntityAreaDefenseStation) {

			if (Linkgrid
					.getWorldMap(world)
					.getSecStation()
					.get(((TileEntityAreaDefenseStation) tileEntity)
							.getSecStation_ID()) != null) {
				if (!(Linkgrid
						.getWorldMap(world)
						.getSecStation()
						.get(((TileEntityAreaDefenseStation) tileEntity)
								.getSecStation_ID()).isAccessGranted(
						entityplayer.username, right))) {
					if (!suppresswarning)
						Functions.ChattoPlayer(entityplayer,
								"[Field Security] Fail: access denied");

					return false;
				}
			}

		}

		return true;
	}

}
