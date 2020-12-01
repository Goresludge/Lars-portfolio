package com.interfacedesign.fd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

//saving and getting preferences
public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "forbidden.desert";
    private static final String PREF_FULLSCREEN_ENABLED = "fullscreen.enabled";
    private static final String PREF_SELECTED_RESOLUTION = "resolution";

    //get the prefef from storage
    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    //get pref fullscreen enabled
    public boolean isFullscreenEnabled(){
        return getPrefs().getBoolean(PREF_FULLSCREEN_ENABLED, false);
    }
    //set pre fullscreen to enabled
    public void setFullscreenEnabled(boolean fullscreenEnabled) {
        getPrefs().putBoolean(PREF_FULLSCREEN_ENABLED, fullscreenEnabled);
        getPrefs().flush();
    }

    //get pref resolution
    public int getSelectedResolution() {
        return getPrefs().getInteger(PREF_SELECTED_RESOLUTION,3);
    }

    //set pref resolution
    public void setResolution(int selectedIndex) {
        getPrefs().putInteger(PREF_SELECTED_RESOLUTION,selectedIndex);
    }

    //get pref soundeffect enabled
    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    //set sound effect pref enabled
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    //get pref music enabled
    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    //set pref music enabled
    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    //get music volume pref
    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    //set music volume pref
    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    //get sound volume pref
    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    //set sound volume pref
    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

}