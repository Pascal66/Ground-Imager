package net.augmented_reality.groundimager.audio.core;

/**
 * Created by noethen on 14.07.17.
 */

public interface Callback {
    void onBufferAvailable(byte[] buffer);
}