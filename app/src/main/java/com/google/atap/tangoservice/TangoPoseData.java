package com.google.atap.tangoservice;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Locale;

public class TangoPoseData implements Parcelable {
	public static final int INDEX_TRANSLATION_X = 0;
	public static final int INDEX_TRANSLATION_Y = 1;
	public static final int INDEX_TRANSLATION_Z = 2;
	public static final int INDEX_ROTATION_X = 0;
	public static final int INDEX_ROTATION_Y = 1;
	public static final int INDEX_ROTATION_Z = 2;
	public static final int INDEX_ROTATION_W = 3;
	public static final int COORDINATE_FRAME_GLOBAL_WGS84 = 0;
	public static final int COORDINATE_FRAME_AREA_DESCRIPTION = 1;
	public static final int COORDINATE_FRAME_START_OF_SERVICE = 2;
	public static final int COORDINATE_FRAME_PREVIOUS_DEVICE_POSE = 3;
	public static final int COORDINATE_FRAME_DEVICE = 4;
	public static final int COORDINATE_FRAME_IMU = 5;
	public static final int COORDINATE_FRAME_DISPLAY = 6;
	public static final int COORDINATE_FRAME_CAMERA_COLOR = 7;
	public static final int COORDINATE_FRAME_CAMERA_DEPTH = 8;
	public static final int COORDINATE_FRAME_CAMERA_FISHEYE = 9;
	public static final int COORDINATE_FRAME_UUID = 10;
	public static final String[] FRAME_NAMES = new String[]{"GLOBAL_WGS84", "AREA_DESCRIPTION", "START_OF_SERVICE", "PREVIOUS_DEVICE_POSE", "DEVICE", "IMU", "DISPLAY", "CAMERA_COLOR", "CAMERA_DEPTH", "CAMERA_FISHEYE", "UUID"};
	public static final int POSE_INITIALIZING = 0;
	public static final int POSE_VALID = 1;
	public static final int POSE_INVALID = 2;
	public static final int POSE_UNKNOWN = 3;
	public static final String[] STATUS_NAMES = new String[]{"INITIALIZING", "VALID", "INVALID", "UNKNOWN"};
	public double timestamp;
	public double[] rotation;
	public double[] translation;
	public int statusCode;
	public int baseFrame;
	public int targetFrame;
	public int confidence;
	public float accuracy;
	public static final Creator<TangoPoseData> CREATOR = new Creator<TangoPoseData>() {
		public TangoPoseData createFromParcel(Parcel in) {
			return new TangoPoseData(in);
		}

		public TangoPoseData[] newArray(int size) {
			return new TangoPoseData[size];
		}
	};

	public TangoPoseData() {
		this.timestamp = 0.0D;
		this.rotation = new double[]{0.0D, 0.0D, 0.0D, 1.0D};
		this.translation = new double[]{0.0D, 0.0D, 0.0D};
		this.statusCode = 0;
		this.baseFrame = 0;
		this.targetFrame = 0;
		this.confidence = 0;
		this.accuracy = 0.0F;
	}

	private TangoPoseData(Parcel in) {
		this.timestamp = 0.0D;
		this.rotation = new double[]{0.0D, 0.0D, 0.0D, 1.0D};
		this.translation = new double[]{0.0D, 0.0D, 0.0D};
		this.statusCode = 0;
		this.baseFrame = 0;
		this.targetFrame = 0;
		this.confidence = 0;
		this.accuracy = 0.0F;
		this.readFromParcel(in);
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		this.timestamp = in.readDouble();
		in.readDoubleArray(this.rotation);
		in.readDoubleArray(this.translation);
		this.statusCode = in.readInt();
		this.baseFrame = in.readInt();
		this.targetFrame = in.readInt();
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.timestamp);
		dest.writeDoubleArray(this.rotation);
		dest.writeDoubleArray(this.translation);
		dest.writeInt(this.statusCode);
		dest.writeInt(this.baseFrame);
		dest.writeInt(this.targetFrame);
	}

	public float[] getRotationAsFloats() {
		float[] out = new float[4];

		for(int i = 0; i < 4; ++i) {
			out[i] = (float)this.rotation[i];
		}

		return out;
	}

	public float[] getTranslationAsFloats() {
		float[] out = new float[3];

		for(int i = 0; i < 3; ++i) {
			out[i] = (float)this.translation[i];
		}

		return out;
	}

	@NonNull
	public String toString() {
		String infoString = String.format(Locale.US, "TangoPoseData: status: %d (%s), time: %f, base: %d (%s), target: %d (%s) ", this.statusCode, STATUS_NAMES[this.statusCode], this.timestamp, this.baseFrame, FRAME_NAMES[this.baseFrame], this.targetFrame, FRAME_NAMES[this.targetFrame]);
		String poseString = String.format(Locale.US, "p: [%.3f, %.3f, %.3f], q: [%.4f, %.4f, %.4f, %.4f]\n", this.translation[0], this.translation[1], this.translation[2], this.rotation[0], this.rotation[1], this.rotation[2], this.rotation[3]);
		return infoString + poseString;
	}
}