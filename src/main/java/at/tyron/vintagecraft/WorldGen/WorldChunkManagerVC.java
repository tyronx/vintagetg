package at.tyron.vintagecraft.WorldGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import at.tyron.vintagecraft.World.BiomeVC;
import at.tyron.vintagecraft.WorldGen.GenLayers.GenLayerVC;


import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManagerVC extends WorldChunkManager {
	protected World worldObj;
	protected GenLayerVC genBiomes;
	public GenLayerVC climateGen;
	
	/** A list of biomes that the player can spawn in. */
	protected List biomesToSpawnIn;
	

	public long seed = 0;
	
	private WorldChunkManagerVC() {
		super();
		this.biomesToSpawnIn = new ArrayList();
		this.biomesToSpawnIn.add(BiomeVC.Flat);
		this.biomesToSpawnIn.add(BiomeVC.Mountains);
		this.biomesToSpawnIn.add(BiomeVC.HighHills);
	}

	
	
	public WorldChunkManagerVC(World world) {
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
		worldObj = world;
	}
	
	public WorldChunkManagerVC(long Seed, WorldType worldtype) {
		this();
		seed = Seed;

		this.genBiomes = GenLayerVC.genErosion(Seed);
		climateGen = GenLayerVC.genClimate(seed);
	}
	
	@Override
	public List getBiomesToSpawnIn() {
		return this.biomesToSpawnIn;
	}
	
	
	
	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
	public BiomeVC[] getBiomesForGeneration(BiomeGenBase[] biomebase, int x, int y, int width, int height) {
		IntCache.resetIntCache();

		BiomeVC[] biomes = (BiomeVC[]) biomebase;
		if (biomes == null || biomes.length < width * height) {
			biomes = new BiomeVC[width * height];
		}

		int[] intmap = this.genBiomes.getInts(x, y, width, height);
		
		for (int i = 0; i < width * height; ++i) {
			int index = Math.max(intmap[i], 0);
			biomes[i] = BiomeVC.biomeList[index];
		}
		
		return biomes;
	}

	

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1, int par2, int par3, int par4, int par5) {
		return this.getBiomeGenAt(par1, par2, par3, par4, par5, true);
	}


	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int x, int y, int width, int length, boolean cache) {
		IntCache.resetIntCache();
		
		int ints[] = genBiomes.getInts(x, y, width, length);
		
		biomes = new BiomeVC[width * length];
		
		for (int i = 0; i < biomes.length; i++) {
			biomes[i] = BiomeVC.biomeList[ints[i]];
		}
		
		return biomes;
	}
	
	@Override
	public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List p_76940_4_) {
        return true;
    }


	
	
	

	@Override
	public BlockPos findBiomePosition(int xCoord, int zCoord, int radius, List biomeList, Random rand) {
		IntCache.resetIntCache();
		int xr = xCoord - radius >> 2;
		int zr = zCoord - radius >> 2;
		int xr2 = xCoord + radius >> 2;
		int zr2 = zCoord + radius >> 2;
		
		int sizeX = xr2 - xr + 1;
		int sizeZ = zr2 - zr + 1;
		
		int[] aint = this.climateGen.getInts(xr, zr, sizeX, sizeZ);
		BlockPos chunkposition = null;
		int j2 = 0;
		
		
		int bestrain = 0;
		int besttemp = 0;

		for (int i = 0; i < sizeX * sizeZ; ++i) {
			int x = xr + i % sizeX << 2;
			int z = zr + i / sizeX << 2;
			
			int climate = aint[i];
			int rain = aint[i] & 0xff;
			int temp = (climate >> 8) & 0xff;
			
			
			if (Math.abs(bestrain - 150) > Math.abs(rain - 150) && Math.abs(besttemp - 150) > Math.abs(temp - 150)) {
				bestrain = rain;
				besttemp = temp;
				
				chunkposition = new BlockPos(x, 0, z);
			}
		}
		

		return chunkposition;		
	}

	
	

	
	/**
	 * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
	 */
	@Override
	public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
		Arrays.fill(listToReuse, 0f);
		return listToReuse;
	}

	
	
	



	
	@Override
	public void cleanupCache() {
		
	}
	
	
	
}
