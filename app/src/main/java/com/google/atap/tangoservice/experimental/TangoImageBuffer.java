package com.google.atap.tangoservice.experimental;

import java.nio.ByteBuffer;

public class TangoImageBuffer {
	public static final int RGBA_8888 = 1;
	public static final int RGB_888 = 3;
	public static final int YV12 = 842094169;
	public static final int YCRCB_420_SP = 17;
	public int width;
	public int height;
	public int stride;
	public double timestamp;
	public long frameNumber;
	public int format;
	public ByteBuffer data;

	public TangoImageBuffer() {
	}

	public TangoImageBuffer(int width, int height, int stride, long frameNumber, double timestamp, int format, ByteBuffer data) {
		this.width = width;
		this.height = height;
		this.stride = stride;
		this.frameNumber = frameNumber;
		this.timestamp = timestamp;
		this.format = format;
		this.data = data;
	}
}
