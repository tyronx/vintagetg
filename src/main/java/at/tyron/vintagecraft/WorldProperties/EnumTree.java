package at.tyron.vintagecraft.WorldProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import at.tyron.vintagecraft.VCraftWorld;
import at.tyron.vintagecraft.WorldGen.DynTreeGen;

public enum EnumTree {
	// EnumTree(weight, mintemp, maxtemp, minrain, maxrain, minfertility, maxfertility, minheight, maxheight, minforest, maxforest)
	BIRCH					(100, 3, 12, 95, 220, 10, 100, 0f, 0.8f, 0, 255, Blocks.log.getStateFromMeta(BlockPlanks.EnumType.BIRCH.getMetadata()), Blocks.leaves.getStateFromMeta(4 + BlockPlanks.EnumType.BIRCH.getMetadata())),		
	OAK						(95, 2, 22, 95,  170,  90,  255, 0f, 0.9f, 0, 150, Blocks.log.getStateFromMeta(BlockPlanks.EnumType.OAK.getMetadata()), Blocks.leaves.getStateFromMeta(4 + BlockPlanks.EnumType.OAK.getMetadata())),
	SPRUCE					(100, -12,  15,  70,  255,  60,  255, 0f, 1f, 0, 255, Blocks.log.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.leaves.getStateFromMeta(4 + BlockPlanks.EnumType.SPRUCE.getMetadata())),
	ACACIA					(100, 23, 30, 100, 180,  50, 200, 0f, 0.8f, 0, 255, Blocks.log2.getStateFromMeta(BlockPlanks.EnumType.ACACIA.getMetadata()-4), Blocks.leaves2.getStateFromMeta(4 + BlockPlanks.EnumType.ACACIA.getMetadata()-4)),
	DARKOAK					(100, 10, 20, 185, 255,  50, 200, 0f, 0.8f, 0, 255, Blocks.log2.getStateFromMeta(BlockPlanks.EnumType.DARK_OAK.getMetadata()-4), Blocks.leaves2.getStateFromMeta(4 + BlockPlanks.EnumType.DARK_OAK.getMetadata()-4)),
	JUNGLE			 		(100, 24, 30, 185, 255, 160, 255,  0, 0.7f, 100, 255, Blocks.log.getStateFromMeta(BlockPlanks.EnumType.JUNGLE.getMetadata()), Blocks.leaves.getStateFromMeta(4 + BlockPlanks.EnumType.JUNGLE.getMetadata())),
	;

	
	public IBlockState log;
	public IBlockState leaves;
	
	int weight;
	int mintemp;
	int maxtemp ;
	int minrain;
	int maxrain;
	int minfertility;
	int maxfertility;
	int minforest;
	int maxforest;
	
	// Height of 0f == sealevel
	// Height of 1f == 255
	float minheight;
	float maxheight;
	
	// Generated from above values
	public int miny;
	public int maxy;
	
	public DynTreeGen defaultGenerator;
	public DynTreeGen poorGenerator;			// Generates a poorly grown tree due to lack of light, rain, fertility, etc.
	public DynTreeGen lushGenerator;			// Generates a lushous tree that has enough water, nutrients and no competition for light 
	
	public void setGenerators(DynTreeGen defaultgen, DynTreeGen poor, DynTreeGen lush) {
		this.defaultGenerator = defaultgen;
		this.poorGenerator = poor;
		this.lushGenerator = lush;
		
	}
	
	public DynTreeGen getGenerator(int rain, int temp, int fertility, int lightcompetition) {
		return defaultGenerator;
	}
	
	EnumTree(int weight, int mintemp, int maxtemp, int minrain, int maxrain, int minfertility, int maxfertility, float minheight, float maxheight, int minforest, int maxforest, IBlockState log, IBlockState branches) {
		this.log = log;
		this.leaves = branches;
		this.weight = weight;
		this.mintemp = mintemp;
		this.maxtemp = maxtemp;
		this.minrain = minrain;
		this.maxrain = maxrain;
		this.minforest = minforest;
		this.maxforest = maxforest;
		this.minfertility = minfertility;
		this.maxfertility = maxfertility;
		this.maxheight = maxheight;
		this.minheight = minheight;
		
		this.miny = (int)(minheight * (255 - VCraftWorld.seaLevel) + VCraftWorld.seaLevel);
		this.maxy = (int)(maxheight * (255 - VCraftWorld.seaLevel) + VCraftWorld.seaLevel);
	}
	
	
	
	
	public static DynTreeGen getRandomTreeGenForClimate(int rainfall, int temperature, int forest, int fertility, int steepness, int y, Random random) {
		EnumTree tree = getRandomTreeForClimate(rainfall, temperature, forest, y, random);
		if (tree == null) return null;
		
		float rainspan = tree.maxrain - tree.minrain;
		float tempspan = tree.maxtemp - tree.mintemp;
		
		float dist = (rainfall - tree.minrain) / rainspan + (temperature - tree.mintemp) / tempspan;    // 0 = at minimum, 2 == at maximum    => 1 means perfect conditions
		
		if ((dist < 0.55 || fertility < 50 || steepness > 5) && tree.poorGenerator != null) return tree.poorGenerator;
		if (dist > 0.9 && dist < 1.4 && fertility > 100 && tree.lushGenerator != null) return tree.lushGenerator;
		return tree.defaultGenerator;
	}
	
	
	public static EnumTree getRandomTreeForClimate(int rainfall, int temperature, int forest, int y, Random random) {
		//ArrayList<Integer> distances = new ArrayList<Integer>();
		HashMap<EnumTree, Integer> distances = new HashMap<EnumTree, Integer>();
		
		
		for (EnumTree tree : EnumTree.values()) {
			if (tree.weight == 0) continue;
			
			/*System.out.println(flora.name() + "   " +
					rainfall + " > " + flora.minrain  
					+ " || " + rainfall + " < " + flora.maxrain 
					+ " || "+ temperature + " > " + flora.mintemp   
					+ " || "+ temperature + " < " + flora.maxtemp 
					+ " || "+ forest + " > " + flora.minforest  
					+ " || "+ forest + " < " + flora.maxforest);
			*/
			if (
				rainfall < tree.minrain 
				|| rainfall > tree.maxrain 
				|| temperature < tree.mintemp 
				|| temperature > tree.maxtemp 
				|| forest < tree.minforest 
				|| forest > tree.maxforest
				|| y > tree.maxy  
			) continue;
			
		
			
			
			int distance = (
				Math.abs(rainfall - (tree.maxrain + tree.minrain)/2) / 10
				+ Math.abs(temperature - (tree.maxtemp + tree.mintemp)/2) * 2
				+ Math.abs(forest - (tree.maxforest + tree.minforest)/2) / 10
			) * (101 - tree.weight);
			
			//distance /= 10;
			distance = Math.max(2, distance);
			
			distances.put(tree, distance);
			
			
			//System.out.println(tree + " " + distance);
		}
		
		Set<EnumTree> keys = distances.keySet();
		List<EnumTree> keyList = new ArrayList(keys);
		Collections.shuffle(keyList);
		
		for (EnumTree tree : keyList) {
			if (random.nextInt(distances.get(tree)) == 0) return tree;
		}
		
		return null;
		
	}
	
}
