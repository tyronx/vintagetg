package at.tyron.vintagecraft.WorldProperties;

import java.util.ArrayList;
import java.util.List;

import sun.security.krb5.Realm;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.*;
import net.minecraft.util.IStringSerializable;
import at.tyron.vintagecraft.World.BiomeVC;
import at.tyron.vintagecraft.interfaces.IGenLayerSupplier;

public enum EnumMaterialDeposit implements IStringSerializable, IGenLayerSupplier {
	//     int id,  hasOre, color, weight, averageHeight, minDepth, maxDepth, relativeDepth
	NODEPOSIT (null, -1,   false,   0, 2000,   0,    0, 0),
	
	CLAY (Blocks.clay,  0,         false,  30,     3,  2,   0,    1, true, 155),
	COAL (Blocks.coal_ore,  1,         true,   60,    33,  1,  10,  50, true, 255),
	IRON (Blocks.iron_ore, 2,   		 true,   90,    18,  1,   4,   40, true, 255),
	GOLD (Blocks.gold_ore, 3,         true,  120,    10,  1,  50, 103),
	REDSTONE (Blocks.redstone_ore, 4,     true,  140,    20,  2,  30, 130),
	DIAMONDS (Blocks.diamond_ore, 5,	 true,  160,     1,  1,  100,130, true, 220), 
	EMERALD (Blocks.emerald_ore, 6,	     true,  180,     3,  1,  90, 100, true, 220),
	LAPIS (Blocks.lapis_ore, 7,	     true,  200,     3,  1,  60,  80, true, 220)
	;

	

	public int id;
	//Block block;
	public boolean hasOre;
	int color;
	public int height;
	public int minDepth;
	public int maxDepth;
	public EnumMetal smelted;
	public int ore2IngotRatio;
	//public final BiomeVC[] biomes;
	public int weight;
	public boolean relativeDepth;
	public int maxheightOnRelDepth; // 0..255    only relevant on relativeDepth = true
	Block block;
	
	private EnumMaterialDeposit(Block block, int id, boolean hasOre, int color, int weight, int averageHeight, int minDepth, int maxDepth) {
		this(block, id, hasOre, color, weight, averageHeight, minDepth, maxDepth, false, 255);
	}
	
	private EnumMaterialDeposit(Block block, int id, boolean hasOre, int color, int weight, int averageHeight, int minDepth, int maxDepth, boolean relativeDepth, int maxheightOnRelDepth) {
		this.id = id;
		this.block = block;
		
		this.weight = weight;
		//this.block = block;
		this.hasOre = hasOre;
		this.height = averageHeight;
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
		//this.biomes = biomes;
		this.color = color;
		this.relativeDepth = relativeDepth;
		this.maxheightOnRelDepth = maxheightOnRelDepth;
	}
	
	
	
	public static EnumMaterialDeposit depositForColor(int color) {
		EnumMaterialDeposit[] deposits = values();
		for (int i = 0; i < deposits.length; i++) {
			if (deposits[i].color == color)
				return deposits[i];
		}
		return null;
	}
	
	
	public boolean isParentMaterial(IBlockState state) {
		if (this == CLAY) return state.getBlock() instanceof BlockDirt;
		
		return state.getBlock() instanceof BlockStone || state.getBlock().getMaterial() == Material.rock;
	}

	/*public Block getBlock() {
		return block;
	}*/
	
	public IBlockState getBlockStateForDepth(int depth, IBlockState parentmaterial) {
		return block.getDefaultState();
	}
	
	public void init(Block block) {
	//	this.block = block;
	}
	
	
	
	public static EnumMaterialDeposit byId(int id) {
		EnumMaterialDeposit[] deposits = values();
		for (int i = 0; i < deposits.length; i++) {
			if (deposits[i].id == id)
				return deposits[i];
		}
		return null;
		
	}


	
	@Override
	public int getColor() {
		return color;
	}


	@Override
	public int getWeight() {
		return weight;
	}
	
	
	@Override
	public int getDepthMax() {
		return maxDepth;
	}
	
	@Override
	public int getDepthMin() {
		return minDepth;
	}


	@Override
	public String getName() {
		return name().toLowerCase();
	}
	
	
	
    public static String[] getNames() {
    	String[] names = new String[values().length];
    	
    	for (int i = 0; i < values().length; i++) {
    		names[i] = values()[i].name().toLowerCase();
    	}
    	return names;
    }	

        
	
}
