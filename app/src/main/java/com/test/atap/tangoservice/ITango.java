package com.test.atap.tangoservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.Surface;

//import com.google.atap.tangoservice.ITangoListener;

import com.test.tango.FoiRequest;
import com.test.tango.TangoCoordinateFramePair;

import java.util.ArrayList;
import java.util.List;

public interface ITango extends IInterface {
	int connect(ITangoListener var1, com.test.atap.tangoservice.TangoConfig var2) throws RemoteException;

	int setPoseListenerFrames(List<com.test.tango.TangoCoordinateFramePair> var1) throws RemoteException;

	int disconnect() throws RemoteException;

	int getPoseAtTime(double var1, com.test.tango.TangoCoordinateFramePair var3, TangoPoseData var4) throws RemoteException;

	int getConfig(int var1, com.test.atap.tangoservice.TangoConfig var2) throws RemoteException;

	int connectSurface(int var1, Surface var2) throws RemoteException;

	int disconnectSurface(int var1) throws RemoteException;

	int resetMotionTracking() throws RemoteException;

	int saveAreaDescription(List<String> var1) throws RemoteException;

	int getAreaDescriptionUuidList(List<String> var1) throws RemoteException;

	int loadAreaDescriptionMetaData(String var1, com.test.atap.tangoservice.TangoAreaDescriptionMetaData var2) throws RemoteException;

	int saveAreaDescriptionMetaData(String var1, com.test.atap.tangoservice.TangoAreaDescriptionMetaData var2) throws RemoteException;

	int importAreaDescriptionFile(List<String> var1, String var2) throws RemoteException;

	int exportAreaDescriptionFile(String var1, String var2) throws RemoteException;

	int deleteAreaDescription(String var1) throws RemoteException;

	int getCameraIntrinsics(int var1, TangoCameraIntrinsics var2) throws RemoteException;

	int setRuntimeConfig(com.test.atap.tangoservice.TangoConfig var1) throws RemoteException;

	int reportApiUsage(com.test.atap.tangoservice.TangoConfig var1) throws RemoteException;

	int getDatasetUuids(List<String> var1) throws RemoteException;

	int deleteDataset(String var1) throws RemoteException;

	int getCurrentDatasetUuid(List<String> var1) throws RemoteException;

	int foiRequest(FoiRequest var1) throws RemoteException;

	int getPoseAtTime2(double var1, String var3, String var4, TangoPoseData var5) throws RemoteException;

	public abstract static class Stub extends Binder implements ITango {
		private static final String DESCRIPTOR = "com.google.atap.tangoservice.ITango";
		static final int TRANSACTION_connect = 1;
		static final int TRANSACTION_setPoseListenerFrames = 2;
		static final int TRANSACTION_disconnect = 3;
		static final int TRANSACTION_getPoseAtTime = 4;
		static final int TRANSACTION_getConfig = 5;
		static final int TRANSACTION_connectSurface = 6;
		static final int TRANSACTION_disconnectSurface = 7;
		static final int TRANSACTION_resetMotionTracking = 8;
		static final int TRANSACTION_saveAreaDescription = 9;
		static final int TRANSACTION_getAreaDescriptionUuidList = 10;
		static final int TRANSACTION_loadAreaDescriptionMetaData = 11;
		static final int TRANSACTION_saveAreaDescriptionMetaData = 12;
		static final int TRANSACTION_importAreaDescriptionFile = 13;
		static final int TRANSACTION_exportAreaDescriptionFile = 14;
		static final int TRANSACTION_deleteAreaDescription = 15;
		static final int TRANSACTION_getCameraIntrinsics = 16;
		static final int TRANSACTION_setRuntimeConfig = 17;
		static final int TRANSACTION_reportApiUsage = 18;
		static final int TRANSACTION_getDatasetUuids = 19;
		static final int TRANSACTION_deleteDataset = 20;
		static final int TRANSACTION_getCurrentDatasetUuid = 21;
		static final int TRANSACTION_foiRequest = 22;
		static final int TRANSACTION_getPoseAtTime2 = 23;

		public Stub() {
			this.attachInterface(this, "com.google.atap.tangoservice.ITango");
		}

		public static ITango asInterface(IBinder obj) {
			if (obj == null) {
				return null;
			} else {
				IInterface iin = obj.queryLocalInterface("com.google.atap.tangoservice.ITango");
				return (ITango)(iin != null && iin instanceof ITango ? (ITango)iin : new ITango.Stub.Proxy(obj));
			}
		}

		public IBinder asBinder() {
			return this;
		}

		public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
			double _arg0;
			int _result;
			ArrayList arrayList;
			String s;
			int result;
			com.test.atap.tangoservice.TangoConfig tangoConfig1;
			String _arg1;
			int i;
			com.test.atap.tangoservice.TangoAreaDescriptionMetaData tangoAreaDescriptionMetaData;
			com.test.atap.tangoservice.TangoConfig tangoConfig;
			switch(code) {
				case 1:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					ITangoListener tangoListener = ITangoListener.Stub.asInterface(data.readStrongBinder());
					if (0 != data.readInt()) {
						tangoConfig = (com.test.atap.tangoservice.TangoConfig) com.test.atap.tangoservice.TangoConfig.CREATOR.createFromParcel(data);
					} else {
						tangoConfig = null;
					}

					result = this.connect(tangoListener, tangoConfig);
					reply.writeNoException();
					reply.writeInt(result);
					return true;
				case 2:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					List lTangoListener = data.createTypedArrayList(com.test.tango.TangoCoordinateFramePair.CREATOR);
					result = this.setPoseListenerFrames(lTangoListener);
					reply.writeNoException();
					reply.writeInt(result);
					return true;
				case 3:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					int iTangoListener = this.disconnect();
					reply.writeNoException();
					reply.writeInt(iTangoListener);
					return true;
				case 4:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					double dTangoListener = data.readDouble();
					com.test.tango.TangoCoordinateFramePair tangoCoordinateFramePair;
					if (0 != data.readInt()) {
						tangoCoordinateFramePair = (com.test.tango.TangoCoordinateFramePair) com.test.tango.TangoCoordinateFramePair.CREATOR.createFromParcel(data);
					} else {
						tangoCoordinateFramePair = null;
					}

					TangoPoseData tangoPoseData1 = new TangoPoseData();
					int iResult = this.getPoseAtTime(dTangoListener, tangoCoordinateFramePair, tangoPoseData1);
					reply.writeNoException();
					reply.writeInt(iResult);
					if (tangoPoseData1 != null) {
						reply.writeInt(1);
						tangoPoseData1.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
					} else {
						reply.writeInt(0);
					}

					return true;
				case 5:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					iTangoListener = data.readInt();
					tangoConfig = new com.test.atap.tangoservice.TangoConfig();
					_result = this.getConfig(iTangoListener, tangoConfig);
					reply.writeNoException();
					reply.writeInt(_result);
					if (tangoConfig != null) {
						reply.writeInt(1);
						tangoConfig.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
					} else {
						reply.writeInt(0);
					}

					return true;
				case 6:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					iTangoListener = data.readInt();
					Surface surface;
					if (0 != data.readInt()) {
						surface = (Surface)Surface.CREATOR.createFromParcel(data);
					} else {
						surface = null;
					}

					_result = this.connectSurface(iTangoListener, surface);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 7:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					iTangoListener = data.readInt();
					_result = this.disconnectSurface(iTangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 8:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					iTangoListener = this.resetMotionTracking();
					reply.writeNoException();
					reply.writeInt(iTangoListener);
					return true;
				case 9:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					lTangoListener = new ArrayList();
					_result = this.saveAreaDescription(lTangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					reply.writeStringList(lTangoListener);
					return true;
				case 10:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					lTangoListener = new ArrayList();
					_result = this.getAreaDescriptionUuidList(lTangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					reply.writeStringList(lTangoListener);
					return true;
				case 11:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					String sTangoListener = data.readString();
					com.test.atap.tangoservice.TangoAreaDescriptionMetaData lSurface = new com.test.atap.tangoservice.TangoAreaDescriptionMetaData();
					_result = this.loadAreaDescriptionMetaData(sTangoListener, lSurface);
					reply.writeNoException();
					reply.writeInt(_result);
					if (lSurface != null) {
						reply.writeInt(1);
						lSurface.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
					} else {
						reply.writeInt(0);
					}

					return true;
				case 12:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					sTangoListener = data.readString();
					if (0 != data.readInt()) {
						lSurface = (com.test.atap.tangoservice.TangoAreaDescriptionMetaData) com.test.atap.tangoservice.TangoAreaDescriptionMetaData.CREATOR.createFromParcel(data);
					} else {
						lSurface = null;
					}

					_result = this.saveAreaDescriptionMetaData(sTangoListener, lSurface);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 13:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					lTangoListener = new ArrayList();
					String ssurface = data.readString();
					_result = this.importAreaDescriptionFile(lTangoListener, ssurface);
					reply.writeNoException();
					reply.writeInt(_result);
					reply.writeStringList(lTangoListener);
					return true;
				case 14:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					sTangoListener = data.readString();
					String sssurface = data.readString();
					_result = this.exportAreaDescriptionFile(sTangoListener, sssurface);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 15:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					sTangoListener = data.readString();
					_result = this.deleteAreaDescription(sTangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 16:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					iTangoListener = data.readInt();
					TangoCameraIntrinsics _arg11 = new TangoCameraIntrinsics();
					_result = this.getCameraIntrinsics(iTangoListener, _arg11);
					reply.writeNoException();
					reply.writeInt(_result);
					if (_arg11 != null) {
						reply.writeInt(1);
						_arg11.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
					} else {
						reply.writeInt(0);
					}

					return true;
				case 17:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					com.test.atap.tangoservice.TangoConfig ftangoListener;
					if (0 != data.readInt()) {
						ftangoListener = (com.test.atap.tangoservice.TangoConfig) com.test.atap.tangoservice.TangoConfig.CREATOR.createFromParcel(data);
					} else {
						ftangoListener = null;
					}

					_result = this.setRuntimeConfig(ftangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 18:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					if (0 != data.readInt()) {
						ftangoListener = (com.test.atap.tangoservice.TangoConfig) com.test.atap.tangoservice.TangoConfig.CREATOR.createFromParcel(data);
					} else {
						ftangoListener = null;
					}

					_result = this.reportApiUsage(ftangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 19:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					lTangoListener = new ArrayList();
					_result = this.getDatasetUuids(lTangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					reply.writeStringList(lTangoListener);
					return true;
				case 20:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					sTangoListener = data.readString();
					_result = this.deleteDataset(sTangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 21:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					lTangoListener = new ArrayList();
					_result = this.getCurrentDatasetUuid(lTangoListener);
					reply.writeNoException();
					reply.writeInt(_result);
					reply.writeStringList(lTangoListener);
					return true;
				case 22:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					FoiRequest foiRequest;
					if (0 != data.readInt()) {
						foiRequest = (FoiRequest)FoiRequest.CREATOR.createFromParcel(data);
					} else {
						foiRequest = null;
					}

					_result = this.foiRequest(foiRequest);
					reply.writeNoException();
					reply.writeInt(_result);
					return true;
				case 23:
					data.enforceInterface("com.google.atap.tangoservice.ITango");
					double dFoiRequest = data.readDouble();
					String s1 = data.readString();
					String s2 = data.readString();
					TangoPoseData tangoPoseData = new TangoPoseData();
					int poseAtTime2 = this.getPoseAtTime2(dFoiRequest, s1, s2, tangoPoseData);
					reply.writeNoException();
					reply.writeInt(poseAtTime2);
					if (tangoPoseData != null) {
						reply.writeInt(1);
						tangoPoseData.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
					} else {
						reply.writeInt(0);
					}

					return true;
				case 1598968902:
					reply.writeString("com.google.atap.tangoservice.ITango");
					return true;
				default:
					return super.onTransact(code, data, reply, flags);
			}
		}

		private static class Proxy implements ITango {
			private IBinder mRemote;

			Proxy(IBinder remote) {
				this.mRemote = remote;
			}

			public IBinder asBinder() {
				return this.mRemote;
			}

			public String getInterfaceDescriptor() {
				return "com.google.atap.tangoservice.ITango";
			}

			public int connect(ITangoListener listener, com.test.atap.tangoservice.TangoConfig config) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeStrongBinder(listener != null ? listener.asBinder() : null);
					if (config != null) {
						_data.writeInt(1);
						config.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(1, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int setPoseListenerFrames(List<com.test.tango.TangoCoordinateFramePair> framePairs) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeTypedList(framePairs);
					this.mRemote.transact(2, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int disconnect() throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					this.mRemote.transact(3, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int getPoseAtTime(double timestamp, TangoCoordinateFramePair framePair, TangoPoseData pose) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeDouble(timestamp);
					if (framePair != null) {
						_data.writeInt(1);
						framePair.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(4, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					if (0 != _reply.readInt()) {
						pose.readFromParcel(_reply);
					}
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int getConfig(int configType, com.test.atap.tangoservice.TangoConfig config) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeInt(configType);
					this.mRemote.transact(5, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					if (0 != _reply.readInt()) {
						config.readFromParcel(_reply);
					}
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int connectSurface(int cameraId, Surface surface) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeInt(cameraId);
					if (surface != null) {
						_data.writeInt(1);
						surface.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(6, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int disconnectSurface(int cameraId) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeInt(cameraId);
					this.mRemote.transact(7, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int resetMotionTracking() throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					this.mRemote.transact(8, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int saveAreaDescription(List<String> uuid) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					this.mRemote.transact(9, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					_reply.readStringList(uuid);
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int getAreaDescriptionUuidList(List<String> uuidList) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					this.mRemote.transact(10, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					_reply.readStringList(uuidList);
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int loadAreaDescriptionMetaData(String uuid, com.test.atap.tangoservice.TangoAreaDescriptionMetaData metadata) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeString(uuid);
					this.mRemote.transact(11, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					if (0 != _reply.readInt()) {
						metadata.readFromParcel(_reply);
					}
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int saveAreaDescriptionMetaData(String uuid, TangoAreaDescriptionMetaData metadata) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeString(uuid);
					if (metadata != null) {
						_data.writeInt(1);
						metadata.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(12, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int importAreaDescriptionFile(List<String> uuid, String filepath) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeString(filepath);
					this.mRemote.transact(13, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					_reply.readStringList(uuid);
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int exportAreaDescriptionFile(String uuid, String filepath) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeString(uuid);
					_data.writeString(filepath);
					this.mRemote.transact(14, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int deleteAreaDescription(String uuid) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeString(uuid);
					this.mRemote.transact(15, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int getCameraIntrinsics(int cameraId, TangoCameraIntrinsics intrinsics) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeInt(cameraId);
					this.mRemote.transact(16, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					if (0 != _reply.readInt()) {
						intrinsics.readFromParcel(_reply);
					}
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int setRuntimeConfig(com.test.atap.tangoservice.TangoConfig config) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					if (config != null) {
						_data.writeInt(1);
						config.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(17, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int reportApiUsage(TangoConfig apiUsage) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					if (apiUsage != null) {
						_data.writeInt(1);
						apiUsage.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(18, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int getDatasetUuids(List<String> uuidList) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					this.mRemote.transact(19, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					_reply.readStringList(uuidList);
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int deleteDataset(String uuid) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeString(uuid);
					this.mRemote.transact(20, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int getCurrentDatasetUuid(List<String> uuid) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					this.mRemote.transact(21, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					_reply.readStringList(uuid);
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int foiRequest(FoiRequest request) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					if (request != null) {
						_data.writeInt(1);
						request.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(22, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}

			public int getPoseAtTime2(double timestamp, String baseFrameUuid, String targetFrameUuid, TangoPoseData pose) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				int _result;
				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITango");
					_data.writeDouble(timestamp);
					_data.writeString(baseFrameUuid);
					_data.writeString(targetFrameUuid);
					this.mRemote.transact(23, _data, _reply, 0);
					_reply.readException();
					_result = _reply.readInt();
					if (0 != _reply.readInt()) {
						pose.readFromParcel(_reply);
					}
				} finally {
					_reply.recycle();
					_data.recycle();
				}

				return _result;
			}
		}
	}
}