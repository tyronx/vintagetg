package at.tyron.vintagecraft.World;

import at.tyron.vintagecraft.WorldProperties.EnumRockType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {

	
	public static void RegisterRecipes() {
		ItemStack []stones = new ItemStack[EnumRockType.values().length - 1];
		int i = 0;
		for (EnumRockType rocktype : EnumRockType.values()) {
			if (rocktype == EnumRockType.STONE) continue;
			
			stones[i] = new ItemStack(
				Item.getItemFromBlock(rocktype.blockstate.getBlock()), 
				1,
				rocktype.blockstate.getBlock().getMetaFromState(rocktype.blockstate)
			);
			
			
			i++;
		}
		
		ItemStack stick = new ItemStack(Items.stick);
		
		for (ItemStack stone : stones) {
		
			GameRegistry.addShapedRecipe(new ItemStack(Items.comparator), new Object[] { " T ", "TQT", "SSS", 'T', Item.getItemFromBlock(Blocks.redstone_torch), 'Q', Items.quartz, 'S', stone});
			GameRegistry.addShapelessRecipe(new ItemStack(Item.getItemFromBlock(Blocks.stone_button)), new Object[] { stone } );
			
			GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(Blocks.stone_pressure_plate)), new Object[] { "  ", "SS", 'S', stone } );
			GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(Blocks.stone_pressure_plate)), new Object[] { "SS", "  ", 'S', stone } );
			
			GameRegistry.addShapedRecipe(new ItemStack(Items.repeater), new Object[] { "   ", "TRT", "SSS", 'T', Item.getItemFromBlock(Blocks.redstone_torch), 'R', Items.redstone, 'S', stone});
			GameRegistry.addShapedRecipe(new ItemStack(Items.repeater), new Object[] { "TRT", "SSS", "   ", 'T', Item.getItemFromBlock(Blocks.redstone_torch), 'R', Items.redstone, 'S', stone});
			
			 
			GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(Blocks.furnace)), new Object[] { "SSS", "S S", "SSS", 'S', stone});
			
			
			
			GameRegistry.addShapedRecipe(new ItemStack(Items.stone_axe), new Object[] { "SS ", "ST ", " T ", 'S', stone, 'T', stick } );
			GameRegistry.addShapedRecipe(new ItemStack(Items.stone_hoe), new Object[] { "SS ", " T ", " T ", 'S', stone, 'T', stick } );
			GameRegistry.addShapedRecipe(new ItemStack(Items.stone_pickaxe), new Object[] { "SSS", " T ", " T ", 'S', stone, 'T', stick } );
			GameRegistry.addShapedRecipe(new ItemStack(Items.stone_shovel), new Object[] { " S ", " T ", " T ", 'S', stone, 'T', stick } );
			GameRegistry.addShapedRecipe(new ItemStack(Items.stone_sword), new Object[] { " S ", " S ", " T ", 'S', stone, 'T', stick } );
			
		}
		
	}
	
}

