package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by john on 02/12/17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    // (4) Create SettingsFragment and extend PreferenceFragmentCompat

    // Do steps 5 - 11 within SettingsFragment
    // (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

    // (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference
    private void setPreferenceSummary(Preference preference, Object value) {
        String valueString = value != null ? value.toString() : "";
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int indexOfValue = listPreference.findIndexOfValue(valueString);
            if (indexOfValue > -1) {
                String label = (String) listPreference.getEntries()[indexOfValue];
                preference.setSummary(label);
            }
        } else {
            preference.setSummary(valueString);
        }
    }

    // (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource

    /**
     * Called during {@link #onCreate(Bundle)} to supply the preferences for this fragment.
     * Subclasses are expected to call {@link #setPreferenceScreen(PreferenceScreen)} either
     * directly or via helper methods such as {@link #addPreferencesFromResource(int)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the
     *                           {@link PreferenceScreen} with this key.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_screen);


        // Do step 9 within onCreatePreference
        // (9) Set the preference summary on each preference that isn't a CheckBoxPreference
        int preferenceCount = getPreferenceScreen().getPreferenceCount();
        for (int i = 0; i < preferenceCount; i ++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (!(preference instanceof CheckBoxPreference)) {
                String value = getPreferenceScreen().getSharedPreferences().getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    // (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop
    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    // (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart
    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    // (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null && !(preference instanceof CheckBoxPreference)) {
            String summary = sharedPreferences.getString(preference.getKey(), "");
            setPreferenceSummary(preference, summary);
        }
    }
}
