package com.google.atap.tangoservice;

import android.os.Parcel;
import android.os.Parcelable;

public class TangoCameraIntrinsics implements Parcelable {
	public static final int TANGO_CAMERA_COLOR = 0;
	public static final int TANGO_CAMERA_RGBIR = 1;
	public static final int TANGO_CAMERA_FISHEYE = 2;
	public static final int TANGO_CAMERA_DEPTH = 3;
	public int cameraId;
	public int height;
	public int width;
	public static final int TANGO_CALIBRATION_UNKNOWN = 0;
	public static final int TANGO_CALIBRATION_EQUIDISTANT = 1;
	public static final int TANGO_CALIBRATION_POLYNOMIAL_2_PARAMETERS = 2;
	public static final int TANGO_CALIBRATION_POLYNOMIAL_3_PARAMETERS = 3;
	public static final int TANGO_CALIBRATION_POLYNOMIAL_5_PARAMETERS = 4;
	public int calibrationType;
	public double fx;
	public double fy;
	public double cx;
	public double cy;
	public double[] distortion;
	public static final Creator<TangoCameraIntrinsics> CREATOR = new Creator<TangoCameraIntrinsics>() {
		public TangoCameraIntrinsics createFromParcel(Parcel in) {
			return new TangoCameraIntrinsics(in);
		}

		public TangoCameraIntrinsics[] newArray(int size) {
			return new TangoCameraIntrinsics[size];
		}
	};

	public TangoCameraIntrinsics() {
		this.distortion = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
	}

	private TangoCameraIntrinsics(Parcel in) {
		this.distortion = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
		this.readFromParcel(in);
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		this.cameraId = in.readInt();
		this.calibrationType = in.readInt();
		this.width = in.readInt();
		this.height = in.readInt();
		this.fx = in.readDouble();
		this.fy = in.readDouble();
		this.cx = in.readDouble();
		this.cy = in.readDouble();
		in.readDoubleArray(this.distortion);
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.cameraId);
		dest.writeInt(this.calibrationType);
		dest.writeInt(this.width);
		dest.writeInt(this.height);
		dest.writeDouble(this.fx);
		dest.writeDouble(this.fy);
		dest.writeDouble(this.cx);
		dest.writeDouble(this.cy);
		dest.writeDoubleArray(this.distortion);
	}
}
