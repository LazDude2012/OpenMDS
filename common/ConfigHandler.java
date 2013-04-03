package OpenMDS.common;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;

public class ConfigHandler
{
	public static int blockDefenceComputerID;
	public static int blockAttunementBenchID;

	public static int itemAttunementCrystalID;
	public static int itemSpannerID;

	public static void Load(FMLPreInitializationEvent e)
	{
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());

		Property defencecomputerproperty = config.getBlock("block","DefenceComputerID",2100,"The BlockID for the Defence Computer");
		blockDefenceComputerID = defencecomputerproperty.getInt();

		Property attunementbenchproperty = config.getBlock("block","AttunementBenchID",2101,"The BlockID for the Attunement Bench");
		blockAttunementBenchID = attunementbenchproperty.getInt();

		Property attunementcrystalproperty = config.getItem("item","AttunementCrystalID",21000,"The ItemID for the Attunement Crystal");
		itemAttunementCrystalID = attunementcrystalproperty.getInt() - 256;

		Property spannerproperty = config.getItem("item","DefenceSpannerID",21001,"The ItemID for the Attunement Crystal");
		itemSpannerID = spannerproperty.getInt();
    }
}
