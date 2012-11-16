package chb.mods.mffs.common;

import cpw.mods.fml.common.registry.LanguageRegistry;
import ic2.api.Items;
import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;



public enum ProjectorTyp {
	
	containment("<Sphere>","BBBBABBBB",ModularForceFieldSystem.MFFSProjectorTypcontainment),
	AdvCube("<Cube>","AAAACAAAA",ModularForceFieldSystem.MFFSProjectorTypAdvCube),
	cube("<Wall>","B B A B B",ModularForceFieldSystem.MFFSProjectorTypkube),
	sphere("<Deflector>"," B BAB B ",ModularForceFieldSystem.MFFSProjectorTypsphere),
	tube("<Tube>","AAA B AAA",ModularForceFieldSystem.MFFSProjectorTyptube),
	deflector("<Adv.Cube>","AAAABAAAA",ModularForceFieldSystem.MFFSProjectorTypdeflector),
	wall("<Containment>","AA AA BB ",ModularForceFieldSystem.MFFSProjectorTypwall);

	
	
	String displayName;
	String recipe;
	Item item;

private ProjectorTyp( String dispNm,String recipe,Item item) {

	
	
	
	this.displayName = dispNm;
	this.recipe=recipe;
	this.item = item;

}

public static void initialize(){
	
	for (ProjectorTyp mach : ProjectorTyp.values()){
		generateRecipesFor(mach);
		addNameForObject(mach);
	}
}

public static void addNameForObject(ProjectorTyp mach){

	LanguageRegistry.instance().addNameForObject(mach.item, "en_US"," MFFS Projector Module  "+ mach.displayName);
	
}

public static void generateRecipesFor(ProjectorTyp mach){
	String[] recipeSplit = new String[]{mach.recipe.substring(0, 3), mach.recipe.substring(3, 6), mach.recipe.substring(6, 9)};
	
	
	CraftingManager.getInstance().addRecipe(new ItemStack(mach.item, 1), recipeSplit,

			'C',ModularForceFieldSystem.MFFSProjectorTypkube,
			'B', Block.obsidian,
			'A', ModularForceFieldSystem.MFFSitemFocusmatix
				
	);
	
}

}