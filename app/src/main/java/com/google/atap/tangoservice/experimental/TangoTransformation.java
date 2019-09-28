package com.google.atap.tangoservice.experimental;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Locale;

public class TangoTransformation implements Parcelable {
	public static final int INDEX_TRANSLATION_X = 0;
	public static final int INDEX_TRANSLATION_Y = 1;
	public static final int INDEX_TRANSLATION_Z = 2;
	public static final int INDEX_ROTATION_X = 0;
	public static final int INDEX_ROTATION_Y = 1;
	public static final int INDEX_ROTATION_Z = 2;
	public static final int INDEX_ROTATION_W = 3;
	public final double[] rotation;
	public final double[] translation;
	public static final Creator<TangoTransformation> CREATOR = new Creator<TangoTransformation>() {
		public TangoTransformation createFromParcel(Parcel in) {
			return new TangoTransformation(in);
		}

		public TangoTransformation[] newArray(int size) {
			return new TangoTransformation[size];
		}
	};

	public TangoTransformation() {
		this.rotation = new double[]{0.0D, 0.0D, 0.0D, 1.0D};
		this.translation = new double[]{0.0D, 0.0D, 0.0D};
	}

	public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == TangoTransformation.class) {
			TangoTransformation other = (TangoTransformation)obj;
			return Arrays.equals(this.rotation, other.rotation) && Arrays.equals(this.translation, other.translation);
		} else {
			return false;
		}
	}

	private TangoTransformation(Parcel in) {
		this.rotation = new double[]{0.0D, 0.0D, 0.0D, 1.0D};
		this.translation = new double[]{0.0D, 0.0D, 0.0D};
		this.readFromParcel(in);
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		in.readDoubleArray(this.rotation);
		in.readDoubleArray(this.translation);
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDoubleArray(this.rotation);
		dest.writeDoubleArray(this.translation);
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
		return String.format(Locale.US, "p: [%.3f, %.3f, %.3f], q: [%.4f, %.4f, %.4f, %.4f]\n", this.translation[0], this.translation[1], this.translation[2], this.rotation[0], this.rotation[1], this.rotation[2], this.rotation[3]);
	}
}
