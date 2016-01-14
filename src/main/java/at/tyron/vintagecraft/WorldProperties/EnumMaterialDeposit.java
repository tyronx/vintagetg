package at.tyron.vintagecraft.WorldProperties;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import at.tyron.vintagecraft.World.BiomeVC;
import at.tyron.vintagecraft.interfaces.IGenLayerSupplier;

public enum EnumMaterialDeposit implements IStringSerializable, IGenLayerSupplier {
	NODEPOSIT (-1,      false, EnumDepositSize.NONE, DepositOccurence.noDeposit(10000)),
	
	CLAY (0, false, EnumDepositSize.LARGE, DepositOccurence.inTopSoil(30, 2, 160)),
	
	COAL (2, true, EnumDepositSize.SMALLANDLARGE, DepositOccurence.mixedDepths(50, 1, 10, 50, 0.5f)), 
	IRON (3, true, EnumDepositSize.SMALLANDLARGE, DepositOccurence.mixedDepths(30, 1, 10, 50, 0.5f)),
	GOLD (4, true, EnumDepositSize.SMALLANDLARGE, DepositOccurence.mixedDepths(5, 1, 10, 50, 0.5f)),
	
	REDSTONE (5, true, EnumDepositSize.SMALLANDLARGE, DepositOccurence.anyBelowSealevel(50, 2, 80, 128)),
	DIAMOND (6, true, EnumDepositSize.TINY, DepositOccurence.anyBelowSealevel(6, 1, 80, 128)),
	EMERALD (7, true, EnumDepositSize.TINY, DepositOccurence.anyBelowSealevel(12, 1, 70, 110)),
	LAPIS (8, true, EnumDepositSize.SMALL, DepositOccurence.anyRelativeDepth(40, 1, 0, 20))
	
	
	;

	

	public int id;
	public boolean dropsOre;

	public EnumDepositSize size;
	public DepositOccurence occurence;
	
	public EnumMaterialDeposit[] subdeposits;

	
	private EnumMaterialDeposit(int id, boolean dropsOre, EnumDepositSize size, DepositOccurence occurence) {
		this.id = id;
		this.dropsOre = dropsOre;
		
		this.size = size;
		this.occurence = occurence;
		
		if (this.occurence.type == EnumDepositOccurenceType.INDEPOSIT) {
			int idx = 0;
			if (subdeposits == null) {
				subdeposits = new EnumMaterialDeposit[1];
			} else {
				subdeposits = (EnumMaterialDeposit[]) Arrays.copyOf(subdeposits, subdeposits.length + 1);
				idx = subdeposits.length - 1;
			}
			
			subdeposits[idx] = this;
		}
	}
	
	
	

	
	public boolean isParentMaterial(IBlockState state, BlockPos pos) {
		boolean isrock = state.getBlock().getMaterial() == Material.rock;
		
		
		switch (this) {
			
			case CLAY:
				return state.getBlock() instanceof BlockGrass || state.getBlock() instanceof BlockDirt;
				
			case LAPIS:
				return state.getBlock() == Blocks.sandstone;
				
				
			default:
				return isrock;
		}
		
		
	}

	
	public IBlockState getBlockStateForDepth(int depth, IBlockState parentmaterial) {
		IBlockState state;
		
		switch (this) {
			case CLAY: return Blocks.clay.getDefaultState();
			case COAL: return Blocks.coal_ore.getDefaultState();
			case IRON: return Blocks.iron_ore.getDefaultState(); 
			case GOLD: return Blocks.gold_ore.getDefaultState();
			case REDSTONE: return Blocks.redstone_ore.getDefaultState();
			case DIAMOND: return Blocks.diamond_ore.getDefaultState();
			case EMERALD: return Blocks.emerald_ore.getDefaultState();
			case LAPIS: return Blocks.lapis_ore.getDefaultState();
			default:
				return null;
		}
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


	public static EnumMaterialDeposit byColor(int color) {
		EnumMaterialDeposit[] deposits = values();
		
		for (int i = 0; i < deposits.length; i++) {
			if (deposits[i].getColor() == color)
				return deposits[i];
		}
		
		return null;
	}
	
	
	@Override
	public int getColor() {
		return (id + 1);
	}


	@Override
	public int getWeight() {
		return occurence.weight;
	}
	
	
	@Override
	public int getDepthMax() {
		return occurence.maxdepth;
	}
	
	@Override
	public int getDepthMin() {
		return occurence.mindepth;
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

	@Override
	public int getSize() {
		if (size == EnumDepositSize.HUGE) return 4;
		return 1;
	}	

        
	
}
