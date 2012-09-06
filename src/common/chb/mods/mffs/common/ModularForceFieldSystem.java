package chb.mods.mffs.common;


import chb.mods.mffs.common.network.*;

import ic2.api.ExplosionWhitelist;
import ic2.api.Ic2Recipes;
import ic2.api.Items;

import java.io.File;
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
			
			Admin = MFFSconfig.getOrCreateProperty("ForceFieldMaster", Configuration.CATEGORY_GENERAL, "Thunderdark").value;
			forcefieldremoveonlywaterandlava = MFFSconfig.getOrCreateBooleanProperty("forcefieldremoveonlywaterandlava", Configuration.CATEGORY_GENERAL, false).getBoolean(false);
			forcefieldtransportcost = MFFSconfig.getOrCreateIntProperty("forcefieldremoveonlywaterandlava", Configuration.CATEGORY_GENERAL, 10000).getInt(10000);
			forcefieldblockcost = MFFSconfig.getOrCreateIntProperty("forcefieldblockcost", Configuration.CATEGORY_GENERAL, 1).getInt(1);
			forcefieldblockcreatemodifier = MFFSconfig.getOrCreateIntProperty("forcefieldblockcreatemodifier", Configuration.CATEGORY_GENERAL, 10).getInt(10);
			forcefieldblockzappermodifier = MFFSconfig.getOrCreateIntProperty("forcefieldblockzappermodifier", Configuration.CATEGORY_GENERAL, 2).getInt(2);
			forcefieldmaxblockpeerTick = MFFSconfig.getOrCreateIntProperty("forcefieldmaxblockpeerTick", Configuration.CATEGORY_GENERAL,5000).getInt(5000);
			DefenseStationDamage = MFFSconfig.getOrCreateIntProperty("DefenseStationDamage", Configuration.CATEGORY_GENERAL,10).getInt(10);
			MobDefenseDamage = MFFSconfig.getOrCreateIntProperty("MobDefenseDamage", Configuration.CATEGORY_GENERAL,10).getInt(10);
			DefenseStationFPpeerAttack = MFFSconfig.getOrCreateIntProperty("DefenseStationFPpeerAttack", Configuration.CATEGORY_GENERAL,25000).getInt(25000);
		
		    MFFSMonazitOre = new BlockMonazitOre(MFFSconfig.getOrCreateBlockIdProperty("MFFSMonazitOre", 4077).getInt(4077)).setBlockName("MFFSMonazitOre");
			MFFSDefenceStation = new BlockAreaDefenseStation(MFFSconfig.getOrCreateBlockIdProperty("MFFSDefenceStation", 4078).getInt(4078),0).setBlockName("MFFSDefenceStation");
			MFFSSecurtyStation = new BlockSecurtyStation(MFFSconfig.getOrCreateBlockIdProperty("MFFSSecurtyStation", 4079).getInt(4079),0).setBlockName("MFFSSecurtyStation");
			MFFSGenerator = new BlockGenerator(MFFSconfig.getOrCreateBlockIdProperty("MFFSGenerator", 4080).getInt(4080),0).setBlockName("MFFSGenerator");
			MFFSProjector = new BlockProjector(MFFSconfig.getOrCreateBlockIdProperty("MFFSProjector", 4081).getInt(4081),0).setBlockName("MFFSProjector");
			MFFSFieldblock = new BlockForceField(MFFSconfig.getOrCreateBlockIdProperty("MFFSFieldblock", 4082).getInt(4082));

			MFFSitemWrench= new ItemWrench(MFFSconfig.getOrCreateIntProperty("itemWrench",Configuration.CATEGORY_ITEM,11107).getInt(11107)).setItemName("itemWrench");
			MFFSitemSwitch= new ItemSwitch(MFFSconfig.getOrCreateIntProperty("itemSwitch",Configuration.CATEGORY_ITEM,11108).getInt(11108)).setItemName("itemSwitch");
			MFFSitemForceFieldsync= new ItemForceFieldSynchronCapacitor(MFFSconfig.getOrCreateIntProperty("itemForceFieldsync",Configuration.CATEGORY_ITEM,11109).getInt(11109)).setItemName("itemForceFieldsync");
			MFFSitemMFDidtool= new ItemPersonalIIDWriter(MFFSconfig.getOrCreateIntProperty("ItemMFDIDwriter",Configuration.CATEGORY_ITEM,11110).getInt(11110)).setItemName("ItemMFDIDwriter");
			MFFSitemMFDdebugger= new ItemDebugger(MFFSconfig.getOrCreateIntProperty("itemMFDdebugger",Configuration.CATEGORY_ITEM,11111).getInt(11111)).setItemName("itemMFDdebugger");
			MFFSitemcardempty= new ItemCardEmpty(MFFSconfig.getOrCreateIntProperty("itemcardempty",Configuration.CATEGORY_ITEM,11115).getInt(11115)).setItemName("itemcardempty");
			MFFSitemfc= new ItemCardPowerLink(MFFSconfig.getOrCreateIntProperty("itemfc",Configuration.CATEGORY_ITEM,11116).getInt(11116)).setItemName("itemfc");
			MFFSitemUpgradegenEUInjektor= new ItemGeneratorInjektorEU(MFFSconfig.getOrCreateIntProperty("ItemUpgradegenEuinjekt",Configuration.CATEGORY_ITEM,11118).getInt(11116)).setItemName("ItemUpgradegenEuinjekt");
			MFFSitemupgradegenrange= new ItemGeneratorUpgradeRange(MFFSconfig.getOrCreateIntProperty("itemupgradegenrange",Configuration.CATEGORY_ITEM,11119).getInt(11119)).setItemName("itemupgradegenrange");
			MFFSitemupgradegencap= new ItemGeneratorUpgradeCapacity(MFFSconfig.getOrCreateIntProperty("itemupgradegencap",Configuration.CATEGORY_ITEM,11120).getInt(11120)).setItemName("itemupgradegencap");
			MFFSProjectorTypsphere= new ItemProjectorModuleSphere(MFFSconfig.getOrCreateIntProperty("itemProjectorTypsphere",Configuration.CATEGORY_ITEM,11121).getInt(11121)).setItemName("itemProjectorTypsphere");
			MFFSProjectorTypkube= new ItemProjectorModuleCube(MFFSconfig.getOrCreateIntProperty("itemProjectorTypkube",Configuration.CATEGORY_ITEM,11122).getInt(11122)).setItemName("itemProjectorTypkube");
			MFFSProjectorTypwall= new ItemProjectorModuleWall(MFFSconfig.getOrCreateIntProperty("itemProjectorTypwall",Configuration.CATEGORY_ITEM,11124).getInt(11124)).setItemName("itemProjectorTypwall");
			MFFSProjectorTypdeflector= new ItemProjectorModuleDeflector(MFFSconfig.getOrCreateIntProperty("itemProjectorTypdeflector",Configuration.CATEGORY_ITEM,11125).getInt(11125)).setItemName("itemProjectorTypdeflector");
			MFFSProjectorTyptube= new ItemProjectorModuleTube(MFFSconfig.getOrCreateIntProperty("itemProjectorTyptube",Configuration.CATEGORY_ITEM,11126).getInt(11126)).setItemName("itemProjectorTyptube");
			MFFSProjectorOptionZapper= new ItemProjectorOptionTouchDamage(MFFSconfig.getOrCreateIntProperty("itemupgradeprozapper",Configuration.CATEGORY_ITEM,11127).getInt(11127)).setItemName("itemupgradeprozapper");
			MFFSProjectorOptionSubwater= new ItemProjectorOptionSponge(MFFSconfig.getOrCreateIntProperty("itemupgradeprosubwater",Configuration.CATEGORY_ITEM,11128).getInt(11128)).setItemName("itemupgradeprosubwater");
			MFFSProjectorOptionDome= new ItemProjectorOptionFieldManipulator(MFFSconfig.getOrCreateIntProperty("itemupgradeprodome",Configuration.CATEGORY_ITEM,11129).getInt(11129)).setItemName("itemupgradeprodome");
			MFFSProjectorOptionCutter= new ItemProjectorOptionBlockBreaker(MFFSconfig.getOrCreateIntProperty("itemUpgradeprocutter",Configuration.CATEGORY_ITEM,11130).getInt(11130)).setItemName("itemUpgradeprocutter");
			MFFSProjectorFFDistance= new ItemProjectorFieldModulatorDistance(MFFSconfig.getOrCreateIntProperty("itemProjectorFFDistance",Configuration.CATEGORY_ITEM,11131).getInt(11131)).setItemName("itemProjectorFFDistance");
			MFFSProjectorFFStrenght= new ItemProjectorFieldModulatorStrength(MFFSconfig.getOrCreateIntProperty("itemProjectorFFStrength",Configuration.CATEGORY_ITEM,11132).getInt(11132)).setItemName("itemProjectorFFStrength");
			MFFSitemFocusmatix= new ItemProjectorFocusMatrix(MFFSconfig.getOrCreateIntProperty("itemPorjectorFocusmatrix",Configuration.CATEGORY_ITEM,11133).getInt(11133)).setItemName("itemPorjectorFocusmatrix");
			MFFSItemIDCard= new ItemCardPersonalID(MFFSconfig.getOrCreateIntProperty("itemIDCard",Configuration.CATEGORY_ITEM,11134).getInt(11134)).setItemName("itemPorjectorFocusmatrix");
			MFFSItemSecLinkCard= new ItemCardSecurityLink(MFFSconfig.getOrCreateIntProperty("itemSecLinkCard",Configuration.CATEGORY_ITEM,11135).getInt(11135)).setItemName("itemSecLinkCard");
			MFFSSecStationexidreader= new ItemSecurityStationExternalIDReader(MFFSconfig.getOrCreateIntProperty("itemSecStationexidreader",Configuration.CATEGORY_ITEM,11136).getInt(11136)).setItemName("itemSecStationexidreader");
			MFFSProjectorOptionDefenceStation= new ItemProjectorOptionDefenseStation(MFFSconfig.getOrCreateIntProperty("itemProjectorOptiondefencestation",Configuration.CATEGORY_ITEM,11137).getInt(11137)).setItemName("itemProjectorOptiondefencestation");
			MFFSProjectorOptionMoobEx= new ItemProjectorOptionMobDefence(MFFSconfig.getOrCreateIntProperty("itemProjectorOptionMoobEx",Configuration.CATEGORY_ITEM,11138).getInt(11138)).setItemName("itemProjectorOptionMoobEx");
			MFFSProjectorOptionForceFieldJammer= new ItemProjectorOptionForceFieldJammer(MFFSconfig.getOrCreateIntProperty("itemProjectorOptionFFJammer",Configuration.CATEGORY_ITEM,11139).getInt(11139)).setItemName("itemProjectorOptionFFJammer");
			MFFSProjectorTypcontainment= new ItemProjectorModuleContainment(MFFSconfig.getOrCreateIntProperty("itemProjectorModuleContainment",Configuration.CATEGORY_ITEM,11140).getInt(11140)).setItemName("itemProjectorModuleContainment");
			MFFSProjectorOptionCamouflage= new ItemProjectorOptionCamoflage(MFFSconfig.getOrCreateIntProperty("itemProjectorOptionCamoflage",Configuration.CATEGORY_ITEM,11141).getInt(11141)).setItemName("itemProjectorOptionCamoflage");
			MFFSProjectorTypAdvCube= new ItemProjectorModuleAdvCube(MFFSconfig.getOrCreateIntProperty("itemProjectorModuleAdvCube",Configuration.CATEGORY_ITEM,11142).getInt(11142)).setItemName("itemProjectorModuleAdvCube");
			MFFSProjectorOptionFieldFusion= new ItemProjectorOptionFieldFusion(MFFSconfig.getOrCreateIntProperty("itemProjectorOptionFieldFusion",Configuration.CATEGORY_ITEM,11143).getInt(11143)).setItemName("itemProjectorOptionFieldFusion");
			MFFSitemForcePowerCrystal= new ItemForcePowerCrystal(MFFSconfig.getOrCreateIntProperty("itemForcePowerCrystal",Configuration.CATEGORY_ITEM,11145).getInt(11145)).setItemName("itemForcePowerCrystal");
			MFFSitemForcicium= new ItemForcicium(MFFSconfig.getOrCreateIntProperty("itemForcicium",Configuration.CATEGORY_ITEM,11144).getInt(11144)).setItemName("itemForcicium");
			
			
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