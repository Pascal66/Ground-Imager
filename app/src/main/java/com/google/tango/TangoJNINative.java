package com.google.tango;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;

import com.google.atap.tangoservice.experimental.TangoTransformation;
import com.google.atap.tangoservice.experimental.TangoImageBuffer;
import com.google.atap.tangoservice.TangoAreaDescriptionMetaData;
import com.google.atap.tangoservice.TangoCameraIntrinsics;
import com.google.atap.tangoservice.TangoConfig;
import com.google.atap.tangoservice.TangoEvent;
import com.google.atap.tangoservice.TangoPointCloudData;
import com.google.atap.tangoservice.TangoPoseData;

//import com.google.atap.tangoservice.TangoAreaDescriptionMetaData;
//import com.google.atap.tangoservice.experimental.TangoTransformation;
//import com.google.atap.tango.TangoClientLibLoader;

public class TangoJNINative {
	public TangoJNINative() {
	}

	public static native int Initialize(Context var0);

	public static native void GetConfig(int var0, com.google.atap.tangoservice.TangoConfig var1);

	public static native int Connect(com.google.atap.tangoservice.TangoConfig var0);

	public static native int ConnectListener(int[] var0, Tango.TangoUpdateCallback var1, com.google.atap.tangoservice.TangoPoseData var2, TangoPointCloudData var3, TangoEvent var4);

	public static native void Disconnect();

	public static native int GetPoseAtTime(double var0, int var2, int var3, com.google.atap.tangoservice.TangoPoseData var4);

	public static native int GetPoseAtTime2(double var0, String var2, String var3, TangoPoseData var4);

	public static native int CreateFrameOfInterest2(double var0, String var2, TangoTransformation var3, Tango.FoiListener var4);

	public static native int LoadFramesOfInterest(String[] var0, Tango.FoiListener var1);

	public static native int DeleteFramesOfInterest(String[] var0, Tango.FoiListener var1);

	public static native void ResetMotionTracking();

	public static native int GetAreaDescriptionMetadata(String var0, com.google.atap.tangoservice.TangoAreaDescriptionMetaData var1);

	public static native int SaveAreaDescription(String[] var0);

	public static native int DeleteAreaDescription(String var0);

	public static native int GetAreaDescriptionUUIDList(String[] var0);

	public static native int SaveAreaDescriptionMetadata(String var0, TangoAreaDescriptionMetaData var1);

	public static native int GetCameraIntrinsics(int var0, TangoCameraIntrinsics var1);

	public static native int ConnectTextureId(int var0, int var1);

	public static native int UpdateTexture(int var0, double[] var1);

	public static native int DisconnectCamera(int var0);

	public static native int SetRuntimeConfig(TangoConfig var0);

	public static native int GetCurrentDatasetUUID(String[] var0);

	public static native int GetDatasets(String[] var0);

	public static native int DeleteDataset(String var0);

	public static native IBinder GetBinder();

	public static native int SetBinder(IBinder var0);

	public static native int ConnectOnFrameAvailable(int var0, Tango.OnFrameAvailableListener var1, TangoImageBuffer var2);

	static {
		if (!TangoClientLibLoader.loadedSuccessfully()) {
			Log.e("TangoJNINative", "ERROR! Unable to load libtango_client_api.so!");
		}

	}
}
