package chb.mods.mffs.common;


import chb.mods.mffs.common.network.*;

import ic2.api.ExplosionWhitelist;
import ic2.api.Ic2Recipes;
import ic2.api.Items;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ModularForceFieldSystem", name = "Modular ForceField System", version = "2.1.1.0.1")
@NetworkMod(channels = { "MFFS" },clientSideRequired = true, serverSideRequired = false, packetHandler = NetworkHandler.class)

public class ModularForceFieldSystem {
	public static final int GUI_GENERATOR = 1;
	public static final int GUI_PROJECTOR = 2;
	public static final int GUI_SECSTATION = 3;
	public static final int GUI_DEFSTATION = 4;

	public static final int FORCEFIELBOCKMETA_DEFAULT = 0;
	public static final int FORCEFIELBOCKMETA_ZAPPER = 1;
	public static final int FORCEFIELBOCKMETA_AREA = 2;
	public static final int FORCEFIELBOCKMETA_CONTAIMENT= 3;
	public static final int FORCEFIELBOCKMETA_CAMOFLAGE= 4;

	public static final int PERSONALID_FULLACCESS = 2;
	public static final int PERSONALID_LIMITEDACCESS = 1;

	public static  int FORCEFIELDRENDER_ID =2908;

	public static Block MFFSGenerator;
	public static Block MFFSProjector;
	public static Block MFFSSecurtyStation;
	public static Block MFFSDefenceStation;
	public static Block MFFSFieldblock;

	public static Block MFFSMonazitOre;
	public static Item MFFSitemForcicium;
	public static Item MFFSitemForcePowerCrystal;

	public static Item MFFSitemFocusmatix;
	public static Item MFFSitemSwitch;
	public static Item MFFSitemWrench;

	public static Item MFFSitemForceFieldsync;
	public static Item MFFSitemMFDidtool;
	public static Item MFFSitemMFDdebugger;
	public static Item MFFSitemcardempty;
	public static Item MFFSitemfc;
	public static Item MFFSItemIDCard;
	public static Item MFFSItemSecLinkCard;

	public static Item MFFSitemupgradegenrange;
	public static Item MFFSitemupgradegencap;
	public static Item MFFSitemUpgradegenEUInjektor;

	public static Item MFFSProjectorTypsphere;
	public static Item MFFSProjectorTypkube;
	public static Item MFFSProjectorTypwall;
	public static Item MFFSProjectorTypdeflector;
	public static Item MFFSProjectorTyptube;
	public static Item MFFSProjectorTypcontainment;
	public static Item MFFSProjectorTypAdvCube;

	public static Item MFFSProjectorOptionZapper;
	public static Item MFFSProjectorOptionSubwater;
	public static Item MFFSProjectorOptionDome;
	public static Item MFFSProjectorOptionCutter;
	public static Item MFFSProjectorOptionMoobEx;
	public static Item MFFSProjectorOptionDefenceStation;
	public static Item MFFSProjectorOptionForceFieldJammer;
	public static Item MFFSProjectorOptionCamouflage;
	public static Item MFFSProjectorOptionFieldFusion;

	public static Item MFFSProjectorFFDistance;
	public static Item MFFSProjectorFFStrenght;
	public static Item MFFSSecStationexidreader;

	public static int forcefieldblockcost;
	public static int forcefieldblockcreatemodifier;
	public static int forcefieldblockzappermodifier;

	public static int forcefieldtransportcost;
	public static int forcefieldmaxblockpeerTick;

	public static int  DefenseStationDamage;
	public static int  MobDefenseDamage;
	public static int  DefenseStationFPpeerAttack;
	public static Boolean forcefieldremoveonlywaterandlava;

	public static Configuration MFFSconfig;

	public static String Admin;
    public static Map<Integer, int[]> idmetatotextur = new HashMap<Integer, int[]>();

	@SidedProxy(clientSide = "chb.mods.mffs.client.ClientProxy", serverSide = "chb.mods.mffs.common.CommonProxy")
    public static CommonProxy proxy;

	@Instance
    public static ModularForceFieldSystem instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		MFFSconfig = new Configuration(event.getSuggestedConfigurationFile());
		event.getModMetadata().version = Versioninfo.version();
		try {
			MFFSconfig.load();

			Admin = Functions.getValuefor("ForceFieldMaster","general","Thunderdark");

			forcefieldtransportcost = Integer.valueOf(Functions.getValuefor(
					"forcefieldtransportcost","general", 10000));
			forcefieldremoveonlywaterandlava = Boolean.valueOf(Functions.getValuefor(
					"forcefieldremoveonlywaterandlava", "general",false));
			forcefieldblockcost = Functions.getConfigIdFor("forcefieldblockcost", 1,2);
			forcefieldblockcreatemodifier = Functions.getConfigIdFor(
					"forcefieldblockcreatemodifier", 10,2);
			forcefieldblockzappermodifier = Functions.getConfigIdFor(
					"forcefieldblockzappermodifier", 2,2);
				forcefieldmaxblockpeerTick = Functions.getConfigIdFor(
					"forcefieldmaxblockpeerTick", 5000,2);
			DefenseStationDamage = Functions.getConfigIdFor(
					"DefenseStationDamage", 10,2);
		MobDefenseDamage = Functions.getConfigIdFor(
					"MobDefenseDamage", 10,2);
		DefenseStationFPpeerAttack = Functions.getConfigIdFor(
					"DefenseStationFPpeerAttack", 25000,2);

		    MFFSMonazitOre = new BlockMonazitOre(Functions.getConfigIdFor(
					"MFFSMonazitOre", 4077,1)).setBlockName("MFFSMonazitOre");
			MFFSDefenceStation = new BlockAreaDefenseStation(Functions.getConfigIdFor(
					"MFFSDefenceStation", 4078,1),0).setBlockName("MFFSDefenceStation");
			MFFSSecurtyStation = new BlockSecurtyStation(Functions.getConfigIdFor(
					"MFFSSecurtyStation", 4079,1), 0)
					.setBlockName("MFFSSecurtyStation");
			MFFSGenerator = new BlockGenerator(Functions.getConfigIdFor(
					"MFFSGenerator", 4080,1), 0).setBlockName("MFFSGenerator");
			MFFSProjector = new BlockProjector(Functions.getConfigIdFor(
					"MFFSProjector", 4081,1), 0).setBlockName("MFFSProjector");
			MFFSFieldblock = new BlockForceField(Functions.getConfigIdFor(
					"HFFPFieldblock", 4082,1));

			MFFSitemWrench= new ItemWrench(
					Functions.getConfigIdFor("itemWrench", 11107,3))
					.setItemName("itemWrench");
			MFFSitemSwitch= new ItemSwitch(
					Functions.getConfigIdFor("itemSwitch", 11108,3))
					.setItemName("itemSwitch");
			MFFSitemForceFieldsync = new ItemForceFieldSynchronCapacitor(
					Functions.getConfigIdFor("itemForceFieldsync", 11109,3))
					.setItemName("itemForceFieldsync");
			MFFSitemMFDidtool = new ItemPersonalIIDWriter(Functions.getConfigIdFor(
					"ItemMFDIDwriter", 11110,3)).setItemName("ItemMFDIDwriter");
			MFFSitemMFDdebugger = new ItemDebugger(Functions.getConfigIdFor(
					"itemMFDdebugger", 11111,3)).setItemName("itemMFDdebugger");
			MFFSitemcardempty = new ItemCardEmpty(Functions.getConfigIdFor(
					"itemcardempty", 11115,3)).setItemName("itemcardempty");
			MFFSitemfc = new ItemCardPowerLink(Functions.getConfigIdFor("itemfc",
					11116,3)).setItemName("itemfc");
			MFFSitemUpgradegenEUInjektor = new ItemGeneratorInjektorEU(
					Functions.getConfigIdFor("ItemUpgradegenEuinjekt ", 11118,3))
					.setItemName("ItemUpgradegenEuinjekt");
			MFFSitemupgradegenrange = new ItemGeneratorUpgradeRange(
					Functions.getConfigIdFor("itemupgradegenrange", 11119,3))
					.setItemName("itemupgradegenrange");
			MFFSitemupgradegencap = new ItemGeneratorUpgradeCapacity(
					Functions.getConfigIdFor("itemupgradegencap", 11120,3))
					.setItemName("itemupgradegencap");
			MFFSProjectorTypsphere = new ItemProjectorModuleSphere(
					Functions.getConfigIdFor("itemProjectorTypsphere", 11121,3))
					.setItemName("itemProjectorTypsphere");
			MFFSProjectorTypkube = new ItemProjectorModuleCube(Functions.getConfigIdFor(
					"itemProjectorTypkube", 11122,3))
					.setItemName("itemProjectorTypkube");
			MFFSProjectorTypwall = new ItemProjectorModuleWall(Functions.getConfigIdFor(
					"itemProjectorTypwall", 11124,3))
					.setItemName("itemProjectorTypwall");
			MFFSProjectorTypdeflector = new ItemProjectorModuleDeflector(
					Functions.getConfigIdFor("itemProjectorTypdeflector", 11125,3))
					.setItemName("itemProjectorTypdeflector");
			MFFSProjectorTyptube = new ItemProjectorModuleTube(Functions.getConfigIdFor(
					"itemProjectorTyptube", 11126,3))
					.setItemName("itemProjectorTyptube");
			MFFSProjectorOptionZapper = new ItemProjectorOptionTouchDamage(
					Functions.getConfigIdFor("itemupgradeprozapper", 11127,3))
					.setItemName("itemupgradeprozapper");
			MFFSProjectorOptionSubwater = new ItemProjectorOptionSponge(
					Functions.getConfigIdFor("itemupgradeprosubwater", 11128,3))
					.setItemName("itemupgradeprosubwater");
			MFFSProjectorOptionDome = new ItemProjectorOptionFieldManipulator(
					Functions.getConfigIdFor("itemupgradeprodome ", 11129,3))
					.setItemName("iitemupgradeprodome");
			MFFSProjectorOptionCutter = new ItemProjectorOptionBlockBreaker(
					Functions.getConfigIdFor("itemUpgradeprocutter ", 11130,3))
					.setItemName("itemUpgradeprocutter");
			MFFSProjectorFFDistance = new ItemProjectorFieldModulatorDistance(
					Functions.getConfigIdFor("itemProjectorFFDistance ", 11131,3))
					.setItemName("itemProjectorFFDistance");
			MFFSProjectorFFStrenght = new ItemProjectorFieldModulatorStrength(
					Functions.getConfigIdFor("itemProjectorFFStrength ", 11132,3))
					.setItemName("itemProjectorFFStrength");
			MFFSitemFocusmatix = new ItemProjectorFocusMatrix(
					Functions.getConfigIdFor("itemPorjectorFocusmatrix ", 11133,3))
					.setItemName("itemPorjectorFocusmatrix");
			MFFSItemIDCard = new ItemCardPersonalID(Functions.getConfigIdFor("itemIDCard",
					11134,3)).setItemName("itemIDCard");
			MFFSItemSecLinkCard = new ItemCardSecurityLink(Functions.getConfigIdFor(
					"iemSecLinkCard", 11135,3)).setItemName("itemSecLinkCard");
			MFFSSecStationexidreader = new ItemSecurityStationExternalIDReader(
					Functions.getConfigIdFor("itemSecStationexidreader ", 11136,3))
					.setItemName("itemSecStationexidreader");
			MFFSProjectorOptionDefenceStation = new ItemProjectorOptionDefenseStation(
					Functions.getConfigIdFor("itemProjectorOptiondefencestation ", 11137,3))
					.setItemName("itemProjectorOptiondefencestation");

			MFFSProjectorOptionMoobEx = new ItemProjectorOptionMobDefence(
					Functions.getConfigIdFor("itemProjectorOptionMoobEx ", 11138,3))
					.setItemName("itemProjectorOptionMoobEx");

			MFFSProjectorOptionForceFieldJammer= new ItemProjectorOptionForceFieldJammer(
					Functions.getConfigIdFor("itemProjectorOptionFFJammer ", 11139,3))
					.setItemName("itemProjectorOptionFFJammer");

			MFFSProjectorTypcontainment= new ItemProjectorModuleContainment(
					Functions.getConfigIdFor("itemProjectorModuleContainment ", 11140,3))
					.setItemName("itemProjectorModuleContainment");

			MFFSProjectorOptionCamouflage= new ItemProjectorOptionCamoflage(
					Functions.getConfigIdFor("itemProjectorOptionCamoflage ", 11141,3))
					.setItemName("itemProjectorOptionCamoflage");
			MFFSProjectorTypAdvCube= new ItemProjectorModuleAdvCube(
					Functions.getConfigIdFor("itemProjectorModuleAdvCube ", 11142,3))
					.setItemName("itemProjectorModuleAdvCube");
			MFFSProjectorOptionFieldFusion = new ItemProjectorOptionFieldFusion(
			Functions.getConfigIdFor("itemProjectorOptionFieldFusion ", 11143,3))
			.setItemName("itemProjectorOptionFieldFusion");

			MFFSitemForcicium = new ItemForcicium(
			Functions.getConfigIdFor("itemForcicium", 11144,3))
			.setItemName("itemForcicium");

			MFFSitemForcePowerCrystal = new ItemForcePowerCrystal(
			Functions.getConfigIdFor("itemForcePowerCrystal", 11145,3))
			.setItemName("itemForcePowerCrystal");
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "ModularForceFieldSystem has a problem loading it's configuration");
		} finally {
			MFFSconfig.save();
		}
	}

	@Init
	public void load(FMLInitializationEvent evt) {
		GameRegistry.registerBlock(MFFSGenerator);
		GameRegistry.registerBlock(MFFSProjector);
		GameRegistry.registerBlock(MFFSSecurtyStation);
		GameRegistry.registerBlock(MFFSFieldblock);
		GameRegistry.registerBlock(MFFSDefenceStation);
		GameRegistry.registerBlock(MFFSMonazitOre);

		OreDictionary.registerOre("Forcicium", MFFSitemForcicium);

		GameRegistry.registerTileEntity(TileEntityAreaDefenseStation.class, "MFFSDefenceStation");
		GameRegistry
				.registerTileEntity(TileEntityGenerator.class, "MFFSGenerator");
		GameRegistry
				.registerTileEntity(TileEntityProjector.class, "MFFSProjector");
		GameRegistry.registerTileEntity(TileEntitySecurityStation.class,
				"MFFSSecurtyStation");
		GameRegistry.registerTileEntity(TileEntityForceField.class,
				"MFFSForceField");

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemFocusmatix, 64),
				new Object[] { "ACA", "CBC", "ACA", 'A',
						Items.getItem("carbonPlate"), 'B',
						Item.diamond, 'C', Block.glass });

		CraftingManager.getInstance().addRecipe(
				new ItemStack(MFFSitemUpgradegenEUInjektor),
				new Object[] { "ABA", "ACA", "ABA", 'A',
						MFFSitemForcicium,
						'B',
						Items.getItem("trippleInsulatedIronCableItem"),
						'C', Items.getItem("hvTransformer") });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemcardempty), new Object[] {
				"AAA", "ABA", "AAA", 'A', Item.paper,
				'B', Items.getItem("electronicCircuit") });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemupgradegencap),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("electrolyzedWaterCell") });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemupgradegenrange),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("frequencyTransmitter") });
		CraftingManager.getInstance().addRecipe(
				new ItemStack(MFFSGenerator, 1),
				new Object[] { "ABA", "CDC", "ABA", 'A',
						MFFSitemForcePowerCrystal,
						'B',
						Items.getItem("frequencyTransmitter"),
						'C',
						Items.getItem("electronicCircuit"),
						'D',
						Items.getItem("advancedMachine") });

		CraftingManager.getInstance()
				.addRecipe(
						new ItemStack(MFFSProjector, 1),
						new Object[] { "DCD", "CAC", "DBD",
								'A',
								Items.getItem("advancedMachine"),
								'B',
								Items.getItem("frequencyTransmitter"),
								'C', MFFSitemFocusmatix,
								'D',
								Items.getItem("advancedAlloy") });
		CraftingManager.getInstance()
				.addRecipe(
						new ItemStack(MFFSSecurtyStation, 1),
						new Object[] { "DCD", "CAC", "DBD",
								'A',
								Items.getItem("advancedMachine"),
								'B',
								Items.getItem("teleporter"),
								'C',
								Items.getItem("electronicCircuit"),
								'D',
								Items.getItem("advancedAlloy") });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemFocusmatix, 64),
				new Object[] { "ACA", "CBC", "ACA", 'A',
						Items.getItem("carbonPlate"), 'B',
						Item.diamond, 'C', Block.glass });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionZapper),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("teslaCoil") });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionSubwater),
				new Object[] { "BAB", "ABA", "BAB", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Item.bucketEmpty });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionDome),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("electronicCircuit") });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionCutter),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Item.pickaxeSteel });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorTypsphere),
				new Object[] { " B ", "BAB", " B ", 'A',
						MFFSitemFocusmatix, 'B',
						Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorTypkube), new Object[] {
				"B B", " A ", "B B", 'A',
				MFFSitemFocusmatix, 'B', Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorTypwall), new Object[] {
				"AA ", "AA ", "BB ", 'A',
				MFFSitemFocusmatix, 'B', Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorTypdeflector),
				new Object[] { "AAA", "ABA", "AAA", 'A',
						MFFSitemFocusmatix, 'B',
						Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorTyptube), new Object[] {
				"AAA", " B ", "AAA", 'A',
				MFFSitemFocusmatix, 'B', Block.obsidian });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorFFStrenght),
				new Object[] { "AAA", "AAA", "AAA", 'A',
						MFFSitemFocusmatix });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorFFDistance),
				new Object[] { "AAA", "   ", "AAA", 'A',
						MFFSitemFocusmatix });

		CraftingManager.getInstance().addRecipe(
				new ItemStack(MFFSSecStationexidreader),
				new Object[] { "A  ", " B ", "  C", 'A',
						MFFSitemcardempty, 'B',
						Items.getItem("electronicCircuit"),
						'C', Item.redstone });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemForceFieldsync),
				new Object[] { " A ", "ABA", " A ", 'A',
			MFFSitemFocusmatix, 'B',
			Item.diamond });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemMFDidtool),
				new Object[] { "DAE", " C ", "CBC", 'A',
			Item.redstone, 'B',Items.getItem("advancedCircuit") ,
			'C',Items.getItem("carbonPlate"),
			'D',Items.getItem("wrench"),
			'E',Block.lever
		});

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemSwitch),
				new Object[] { "DEA", " C ", "CBC", 'A',
			Item.redstone, 'B',Items.getItem("advancedCircuit") ,
			'C',Items.getItem("carbonPlate"),
			'D',Items.getItem("wrench"),
			'E',Block.lever
		});

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemWrench),
				new Object[] { "EDA", " C ", "CBC", 'A',
			Item.redstone, 'B',Items.getItem("advancedCircuit") ,
			'C',Items.getItem("carbonPlate"),
			'D',Items.getItem("wrench"),
			'E',Block.lever
		});

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorTypcontainment), new Object[] {
			"AAA", "ABA", "AAA", 'B',
			MFFSitemFocusmatix, 'A', Block.obsidian });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorTypAdvCube), new Object[] {
			"AAA", "ABA", "AAA", 'A',
			MFFSitemFocusmatix, 'B', MFFSProjectorTypkube });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionForceFieldJammer), new Object[] { " A ", "ABA", " A ", 'A', Items.getItem("frequencyTransmitter"),'B', MFFSitemFocusmatix });
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionCamouflage), new Object[] { " A ", "ABA", " A ", 'B', Items.getItem("matter"),'A', Items.getItem("advancedAlloy") });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionFieldFusion), new Object[] { " A ", "ABA", " A ", 'B', Items.getItem("advancedCircuit"),'A', Items.getItem("advancedAlloy") });

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSDefenceStation,1),new Object[] { " B ", "CAC", " D ",'A',Items.getItem("advancedMachine"),
			'B',Items.getItem("frequencyTransmitter"),
			'C',Item.enderPearl,
			'D',Items.getItem("carbonPlate")
		});

		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionDefenceStation),new Object[] { " B ", "CAC", " B ", 'A', Items.getItem("teslaCoil"), 'B',this.MFFSItemIDCard , 'C',Items.getItem("electronicCircuit")});
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSProjectorOptionMoobEx), new Object[] { "BCB", "DAD", "ECE", 'A', Items.getItem("teslaCoil"), 'B', Item.bone, 'C', Item.blazeRod, 'D', Item.rottenFlesh, 'E', Item.spiderEye});
		CraftingManager.getInstance().addRecipe(new ItemStack(MFFSitemForcePowerCrystal), new Object[] { "BBB", "BAB", "BBB", 'A', Item.diamond,'B', MFFSitemForcicium});

		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(MFFSitemcardempty),new Object[] { new ItemStack(MFFSitemfc) });
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(MFFSitemcardempty),new Object[] { new ItemStack(MFFSItemIDCard) });
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(MFFSitemcardempty),new Object[] { new ItemStack(MFFSItemSecLinkCard) });

		Ic2Recipes.addExtractorRecipe(new ItemStack(MFFSMonazitOre), new ItemStack(MFFSitemForcicium,4));

		NetworkRegistry.instance().registerGuiHandler(instance, proxy);

		proxy.registerRenderInformation();
		proxy.registerTileEntitySpecialRenderer();

		Generatetexturindex(Block.oreLapis, 0);
		Generatetexturindex(Block.blockLapis, 0);
		Generatetexturindex(Block.sandStone, 0);
		Generatetexturindex(Block.sandStone, 1);
		Generatetexturindex(Block.sandStone, 2);
		Generatetexturindex(Block.melon, 0);
		Generatetexturindex(Block.mycelium, 0);
		Generatetexturindex(Block.netherBrick, 0);
		Generatetexturindex(Block.whiteStone, 0);
		Generatetexturindex(Block.whiteStone, 1);
		Generatetexturindex(Block.redstoneLampIdle, 0);
		Generatetexturindex(Block.music, 0);
		Generatetexturindex(Block.blockGold, 0);
		Generatetexturindex(Block.blockSteel, 0);
		Generatetexturindex(Block.brick, 0);
		Generatetexturindex(Block.tnt, 0);
		Generatetexturindex(Block.bookShelf, 0);
		Generatetexturindex(Block.cobblestoneMossy, 0);
		Generatetexturindex(Block.obsidian, 0);
		Generatetexturindex(Block.oreDiamond, 0);
		Generatetexturindex(Block.blockDiamond, 0);
		Generatetexturindex(Block.workbench, 0);
		Generatetexturindex(Block.oreRedstone, 0);
		Generatetexturindex(Block.ice, 0);
 		Generatetexturindex(Block.stone, 0);
 		Generatetexturindex(Block.dirt, 0);
		Generatetexturindex(Block.cobblestone, 0);
		Generatetexturindex(Block.bedrock, 0);
		Generatetexturindex(Block.sand, 0);
		Generatetexturindex(Block.gravel, 0);
		Generatetexturindex(Block.oreGold, 0);
		Generatetexturindex(Block.oreIron, 0);
		Generatetexturindex(Block.oreCoal, 0);
		Generatetexturindex(Block.sponge, 0);
		Generatetexturindex(Block.glass, 0);
		Generatetexturindex(Block.blockSnow, 0);
		Generatetexturindex(Block.blockClay, 0);
		Generatetexturindex(Block.jukebox, 0);
		Generatetexturindex(Block.netherrack, 0);
		Generatetexturindex(Block.slowSand, 0);
		Generatetexturindex(Block.glowStone, 0);
		Generatetexturindex(Block.stoneBrick, 0);
		Generatetexturindex(Block.stoneBrick, 1);
		Generatetexturindex(Block.stoneBrick, 2);

		for(int meta = 0; meta< 4; meta++) {
			Generatetexturindex(Block.planks, meta);
			Generatetexturindex(Block.wood, meta);
		}

		for(int meta = 0; meta < 12; meta++) {
			Generatetexturindex(Block.mushroomCapBrown, meta);
			Generatetexturindex(Block.mushroomCapRed, meta);
		}

		for(int meta = 0; meta< 16; meta++)
		{	Generatetexturindex(Block.cloth, meta);}

		LanguageRegistry.instance().addNameForObject(MFFSMonazitOre,"en_US", "Monazit");
		LanguageRegistry.instance().addNameForObject(MFFSitemForcicium,"en_US", "Forcicium");
		LanguageRegistry.instance().addNameForObject(MFFSitemForcePowerCrystal,"en_US", "MFFS Force Energy Crystal");
		LanguageRegistry.instance().addNameForObject(MFFSitemSwitch,"en_US", "MFFS MultiTool <Switch>");
		LanguageRegistry.instance().addNameForObject(MFFSitemWrench,"en_US", "MFFS MultiTool <Wrench>");
		LanguageRegistry.instance().addNameForObject(MFFSDefenceStation,"en_US", "MFFS Area Defense Station");
		LanguageRegistry.instance().addNameForObject(MFFSGenerator,"en_US", "MFFS Force Power Generator");
		LanguageRegistry.instance().addNameForObject(MFFSProjector,"en_US", "MFFS Modular Projector");
		LanguageRegistry.instance().addNameForObject(MFFSSecurtyStation,"en_US", "MFFS Security Station");
		LanguageRegistry.instance().addNameForObject(MFFSitemFocusmatix,"en_US", "MFFS Projector Focus Matrix");
		LanguageRegistry.instance().addNameForObject(MFFSitemForceFieldsync,"en_US","MFFS Force Field Synchron Capacitor");
		LanguageRegistry.instance().addNameForObject(MFFSitemcardempty,"en_US", "MFFS Card <blank> ");
		LanguageRegistry.instance().addNameForObject(MFFSitemfc,"en_US", "MFFS Card <Power Link>");
		LanguageRegistry.instance().addNameForObject(MFFSItemIDCard,"en_US", "MFFS Card <Personal ID>");
		LanguageRegistry.instance().addNameForObject(MFFSItemSecLinkCard,"en_US", "MFFS Card <Security Station Link> ");
		LanguageRegistry.instance().addNameForObject(MFFSitemMFDdebugger,"en_US", "MFFS Device <Debugger>");
		LanguageRegistry.instance().addNameForObject(MFFSitemMFDidtool,"en_US", "MFFS MultiTool <ID-Card Coder>");
		LanguageRegistry.instance().addNameForObject(MFFSitemUpgradegenEUInjektor,"en_US","MFFS  Generator Injektor <EU>");
		LanguageRegistry.instance().addNameForObject(MFFSitemupgradegenrange,"en_US","MFFS Generator Upgrade <Range> ");
		LanguageRegistry.instance().addNameForObject(MFFSitemupgradegencap,"en_US","MFFS Generator Upgrade <Capacity> ");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorTypsphere,"en_US","MFFS Projector Module <Sphere> ");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorTypkube,"en_US",	"MFFS Projector Module <Cube>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorTypwall,"en_US","MFFS Projector Module <Wall>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorTypdeflector,"en_US","MFFS Projector Module <Deflector>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorTyptube,"en_US","MFFS Projector Module <Tube>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionZapper,"en_US","MFFS Projector Upgrade <touch damage> ");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionSubwater,"en_US","MFFS Projector Upgrade <Sponge> ");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionDome,"en_US",	"MFFS Projector Upgrade <Field Manipulator> ");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionCutter,"en_US","MFFS Projector Upgrade <Block Breaker> ");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionDefenceStation,"en_US","MFFS Defense Station Upgrade");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionMoobEx,"en_US","MFFS NPC Defense Upgrade");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionForceFieldJammer ,"en_US","MFFS Projector Upgrade <Force Field Jammer>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorFFDistance,"en_US","MFFS Projector Field Modulator <distance>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorFFStrenght,"en_US","MFFS Projector Field Modulator <strength>");
		LanguageRegistry.instance().addNameForObject(MFFSSecStationexidreader,"en_US","MFFS Security Station <External ID-Reader>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorTypcontainment,"en_US","MFFS Projector Module <Containment>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorTypAdvCube,"en_US","MFFS Projector Module <Adv.Cube>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionCamouflage ,"en_US","MFFS Projector Upgrade <Camouflage>");
		LanguageRegistry.instance().addNameForObject(MFFSProjectorOptionFieldFusion ,"en_US","MFFS Projector Upgrade <Field Fusion>");

		GameRegistry.registerWorldGenerator(new MFFSWorldGenerator());
	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) {
		ExplosionWhitelist.addWhitelistedBlock(MFFSDefenceStation);
		ExplosionWhitelist.addWhitelistedBlock(MFFSGenerator);
		ExplosionWhitelist.addWhitelistedBlock(MFFSProjector);
		ExplosionWhitelist.addWhitelistedBlock(MFFSSecurtyStation);
	}

	private void Generatetexturindex(Block block, int meta)
	{
		int[] index = new int[6];
		for(int side = 0; side < 6; side++)
			index[side] = block.getBlockTextureFromSideAndMetadata(side, meta);
		idmetatotextur.put(block.blockID + meta*1000, index);
	}
}