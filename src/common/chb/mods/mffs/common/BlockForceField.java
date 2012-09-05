package chb.mods.mffs.common;

import ic2.api.ElectricItem;

import java.util.List;
import java.util.Random;

import chb.mods.mffs.common.WorldMap.ForceFieldWorld;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockForceField extends BlockContainer{
    public static int renderer;
    public int posx;
    public int posy;
    public int posz;

	public BlockForceField(int i) {
		super(i, i, Material.glass);
		setHardness(0.5F);
		setResistance(999F);
		setTickRandomly(true);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		this.posx = i;
		this.posy = j;
	    this.posz = k;
	}

	@Override
	public int getRenderBlockPass() {
		if(Functions.getClientWorld().getBlockMetadata(posx , posy, posz) == ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE)
		{
			TileEntityForceField ForceField   =	(TileEntityForceField) Functions.getClientWorld().getBlockTileEntity(posx , posy, posz);

	        if(ForceField  != null){
	        	if(ForceField.getTexturid(1) == 67 || ForceField.getTexturid(1) == 205)
	        	{
	        		return 1;
	        	}
	        }
	     }
		return 0;
	}

    @Override
    public int getRenderType()
    {
        return ModularForceFieldSystem.FORCEFIELDRENDER_ID;
    }

	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

	@Override
    protected boolean canSilkHarvest()
    {
        return false;
    }

	private ItemStack ForceFieldsync(EntityPlayer entityplayer)
	{
		List<Slot> slots = entityplayer.inventorySlots.inventorySlots;
		for (Slot slot : slots) {
			if (slot.getStack() != null) {
				if (slot.getStack().getItem() == ModularForceFieldSystem.MFFSitemForceFieldsync) {
						return slot.getStack();
				}
			}
		}
		Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: No Force Field Synchron Capacitor in Inventory");
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (!world.isRemote) {
				ForceFieldWorld wff = WorldMap.getForceFieldWorld(world);

				ForceFieldBlockStack ffworldmap = wff.getorcreateFFStackMap(i, j, k);
				if (ffworldmap != null) {
					int Sec_Gen_ID = -1;
					int First_Gen_ID = ffworldmap.getGenratorID();
					int First_Pro_ID = ffworldmap.getProjectorID();

					 TileEntityGenerator  generator = Linkgrid.getWorldMap(world).getGenerator().get(First_Gen_ID);
					 TileEntityProjector  projector = Linkgrid.getWorldMap(world).getProjektor().get(First_Pro_ID);

					 if(generator != null && projector!= null)
					 {
						 if(projector.isActive())
						 {
						 boolean passtrue = false;

					switch(projector.getaccesstyp())
					{
					case 0:
					passtrue = false;
					break;
					case 1:
					passtrue = true;
					break;
					case 2:

						if(generator.getSecStation_ID()!=0)
						{
						 passtrue = Linkgrid
							.getWorldMap(world)
							.getSecStation()
							.get(generator.getSecStation_ID())
							.isAccessGranted(
					entityplayer.username,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
						}

					break;
					case 3:
						if(projector.getSecStation_ID()!=0)
						{
						 passtrue = Linkgrid
							.getWorldMap(world)
							.getSecStation()
							.get(projector.getSecStation_ID())
							.isAccessGranted(
					entityplayer.username,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
						}

					break;
					}

						ItemStack  energycap = ForceFieldsync(entityplayer);

						if (passtrue && energycap!= null) {
							int x = i;
							int y = j;
							int z = k;
							int typ = 99;
							int ymodi =0;

							int lm = MathHelper
									.floor_double((double) ((entityplayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
							int i1 = Math.round(entityplayer.rotationPitch);

							if (i1 >= 65) { // Facing 1
							typ = 1;
							} else if (i1 <= -65) { // Facing 0
							typ = 0;
							} else if (lm == 0) { // Facing 2
							typ = 2;
							} else if (lm == 1) { // Facing 5
							typ = 5;
							} else if (lm == 2) { // Facing 3
							typ = 3;
							} else if (lm == 3) { // Facing 4
							typ = 4;
							}

							int counter = 0;
							while (Sec_Gen_ID != 0) {
								Sec_Gen_ID = wff.isExistForceFieldStackMap(x, y , z ,counter,typ);
								if(Sec_Gen_ID!=0)
								{
								counter++;
								}
							}

							if(First_Gen_ID != wff.isExistForceFieldStackMap(x, y , z ,counter-1,typ))
							{
							 Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
							 return false;
							}

							switch(typ)
							{
							case 0:
								y += counter;
								ymodi  =-1;
								break;
							case 1:
								y -= counter;
								ymodi  = 1;
								break;
							case 2:
								z += counter;
								break;
							case 3:
								z -= counter;
								break;
						    case 4:
						    	x += counter;
						    	break;
						    case 5:
						    	x -= counter;
						    	break;
							}

							Functions.ChattoPlayer(entityplayer,
									"[Field Security] Success: access granted");

							if (counter >= 0 && counter <= 5) {
								if (world.getBlockId(x, y , z) == 0 && world.getBlockId(x, y - ymodi, z) == 0) {
									if(y-ymodi <=0){
									Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: transmission into Void not allowed ");
									}else{
									if(ItemForceFieldSynchronCapacitor.canuseBatpack(energycap))
									{
										if(ElectricItem.canUse(energycap, ModularForceFieldSystem.forcefieldtransportcost))
										{
											ElectricItem.use(energycap, ModularForceFieldSystem.forcefieldtransportcost, entityplayer);
											entityplayer.setPositionAndUpdate(x + 0.5, y-ymodi ,z + 0.5);
											Functions.ChattoPlayer(entityplayer,"[Field Security] Success: transmission complete");
										}else{
											Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: MFFS Forcefield synchron capacitor is empty ");
										}
									}else{
									int amount = ElectricItem.discharge(energycap, ModularForceFieldSystem.forcefieldtransportcost, 1, true, true);
									if(amount == ModularForceFieldSystem.forcefieldtransportcost)
										{
											ElectricItem.discharge(energycap, ModularForceFieldSystem.forcefieldtransportcost, 1, true, false);
											entityplayer.setPositionAndUpdate(x + 0.5, y-ymodi ,z + 0.5);
											Functions.ChattoPlayer(entityplayer,"[Field Security] Success: transmission complete");
										}else{
											Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: MFFS Forcefield synchron capacitor is empty ");
										}
								    	}
									}
								} else {
									Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: detected obstacle ");
								}
							}else {
								Functions
										.ChattoPlayer(entityplayer,
												"[Field Security] Fail: Field to Strong >= 5 Blocks");
							}
						} else {
							if(energycap!= null){
								Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
							}
						}
					 }
				}
		}
		}
		return true;
	}

	@Override
	 public void onNeighborBlockChange(World world, int x, int y, int z, int blockid) {
		if(blockid  != ModularForceFieldSystem.MFFSFieldblock.blockID)
	    {
			for(int x1 = -1 ;x1<=1; x1++){
				for(int y1 = -1 ;y1<=1; y1++){
					for(int z1 = -1 ;z1<=1; z1++){
					if(world.getBlockId(x+x1, y+y1,z+z1)!= ModularForceFieldSystem.MFFSFieldblock.blockID )
					{
						if(world.getBlockId(x+x1, y+y1,z+z1)==0)
						{
							breakBlock(world, x+x1, y+y1, z+z1,0,0);
						}
					}
			}
		}
	}
		}
	 }

	@Override
	public void breakBlock(World world, int i, int j, int k,int a,int b){
		ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(WorldMap.Cordhash(i, j, k));

		if (ffworldmap != null) {
				if(!ffworldmap.isEmpty()) {
					TileEntityProjector Projector  =	Linkgrid.getWorldMap(world).getProjektor().get(ffworldmap.getProjectorID());
			if(Projector != null){
				if(!Projector.isActive()){
					ffworldmap.removebyProjector(ffworldmap.getProjectorID());
				}else{
					world.setBlockAndMetadataWithNotify(i, j, k,ModularForceFieldSystem.MFFSFieldblock.blockID,ffworldmap.getTyp());
					world.markBlockAsNeedsUpdate(i, j, k);
					ffworldmap.setSync(true);

					TileEntity tileEntity = Linkgrid.getWorldMap(world).getGenerator().get(ffworldmap.getGenratorID());
					if (tileEntity instanceof TileEntityGenerator && tileEntity != null) {
						if (ffworldmap.getTyp() == 1) {
							((TileEntityGenerator) tileEntity).Energylost(ModularForceFieldSystem.forcefieldblockcost* ModularForceFieldSystem.forcefieldblockcreatemodifier);
						} else {
							((TileEntityGenerator) tileEntity).Energylost(ModularForceFieldSystem.forcefieldblockcost* ModularForceFieldSystem.forcefieldblockcreatemodifier* ModularForceFieldSystem.forcefieldblockzappermodifier);
						}
				}
			}
			}
		}
		}
	}

	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		if (world.getBlockMetadata(i, j, k) == 1) {
			float f = 0.0625F;
			return AxisAlignedBB.getBoundingBox(i + f, j + f, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
		}

		return AxisAlignedBB.getBoundingBox((float) i, j, (float) k, (float) (i + 1), (float) (j + 1), (float) (k + 1));
	}

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBox((float) i, j, (float) k, (float) (i + 0), j + 0, (float) (k + 0));
	}

    @Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k,
			Entity entity) {
		if (world.getBlockMetadata(i, j, k) == 1) {
			if (entity instanceof EntityLiving) {
				entity.attackEntityFrom(DamageSource.generic, 5);
			}
		}
	}
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side) {
    	int xCord = x;
    	int yCord = y;
    	int zCord = z;

		switch(side) {
		case 0: yCord++;
		break;
		case 1: yCord--;
		break;
		case 2: zCord++;
		break;
		case 3: zCord--;
		break;
		case 4: xCord++;
		break;
		case 5: xCord--;
		break;
		}

		if(blockID == iblockaccess.getBlockId(x, y, z) && iblockaccess.getBlockMetadata(x, y, z) == iblockaccess.getBlockMetadata(xCord, yCord, zCord))
			return false;

		return super.shouldSideBeRendered(iblockaccess, x, y, z, side);
	}

    @Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		TileEntity tileEntity = iblockaccess.getBlockTileEntity(i, j, k);

		if (tileEntity!=null && tileEntity instanceof TileEntityForceField )
		{
		return  ((TileEntityForceField)tileEntity).getTexturid(l);
	}else{
		if (iblockaccess.getBlockMetadata(i, j, k) == ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE)
		{
			 return 180;
		}else{
			return iblockaccess.getBlockMetadata(i, j, k);
		}
	}
	}

	@Override
	public float getExplosionResistance(Entity entity,World world, int i, int j,
			int k, double d, double d1, double d2) {
		ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world)
				.getForceFieldStackMap(WorldMap.Cordhash(i, j, k));
		if (ffworldmap != null && !ffworldmap.isEmpty()) {
			TileEntity tileEntity = Linkgrid.getWorldMap(world).getGenerator()
					.get(ffworldmap.getGenratorID());
			if (tileEntity instanceof TileEntityGenerator && tileEntity != null) {
				((TileEntityGenerator) tileEntity)
						.Energylost(ModularForceFieldSystem.forcefieldblockcost
								* ModularForceFieldSystem.forcefieldblockcreatemodifier);
			}
		}

		return 999F;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k,
			Random random) {
		if (world.getBlockMetadata(i, j, k) == ModularForceFieldSystem.FORCEFIELBOCKMETA_ZAPPER) {
			double d = i + Math.random()+ 0.2D;
			double d1 = j + Math.random() + 0.2D;
			double d2 = k + Math.random() + 0.2D;

			world.spawnParticle("townaura", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
	@Override
	public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k,
			int dir) {
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(WorldMap.Cordhash(x, y, z));

	if(ffworldmap != null)
	{
		if(!ffworldmap.isEmpty()) {
			TileEntityProjector Projector  =	Linkgrid.getWorldMap(world).getProjektor().get(ffworldmap.getProjectorID());
	if(Projector != null){
		if(!Projector.isActive()){
			ffworldmap.removebyProjector(ffworldmap.getProjectorID());
		}
	}
		}
	}

		if(ffworldmap == null || ffworldmap.isEmpty())
		{
			world.removeBlockTileEntity(x, y, z);
			world.setBlockWithNotify(x, y, z, 0);
		}
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		if(meta == ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE)
		{
			return new TileEntityForceField();
		}

		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return createTileEntity(world,0);
	}
}
