package chb.mods.mffs.common;

import cpw.mods.fml.common.registry.LanguageRegistry;
import ic2.api.Items;
import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;



public enum ProjectorTyp {
	
	wall(1,"Wall","AA AA BB ",ModularForceFieldSystem.MFFSProjectorTypwall,true),
	deflector(2,"Deflector","AAAABAAAA",ModularForceFieldSystem.MFFSProjectorTypdeflector,true),
	tube(3,"Tube","AAA B AAA",ModularForceFieldSystem.MFFSProjectorTyptube,false),
	cube(4,"Cube","B B A B B",ModularForceFieldSystem.MFFSProjectorTypkube,false),
	sphere(5,"Sphere"," B BAB B ",ModularForceFieldSystem.MFFSProjectorTypsphere,false),
	containment(6,"Containment","BBBBABBBB",ModularForceFieldSystem.MFFSProjectorTypcontainment,false),
	AdvCube(7,"Adv.Cube","AAAACAAAA",ModularForceFieldSystem.MFFSProjectorTypAdvCube,false);
	
	public String displayName;
	String recipe;
	Item item;
	int ProTyp;
	boolean Blockdropper;


private ProjectorTyp(int ProTyp,String dispNm,String recipe,Item item,boolean Blockdropper) {

	this.displayName = dispNm;
	this.recipe=recipe;
	this.item = item;
    this.ProTyp = ProTyp;
    this.Blockdropper = Blockdropper;
}

public static ProjectorTyp TypfromItem(Item item){
	for (ProjectorTyp mach : ProjectorTyp.values()) {
		if (mach.item ==item)
		{	
			return mach;
		}
	}
	return null;
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