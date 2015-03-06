package at.tyron.vintagecraft.WorldProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.tyron.vintagecraft.interfaces.IGenLayerSupplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;

public enum EnumRockType implements IStringSerializable, IGenLayerSupplier {
	SANDSTONE		(0, 0, 40, "sandstone", EnumCrustLayerGroup.SEDIMENTARY, Blocks.sandstone.getDefaultState()),
	STONE			(1, 1, 5, "stone", EnumCrustLayerGroup.SEDIMENTARY, Blocks.stone.getDefaultState()),
	REDSANDSTONE	(2, 2, 20, "redsandstone", EnumCrustLayerGroup.SEDIMENTARY2, Blocks.red_sandstone.getDefaultState()),
	GRANITE			(3, 3, 80, "granite", EnumCrustLayerGroup.IGNEOUS_INTRUSIVE, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)),
	DIORITE			(4, 4, 50, "diorite", EnumCrustLayerGroup.IGNEOUS_INTRUSIVE, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE)),
	ANDESITE		(5, 5, 80, "andesite", EnumCrustLayerGroup.IGNEOUS_EXTRUSIVE, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE)),
    ;
    
	private static HashMap<EnumCrustLayer, List<EnumRockType>> CRUSTLAYER_ROCKTYPES = new HashMap<EnumCrustLayer, List<EnumRockType>>(EnumCrustLayer.values().length);
    private static EnumRockType[] META_LOOKUP = new EnumRockType[values().length+1];
    private static EnumRockType[] ID_LOOKUP = new EnumRockType[values().length+1];
    
    public IBlockState blockstate;
    public int id; // used for GenLayerRockInit
    public int meta;
    public int weight;
    public String name;
    public String unlocalizedName;
    public EnumCrustLayerGroup group;
    

    private EnumRockType(int id, int meta, int weight, String name, EnumCrustLayerGroup group, IBlockState state) {
        this(id, meta, weight, name, name, group, state);
    }

    private EnumRockType(int id, int meta, int weight, String name, String unlocalizedName, EnumCrustLayerGroup group, IBlockState state) {
    	this.id = id;
        this.meta = meta;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
        this.group = group;
        this.weight = weight;
        this.blockstate = state;
    }
    
    
    public EnumRockType randomRockTypeByLayer(EnumCrustLayerGroup layer) {
		return null;
    	
    }


    
	
    @Override
    public int getColor() {
    	return id * 15;
    }
    
    static int Color2Id(int color) {
    	return color / 15;
    }
    
    public int getMetaData(Block block) {
        return this.meta;
    }

    public String toString() {
        return this.name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getStateName() {
        return this.name;
    }
    
    public String getModelResourceName() {
    	return "rock/" + getStateName();
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }


    public static String[] getNames() {
    	String[] names = new String[values().length];
    	
    	for (int i = 0; i < values().length; i++) {
    		names[i] = values()[i].name;
    	}
    	return names;
    }

    public static EnumRockType byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }
    
    
    public static EnumRockType byId(int id) {
        if (id < 0 || id >= META_LOOKUP.length) {
            id = 0;
        }

        return ID_LOOKUP[id];
    }
    
    
    public static EnumRockType byColor(int color) {
    	return byId(Color2Id(color));
    }
    
    
    
    
    
    public static EnumRockType[] getRockTypesForCrustLayer(EnumCrustLayer layer) {
    //	System.out.println("get for " + layer);
    	return CRUSTLAYER_ROCKTYPES.get(layer).toArray(new EnumRockType[0]);
    }


    static {
    	EnumRockType[] rocktypes = values();

        for (int i = 0; i < rocktypes.length; ++i) {
        	EnumRockType rocktype = rocktypes[i];
            META_LOOKUP[rocktype.meta] = rocktype;      
            ID_LOOKUP[rocktype.id] = rocktype;
            
            for (int j = 0; j < rocktype.group.crustlayers.length; j++) {
            	List<EnumRockType> crustrocktypes = CRUSTLAYER_ROCKTYPES.get(rocktype.group.crustlayers[j]);
            	if (crustrocktypes == null) crustrocktypes = new ArrayList<EnumRockType>();
            	
            	crustrocktypes.add(rocktype);
            	
            	CRUSTLAYER_ROCKTYPES.put(rocktype.group.crustlayers[j], crustrocktypes);
            }
        }
        
        
        

        
    }


	@Override
	public int getWeight() {
		return weight;
	}
	
	@Override
	public int getDepthMin() {
		return group.minThickness;
	}
	
	@Override
	public int getDepthMax() {
		return group.maxThickness;
	}

}
