package com.google.atap.tangoservice;

import android.os.Parcel;
import android.os.Parcelable;

public class TangoEvent implements Parcelable {
	public static final int EVENT_UNKNOWN = 0;
	public static final int EVENT_GENERAL = 1;
	public static final int EVENT_FISHEYE_CAMERA = 2;
	public static final int EVENT_COLOR_CAMERA = 3;
	public static final int EVENT_IMU = 4;
	public static final int EVENT_FEATURE_TRACKING = 5;
	public static final int EVENT_AREA_LEARNING = 6;
	public static final int EVENT_CLOUD_ADF = 7;
	public static final String KEY_AREA_DESCRIPTION_SAVE_PROGRESS = "AreaDescriptionSaveProgress";
	public static final String KEY_SERVICE_EXCEPTION = "TangoServiceException";
	public static final String VALUE_SERVICE_FAULT = "Service faulted will restart.";
	public static final String DESCRIPTION_FISHEYE_OVER_EXPOSED = "FisheyeOverExposed";
	public static final String DESCRIPTION_FISHEYE_UNDER_EXPOSED = "FisheyeUnderExposed";
	public static final String DESCRIPTION_COLOR_OVER_EXPOSED = "ColorOverExposed";
	public static final String DESCRIPTION_COLOR_UNDER_EXPOSED = "ColorUnderExposed";
	public static final String DESCRIPTION_TOO_FEW_FEATURES_TRACKED = "TooFewFeaturesTracked";
	public double timestamp;
	public int eventType;
	public String eventKey;
	public String eventValue;
	public static final Creator<TangoEvent> CREATOR = new Creator<TangoEvent>() {
		public TangoEvent createFromParcel(Parcel in) {
			return new TangoEvent(in);
		}

		public TangoEvent[] newArray(int size) {
			return new TangoEvent[size];
		}
	};

	public TangoEvent() {
	}

	private TangoEvent(Parcel in) {
		this.readFromParcel(in);
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		this.timestamp = in.readDouble();
		this.eventType = in.readInt();
		int status = in.readInt();
		if (status != 0) {
			this.eventKey = in.readString();
			this.eventValue = in.readString();
		}

	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.timestamp);
		dest.writeInt(this.eventType);
		dest.writeInt(1);
		dest.writeString(this.eventKey);
		dest.writeString(this.eventValue);
	}
}
