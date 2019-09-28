package com.test.tango;

import com.test.atap.tangoservice.TangoPointCloudData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TangoPointCloudManager {
	private static final int SIZE_OF_FLOAT = 4;
	private static final String TAG = TangoPointCloudManager.class.getSimpleName();
	private com.test.atap.tangoservice.TangoPointCloudData mBackPointCloud = new com.test.atap.tangoservice.TangoPointCloudData();
	private com.test.atap.tangoservice.TangoPointCloudData mFrontPointCloud = new com.test.atap.tangoservice.TangoPointCloudData();
	private com.test.atap.tangoservice.TangoPointCloudData mSharePointCloud = new com.test.atap.tangoservice.TangoPointCloudData();

	public TangoPointCloudManager() {
	}

	public com.test.atap.tangoservice.TangoPointCloudData getLatestPointCloud() {
		if (this.mFrontPointCloud.points == null && this.mSharePointCloud.points == null) {
			return null;
		} else {
			synchronized(this) {
				if (this.mSharePointCloud.timestamp > this.mFrontPointCloud.timestamp) {
					com.test.atap.tangoservice.TangoPointCloudData tempPointCloud = this.mFrontPointCloud;
					this.mFrontPointCloud = this.mSharePointCloud;
					this.mSharePointCloud = tempPointCloud;
				}
			}

			return this.mFrontPointCloud;
		}
	}

	public void updatePointCloud(com.test.atap.tangoservice.TangoPointCloudData pointCloud) throws TangoInvalidException {
		if (pointCloud == null || Double.isNaN(pointCloud.timestamp) || pointCloud.timestamp < 0.0D || pointCloud.numPoints < 0 || pointCloud.numPoints > 0 && pointCloud.points == null) {
			throw new TangoInvalidException();
		} else {
			if (pointCloud.numPoints > 0) {
				if (this.mBackPointCloud.points != null && this.mBackPointCloud.points.capacity() >= pointCloud.numPoints * 4) {
					this.mBackPointCloud.points.rewind();
				} else {
					this.mBackPointCloud.points = ByteBuffer.allocateDirect(pointCloud.numPoints * 4 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
				}

				this.mBackPointCloud.numPoints = pointCloud.numPoints;
				this.mBackPointCloud.timestamp = pointCloud.timestamp;
				pointCloud.points.rewind();
				this.mBackPointCloud.points.put(pointCloud.points);
				this.mBackPointCloud.points.rewind();
				pointCloud.points.rewind();
				synchronized(this) {
					TangoPointCloudData tempPointCloud = this.mSharePointCloud;
					this.mSharePointCloud = this.mBackPointCloud;
					this.mBackPointCloud = tempPointCloud;
				}
			}

		}
	}
}
