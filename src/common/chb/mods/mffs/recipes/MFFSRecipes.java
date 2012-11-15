package chb.mods.mffs.recipes;

import chb.mods.mffs.common.ModularForceFieldSystem;
import ic2.api.ExplosionWhitelist;
import ic2.api.Ic2Recipes;
import ic2.api.Items;
import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class MFFSRecipes {

	
	public static void init()
	{
		
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty),new Object[] { new ItemStack(ModularForceFieldSystem.MFFSitemfc) });
		
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty),new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemIDCard) });
		
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty),new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard) });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemForcePowerCrystal), new Object[] { "BBB", "BAB", "BBB", 'A', Item.diamond,'B', ModularForceFieldSystem.MFFSitemForcicium});
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypcontainment), new Object[] {
			"AAA", "ABA", "AAA", 'B',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'A', Block.obsidian });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypAdvCube), new Object[] {
			"AAA", "ABA", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'B', ModularForceFieldSystem.MFFSProjectorTypkube });
			
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypsphere),
				new Object[] { " B ", "BAB", " B ", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'B',
						Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypkube), new Object[] {
				"B B", " A ", "B B", 'A',
				ModularForceFieldSystem.MFFSitemFocusmatix, 'B', Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypwall), new Object[] {
				"AA ", "AA ", "BB ", 'A',
				ModularForceFieldSystem.MFFSitemFocusmatix, 'B', Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypdeflector),
				new Object[] { "AAA", "ABA", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'B',
						Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTyptube), new Object[] {
				"AAA", " B ", "AAA", 'A',
				ModularForceFieldSystem.MFFSitemFocusmatix, 'B', Block.obsidian });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorFFStrenght),
				new Object[] { "AAA", "AAA", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorFFDistance),
				new Object[] { "AAA", "   ", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix });


		if(Items.getItem("carbonPlate")!=null){
			
			
			Ic2Recipes.addMaceratorRecipe(new ItemStack(ModularForceFieldSystem.MFFSMonazitOre, 1), new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 10));
			Ic2Recipes.addMatterAmplifier(new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 1), 50000);
			
			
			CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemForcicumCell, 1),
			new Object[] { "AAA", "ABA", "ACA",
				'A',Items.getItem("refinedIronIngot"),
				'B',ModularForceFieldSystem.MFFSitemForcePowerCrystal,
				'C', Items.getItem("advancedCircuit") 
				});
			
			
			
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemupgradeexctractorboost,2),
				new Object[] { " B ", "BAB", " B ",
			'A',Items.getItem("overclockerUpgrade"), 'B',Items.getItem("carbonPlate")
		});
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSExtractor,1),
				new Object[] { " B ", "CDC", " E ",
			'B',Items.getItem("advancedCircuit"), 'C',ModularForceFieldSystem.MFFSitemForcePowerCrystal, 
			'D',Items.getItem("advancedMachine"), 'E',Items.getItem("extractor")
		});
		
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemupgradecapcap),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("carbonPlate"), 'B',
						ModularForceFieldSystem.MFFSitemForcePowerCrystal });
		
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemupgradecaprange),
				new Object[] { "AAA", "BCB", "BDB",
			'A',Items.getItem("copperCableItem"), 
			'B',Items.getItem("carbonPlate"),
			'C',Items.getItem("insulatedCopperCableItem"), 
			'D',Items.getItem("advancedCircuit") 				
		});

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemFocusmatix, 64),
				new Object[] { "ACA", "CBC", "ACA", 'A',
						Items.getItem("carbonPlate"), 'B',
						Item.diamond, 'C', Block.glass });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] {
				"AAA", "ABA", "AAA", 'A', Item.paper,
				'B', Items.getItem("electronicCircuit") });


		

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemFocusmatix, 64),
				new Object[] { "ACA", "CBC", "ACA", 'A',
						Items.getItem("carbonPlate"), 'B',
						Item.diamond, 'C', Block.glass });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionZapper),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("teslaCoil") });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionSubwater),
				new Object[] { "BAB", "ABA", "BAB", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Item.bucketEmpty });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionDome),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("electronicCircuit") });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionCutter),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Item.pickaxeSteel });


		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemWrench),
				new Object[] { "ADE", "CFC", "CBC", 'A',
			Item.redstone, 'B',Items.getItem("advancedCircuit") ,
			'C',Items.getItem("carbonPlate"),
			'D',Items.getItem("wrench"),
			'E',Block.lever,'F',ModularForceFieldSystem.MFFSitemForcePowerCrystal
		});


		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer), new Object[] { " A ", "ABA", " A ", 'A', Items.getItem("frequencyTransmitter"),'B', ModularForceFieldSystem.MFFSitemFocusmatix });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionCamouflage), new Object[] { " A ", "ABA", " A ", 'B', Items.getItem("matter"),'A', Items.getItem("advancedAlloy") });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionFieldFusion), new Object[] { " A ", "ABA", " A ", 'B', Items.getItem("advancedCircuit"),'A', Items.getItem("advancedAlloy") });


		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionDefenceStation),new Object[] { " B ", "CAC", " B ", 'A', Items.getItem("teslaCoil"), 'B',ModularForceFieldSystem.MFFSItemIDCard , 'C',Items.getItem("electronicCircuit")});
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionMoobEx), new Object[] { "BCB", "DAD", "ECE", 'A', Items.getItem("teslaCoil"), 'B', Item.bone, 'C', Item.blazeRod, 'D', Item.rottenFlesh, 'E', Item.spiderEye});
	
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemForcicium,16), new Object[] { " AA", "A  ", " A ", 'A', Items.getItem("matter") });
		

		}
	}
	
}
