package at.tyron.vintagecraft.WorldProperties;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public enum EnumCrustType {
	TOPSOIL,
	SUBOIL,
	REGOLITH,
	SNOW,
	ICE, 
	PACKEDICE,
	ROCK,
	SAND,
	GRAVEL;
	
	
	
	public IBlockState getBlock(EnumRockType rocktype, int []climate) {
		switch (this) {
			case TOPSOIL:
				return Blocks.grass.getDefaultState();
				
			case SUBOIL:
				return Blocks.dirt.getDefaultState();
				
			case REGOLITH:
				return Blocks.dirt.getDefaultState();
				
			case SNOW:
				return Blocks.snow.getDefaultState();
				
			case ICE:
				return Blocks.ice.getDefaultState();
				
			case PACKEDICE:
				return Blocks.packed_ice.getDefaultState();
						
			case SAND:
				return Blocks.sand.getDefaultState();
				
			case GRAVEL:
				return Blocks.gravel.getDefaultState();
				
			case ROCK:
				return rocktype.blockstate;
		}
		
		return null; 
	}
	
	
}
