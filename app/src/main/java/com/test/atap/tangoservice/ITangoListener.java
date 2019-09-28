package com.test.atap.tangoservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.test.tango.FoiResponse;

public interface ITangoListener extends IInterface {
	void onPoseAvailable(com.test.atap.tangoservice.TangoPoseData var1) throws RemoteException;

	void onXyzIjAvailable() throws RemoteException;

	void onTangoEvent(com.test.atap.tangoservice.TangoEvent var1) throws RemoteException;

	void onGraphicBufferAvailable(int var1) throws RemoteException;

	void onPointCloudAvailable(TangoPointCloudData var1) throws RemoteException;

	void onFoiResponse(FoiResponse var1) throws RemoteException;

	public abstract static class Stub extends Binder implements ITangoListener {
		private static final String DESCRIPTOR = "com.google.atap.tangoservice.ITangoListener";
		static final int TRANSACTION_onPoseAvailable = 1;
		static final int TRANSACTION_onXyzIjAvailable = 2;
		static final int TRANSACTION_onTangoEvent = 3;
		static final int TRANSACTION_onGraphicBufferAvailable = 4;
		static final int TRANSACTION_onPointCloudAvailable = 5;
		static final int TRANSACTION_onFoiResponse = 6;

		public Stub() {
			this.attachInterface(this, "com.google.atap.tangoservice.ITangoListener");
		}

		public static ITangoListener asInterface(IBinder obj) {
			if (obj == null) {
				return null;
			} else {
				IInterface iin = obj.queryLocalInterface("com.google.atap.tangoservice.ITangoListener");
				return (ITangoListener)(iin != null && iin instanceof ITangoListener ? (ITangoListener)iin : new ITangoListener.Stub.Proxy(obj));
			}
		}

		public IBinder asBinder() {
			return this;
		}

		public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
			switch(code) {
				case 1:
					data.enforceInterface("com.google.atap.tangoservice.ITangoListener");
					com.test.atap.tangoservice.TangoPoseData _arg0;
					if (0 != data.readInt()) {
						_arg0 = (com.test.atap.tangoservice.TangoPoseData) com.test.atap.tangoservice.TangoPoseData.CREATOR.createFromParcel(data);
					} else {
						_arg0 = null;
					}

					this.onPoseAvailable(_arg0);
					reply.writeNoException();
					return true;
				case 2:
					data.enforceInterface("com.google.atap.tangoservice.ITangoListener");
					this.onXyzIjAvailable();
					reply.writeNoException();
					return true;
				case 3:
					data.enforceInterface("com.google.atap.tangoservice.ITangoListener");
					com.test.atap.tangoservice.TangoEvent tangoEvent;
					if (0 != data.readInt()) {
						tangoEvent = (com.test.atap.tangoservice.TangoEvent) com.test.atap.tangoservice.TangoEvent.CREATOR.createFromParcel(data);
					} else {
						tangoEvent = null;
					}

					this.onTangoEvent(tangoEvent);
					reply.writeNoException();
					return true;
				case 4:
					data.enforceInterface("com.google.atap.tangoservice.ITangoListener");
					int readInt = data.readInt();
					this.onGraphicBufferAvailable(readInt);
					reply.writeNoException();
					return true;
				case 5:
					data.enforceInterface("com.google.atap.tangoservice.ITangoListener");
					TangoPointCloudData tangoPointCloudData;
					if (0 != data.readInt()) {
						tangoPointCloudData = (TangoPointCloudData) TangoPointCloudData.CREATOR.createFromParcel(data);
					} else {
						tangoPointCloudData = null;
					}

					this.onPointCloudAvailable(tangoPointCloudData);
					reply.writeNoException();
					return true;
				case 6:
					data.enforceInterface("com.google.atap.tangoservice.ITangoListener");
					FoiResponse foiResponse;
					if (0 != data.readInt()) {
						foiResponse = (FoiResponse)FoiResponse.CREATOR.createFromParcel(data);
					} else {
						foiResponse = null;
					}

					this.onFoiResponse(foiResponse);
					return true;
				case 1598968902:
					reply.writeString("com.google.atap.tangoservice.ITangoListener");
					return true;
				default:
					return super.onTransact(code, data, reply, flags);
			}
		}

		private static class Proxy implements ITangoListener {
			private IBinder mRemote;

			Proxy(IBinder remote) {
				this.mRemote = remote;
			}

			public IBinder asBinder() {
				return this.mRemote;
			}

			public String getInterfaceDescriptor() {
				return "com.google.atap.tangoservice.ITangoListener";
			}

			public void onPoseAvailable(TangoPoseData pose) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITangoListener");
					if (pose != null) {
						_data.writeInt(1);
						pose.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(1, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

			}

			public void onXyzIjAvailable() throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITangoListener");
					this.mRemote.transact(2, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

			}

			public void onTangoEvent(TangoEvent event) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITangoListener");
					if (event != null) {
						_data.writeInt(1);
						event.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(3, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

			}

			public void onGraphicBufferAvailable(int cameraId) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITangoListener");
					_data.writeInt(cameraId);
					this.mRemote.transact(4, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

			}

			public void onPointCloudAvailable(TangoPointCloudData pointCloud) throws RemoteException {
				Parcel _data = Parcel.obtain();
				Parcel _reply = Parcel.obtain();

				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITangoListener");
					if (pointCloud != null) {
						_data.writeInt(1);
						pointCloud.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(5, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}

			}

			public void onFoiResponse(FoiResponse response) throws RemoteException {
				Parcel _data = Parcel.obtain();

				try {
					_data.writeInterfaceToken("com.google.atap.tangoservice.ITangoListener");
					if (response != null) {
						_data.writeInt(1);
						response.writeToParcel(_data, 0);
					} else {
						_data.writeInt(0);
					}

					this.mRemote.transact(6, _data, (Parcel)null, 1);
				} finally {
					_data.recycle();
				}

			}
		}
	}
}
