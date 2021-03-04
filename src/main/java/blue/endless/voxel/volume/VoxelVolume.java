package blue.endless.voxel.volume;

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface VoxelVolume<V> {
	
	long getXSize();
	long getYSize();
	long getZSize();
	
	/**
	 * Gets the value at the specified location.
	 * @param x the x coordinate of the value
	 * @param y the y coordinate of the value
	 * @param z the z coordinate of the value
	 * @return the value if present, or null if there is no value present at this location
	 * @throws ArrayIndexOutOfBoundsException if the coordinates are outside the bounds of this Volume
	 */
	@Nullable V get(long x, long y, long z) throws ArrayIndexOutOfBoundsException;
	
	/**
	 * Sets the value at the specified location, returning the value that was already present if there is one, otherwise null.
	 * @param x the x coordinate of the value
	 * @param y the y coordinate of the value
	 * @param z the z coordinate of the value
	 * @param value the value this cell should be set to. If value is null, the cell will be cleared.
	 * @return the previous value held in this cell if any, otherwise null
	 * @throws ArrayIndexOutOfBoundsException if the coordinates are outside the bounds of this Volume
	 */
	@Nullable V set(long x, long y, long z, @Nullable V value) throws ArrayIndexOutOfBoundsException;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param xsize
	 * @param ysize
	 * @param zsize
	 * @return
	 */
	/*
	 * TODO: I'm trying to decide whether to let the implementation or the caller decide what VoxelVolume to [re]use.
	 * In the latter case I could say "null to get an unspecified impl back".
	 * Basically there are a lot of decisions to make and I'm not sure which route I'm going to take.
	 */
	@Nonnull VoxelVolume<V> copyOut(long x, long y, long z, long xsize, long ysize, long zsize, @Nullable VoxelVolume<V> destination);
	
	/**
	 * Same as {@link #copyOut(long, long, long, long, long, long, VoxelVolume)} but remaps the voxels as they're copied. destination MUST NOT be null in this version.
	 */
	<U> @Nonnull VoxelVolume<U> copyOut(long x, long y, long z, long xsize, long ysize, long zsize, @Nonnull VoxelVolume<U> destination, @Nonnull Function<V, U> remapper);
}
