package com.google.tango;

import android.util.Log;

import java.io.File;

public class TangoClientLibLoader {
	public static final boolean PURE_JAVA_PATH = false;
	public static final int ARCH_ERROR = -2;
	public static final int ARCH_FALLBACK = -1;
	public static final int ARCH_DEFAULT = 0;
	public static final int ARCH_ARM64 = 1;
	public static final int ARCH_ARM32 = 2;
	public static final int ARCH_X86_64 = 3;
	public static final int ARCH_X86 = 4;
	private static final int loadedSoArch;
	private static final String TAG = "TangoClientLibLoader";

	public TangoClientLibLoader() {
	}

	public static int getTangoClientApiArch() {
		return loadedSoArch;
	}

	public static boolean loadedSuccessfully() {
		return getTangoClientApiArch() != -2;
	}

	static {
		int loadedSoId = -2;
		String basePath = "/data/data/com.google.tango/libfiles/";
		if (!(new File(basePath)).exists()) {
			basePath = "/data/data/com.projecttango.tango/libfiles/";
		}

		Log.i("TangoClientLibLoader", "basePath: " + basePath);

		try {
			System.loadLibrary("tango_client_api");
			loadedSoId = 1;
			Log.i("NTangoClientLibLoader", "Success! libtango_client_api.");
		} catch (UnsatisfiedLinkError var8) {
			var8.printStackTrace();
			loadedSoId = 1;
		}

//		try {
//			System.load(basePath + "arm64-v8a/libtango_client_api.so");
//			loadedSoId = 1;
//			Log.i("TangoClientLibLoader", "Success! Using arm64-v8a/libtango_client_api.");
//		} catch (UnsatisfiedLinkError var8) {
//		}
//
//		if (loadedSoId < 0) {
//			try {
//				System.load(basePath + "armeabi-v7a/libtango_client_api.so");
//				loadedSoId = 2;
//				Log.i("TangoClientLibLoader", "Success! Using armeabi-v7a/libtango_client_api.");
//			} catch (UnsatisfiedLinkError var7) {
//			}
//		}
//
//		if (loadedSoId < 0) {
//			try {
//				System.load(basePath + "x86_64/libtango_client_api.so");
//				loadedSoId = 3;
//				Log.i("TangoClientLibLoader", "Success! Using x86_64/libtango_client_api.");
//			} catch (UnsatisfiedLinkError var6) {
//			}
//		}
//
//		if (loadedSoId < 0) {
//			try {
//				System.load(basePath + "x86/libtango_client_api.so");
//				loadedSoId = 4;
//				Log.i("TangoClientLibLoader", "Success! Using x86/libtango_client_api.");
//			} catch (UnsatisfiedLinkError var5) {
//			}
//		}
//
//		if (loadedSoId < 0) {
//			try {
//				System.load(basePath + "default/libtango_client_api.so");
//				loadedSoId = 0;
//				Log.i("TangoClientLibLoader", "Success! Using default/libtango_client_api.");
//			} catch (UnsatisfiedLinkError var4) {
//			}
//		}
//
//		if (loadedSoId < 0) {
//			try {
//				System.loadLibrary("tango_client_api");
//				loadedSoId = -1;
//				Log.i("TangoClientLibLoader", "Falling back to libtango_client_api.so symlink.");
//			} catch (UnsatisfiedLinkError var3) {
//			}
//		}

		loadedSoArch = loadedSoId;
	}
}
