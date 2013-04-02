package OpenMDS.common;

import OpenMDS.block.BlockAttunementBench;
import OpenMDS.block.BlockDefenceComputer;
import OpenMDS.client.ClientPacketHandler;
import OpenMDS.client.ClientProxy;
import OpenMDS.item.ItemAttunementCrystal;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod(modid="OpenMDS",version = "ALPHA 1", name="Open Modular Defence System")
@NetworkMod(clientSideRequired = true,serverSideRequired = true,
clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels="OpenMDS",packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels="OpenMDS",packetHandler = ServerPacketHandler.class),
channels="OpenMDS")
public class OpenMDS
{
	@SidedProxy(clientSide="OpenMDS.client.ClientProxy",serverSide="OpenMDS.common.CommonProxy")
	public static CommonProxy proxy = new CommonProxy();

	//region  Static fields
	public static Block blockDefenceComputer;
	public static Block blockAttunementBench;

	public static Item itemAttunementCrystal;

	public static int DEFENCECOMP_GUI = 1;
	public static int ATTUNEMENT_GUI = 2;

	public static GuiHandler guiHandler;
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
		NetworkRegistry.instance().registerGuiHandler(this,guiHandler);
	}

	private void RegisterBlocks()
	{
		blockAttunementBench = new BlockAttunementBench(ConfigHandler.blockAttunementBenchID);
		GameRegistry.registerBlock(blockAttunementBench, "blockAttunementBench");
		blockDefenceComputer = new BlockDefenceComputer(ConfigHandler.blockDefenceComputerID);
		GameRegistry.registerBlock(blockDefenceComputer, "blockDefenceComputer");
	}

	private void RegisterItems()
	{
		itemAttunementCrystal = new ItemAttunementCrystal(ConfigHandler.itemAttunementCrystalID);
		GameRegistry.registerItem(itemAttunementCrystal, "itemAttunementCrystal");
	}
}
