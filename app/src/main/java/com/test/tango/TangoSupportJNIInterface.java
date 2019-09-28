package com.test.tango;

import com.test.atap.tangoservice.experimental.TangoImageBuffer;
import com.test.atap.tangoservice.TangoCameraIntrinsics;
import com.test.atap.tangoservice.TangoPointCloudData;
import com.test.atap.tangoservice.TangoPoseData;

import java.util.List;

class TangoSupportJNIInterface {
	public static int doubleTransformPointCloud(double[] matrixTransform, com.test.atap.tangoservice.TangoPointCloudData inputCloud, com.test.atap.tangoservice.TangoPointCloudData outputCloud) {
		return 1;}

	public static int getVideoOverlayUVBasedOnDisplayRotation(float[] uvCoordinates, int displayRotation, float[] outputUvCoordinates) {
		return 1;}

	public static int getCameraIntrinsicsBasedOnDisplayRotation(int cameraId, int displayRotation, TangoCameraIntrinsics cameraIntrinsics) {
		return 1;}

	public static int detectMarkers(TangoImageBuffer imageBuffer, int cameraId, double[] translation, double[] orientation, TangoSupport.MarkerParam param, List<TangoSupport.Marker> markerList) {
		return 1;}

	public static int transformPose(float[] matrixTransform, float[] inTranslation, float[] inOrientation, float[] outTranslation, float[] outOrientation) {
		return 1;}

	public static int transformPointCloud(float[] matrixTransform, com.test.atap.tangoservice.TangoPointCloudData inputCloud, com.test.atap.tangoservice.TangoPointCloudData outputCloud) {
		return 1;}

	public static int doubleTransformPoint(double[] matrixTransform, double[] point, double[] out) {
		return 1;}

	public static int doubleTransformPose(double[] matrixTransform, double[] inTranslation, double[] inOrientation, double[] outTranslation, double[] outOrientation) {
		return 1;}

	public static int getMatrixTransformAtTime(double timestamp, int baseFrame, int targetFrame, int baseEngine, int targetEngine, int displayRotation, TangoSupport.TangoMatrixTransformData matrixTransform) {
		return 1;}

	public static int getDoubleMatrixTransformAtTime(double timestamp, int baseFrame, int targetFrame, int baseEngine, int targetEngine, int displayRotation, TangoSupport.TangoDoubleMatrixTransformData matrixTransform) {
		return 1;}

	public static int transformPoint(float[] matrixTransform, float[] point, float[] out) {
		return 1;}

	public static int upsampleImageBilateral(boolean approximate, com.test.atap.tangoservice.TangoPointCloudData pointCloud, TangoImageBuffer imageBuffer, com.test.atap.tangoservice.TangoPoseData colorCameraTPointCloud, ByteDepthBuffer byteDepthBuffer) {
		return 1;}

	public static int upsampleImageNearestNeighbor(com.test.atap.tangoservice.TangoPointCloudData pointCloud, int imageWidth, int imageHeight, com.test.atap.tangoservice.TangoPoseData colorCameraTPointCloud, ByteDepthBuffer byteDepthBuffer) {
		return 1;}

	public static int findCorrespondenceSimilarityTransform(double[][] srcPoints, double[][] destPoints, double[] srcTdest) {
		return 1;}

	public static int getDepthAtPointBilateral(com.test.atap.tangoservice.TangoPointCloudData pointCloud, double[] pointCloudTranslation, double[] pointCloudOrientation, TangoImageBuffer imageBuffer, float u, float v, int displayRotation, double[] colorCameraTranslation, double[] colorCameraOrientation, float[] colorCameraPoint) {
		return 1;}

	public static int getPoseAtTime(double timestamp, int baseFrame, int targetFrame, int baseEngine, int targetEngine, int rotationIndex, com.test.atap.tangoservice.TangoPoseData enginePose) {
		return 1;}

	public static int getDepthAtPointNearestNeighbor(com.test.atap.tangoservice.TangoPointCloudData pointCloud, double[] pointCloudTranslation, double[] pointCloudOrientation, float u, float v, int displayRotation, double[] colorCameraTranslation, double[] colorCameraOrientation, float[] colorCameraPoint) {
		return 1;}

	public static int initializeWithCallbacks(TangoSupport.TangoSupportCallbacks callbacks) {
		return 1;}

	public static int fitPlaneModelNearPoint(TangoPointCloudData pointCloud, double[] pointCloudTranslation, double[] pointCloudOrientation, float u, float v, int displayRotation, double[] colorCameraTranslation, double[] colorCameraOrientation, double[] intersectionPoint, double[] planeModel) {
		return 1;}

	public static int calculateRelativePose(double baseTimestamp, int baseFrame, double targetTimestamp, int targetFrame, TangoPoseData baseFrameTTargetFrame) {
		return 1;}
}
