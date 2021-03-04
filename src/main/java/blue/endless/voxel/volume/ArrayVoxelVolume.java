package blue.endless.voxel.volume;

import java.util.function.Function;

import javax.annotation.Nonnull;

public class ArrayVoxelVolume<V> implements VoxelVolume<V> {
	private V[] data;
	private int xsize;
	private int ysize;
	private int zsize;
	
	@SuppressWarnings("unchecked")
	public ArrayVoxelVolume(int xsize, int ysize, int zsize) {
		data = (V[]) new Object[xsize*ysize*zsize]; //If we took a Class<?> arg we could do better but that's extra work for the caller.
		this.xsize = xsize;
		this.ysize = ysize;
		this.zsize = zsize;
	}
	
	@Override
	public long getXSize() {
		return xsize;
	}

	@Override
	public long getYSize() {
		return ysize;
	}

	@Override
	public long getZSize() {
		return zsize;
	}
	
	private int getIndex(long x, long y, long z) throws ArrayIndexOutOfBoundsException {
		//This test also covers if x/y/z are outside the integer range
		if (x<0 || y<0 || z<0 || x>=xsize || y>=ysize || z>=zsize) throw new ArrayIndexOutOfBoundsException();
		return ((int)y) + ((int)x)*ysize + ((int)z)*xsize*ysize;
	}
	
	@Override
	public V get(long x, long y, long z) throws ArrayIndexOutOfBoundsException {
		if (x<0 || y<0 || z<0 || x>=xsize || y>=ysize || z>=zsize) throw new ArrayIndexOutOfBoundsException();
		return data[ getIndex(x,y,z) ] ;
	}

	@Override
	public V set(long x, long y, long z, V voxel) throws ArrayIndexOutOfBoundsException {
		if (x<0 || y<0 || z<0 || x>=xsize || y>=ysize || z>=zsize) throw new ArrayIndexOutOfBoundsException();
		int index = getIndex(x,y,z);
		V result = data[index];
		data[index] = voxel;
		return result;
	}
	
	public VoxelVolume<V> copyOut(long x, long y, long z, long xsize, long ysize, long zsize, VoxelVolume<V> destination) {
		return new ArrayVoxelVolume<V>((int)xsize, (int)ysize, (int)zsize); //TODO: Implement
	}

	@Override
	public <U> @Nonnull VoxelVolume<U> copyOut(long x, long y, long z, long xsize, long ysize, long zsize, VoxelVolume<U> destination, Function<V, U> remapper) {
		// TODO: Implement
		return destination;
	}

	
}
