package com.test.tango;

import com.test.atap.tangoservice.TangoCameraIntrinsics;
import com.test.atap.tangoservice.TangoPoseData;
import com.test.atap.tangoservice.experimental.TangoImageBuffer;
import com.test.atap.tangoservice.TangoPointCloudData;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class TangoSupport {
	private static final String TAG = TangoSupport.class.getSimpleName();
	public static final int TANGO_SUPPORT_COORDINATE_CONVENTION_OPENGL = 0;
	public static final int TANGO_SUPPORT_COORDINATE_CONVENTION_UNITY = 1;
	public static final int TANGO_SUPPORT_COORDINATE_CONVENTION_TANGO = 2;
	public static final int TANGO_MARKER_ARTAG = 1;
	public static final int TANGO_MARKER_QRCODE = 2;
	public static final int TANGO_SUPPORT_ENGINE_TANGO = 0;
	public static final int TANGO_SUPPORT_ENGINE_OPENGL = 1;
	public static final int TANGO_SUPPORT_ENGINE_UNITY = 2;
	public static final int TANGO_SUPPORT_ENGINE_UNREAL = 3;
	public static final int ROTATION_IGNORED = -1;
	public static final int ROTATION_0 = 0;
	public static final int ROTATION_90 = 1;
	public static final int ROTATION_180 = 2;
	public static final int ROTATION_270 = 3;

	public TangoSupport() {
	}

	public static void initialize(final Tango tango) {
		if (tango == null) {
			throw new TangoInvalidException();
		} else {
			Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.initializeWithCallbacks(new TangoSupport.TangoSupportCallbacks() {
				public TangoPoseData getPoseAtTime(double timestamp, TangoCoordinateFramePair framePair) {
					return tango.getPoseAtTime(timestamp, framePair);
				}

				public TangoCameraIntrinsics getCameraIntrinsics(int cameraId) {
					return tango.getCameraIntrinsics(cameraId);
				}
			}));
		}
	}

	public static void initialize(TangoSupport.TangoSupportCallbacks callbacks) {
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.initializeWithCallbacks(callbacks));
	}

	public static TangoSupport.IntersectionPointPlaneModelPair fitPlaneModelNearPoint(com.test.atap.tangoservice.TangoPointCloudData pointCloud, double[] pointCloudTranslation, double[] pointCloudOrientation, float u, float v, int displayRotation, double[] colorCameraTranslation, double[] colorCameraOrientation) throws TangoErrorException, TangoInvalidException {
		double[] intersectionPoint = new double[3];
		double[] planeModel = new double[4];
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.fitPlaneModelNearPoint(pointCloud, pointCloudTranslation, pointCloudOrientation, u, v, displayRotation, colorCameraTranslation, colorCameraOrientation, intersectionPoint, planeModel));
		return new TangoSupport.IntersectionPointPlaneModelPair(intersectionPoint, planeModel);
	}

	public static TangoPoseData calculateRelativePose(double baseTimestamp, int baseFrame, double targetTimestamp, int targetFrame) throws TangoErrorException, TangoInvalidException {
		if (!Double.isNaN(baseTimestamp) && !Double.isNaN(targetTimestamp)) {
			TangoPoseData baseFrameTTargetFrame = new TangoPoseData();
			Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.calculateRelativePose(baseTimestamp, baseFrame, targetTimestamp, targetFrame, baseFrameTTargetFrame));
			return baseFrameTTargetFrame;
		} else {
			throw new TangoInvalidException();
		}
	}

	public static TangoPoseData getPoseAtTime(double timestamp, int baseFrame, int targetFrame, int baseEngine, int targetEngine, int rotationIndex) throws TangoErrorException, TangoInvalidException {
		if (timestamp < 0.0D) {
			throw new TangoInvalidException();
		} else {
			TangoPoseData enginePose = new TangoPoseData();
			Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.getPoseAtTime(timestamp, baseFrame, targetFrame, baseEngine, targetEngine, rotationIndex, enginePose));
			return enginePose;
		}
	}

	public static float[] getDepthAtPointNearestNeighbor(com.test.atap.tangoservice.TangoPointCloudData pointCloud, double[] pointCloudTranslation, double[] pointCloudOrientation, float u, float v, int displayRotation, double[] colorCameraTranslation, double[] colorCameraOrientation) throws TangoInvalidException {
		float[] colorCameraPoint = new float[3];
		int result = TangoSupportJNIInterface.getDepthAtPointNearestNeighbor(pointCloud, pointCloudTranslation, pointCloudOrientation, u, v, displayRotation, colorCameraTranslation, colorCameraOrientation, colorCameraPoint);
		if (result == -1) {
			return null;
		} else {
			if (result != 0) {
				Tango.throwTangoExceptionIfNeeded(result);
			}

			return colorCameraPoint;
		}
	}

	public static float[] getDepthAtPointBilateral(com.test.atap.tangoservice.TangoPointCloudData pointCloud, double[] pointCloudTranslation, double[] pointCloudOrientation, TangoImageBuffer imageBuffer, float u, float v, int displayRotation, double[] colorCameraTranslation, double[] colorCameraOrientation) throws TangoInvalidException {
		float[] colorCameraPoint = new float[3];
		int result = TangoSupportJNIInterface.getDepthAtPointBilateral(pointCloud, pointCloudTranslation, pointCloudOrientation, imageBuffer, u, v, displayRotation, colorCameraTranslation, colorCameraOrientation, colorCameraPoint);
		if (result == -1) {
			return null;
		} else {
			if (result != 0) {
				Tango.throwTangoExceptionIfNeeded(result);
			}

			return colorCameraPoint;
		}
	}

	public static double[] findCorrespondenceSimilarityTransform(double[][] srcPoints, double[][] destPoints) throws TangoInvalidException {
		double[] srcTdest = new double[16];
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.findCorrespondenceSimilarityTransform(srcPoints, destPoints, srcTdest));
		return srcTdest;
	}

	public static TangoSupport.DepthBuffer upsampleImageNearestNeighbor(com.test.atap.tangoservice.TangoPointCloudData pointCloud, int imageWidth, int imageHeight, TangoPoseData colorCameraTPointCloud) throws TangoInvalidException {
		ByteDepthBuffer byteDepthBuffer = new ByteDepthBuffer();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.upsampleImageNearestNeighbor(pointCloud, imageWidth, imageHeight, colorCameraTPointCloud, byteDepthBuffer));
		TangoSupport.DepthBuffer depthBuffer = new TangoSupport.DepthBuffer();
		depthBuffer.depths = byteDepthBuffer.depths.order(ByteOrder.nativeOrder()).asFloatBuffer();
		depthBuffer.height = byteDepthBuffer.height;
		depthBuffer.width = byteDepthBuffer.width;
		return depthBuffer;
	}

	public static TangoSupport.DepthBuffer upsampleImageBilateral(boolean approximate, com.test.atap.tangoservice.TangoPointCloudData pointCloud, TangoImageBuffer imageBuffer, TangoPoseData colorCameraTPointCloud) throws TangoInvalidException {
		ByteDepthBuffer byteDepthBuffer = new ByteDepthBuffer();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.upsampleImageBilateral(approximate, pointCloud, imageBuffer, colorCameraTPointCloud, byteDepthBuffer));
		TangoSupport.DepthBuffer depthBuffer = new TangoSupport.DepthBuffer();
		depthBuffer.depths = byteDepthBuffer.depths.order(ByteOrder.nativeOrder()).asFloatBuffer();
		depthBuffer.height = byteDepthBuffer.height;
		depthBuffer.width = byteDepthBuffer.width;
		return depthBuffer;
	}

	public static TangoSupport.TangoMatrixTransformData getMatrixTransformAtTime(double timestamp, int baseFrame, int targetFrame, int baseEngine, int targetEngine, int displayRotation) throws TangoInvalidException {
		TangoSupport.TangoMatrixTransformData matrixTransform = new TangoSupport.TangoMatrixTransformData();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.getMatrixTransformAtTime(timestamp, baseFrame, targetFrame, baseEngine, targetEngine, displayRotation, matrixTransform));
		return matrixTransform;
	}

	public static TangoSupport.TangoDoubleMatrixTransformData getDoubleMatrixTransformAtTime(double timestamp, int baseFrame, int targetFrame, int baseEngine, int targetEngine, int displayRotation) throws TangoInvalidException {
		TangoSupport.TangoDoubleMatrixTransformData matrixTransform = new TangoSupport.TangoDoubleMatrixTransformData();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.getDoubleMatrixTransformAtTime(timestamp, baseFrame, targetFrame, baseEngine, targetEngine, displayRotation, matrixTransform));
		return matrixTransform;
	}

	public static float[] transformPoint(float[] matrixTransform, float[] point) throws TangoInvalidException {
		float[] out = new float[3];
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.transformPoint(matrixTransform, point, out));
		return out;
	}

	public static void transformPose(float[] matrixTransform, float[] inTranslation, float[] inOrientation, float[] outTranslation, float[] outOrientation) throws TangoInvalidException {
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.transformPose(matrixTransform, inTranslation, inOrientation, outTranslation, outOrientation));
	}

	public static com.test.atap.tangoservice.TangoPointCloudData transformPointCloud(float[] matrixTransform, com.test.atap.tangoservice.TangoPointCloudData inputCloud) throws TangoInvalidException {
		com.test.atap.tangoservice.TangoPointCloudData outputCloud = new com.test.atap.tangoservice.TangoPointCloudData();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.transformPointCloud(matrixTransform, inputCloud, outputCloud));
		return outputCloud;
	}

	public static double[] doubleTransformPoint(double[] matrixTransform, double[] point) throws TangoInvalidException {
		double[] out = new double[3];
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.doubleTransformPoint(matrixTransform, point, out));
		return out;
	}

	public static void doubleTransformPose(double[] matrixTransform, double[] inTranslation, double[] inOrientation, double[] outTranslation, double[] outOrientation) throws TangoInvalidException {
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.doubleTransformPose(matrixTransform, inTranslation, inOrientation, outTranslation, outOrientation));
	}

	public static com.test.atap.tangoservice.TangoPointCloudData doubleTransformPointCloud(double[] matrixTransform, com.test.atap.tangoservice.TangoPointCloudData inputCloud) throws TangoInvalidException {
		com.test.atap.tangoservice.TangoPointCloudData outputCloud = new TangoPointCloudData();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.doubleTransformPointCloud(matrixTransform, inputCloud, outputCloud));
		return outputCloud;
	}

	public static float[] getVideoOverlayUVBasedOnDisplayRotation(float[] uvCoordinates, int displayRotation) {
		float[] outputUvCoordinates = new float[8];
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.getVideoOverlayUVBasedOnDisplayRotation(uvCoordinates, displayRotation, outputUvCoordinates));
		return outputUvCoordinates;
	}

	public static TangoCameraIntrinsics getCameraIntrinsicsBasedOnDisplayRotation(int cameraId, int displayRotation) {
		TangoCameraIntrinsics cameraIntrinsics = new TangoCameraIntrinsics();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.getCameraIntrinsicsBasedOnDisplayRotation(cameraId, displayRotation, cameraIntrinsics));
		return cameraIntrinsics;
	}

	public static List<Marker> detectMarkers(TangoImageBuffer imageBuffer, int cameraId, double[] translation, double[] orientation, TangoSupport.MarkerParam param) throws TangoErrorException, TangoInvalidException {
		List<TangoSupport.Marker> markerList = new ArrayList();
		Tango.throwTangoExceptionIfNeeded(TangoSupportJNIInterface.detectMarkers(imageBuffer, cameraId, translation, orientation, param, markerList));
		return markerList;
	}

	public interface TangoSupportCallbacks {
		TangoPoseData getPoseAtTime(double var1, TangoCoordinateFramePair var3);

		TangoCameraIntrinsics getCameraIntrinsics(int var1);
	}

	public static class Marker {
		public int type;
		public double timestamp;
		public String content;
		public float[][] corners2d = new float[4][2];
		public float[][] corners3d = new float[4][3];
		public double[] translation = new double[3];
		public double[] orientation = new double[4];

		public Marker() {
		}
	}

	public static class MarkerParam {
		public int type;
		public double markerSize;

		public MarkerParam() {
		}
	}

	public static class IntersectionPointPlaneModelPair {
		public double[] intersectionPoint;
		public double[] planeModel;

		public IntersectionPointPlaneModelPair() {
		}

		public IntersectionPointPlaneModelPair(double[] intersectionPoint, double[] planeModel) {
			this.intersectionPoint = intersectionPoint;
			this.planeModel = planeModel;
		}
	}

	public static class DepthBuffer {
		public FloatBuffer depths;
		public int height;
		public int width;

		public DepthBuffer() {
		}
	}

	public static class TangoDoubleMatrixTransformData {
		public double timestamp;
		public double[] matrix;
		public int statusCode;

		public TangoDoubleMatrixTransformData() {
		}
	}

	public static class TangoMatrixTransformData {
		public double timestamp;
		public float[] matrix;
		public int statusCode;

		public TangoMatrixTransformData() {
		}
	}
}
