package com.test.atap.tangoservice;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TangoConfig implements Parcelable {
	private static final String VALUETYPE_BOOL = "bool";
	private static final String VALUETYPE_INT32 = "int32";
	private static final String VALUETYPE_INT64 = "uint64";
	private static final String VALUETYPE_DOUBLE = "double";
	private static final String VALUETYPE_STRING = "string";
	public static final int CONFIG_TYPE_DEFAULT = 0;
	public static final int CONFIG_TYPE_CURRENT = 1;
	public static final int CONFIG_TYPE_MOTION_TRACKING = 2;
	public static final int CONFIG_TYPE_AREA_DESCRIPTION = 3;
	public static final int CONFIG_TYPE_RUNTIME = 4;
	public static final String KEY_BOOLEAN_AUTORECOVERY = "config_enable_auto_recovery";
	public static final String KEY_BOOLEAN_COLORCAMERA = "config_enable_color_camera";
	public static final String KEY_BOOLEAN_DEPTH = "config_enable_depth";
	public static final String KEY_INT_DEPTH_MODE = "config_depth_mode";
	public static final int TANGO_DEPTH_MODE_XYZ_IJ = -1;
	public static final int TANGO_DEPTH_MODE_POINT_CLOUD = 0;
	public static final String KEY_BOOLEAN_LOWLATENCYIMUINTEGRATION = "config_enable_low_latency_imu_integration";
	public static final String KEY_BOOLEAN_LEARNINGMODE = "config_enable_learning_mode";
	public static final String KEY_BOOLEAN_MOTIONTRACKING = "config_enable_motion_tracking";
	public static final String KEY_BOOLEAN_DATASETRECORDING = "config_enable_dataset_recording";
	public static final String KEY_BOOLEAN_DRIFT_CORRECTION = "config_enable_drift_correction";
	public static final String KEY_INT_DATASETRECORDING_MODE = "config_dataset_recording_mode";
	public static final int TANGO_DATASETRECORDING_MODE_MOTION_TRACKING = 0;
	public static final int TANGO_DATASETRECORDING_MODE_SCENE_RECONSTRUCTION = 1;
	public static final int TANGO_DATASETRECORDING_MODE_MOTION_TRACKING_AND_FISHEYE = 2;
	public static final int TANGO_DATASETRECORDING_MODE_ALL = 3;
	public static final String KEY_STRING_DATASETS_PATH = "config_datasets_path";
	public static final String KEY_BOOLEAN_EXPERIMENTAL_LOADDATASETUUID = "config_experimental_load_dataset_UUID";
	public static final String KEY_INT_EXPERIMENTAL_RUNTIME_RECORDING_CONTROL = "config_runtime_recording_control";
	public static final int TANGO_RUNTIME_RECORDING_NO_CHANGE = 0;
	public static final int TANGO_RUNTIME_RECORDING_START = 1;
	public static final int TANGO_RUNTIME_RECORDING_STOP = 2;
	public static final String KEY_STRING_AREADESCRIPTION = "config_load_area_description_UUID";
	public static final String KEY_INT_MAXPOINTCLOUDELEMENTS = "max_point_cloud_elements";
	public static final String KEY_DOUBLE_DEPTHPERIODINSECONDS = "depth_period_in_seconds";
	public static final String KEY_STRING_SERVICEVERSION = "tango_service_library_version";
	public static final String KEY_BOOLEAN_HIGH_RATE_POSE = "config_high_rate_pose";
	public static final String KEY_BOOLEAN_SMOOTH_POSE = "config_smooth_pose";
	public static final String KEY_BOOLEAN_USE_3DOF_FALLBACK = "config_experimental_3dof_fallback";
	public static final String KEY_INT_RUNTIME_DEPTH_FRAMERATE = "config_runtime_depth_framerate";
	private Bundle data;
	private HashMap<String, String> typemap;
	public static final Creator<TangoConfig> CREATOR = new Creator<TangoConfig>() {
		public TangoConfig createFromParcel(Parcel in) {
			return new TangoConfig(in);
		}

		public TangoConfig[] newArray(int size) {
			return new TangoConfig[size];
		}
	};

	public TangoConfig() {
		this.data = new Bundle();
		this.typemap = new HashMap();
	}

	private TangoConfig(Parcel in) {
		this.data = new Bundle();
		this.typemap = new HashMap();
		this.readFromParcel(in);
	}

	public Set<String> keySet() {
		return this.typemap.keySet();
	}

	public boolean getBoolean(String key) {
		return this.data.getBoolean(key);
	}

	public int getInt(String key) {
		return this.data.getInt(key);
	}

	public long getLong(String key) {
		return this.data.getLong(key);
	}

	public double getDouble(String key) {
		return this.data.getDouble(key);
	}

	public String getString(String key) {
		return this.data.getString(key);
	}

	public void putBoolean(String key, boolean value) {
		this.typemap.put(key, "bool");
		this.data.putBoolean(key, value);
	}

	public void putInt(String key, int value) {
		this.typemap.put(key, "int32");
		this.data.putInt(key, value);
	}

	public void putLong(String key, long value) {
		this.typemap.put(key, "uint64");
		this.data.putLong(key, value);
	}

	public void putDouble(String key, double value) {
		this.typemap.put(key, "double");
		this.data.putDouble(key, value);
	}

	public void putString(String key, String value) {
		this.typemap.put(key, "string");
		this.data.putString(key, value);
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		while(in.dataAvail() > 0) {
			String key = in.readString();
			String valueType = in.readString();
			String desc = in.readString();
			String value = in.readString();
			if (valueType.equals("bool")) {
				this.putBoolean(key, value.equalsIgnoreCase("true"));
			} else if (valueType.equals("int32")) {
				this.putInt(key, Integer.parseInt(value));
			} else if (valueType.equals("uint64")) {
				this.putLong(key, Long.parseLong(value));
			} else if (valueType.equals("double")) {
				this.putDouble(key, Double.parseDouble(value));
			} else if (valueType.equals("string")) {
				this.putString(key, value);
			}
		}

	}

	public void writeToParcel(Parcel dest, int flags) {
		String valueString;
		for(Iterator keyIt = this.typemap.keySet().iterator(); keyIt.hasNext(); dest.writeString(valueString)) {
			String key = (String)keyIt.next();
			String valueType = (String)this.typemap.get(key);
			dest.writeString(key);
			dest.writeString(valueType);
			dest.writeString("desc");
			valueString = "";
			if (valueType.equals("bool")) {
				valueString = valueString + this.getBoolean(key);
			} else if (valueType.equals("int32")) {
				valueString = valueString + this.getInt(key);
			} else if (valueType.equals("uint64")) {
				valueString = valueString + this.getLong(key);
			} else if (valueType.equals("double")) {
				valueString = valueString + this.getDouble(key);
			} else if (valueType.equals("string")) {
				valueString = valueString + this.getString(key);
			}
		}

	}
}
