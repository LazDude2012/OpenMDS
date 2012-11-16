/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    
    Matchlighter
    Thunderdark 

 */

package chb.mods.mffs.common;

import basiccomponents.BasicComponents;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import ic2.api.ExplosionWhitelist;
import ic2.api.Items;
import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import chb.mods.mffs.client.GuiAdvSecurityStation;
import chb.mods.mffs.client.GuiAreaDefenseStation;
import chb.mods.mffs.client.GuiCapacitor;
import chb.mods.mffs.client.GuiConverter;
import chb.mods.mffs.client.GuiExtractor;
import chb.mods.mffs.client.GuiProjector;
import chb.mods.mffs.client.GuiSecStorage;
import chb.mods.mffs.recipes.MFFSRecipes;

public enum MFFSMaschines{
	Projector("maschine","mffsProjector", "Projector", "chb.mods.mffs.common.TileEntityProjector", "chb.mods.mffs.client.GuiProjector", "chb.mods.mffs.common.ContainerProjector",ModularForceFieldSystem.MFFSProjector,0, "AmAmMmAFA"),
	Extractor("maschine","mffsExtractor", "Extractor", "chb.mods.mffs.common.TileEntityExtractor", "chb.mods.mffs.client.GuiExtractor", "chb.mods.mffs.common.ContainerForceEnergyExtractor",ModularForceFieldSystem.MFFSExtractor,0, " C fMf E "),
	Capacitor("maschine","mffsCapacitor", "Capacitor", "chb.mods.mffs.common.TileEntityCapacitor", "chb.mods.mffs.client.GuiCapacitor", "chb.mods.mffs.common.ContainerCapacitor",ModularForceFieldSystem.MFFSCapacitor,0, "fFfcMcfFf"),
	Converter("maschine","mffsConverter", "Converter", "chb.mods.mffs.common.TileEntityConverter", "chb.mods.mffs.client.GuiConverter", "chb.mods.mffs.common.ContainerConverter",ModularForceFieldSystem.MFFSForceEnergyConverter,0, "IJIFKGILI"),
	DefenceStation("maschine","mffsDefenceStation","Defence Station","chb.mods.mffs.common.TileEntityAreaDefenseStation","chb.mods.mffs.client.GuiAreaDefenseStation","chb.mods.mffs.common.ContainerAreaDefenseStation",ModularForceFieldSystem.MFFSDefenceStation,0," F pMp P "),
	SecurityStation("maschine","mffsSecurityStation", "Security Station", "chb.mods.mffs.common.TileEntityAdvSecurityStation", "chb.mods.mffs.client.GuiAdvSecurityStation", "chb.mods.mffs.common.ContainerAdvSecurityStation",ModularForceFieldSystem.MFFSSecurtyStation,0, "AcAcMcAFA"),
	SecurityStorage("maschine","mffsSecurityStorage", "Security Storage", "chb.mods.mffs.common.TileEntitySecStorage", "chb.mods.mffs.client.GuiSecStorage", "chb.mods.mffs.common.ContainerSecStorage",ModularForceFieldSystem.MFFSSecurtyStorage,0, "IIIIcIIII");
  
	String parttyp;
	String inCodeName;
	String displayName;
	Class<?extends TileEntity> clazz;
	Class<?extends GuiContainer> Gui;
	Class<? extends Container> Container;
	Block block;
	String recipe;
	int baseTex;
	
	
	private MFFSMaschines(String typ,String nm, String dispNm, String cls, String gui,
			String container, Block block ,int baseTex,String recipe) {

		
		this.parttyp = typ;
		this.inCodeName=nm;
		this.displayName = dispNm;
		try{this.clazz =  (Class<? extends TileEntity>) Class.forName(cls);}catch(ClassNotFoundException ex){this.clazz = null;}
		try{this.Gui =  (Class<?extends GuiContainer>) Class.forName(gui);}catch(ClassNotFoundException ex){this.Gui = null;}
		try{this.Container =  (Class<?extends Container>) Class.forName(container);}catch(ClassNotFoundException ex){this.Container = null;}
		this.recipe=recipe;
		this.block = block;
		this.baseTex = baseTex;
	}
	
	public static MFFSMaschines fromTE(TileEntity tem){
		for (MFFSMaschines mach : MFFSMaschines.values()) {
			if (mach.clazz.isInstance(tem))
			{	
				return mach;
			}
			
		}
	
		return null;
	}
	
	
	public static void initialize(){
		
		for (MFFSMaschines mach : MFFSMaschines.values()){
			
			GameRegistry.registerBlock(mach.block);
	        GameRegistry.registerTileEntity(mach.clazz, mach.inCodeName);
	        
	        if(mach.parttyp.equalsIgnoreCase("maschine"))
	        {
	        generateRecipesFor(mach);
			LanguageRegistry.instance().addNameForObject(mach.block, "en_US","MFFS "+ mach.displayName);
			ExplosionWhitelist.addWhitelistedBlock(mach.block);
	        }

		}
	}
	
	
	
	public static void generateRecipesFor(MFFSMaschines mach){
		String[] recipeSplit = new String[]{mach.recipe.substring(0, 3), mach.recipe.substring(3, 6), mach.recipe.substring(6, 9)};
		
		
		if(ModularForceFieldSystem.ic2found)
		{
		CraftingManager.getInstance().addRecipe(new ItemStack(mach.block, 1), recipeSplit,
				'I', Items.getItem("refinedIronIngot"),
				'O', Items.getItem("overclockerUpgrade"),
				'c', Items.getItem("electronicCircuit"),
				'C', Items.getItem("advancedCircuit"),
				'P', Items.getItem("carbonPlate"),
				'M', Items.getItem("advancedMachine"),
				'E', Items.getItem("extractor"),
				'w', Items.getItem("copperCableItem"),
				'W', Items.getItem("insulatedCopperCableItem"),
				'F', Items.getItem("frequencyTransmitter"),
				'A', Items.getItem("advancedAlloy"),
				'G', Items.getItem("glassFiberCableItem"),
				'J', Items.getItem("lvTransformer"),
				'K', Items.getItem("mvTransformer"),
				'L', Items.getItem("hvTransformer"),
				
				'f', ModularForceFieldSystem.MFFSitemForcePowerCrystal,
				'm', ModularForceFieldSystem.MFFSitemFocusmatix,
				
				'p', Item.enderPearl	
		);
		}
		
		
		if(ModularForceFieldSystem.uefound)
		{
		CraftingManager.getInstance().addRecipe(new ItemStack(mach.block, 1), recipeSplit,
				
				'f', ModularForceFieldSystem.MFFSitemForcePowerCrystal,
				'm', ModularForceFieldSystem.MFFSitemFocusmatix,
				'p', Item.enderPearl,	
				'I', BasicComponents.itemSteelIngot,
				'c', new ItemStack(BasicComponents.itemCircuit, 1, 0),
				'C', new ItemStack(BasicComponents.itemCircuit, 1, 1),
				'E', BasicComponents.itemMotor,
				'P', BasicComponents.itemBronzePlate,
				'A', BasicComponents.itemSteelPlate,
			    'w', new ItemStack(BasicComponents.blockCopperWire, 1, 0),
				'W', new ItemStack(BasicComponents.blockCopperWire, 1, 0),
				'G', new ItemStack(BasicComponents.blockCopperWire, 1, 0),
				'J', BasicComponents.batteryBox,
				'K', BasicComponents.batteryBox,
				'L', BasicComponents.batteryBox,
				'M', new ItemStack(BasicComponents.itemCircuit, 1, 2),
				'O', new ItemStack(BasicComponents.itemCircuit, 1, 2),
	            'F', BasicComponents.itemTinPlate
		);
		}
		
		
		
		
		
		

	}
	

	
}