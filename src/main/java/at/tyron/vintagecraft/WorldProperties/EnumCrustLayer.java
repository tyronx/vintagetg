package at.tyron.vintagecraft.WorldProperties;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public enum EnumCrustLayer {
	/* Top Soil Layer */
	L1_TOPSOIL			(3, 40, -15, 99, 230, EnumCrustType.TOPSOIL),
	L1_SAND				(2, 20,  10, 99, 40, 180, EnumCrustType.SAND),
	L1_GRAVEL			(3,  5,  -8, 99, 60, 200, EnumCrustType.GRAVEL),
	L1_SNOW				(99,  0, -99,-16, 255, EnumCrustType.SNOW),
	
	/* Sub Soil Layer */
	L2_SUBSOIL			(1, 60, -15, 99, 170, EnumCrustType.SUBOIL),
	L2_SAND				(1, 20,  13, 99, 40, 170, EnumCrustType.SAND),
	L2_ICE				(99,  0, -99,-20, 170, EnumCrustType.ICE),
	
	L3_SUBSOIL			(1, 60, -10, 99, 170, 255, 0.5f, EnumCrustType.SUBOIL),
	
	
	/* Regolith Layer */
	L3_REGOLITH			(1, 60, -10, 99, 160, EnumCrustType.REGOLITH),
	L3_SAND				(2, 20,  16, 99, 40, 180, EnumCrustType.SAND),
	L3_GRAVEL			(2, 10,  -2, 99, 60, 200, EnumCrustType.GRAVEL),
	L3_PACKEDICE 		(99,  0, -99,-25, 255, EnumCrustType.PACKEDICE),
	
	
	L4_REGOLITH			(1, 60, -10, 99, 160, 255, 0.5f, EnumCrustType.REGOLITH),
	
	
	/* Below Regolith */
	L5_PACKEDICE		(99,  0, -99, -26, 255, EnumCrustType.PACKEDICE),
	L6_PACKEDICE		(99,  0, -99, -27, 255, EnumCrustType.PACKEDICE),
	L7_PACKEDICE		(99,  0, -99, -28, 255, EnumCrustType.PACKEDICE),
	
	L5_ROCK			    (999, 99,  0, -99, 99, 255, EnumCrustType.ROCK)
	
	
	;
	
	
	
	
	int maxstepness;
	int minfertility;
	int mintemperature;
	int maxtemperature;
	int maxrain;
	int maxy;
	float placementchance;
	
	EnumCrustType blocktype;
	
	private EnumCrustLayer(int maxstepness, int minfertility, int mintemperature, int maxtemperature, int maxy, EnumCrustType blocktype) {
		this (maxstepness, minfertility, mintemperature, maxtemperature, maxy, 255, 1f, blocktype);
	}

	private EnumCrustLayer(int maxstepness, int minfertility, int mintemperature, int maxtemperature, int maxrain, int maxy, EnumCrustType blocktype) {
		this (maxstepness, minfertility, mintemperature, maxtemperature, maxy, maxrain, 1f, blocktype);
	}
	
	private EnumCrustLayer(int maxstepness, int minfertility, int mintemperature, int maxtemperature, int maxy, int maxrain, float placementchance, EnumCrustType blocktype) {
		this.maxstepness = maxstepness;
		this.mintemperature = mintemperature;
		this.maxtemperature = maxtemperature;
		this.minfertility = minfertility;
		this.maxrain = maxrain;
		this.maxy = maxy;
		this.blocktype = blocktype;
		this.placementchance = placementchance;
	}
	

	
	boolean valid(int []climate, int y, Random rand) {
		
		int distfert = climate[1] - minfertility;
		int disttempmin = climate[0] - mintemperature;
		int disttempmax = maxtemperature - climate[0];
		int distrainmax = maxrain - climate[2];
		int distycoord = maxy - y;
		
		return 
			distfert >= 0 && 
			disttempmin >= 0 && 
			disttempmax >=0 && 
			distrainmax >= 0 &&
			distycoord >= 0 &&
			rand.nextFloat() <= placementchance;
		
	}

}
