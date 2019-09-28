package com.test.tango;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

//import com.google.atap.tango.TangoJNINative;
//import com.google.atap.tangoservice.TangoAreaDescriptionMetaData;
//import com.google.atap.tangoservice.fois.FoiResponse;
//import com.google.atap.tangoservice.TangoFoiResult;

//import com.google.atap.tangoservice.ITango;
//import com.google.atap.tangoservice.ITangoListener;

import com.test.atap.tangoservice.TangoAreaDescriptionMetaData;
import com.test.atap.tangoservice.TangoFoiResult;
import com.test.atap.tangoservice.experimental.TangoTransformation;
import com.test.atap.tangoservice.experimental.TangoImageBuffer;
import com.test.atap.tangoservice.ITango;
import com.test.atap.tangoservice.ITangoListener;
import com.test.atap.tangoservice.TangoCameraIntrinsics;
import com.test.atap.tangoservice.TangoConfig;
import com.test.atap.tangoservice.TangoEvent;
import com.test.atap.tangoservice.TangoPointCloudData;
import com.test.atap.tangoservice.TangoPoseData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tango {
	private static final boolean PURE_JAVA_PATH = false;
	private static final String TAG = "Tango";
	private static final int MIN_VERSION = 13636;
	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_ERROR = -1;
	public static final int STATUS_INVALID = -2;
	private static final int STATUS_NO_MOTION_TRACKING_PERMISSION = -3;
	private static final int STATUS_NO_ADF_PERMISSION = -4;
	private static final int STATUS_NO_CAMERA_PERMISSION = -5;
	private static final int STATUS_NO_DATASET_PERMISSION = -7;
	private static final String INTENT_CLASSPACKAGE = "com.google.tango";
	private static final String INTENT_DEPRECATED_CLASSPACKAGE = "com.projecttango.tango";
	private static final String INTENT_REQUESTPERMISSION_CLASSNAME = "com.google.atap.tango.RequestPermissionActivity";
	private static final String INTENT_IMPORTEXPORT_CLASSNAME = "com.google.atap.tango.RequestImportExportActivity";
	private static final String MAGIC_CLOUD_UUID = "use_cloud";
	public static final int TANGO_INTENT_ACTIVITYCODE = 1129;
	private static final String EXTRA_KEY_SOURCEUUID = "SOURCE_UUID";
	private static final String EXTRA_KEY_DESTINATIONFILE = "DESTINATION_FILE";
	private static final String EXTRA_KEY_SOURCEFILE = "SOURCE_FILE";
	public static final String EXTRA_KEY_DESTINATIONUUID = "DESTINATION_UUID";
	private static final String EXTRA_KEY_PERMISSIONTYPE = "PERMISSIONTYPE";
	public static final String PERMISSIONTYPE_MOTION_TRACKING = "MOTION_TRACKING_PERMISSION";
	public static final String PERMISSIONTYPE_ADF_LOAD_SAVE = "ADF_LOAD_SAVE_PERMISSION";
	public static final String PERMISSIONTYPE_DATASET = "DATASET_PERMISSION";
	public static final String ANDROID_PERMISSION_DATASET = "com.google.tango.permission.DATASETS";
	private Context mParent;
	private ServiceConnection mServiceConnection;
	private volatile boolean mTangoShouldBeDisconnected;
	private ITango mITango;
	private com.test.atap.tangoservice.ITangoListener mITangoListener;
	private Tango.TangoUpdateCallback mCallback;
	private volatile boolean mTangoServiceConnected;
	public static final String COORDINATE_FRAME_ID_NONE = "10000000-0000-0000-0000-0000000000ff";
	public static final String COORDINATE_FRAME_ID_GLOBAL_WGS84 = "10000000-0000-0000-0000-000000000000";
	public static final String COORDINATE_FRAME_ID_AREA_DESCRIPTION = "10000000-0000-0000-0000-000000000001";
	public static final String COORDINATE_FRAME_ID_START_OF_SERVICE = "10000000-0000-0000-0000-000000000002";
	public static final String COORDINATE_FRAME_ID_PREVIOUS_DEVICE_POSE = "10000000-0000-0000-0000-000000000003";
	public static final String COORDINATE_FRAME_ID_DEVICE = "10000000-0000-0000-0000-000000000004";
	public static final String COORDINATE_FRAME_ID_IMU = "10000000-0000-0000-0000-000000000005";
	public static final String COORDINATE_FRAME_ID_DISPLAY = "10000000-0000-0000-0000-000000000006";
	public static final String COORDINATE_FRAME_ID_CAMERA_COLOR = "10000000-0000-0000-0000-000000000007";
	public static final String COORDINATE_FRAME_CAMERA_DEPTH = "10000000-0000-0000-0000-000000000008";
	public static final String COORDINATE_FRAME_CAMERA_FISHEYE = "10000000-0000-0000-0000-000000000009";

	public Tango(final Context context, final Runnable runOnTangoReady) {
		class NamelessClass_1 extends ITangoListener.Stub {
			NamelessClass_1() {
			}

			@Override
			public void onPoseAvailable(com.test.atap.tangoservice.TangoPoseData pose) {
				if (Tango.this.mCallback != null) {
					Tango.this.mCallback.onPoseAvailable(pose);
				}
			}

			@Override
			public void onXyzIjAvailable() {
			}

			@Override
			public void onTangoEvent(com.test.atap.tangoservice.TangoEvent event) {
				if (Tango.this.mCallback != null) {
					Tango.this.mCallback.onTangoEvent(event);
				}
			}

			@Override
			public void onGraphicBufferAvailable(int cameraId) {
				if (Tango.this.mCallback != null) {
					Tango.this.mCallback.onFrameAvailable(cameraId);
				}
			}

			@Override
			public void onPointCloudAvailable(com.test.atap.tangoservice.TangoPointCloudData pointCloud) {
				if (Tango.this.mCallback != null) {
					Tango.this.mCallback.onPointCloudAvailable(pointCloud);
				}
			}

			@Override
			public void onFoiResponse(FoiResponse foiResponse) {
			}
		}

		this.mITangoListener = new NamelessClass_1();
		this.mTangoServiceConnected = false;
		this.mParent = context;
		Intent intent = new Intent();
		intent.setClassName("com.google.tango", "com.google.atap.tango.TangoService");
		boolean hasJavaService = context.getPackageManager().resolveService(intent, 0) != null;

		//Not using jni at it missing library or package name of fakeTango must be the same
		//hasJavaService = true;
		if (!hasJavaService) {
			Log.e("Tango", "Java version of Tango Service not found, falling back to projecttango.tango.");
			intent = new Intent();
			intent.setClassName("com.projecttango.tango", "com.google.atap.tango.TangoService");
			hasJavaService = context.getPackageManager().resolveService(intent, 0) != null;
		}
		if (!hasJavaService) {
			Log.e("Tango", "Java version of Tango Service not found, falling back to tangoservice_jni.");
			TangoJNINative.Initialize(context);
			(new Thread(runOnTangoReady)).start();
		} else {
			Log.w("Tango", "Java version of Tango Service found.");
			this.mServiceConnection = new ServiceConnection() {
				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					TangoJNINative.SetBinder(service);
					if (Tango.this.mTangoShouldBeDisconnected) {
						Tango.this.disconnect();
					} else {
						(new Thread(runOnTangoReady)).start();
					}
				}
				@Override
				public void onServiceDisconnected(ComponentName name) {
				}
			};
			context.bindService(intent, this.mServiceConnection,
					Context.BIND_DEBUG_UNBIND | Context.BIND_AUTO_CREATE);
			this.mTangoShouldBeDisconnected = false;
		}
	}

	public com.test.atap.tangoservice.TangoConfig getConfig(int configType) {
		com.test.atap.tangoservice.TangoConfig config = new com.test.atap.tangoservice.TangoConfig();
		TangoJNINative.GetConfig(configType, config);
		return config;
	}

	public void connectTextureId(int cameraId, int textureId) {
		throwTangoExceptionIfNeeded(TangoJNINative.ConnectTextureId(cameraId, textureId));
	}

	public double updateTexture(int cameraId) {
		double[] timestamp = new double[1];
		throwTangoExceptionIfNeeded(TangoJNINative.UpdateTexture(cameraId, timestamp));
		return timestamp[0];
	}

	public void connectOnTextureAvailable(int cameraId) {
		throwTangoExceptionIfNeeded(-2);
	}

	public double updateTextureExternalOes(int cameraId, int textureId) {
		double[] timestamp = new double[1];
		throwTangoExceptionIfNeeded(-2);
		return timestamp[0];
	}

	public double lockCameraBuffer(int cameraId, long[] bufferIdHolder) {
		double[] timestamp = new double[1];
		throwTangoExceptionIfNeeded(-2);
		return timestamp[0];
	}

	public void unlockCameraBuffer(int cameraId, long bufferId) {
		throwTangoExceptionIfNeeded(-2);
	}

	public void updateTextureExternalOesForBuffer(int cameraId, int textureId, long bufferId) {
		throwTangoExceptionIfNeeded(-2);
	}

	public void experimentalConnectOnFrameListener(int cameraId, Tango.OnFrameAvailableListener listener) {
		throwTangoExceptionIfNeeded(TangoJNINative.ConnectOnFrameAvailable(cameraId, listener, new TangoImageBuffer()));
	}

	public void connectListener(List<TangoCoordinateFramePair> framePairs, Tango.TangoUpdateCallback listener) {
		int[] coordinateFramePairsArray = new int[0];
		if (framePairs != null) {
			coordinateFramePairsArray = new int[framePairs.size() * 2];

			for(int i = 0; i < framePairs.size(); ++i) {
				coordinateFramePairsArray[i * 2] = ((TangoCoordinateFramePair)framePairs.get(i)).baseFrame;
				coordinateFramePairsArray[i * 2 + 1] = ((TangoCoordinateFramePair)framePairs.get(i)).targetFrame;
			}
		}

		if (listener != null) {
			throwTangoExceptionIfNeeded(TangoJNINative.ConnectListener(coordinateFramePairsArray, listener, new com.test.atap.tangoservice.TangoPoseData(), new com.test.atap.tangoservice.TangoPointCloudData(), new com.test.atap.tangoservice.TangoEvent()));
		} else {
			throwTangoExceptionIfNeeded(TangoJNINative.ConnectListener(coordinateFramePairsArray, listener, (com.test.atap.tangoservice.TangoPoseData)null, (com.test.atap.tangoservice.TangoPointCloudData)null, (com.test.atap.tangoservice.TangoEvent)null));
		}

	}

	public void connect(com.test.atap.tangoservice.TangoConfig config) {
		String adfUuid = config.getString("config_load_area_description_UUID");
		if (adfUuid != null && adfUuid.equals("use_cloud")) {
			Log.e("Tango", "The 'use_cloud' ADF string is deprecated. This is now enabled via the TangoConfig parameter 'config_experimental_use_cloud_adf'.");
			throw new TangoErrorException();
		} else {
			PackageManager pm = this.mParent.getPackageManager();
			boolean tangoOutdated = false;
			int version = getVersion(this.mParent);
			Log.i("Tango", "com.google.tango: " + version);
			if (version < 0) {
				throw new TangoErrorException();
			} else {
				if (version < 13636) {
					tangoOutdated = true;
				}

				throwTangoExceptionIfNeeded(TangoJNINative.Connect(config));
				if (tangoOutdated) {
					throw new TangoOutOfDateException();
				}
			}
		}
	}

	public void disconnect() {
		this.mTangoShouldBeDisconnected = true;
		TangoJNINative.Disconnect();
		if (this.mServiceConnection != null) {
			this.mParent.unbindService(this.mServiceConnection);
			this.mServiceConnection = null;
		}

	}

	public com.test.atap.tangoservice.TangoPoseData getPoseAtTime(double timestamp, TangoCoordinateFramePair framePair) {
		com.test.atap.tangoservice.TangoPoseData result = new com.test.atap.tangoservice.TangoPoseData();
		throwTangoExceptionIfNeeded(TangoJNINative.GetPoseAtTime(timestamp, framePair.baseFrame, framePair.targetFrame, result));
		return result;
	}

	public com.test.atap.tangoservice.TangoPoseData getPoseAtTime(double timestamp, String baseFrameUuid, String targetFrameUuid) {
		com.test.atap.tangoservice.TangoPoseData result = new com.test.atap.tangoservice.TangoPoseData();
		throwTangoExceptionIfNeeded(TangoJNINative.GetPoseAtTime2(timestamp, baseFrameUuid, targetFrameUuid, result));
		return result;
	}

	public void disconnectCamera(int cameraId) {
		throwTangoExceptionIfNeeded(TangoJNINative.DisconnectCamera(cameraId));
	}

	public void resetMotionTracking() {
		TangoJNINative.ResetMotionTracking();
	}

	public TangoAreaDescriptionMetaData loadAreaDescriptionMetaData(String uuid) {
		TangoAreaDescriptionMetaData result = new TangoAreaDescriptionMetaData();
		throwTangoExceptionIfNeeded(TangoJNINative.GetAreaDescriptionMetadata(uuid, result));
		return result;
	}

	public String saveAreaDescription() {
		String[] uuidHolder = new String[1];
		throwTangoExceptionIfNeeded(TangoJNINative.SaveAreaDescription(uuidHolder));
		return uuidHolder[0];
	}

	public void deleteAreaDescription(String uuid) {
		throwTangoExceptionIfNeeded(TangoJNINative.DeleteAreaDescription(uuid));
	}

	public ArrayList<String> listAreaDescriptions() {
		String[] uuidListHolder = new String[1];
		throwTangoExceptionIfNeeded(TangoJNINative.GetAreaDescriptionUUIDList(uuidListHolder));
		String commaseparatedUuids = uuidListHolder[0];
		ArrayList uuidList;
		if (commaseparatedUuids.length() > 0) {
			uuidList = new ArrayList(Arrays.asList(commaseparatedUuids.split("\\s*,\\s*")));
		} else {
			Log.w("Tango", "No UUIDs.");
			uuidList = new ArrayList();
		}

		Log.i("Tango", "Number of uuids is " + uuidList.size());
		return uuidList;
	}

	public String experimentalGetCurrentDatasetUuid() {
		String[] uuidHolder = new String[1];
		throwTangoExceptionIfNeeded(TangoJNINative.GetCurrentDatasetUUID(uuidHolder));
		return uuidHolder[0];
	}

	public List<String> experimentalListDatasets() {
		String[] datasetHolder = new String[1];
		throwTangoExceptionIfNeeded(TangoJNINative.GetDatasets(datasetHolder));
		String commaseparatedUuids = datasetHolder[0];
		ArrayList uuidList;
		if (commaseparatedUuids.length() > 0) {
			uuidList = new ArrayList(Arrays.asList(commaseparatedUuids.split("\\s*,\\s*")));
		} else {
			Log.w("Tango", "No datasets were found.");
			uuidList = new ArrayList();
		}

		return uuidList;
	}

	public void experimentalDeleteDataset(String uuid) {
		throwTangoExceptionIfNeeded(TangoJNINative.DeleteDataset(uuid));
	}

	public void saveAreaDescriptionMetadata(String uuid, TangoAreaDescriptionMetaData metadata) {
		throwTangoExceptionIfNeeded(TangoJNINative.SaveAreaDescriptionMetadata(uuid, metadata));
	}

	public void importAreaDescriptionFile(String filepath) {
		Activity parentActivity;
		try {
			parentActivity = (Activity)this.mParent;
		} catch (ClassCastException var4) {
			Log.e("Tango", "Error: importAreaDescriptionFile can only be called from an Activity.");
			throw new TangoErrorException();
		}

		Intent importIntent = new Intent();
		importIntent.setClassName("com.google.tango", "com.google.atap.tango.RequestImportExportActivity");
		if (importIntent.resolveActivity(this.mParent.getPackageManager()) == null) {
			importIntent = new Intent();
			importIntent.setClassName("com.projecttango.tango", "com.google.atap.tango.RequestImportExportActivity");
		}

		importIntent.putExtra("SOURCE_FILE", filepath);
		parentActivity.startActivityForResult(importIntent, 1129);
	}

	public void exportAreaDescriptionFile(String uuid, String filepathDirectory) {
		Activity parentActivity;
		try {
			parentActivity = (Activity)this.mParent;
		} catch (ClassCastException var5) {
			Log.e("Tango", "Error: exportAreaDescriptionFile can only be called from an Activity.");
			throw new TangoErrorException();
		}

		Intent exportIntent = new Intent();
		exportIntent.setClassName("com.google.tango", "com.google.atap.tango.RequestImportExportActivity");
		if (exportIntent.resolveActivity(this.mParent.getPackageManager()) == null) {
			exportIntent = new Intent();
			exportIntent.setClassName("com.projecttango.tango", "com.google.atap.tango.RequestImportExportActivity");
		}

		exportIntent.putExtra("SOURCE_UUID", uuid);
		exportIntent.putExtra("DESTINATION_FILE", filepathDirectory);
		parentActivity.startActivityForResult(exportIntent, 1129);
	}

	public void createFrameOfInterest(double timestamp, String baseFrameUuid, TangoTransformation transformation, Tango.FoiListener listener) {
		if (listener == null) {
			throwTangoExceptionIfNeeded(-2);
		}

		if (baseFrameUuid != null && transformation != null) {
			throwTangoExceptionIfNeeded(TangoJNINative.CreateFrameOfInterest2(timestamp, baseFrameUuid, transformation, listener));
		} else {
			throwTangoExceptionIfNeeded(-2);
		}

	}

	public void loadFramesOfInterest(String[] ids, Tango.FoiListener listener) {
		if (listener == null) {
			throwTangoExceptionIfNeeded(-2);
		}

		if (ids != null) {
			for(int i = 0; i < ids.length; ++i) {
				if (ids[i] == null) {
					throwTangoExceptionIfNeeded(-2);
				}
			}

			throwTangoExceptionIfNeeded(TangoJNINative.LoadFramesOfInterest(ids, listener));
		} else {
			throwTangoExceptionIfNeeded(-2);
		}

	}

	public void deleteFramesOfInterest(String[] ids, Tango.FoiListener listener) {
		if (listener == null) {
			throwTangoExceptionIfNeeded(-2);
		}

		if (ids != null) {
			for(int i = 0; i < ids.length; ++i) {
				if (ids[i] == null) {
					throwTangoExceptionIfNeeded(-2);
				}
			}

			throwTangoExceptionIfNeeded(TangoJNINative.DeleteFramesOfInterest(ids, listener));
		} else {
			throwTangoExceptionIfNeeded(-2);
		}

	}

	public com.test.atap.tangoservice.TangoCameraIntrinsics getCameraIntrinsics(int cameraId) {
		com.test.atap.tangoservice.TangoCameraIntrinsics intrinsics = new TangoCameraIntrinsics();
		throwTangoExceptionIfNeeded(TangoJNINative.GetCameraIntrinsics(cameraId, intrinsics));
		return intrinsics;
	}

	public static void throwTangoExceptionIfNeeded(int resultCode) {
		switch(resultCode) {
			case -7:
				throw new SecurityException("Tango Permission Denied. No Dataset permission.");
			case -6:
			case -1:
			default:
				throw new TangoErrorException();
			case -5:
				throw new SecurityException("Tango Permission Denied. No android.permission.CAMERA permission.");
			case -4:
				throw new SecurityException("Tango Permission Denied. No ADF permission.");
			case -3:
				throw new SecurityException("Tango Permission Denied. No Motion Tracking permission.");
			case -2:
				throw new TangoInvalidException();
			case 0:
		}
	}

	public static Intent getRequestPermissionIntent(String permissionType) {
		Intent permissionIntent = new Intent();
		permissionIntent.setClassName("com.google.tango", "com.google.atap.tango.RequestPermissionActivity");
		permissionIntent.putExtra("PERMISSIONTYPE", permissionType);
		return permissionIntent;
	}

	public static boolean hasPermission(Context context, String permissionType) {
		if (permissionType.equals("MOTION_TRACKING_PERMISSION")) {
			Log.w("Tango", "You no longer need to request motion tracking permissions.");
			return true;
		} else {
			Uri uri = Uri.parse("content://com.google.atap.tango.PermissionStatusProvider/" + permissionType);
			Cursor cursor = context.getContentResolver().query(uri, (String[])null, (String)null, (String[])null, (String)null);
			return cursor != null;
		}
	}

	public void setRuntimeConfig(TangoConfig config) {
		throwTangoExceptionIfNeeded(TangoJNINative.SetRuntimeConfig(config));
	}

	public static int getVersion(Context context) {
		PackageManager pm = context.getPackageManager();

		try {
			PackageInfo info = pm.getPackageInfo("com.google.tango", 0);
			return Integer.parseInt(Integer.toString(info.versionCode).substring(2));
		} catch (PackageManager.NameNotFoundException var3) {
			return -1;
		}
	}

	/** @deprecated */
	public abstract static class OnTangoUpdateListener extends Tango.TangoUpdateCallback {
		public OnTangoUpdateListener() {
		}
	}

	public abstract static class TangoUpdateCallback {
		public TangoUpdateCallback() {
		}

		public void onPoseAvailable(TangoPoseData pose) {
		}

		public void onXyzIjAvailable(TangoXyzIjData xyzIj) {
		}

		public void onFrameAvailable(int cameraId) {
		}

		public void onTangoEvent(TangoEvent event) {
		}

		public void onPointCloudAvailable(TangoPointCloudData pointCloud) {
		}
	}

	public interface FoiListener {
		void onFoiResult(TangoFoiResult[] var1);
	}

	public interface OnFrameAvailableListener {
		void onFrameAvailable(TangoImageBuffer var1, int var2);
	}
}
