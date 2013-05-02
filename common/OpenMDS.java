package OpenMDS.common;

import OpenMDS.block.BlockAttunementBench;
import OpenMDS.block.BlockAttunementMonitor;
import OpenMDS.client.ClientPacketHandler;
import OpenMDS.item.ItemAttunementCrystal;
import OpenMDS.item.ItemShimmeringPearl;
import OpenMDS.item.ItemSpanner;
import OpenMDS.tile.TileAttunementBench;
import OpenMDS.tile.TileAttunementMonitor;
import OpenMDS.util.ConfigHandler;
import OpenMDS.util.GuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid="OpenMDS",version = "ALPHA 1", name="Open Modular Defence System")
@NetworkMod(clientSideRequired = true,serverSideRequired = true,
clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels={"OpenMDS","OpenMDS_TAB","OpenMDS_TAM"},packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels={"OpenMDS","OpenMDS_TAB","OpenMDS_TAM"},packetHandler = ServerPacketHandler.class))
public class OpenMDS
{
	@SidedProxy(clientSide="OpenMDS.client.ClientProxy",serverSide="OpenMDS.common.CommonProxy")
	public static CommonProxy proxy = new CommonProxy();

	//region  Static fields
	public static Block blockAttunementBench;
	public static Block blockAttunementMonitor;

	public static Item itemAttunementCrystal;
	public static Item itemShimmeringPearl;
	public static Item itemDefenceSpanner;

	final public static int ATTUNEBENCH_GUI = 1;
	final public static int ATTUNEMONITOR_GUI = 2;

	public static GuiHandler guiHandler;

	public static CreativeTabs tabMDS = new TabMDS(CreativeTabs.getNextID(),"MDSTabMDS");

	@Mod.Instance
	public static OpenMDS instance;
	//endregion

	@Mod.PreInit
	public void PreInit(FMLPreInitializationEvent e)
	{
		guiHandler = new GuiHandler();
		ConfigHandler.Load(e);
	}

	@Mod.Init
	public void Init(FMLInitializationEvent e)
	{
		RegisterBlocks();
		RegisterItems();
		RegisterRecipes();
		NetworkRegistry.instance().registerGuiHandler(this,guiHandler);
	}

	private void RegisterRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(itemShimmeringPearl, 1)," X ","XPX"," X ",'X',Item.redstone, 'P',Item.enderPearl);
		GameRegistry.addRecipe(new ItemStack(itemDefenceSpanner, 1),"X X","XPX"," X ",'X',Item.ingotIron, 'P',itemShimmeringPearl);
		GameRegistry.addRecipe(new ItemStack(itemAttunementCrystal,1),"PIP","IDI",'P',itemShimmeringPearl, 'I',Item.ingotIron,'D',Item.diamond);
		GameRegistry.addRecipe(new ItemStack(blockAttunementBench, 1),"P P","SIS",'P',itemShimmeringPearl, 'I',Item.ingotIron,'S',Block.stone);
		GameRegistry.addRecipe(new ItemStack(blockAttunementMonitor, 1),"PEP","SRS",'P',itemShimmeringPearl, 'E',Item.eyeOfEnder,'R',Item.redstone,'S',Block.stone);
	}

	private void RegisterBlocks()
	{
		blockAttunementBench = new BlockAttunementBench(ConfigHandler.blockAttunementBenchID);
		GameRegistry.registerBlock(blockAttunementBench, "blockAttunementBench");
		LanguageRegistry.addName(blockAttunementBench, "Attunement Bench");
		GameRegistry.registerTileEntity(TileAttunementBench.class,"tileAttunementBench");

		blockAttunementMonitor = new BlockAttunementMonitor(ConfigHandler.blockAttunementMonitorID);
		GameRegistry.registerBlock(blockAttunementMonitor,"blockAttunementMonitor");
		LanguageRegistry.addName(blockAttunementMonitor, "Attunement Monitor");
		GameRegistry.registerTileEntity(TileAttunementMonitor.class,"tileAttunementMonitor");
	}

	private void RegisterItems()
	{
		itemAttunementCrystal = new ItemAttunementCrystal(ConfigHandler.itemAttunementCrystalID);
		GameRegistry.registerItem(itemAttunementCrystal, "itemAttunementCrystal");
		LanguageRegistry.addName(new ItemStack(itemAttunementCrystal,1), "Attunement Crystal");
		itemDefenceSpanner = new ItemSpanner(ConfigHandler.itemSpannerID);
		GameRegistry.registerItem(itemDefenceSpanner,"itemDefenceSpanner");
		LanguageRegistry.addName(new ItemStack(itemDefenceSpanner,1), "Defence Spanner");
		itemShimmeringPearl = new ItemShimmeringPearl(ConfigHandler.itemShimmeringPearlID);
		GameRegistry.registerItem(itemShimmeringPearl,"itemShimmeringPearl");
		LanguageRegistry.addName(itemShimmeringPearl, "Shimmering Pearl");
	}
}
