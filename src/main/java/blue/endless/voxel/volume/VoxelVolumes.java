package blue.endless.voxel.volume;

public final class VoxelVolumes {
	private VoxelVolumes() {}
	
	/**
	 * Returns true if an ArrayVoxelVolume can be used to represent the specified dimensions
	 */
	public static boolean isArrayCompatible(long xsize, long ysize, long zsize) {
		//Each dimension individually, *and their product*, must fall under the integer / array size limit
		return
			xsize<=Integer.MAX_VALUE &&
			ysize<=Integer.MAX_VALUE &&
			zsize<=Integer.MAX_VALUE &&
			(xsize*ysize*zsize)<=Integer.MAX_VALUE;
	}
	
	/**
	 * Creates the best VoxelVolume it can to represent the specified dimensions. Throws if no built-in implementation
	 * can possibly contain data of that size.
	 */
	public static <V> VoxelVolume<V> createCompatibleVolume(long xsize, long ysize, long zsize) {
		if (!isArrayCompatible(xsize,ysize,zsize)) throw new UnsupportedOperationException(); //TODO: Offer a more durable impl in this case, even if it's slower
		return new ArrayVoxelVolume<V>((int) xsize, (int) ysize, (int) zsize);
	}
}
