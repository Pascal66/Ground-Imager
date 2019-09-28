package com.test.tango;

import android.os.Parcel;
import android.os.Parcelable;

public class TangoCoordinateFramePair implements Parcelable {
	public int baseFrame;
	public int targetFrame;
	public static final Creator<TangoCoordinateFramePair> CREATOR = new Creator<TangoCoordinateFramePair>() {
		public TangoCoordinateFramePair createFromParcel(Parcel in) {
			return new TangoCoordinateFramePair(in);
		}

		public TangoCoordinateFramePair[] newArray(int size) {
			return new TangoCoordinateFramePair[size];
		}
	};

	public TangoCoordinateFramePair() {
		this.baseFrame = 0;
		this.targetFrame = 0;
	}

	public TangoCoordinateFramePair(int base, int target) {
		this.baseFrame = 0;
		this.targetFrame = 0;
		this.baseFrame = base;
		this.targetFrame = target;
	}

	private TangoCoordinateFramePair(Parcel in) {
		this.baseFrame = 0;
		this.targetFrame = 0;
		this.readFromParcel(in);
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		this.baseFrame = in.readInt();
		this.targetFrame = in.readInt();
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.baseFrame);
		dest.writeInt(this.targetFrame);
	}
}