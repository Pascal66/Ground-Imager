package net.augmented_reality.groundimager;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by noethen on 21.07.17.
 */

public class SettingsFragment extends PreferenceFragmentCompat{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_ground_imager);
    }
}
