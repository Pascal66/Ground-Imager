package com.google.tango;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TangoXyzIjData implements Parcelable {
	public double timestamp;
	public int xyzCount;
	public FloatBuffer xyz;
	/** @deprecated */
	public ParcelFileDescriptor xyzParcelFileDescriptor;
	/** @deprecated */
	public int xyzParcelFileDescriptorSize;
	/** @deprecated */
	public int xyzParcelFileDescriptorFlags;
	/** @deprecated */
	public int xyzParcelFileDescriptorOffset;
	public int ijRows;
	public int ijCols;
	public ParcelFileDescriptor ijParcelFileDescriptor;
	public static final Creator<TangoXyzIjData> CREATOR = new Creator<TangoXyzIjData>() {
		public TangoXyzIjData createFromParcel(Parcel in) {
			return new TangoXyzIjData(in);
		}

		public TangoXyzIjData[] newArray(int size) {
			return new TangoXyzIjData[size];
		}
	};

	public TangoXyzIjData() {
	}

	private TangoXyzIjData(Parcel in) {
		this.readFromParcel(in);
	}

	/** @deprecated */
	public FloatBuffer getXyzBuffer() {
		return this.xyz;
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		this.timestamp = in.readDouble();
		this.xyzCount = in.readInt();
		IBinder binder = in.readStrongBinder();
		Parcel data = Parcel.obtain();
		Parcel reply = Parcel.obtain();

		try {
			data.writeInterfaceToken(binder.getInterfaceDescriptor());
			binder.transact(1, data, reply, 0);
		} catch (RemoteException var8) {
			var8.printStackTrace();
		}

		this.xyzParcelFileDescriptor = reply.readFileDescriptor();
		this.xyzParcelFileDescriptorSize = reply.readInt();
		this.xyzParcelFileDescriptorFlags = reply.readInt();
		this.xyzParcelFileDescriptorOffset = reply.readInt();

		try {
			FileInputStream fileStream = new FileInputStream(this.xyzParcelFileDescriptor.getFileDescriptor());
			MappedByteBuffer mappedByteBuffer = fileStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, (long)(this.xyzCount * 3 * 4));
			mappedByteBuffer.order(ByteOrder.nativeOrder());
			fileStream.close();
			this.xyz = mappedByteBuffer.asFloatBuffer();
		} catch (IOException var7) {
			var7.printStackTrace();
		}

		data.recycle();
		reply.recycle();
		this.ijRows = in.readInt();
		this.ijCols = in.readInt();
	}

	public void writeToParcel(Parcel dest, int flags) {
	}
}

