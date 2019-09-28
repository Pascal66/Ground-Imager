package com.google.atap.tangoservice;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TangoPointCloudData implements Parcelable {
	public double timestamp;
	public int numPoints;
	public FloatBuffer points;
	/** @deprecated */
	public ParcelFileDescriptor pointCloudParcelFileDescriptor;
	/** @deprecated */
	public int pointCloudParcelFileDescriptorSize;
	/** @deprecated */
	public int pointCloudParcelFileDescriptorFlags;
	/** @deprecated */
	public int pointCloudParcelFileDescriptorOffset;
	public int pointCloudNativeFileDescriptor;
	public static final Creator<TangoPointCloudData> CREATOR = new Creator<TangoPointCloudData>() {
		public TangoPointCloudData createFromParcel(Parcel in) {
			return new TangoPointCloudData(in);
		}

		public TangoPointCloudData[] newArray(int size) {
			return new TangoPointCloudData[size];
		}
	};

	public TangoPointCloudData() {
	}

	private TangoPointCloudData(Parcel in) {
		this.readFromParcel(in);
	}

	/** @deprecated */
	public FloatBuffer getPointsBuffer() {
		return this.points;
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		this.timestamp = in.readDouble();
		this.numPoints = in.readInt();
		if (this.numPoints != 0) {
			this.pointCloudParcelFileDescriptor = in.readFileDescriptor();
			this.pointCloudParcelFileDescriptorSize = in.readInt();
			this.pointCloudParcelFileDescriptorFlags = in.readInt();
			this.pointCloudParcelFileDescriptorOffset = in.readInt();

			try {
				FileInputStream fileStream = new FileInputStream(this.pointCloudParcelFileDescriptor.getFileDescriptor());
				MappedByteBuffer mappedByteBuffer = fileStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, (long)(this.numPoints * 4 * 4));
				mappedByteBuffer.order(ByteOrder.nativeOrder());
				fileStream.close();
				this.pointCloudParcelFileDescriptor.close();
				this.points = mappedByteBuffer.asFloatBuffer();
			} catch (IOException var4) {
				var4.printStackTrace();
			}

		}
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.timestamp);
		dest.writeInt(this.numPoints);
		dest.writeFileDescriptor(this.pointCloudParcelFileDescriptor.getFileDescriptor());
		dest.writeInt(this.pointCloudParcelFileDescriptorSize);
		dest.writeInt(this.pointCloudParcelFileDescriptorFlags);
		dest.writeInt(this.pointCloudParcelFileDescriptorOffset);
	}
}

