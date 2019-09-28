package com.test.atap.tangoservice;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Iterator;
import java.util.Set;

public class TangoAreaDescriptionMetaData implements Parcelable {
	public static final String KEY_UUID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_TRANSFORMATION = "transformation";
	public static final String KEY_DATE_MS_SINCE_EPOCH = "date_ms_since_epoch";
	private Bundle data;
	public static final Creator<TangoAreaDescriptionMetaData> CREATOR = new Creator<TangoAreaDescriptionMetaData>() {
		public TangoAreaDescriptionMetaData createFromParcel(Parcel in) {
			return new TangoAreaDescriptionMetaData(in);
		}

		public TangoAreaDescriptionMetaData[] newArray(int size) {
			return new TangoAreaDescriptionMetaData[size];
		}
	};

	public TangoAreaDescriptionMetaData() {
		this.data = new Bundle();
	}

	private TangoAreaDescriptionMetaData(Parcel in) {
		this.data = new Bundle();
		this.readFromParcel(in);
	}

	public byte[] get(String key) {
		return this.data.getByteArray(key);
	}

	public void set(String key, byte[] value) {
		this.data.putByteArray(key, value);
	}

	public Set<String> keySet() {
		return this.data.keySet();
	}

	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		int entriesCount = in.readInt();

		for(int i = 0; i < entriesCount; ++i) {
			String key = in.readString();
			byte[] value = in.createByteArray();
			if (key != null && value != null) {
				this.data.putByteArray(key, value);
			}
		}

	}

	public void writeToParcel(Parcel dest, int flags) {
		Set<String> keys = this.data.keySet();
		dest.writeInt(keys.size());
		Iterator var4 = keys.iterator();

		while(var4.hasNext()) {
			String key = (String)var4.next();
			dest.writeString(key);
			byte[] value = this.data.getByteArray(key);
			dest.writeByteArray(value);
		}

	}
}
