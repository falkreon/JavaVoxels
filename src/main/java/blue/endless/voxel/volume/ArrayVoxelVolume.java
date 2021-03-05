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
	
	private boolean isInBounds(long x, long y, long z) {
		return
			x >= 0 &&
			y >= 0 &&
			z >= 0 &&
			x < xsize &&
			y < ysize &&
			z < zsize;
	}
	
	private int getIndex(long x, long y, long z) throws ArrayIndexOutOfBoundsException {
		//This test also covers if x/y/z are outside the integer range
		if (!isInBounds(x,y,z)) throw new ArrayIndexOutOfBoundsException();
		//if (x<0 || y<0 || z<0 || x>=xsize || y>=ysize || z>=zsize) throw new ArrayIndexOutOfBoundsException();
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
		return copyOut(x,y,z,xsize,ysize,zsize, destination, Function.identity());
	}

	@Override
	public <U> @Nonnull VoxelVolume<U> copyOut(long x, long y, long z, long xsize, long ysize, long zsize, VoxelVolume<U> destination, Function<V, U> remapper) {
		/* TODO: The following introduces an edge case bug where the destination is null or too small, but xsize, ysize, and/or zsize is also greater than Integer.MAX_VALUE, so an appropriate volume cannot be created.
		 * This case is extremely unlikely in early testing, but will definitely need to get cleaned up later.
		 * 
		 * In fact, this whole thing breaks pretty hard if sizes are too big.
		 */
		if (destination==null || destination.getXSize()<xsize || destination.getYSize()<ysize || destination.getZSize()<zsize) {
			destination = new ArrayVoxelVolume<U>((int)xsize,(int)ysize,(int)zsize);
		}
		
		for(long zi = 0; zi<zsize; zi++) {
			for(long xi = 0; xi<xsize; xi++) {
				//TODO: This innermost loop could be reduced to arraycopy calls if the destination is backed by an array
				for(long yi = 0; yi<ysize; yi++) {
					long cx = x+xi;
					long cy = y+yi;
					long cz = z+zi;
					if (isInBounds(cx,cy,cz)) {
						destination.set(xi, yi, zi, remapper.apply(get(cx,cy,cz)));
					}
				}
			}
		}
		
		return destination;
	}

	
}
