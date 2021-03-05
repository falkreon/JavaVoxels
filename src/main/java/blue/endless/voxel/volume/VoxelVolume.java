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
	 * Grabs some subset of the voxels in this volume and copies it into a destination volume. If coordinates or sizes are out of bounds,
	 * voxels will not be altered in the destination volume for those locations which are out of bounds in the source. Voxels from
	 * x,y,z to x+xsize-1,y+ysize-1,z+zsize-1 in this Volume will be placed from 0,0,0 to xsize-1,ysize-1,zsize-1 in the destination volume.
	 * 
	 * @param x the first x coordinate to be copied from this Volume
	 * @param y the first y coordinate to be copied from this Volume
	 * @param z the first z coordinate to be copied from this Volume
	 * @param xsize the width in the x dimension to be copied
	 * @param ysize the width in the y dimension to be copied
	 * @param zsize the width in the z dimension to be copied
	 * @param destination the Volume to copy voxels into. If null, or inappropriately sized, an appropriate volume will be created with an unspecified implementation.
	 * @return the destination argument if the copy into it was successful, otherwise a new volume containing the copy
	 */
	@Nonnull VoxelVolume<V> copyOut(long x, long y, long z, long xsize, long ysize, long zsize, @Nullable VoxelVolume<V> destination);
	
	/**
	 * Same as {@link #copyOut(long, long, long, long, long, long, VoxelVolume)} but remaps the voxels as they're copied.
	 */
	<U> @Nonnull VoxelVolume<U> copyOut(long x, long y, long z, long xsize, long ysize, long zsize, @Nonnull VoxelVolume<U> destination, @Nonnull Function<V, U> remapper);
}
