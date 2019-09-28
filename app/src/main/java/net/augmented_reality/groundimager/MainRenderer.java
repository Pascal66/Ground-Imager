package net.augmented_reality.groundimager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

//import com.google.atap.tangoservice.TangoPoseData;
//import com.projecttango.tangosupport.TangoSupport;

//import com.test.atap.tangoservice.TangoPoseData;
//import com.test.tango.TangoSupport;

import com.google.atap.tangoservice.TangoPoseData;
import com.projecttango.tangosupport.TangoSupport;
//import com.google.tango.support.TangoSupport;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.methods.SpecularMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.Matrix4;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.ScreenQuad;
import org.rajawali3d.renderer.Renderer;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by noethen on 14.07.17.
 */

public class MainRenderer extends Renderer {
    private static final String TAG = MainRenderer.class.getSimpleName();

    private static final Matrix4 DEPTH_T_OPENGL = new Matrix4(new float[] {
            1.0f,  0.0f, 0.0f, 0.0f,
            0.0f,  0.0f, 1.0f, 0.0f,
            0.0f, -1.0f, 0.0f, 0.0f,
            0.0f,  0.0f, 0.0f, 1.0f
    });

    private float[] textureCoords0 = new float[]{0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F};

    // Augmented Reality related fields.
    private ATexture mTangoCameraTexture;
    private boolean mSceneCameraConfigured;

    private Object3D mObjectObject3D;
    private Object3D mSearchObject3D;
    private Matrix4 mObjectTransform;
    private boolean mObjectPoseUpdated = false;

    public double mFrequency;
    public boolean mNewObjectSignal = false;
    public boolean mNewSearchArea = false;

    private boolean mNewObject = false;
    private boolean mNewSearch = false;
    public double mMinObjectDistance;
    public double mMinSearchDistance;
    private Vector3 mLastObjectVector;
    private Vector3 mLastSearchVector;

    public int mMaxFrequencyDark;
    public int mMaxFrequencyGreen;

    private ScreenQuad mBackgroundQuad;


    public MainRenderer(Context context) {
        super(context);
    }

    @Override
    protected void initScene() {
        // Create a quad covering the whole background and assign a texture to it where the
        // Tango color camera contents will be rendered.
        if (mBackgroundQuad == null) {
            mBackgroundQuad = new ScreenQuad();
            mBackgroundQuad.getGeometry().setTextureCoords(textureCoords0);
        }
        Material tangoCameraMaterial = new Material();
        tangoCameraMaterial.setColorInfluence(0);
        // We need to use Rajawali's {@code StreamingTexture} since it sets up the texture
        // for GL_TEXTURE_EXTERNAL_OES rendering.
        mTangoCameraTexture =
                new StreamingTexture("camera", (StreamingTexture.ISurfaceListener) null);
        try {
            tangoCameraMaterial.addTexture(mTangoCameraTexture);
            mBackgroundQuad.setMaterial(tangoCameraMaterial);
        } catch (ATexture.TextureException e) {
            Log.e(TAG, "Exception creating texture for RGB camera contents", e);
        }
        getCurrentScene().addChildAt(mBackgroundQuad, 0);

        // Add a directional light in an arbitrary direction.
        //TODO Check Parameter
        DirectionalLight light = new DirectionalLight(1, 0.2, -1);
        light.setColor(1, 1, 1);
        //TODO Light Power
        light.setPower(1.8f);
        //TODO Check
        light.setPosition(3, 2, 4);
         getCurrentScene().addLight(light);
    }

    /**
     * Update background texture's UV coordinates when device orientation is changed (i.e., change
     * between landscape and portrait mode.
     * This must be run in the OpenGL thread.
     */
    public void updateColorCameraTextureUvGlThread(int rotation) {
        if (mBackgroundQuad == null) {
            mBackgroundQuad = new ScreenQuad();
        }

        float[] textureCoords =
                TangoSupport.getVideoOverlayUVBasedOnDisplayRotation(textureCoords0, rotation);
        mBackgroundQuad.getGeometry().setTextureCoords(textureCoords, true);
        mBackgroundQuad.getGeometry().reload();
    }

    @Override
    protected void onRender(long elapsedRealTime, double deltaTime) {
        // Update the AR object if necessary.
        // Synchronize against concurrent access with the setter below.
        synchronized (this) {
            if (mObjectPoseUpdated && (mNewObjectSignal||mNewSearchArea)) {

                //TODO Object Vector
                if (mNewObjectSignal) {

                    Vector3 vector = mObjectTransform.getTranslation();
                    Log.e("*************** ", "**************");
                    Log.e("Object Vector ", vector.toString());
                    Log.e("*************** ", "**************");
                    Log.e("Frequency ", Double.valueOf(mFrequency).toString());
                    Log.e("*************** ", "**************");

                    if (mLastObjectVector == null) {
                        mLastObjectVector = vector;
                        mNewObject = true;
                    } else {
                        double distance = mLastObjectVector.distanceTo2(vector);
                        Log.e("*************** ", "**************");
                        Log.e("Object Distance ", Double.valueOf(distance).toString());
                        Log.e("*************** ", "**************");

                        if (distance > mMinObjectDistance) {
                            mNewObject = true;
                            mLastObjectVector = vector;
                        } else {
                            mNewObject = false;
                            Log.e("*************** ", "**************");
                            Log.e("No Object placed ", "No Object placed");
                            Log.e("*************** ", "**************");
                        }
                        mLastObjectVector = vector;
                    }
                }

                if (mNewSearchArea) {

                    Vector3 vector = mObjectTransform.getTranslation();
                    Log.e("*************** ", "**************");
                    Log.e("Search Vector ", vector.toString());

                    Log.e("*************** ", "**************");
                    if (mLastSearchVector == null) {
                        mLastSearchVector = vector;
                        mNewSearch = true;
                    } else {
                        double distance = mLastSearchVector.distanceTo2(vector);
                        Log.e("*************** ", "**************");
                        Log.e("Search Distance ", Double.valueOf(distance).toString());
                        Log.e("*************** ", "**************");

                        if (distance > mMinSearchDistance) {
                            mNewSearch = true;
                            mLastSearchVector = vector;
                        } else {
                            mNewSearch = false;
                            Log.e("*************** ", "**************");
                            Log.e("No Search placed ", "No Search placed");
                            Log.e("*************** ", "**************");
                        }
                        mLastSearchVector = vector;
                    }
                }

                //Add Object
                if (mNewObject) {

                    //TODO Object Material
                    Material material = new Material();
                    if (mFrequency <= mMaxFrequencyDark) {
                        material.setColor(Color.DKGRAY);
                        Log.e("*************** ", "**************");
                        Log.e("Color ", "Gray");
                        Log.e("*************** ", "**************");
                    } else if (mFrequency <= mMaxFrequencyGreen) {
                        material.setColor(Color.GREEN);
                        Log.e("Color ", "Green");
                    } else {
                        material.setColor(Color.RED);
                        Log.e("Color ", "Red");
                    }
                    material.enableLighting(true);
                    material.setDiffuseMethod(new DiffuseMethod.Lambert());
                    material.setSpecularMethod(new SpecularMethod.Phong());

                    LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.signal_obj);

                    try {
                        objParser.parse();
                    } catch (ParsingException e) {
                        e.printStackTrace();
                    }
                    mObjectObject3D = objParser.getParsedObject();
                    mObjectObject3D.setMaterial(material);
                    mObjectObject3D.setBlendingEnabled(true);
                    mObjectObject3D.setTransparent(true);

                    // Place the 3D object in the location of the detected plane.
                    mObjectObject3D.setPosition(mObjectTransform.getTranslation());
                    // Note that Rajawali uses left-hand convention for Quaternions so we need to
                    // specify a quaternion with rotation in the opposite direction.
                    mObjectObject3D.setOrientation(new Quaternion().fromMatrix(mObjectTransform));
                    // Move it forward by half of the size of the cube to make it
                    // flush with the plane surface.
                    mObjectPoseUpdated = false;
                    getCurrentScene().addChild(mObjectObject3D);
                }

                //Add Search
                if (mNewSearch) {

                    //TODO Search Material
                    Material material = new Material();

                    try {
                        Texture t = new Texture("pattern", R.drawable.red_transparent);
                        material.addTexture(t);
                    } catch (ATexture.TextureException e) {
                        e.printStackTrace();
                    }
                    material.setColorInfluence(0f);
                    //material.enableLighting(true);
                    material.setDiffuseMethod(new DiffuseMethod.Lambert());

                    LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.search_obj);

                    try {
                        objParser.parse();
                    } catch (ParsingException e) {
                        e.printStackTrace();
                    }
                    mSearchObject3D = objParser.getParsedObject();
                    mSearchObject3D.setMaterial(material);
                    mSearchObject3D.setBlendingEnabled(true);
                    mSearchObject3D.setTransparent(true);

                    // Place the 3D object in the location of the detected plane.
                    mSearchObject3D.setPosition(mObjectTransform.getTranslation());
                    // Note that Rajawali uses left-hand convention for Quaternions so we need to
                    // specify a quaternion with rotation in the opposite direction.
                    mSearchObject3D.setOrientation(new Quaternion().fromMatrix(mObjectTransform));
                    // Move it forward by half of the size of the cube to make it
                    // flush with the plane surface.
                    mObjectPoseUpdated = false;
                    getCurrentScene().addChild(mSearchObject3D);
                }
                mNewObjectSignal = false;
                mNewSearchArea = false;
                mNewSearch = false;
                mNewObject = false;
            }
        }
        super.onRender(elapsedRealTime, deltaTime);
    }

    /**
     * Save the updated plane fit pose to update the AR object on the next render pass.
     * This is synchronized against concurrent access in the render loop above.
     */
    public synchronized void updateObjectPose(
            float[] openglTDepthArr,
            float[] mDepthTPlaneArr) {
        Matrix4 openglTDepth = new Matrix4(openglTDepthArr);
        Matrix4 openglTPlane =
                openglTDepth.multiply(new Matrix4(mDepthTPlaneArr));

        mObjectTransform = openglTPlane.multiply(DEPTH_T_OPENGL);
        mObjectPoseUpdated = true;
    }

    /**
     * Update the scene camera based on the provided pose in Tango start of service frame.
     * The camera pose should match the pose of the camera color at the time of the last rendered
     * RGB frame, which can be retrieved with this.getTimestamp();
     * <p/>
     * NOTE: This must be called from the OpenGL render thread; it is not thread safe.
     */
    public void updateRenderCameraPose(TangoPoseData cameraPose) {
        float[] rotation = cameraPose.getRotationAsFloats();
        float[] translation = cameraPose.getTranslationAsFloats();
        Quaternion quaternion = new Quaternion(rotation[3], rotation[0], rotation[1], rotation[2]);
        // Conjugating the Quaternion is needed because Rajawali uses left-handed convention for
        // quaternions.
        getCurrentCamera().setRotation(quaternion.conjugate());
        getCurrentCamera().setPosition(translation[0], translation[1], translation[2]);
    }

    /**
     * It returns the ID currently assigned to the texture where the Tango color camera contents
     * should be rendered.
     * NOTE: This must be called from the OpenGL render thread; it is not thread safe.
     */
    public int getTextureId() {
        return mTangoCameraTexture == null ? -1 : mTangoCameraTexture.getTextureId();
    }

    /**
     * We need to override this method to mark the camera for re-configuration (set proper
     * projection matrix) since it will be reset by Rajawali on surface changes.
     */
    @Override
    public void onRenderSurfaceSizeChanged(GL10 gl, int width, int height) {
        super.onRenderSurfaceSizeChanged(gl, width, height);
        mSceneCameraConfigured = false;
    }

    public boolean isSceneCameraConfigured() {
        return mSceneCameraConfigured;
    }

    /**
     * Sets the projection matrix for the scene camera to match the parameters of the color camera,
     * provided by the {@code TangoCameraIntrinsics}.
     */
    public void setProjectionMatrix(float[] matrix) {
        getCurrentCamera().setProjectionMatrix(new Matrix4(matrix));
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset,
                                 float xOffsetStep, float yOffsetStep,
                                 int xPixelOffset, int yPixelOffset) {
    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}