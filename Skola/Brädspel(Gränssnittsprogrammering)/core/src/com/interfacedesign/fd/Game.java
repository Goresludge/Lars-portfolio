package com.interfacedesign.fd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.interfacedesign.fd.Screens.GameScreen;
import com.interfacedesign.fd.Screens.LoadingScreen;
import com.interfacedesign.fd.Screens.MainMenuScreen;
import com.interfacedesign.fd.Screens.PreferenceScreen;
import com.interfacedesign.fd.Screens.StartScreen;
import com.interfacedesign.fd.Screens.TutorialScreen;

import javax.swing.*;
import java.util.Locale;

//main game class
public class Game extends com.badlogic.gdx.Game {

	public static int GAMEWIDTH = 800;
	public static int GAMEHEIGHT = 600;

	public static int MAX_SAND = 48;

	private AppPreferences preferences;
	private Music bgMusic;
	private Sound buttonSound;
	private com.interfacedesign.fd.Screens.LoadingScreen loadingScreen;
	private com.interfacedesign.fd.Screens.StartScreen startScreen;
	private com.interfacedesign.fd.Screens.MainMenuScreen mainMenuScreen;
	private com.interfacedesign.fd.Screens.TutorialScreen tutorialScreen;
	private com.interfacedesign.fd.Screens.GameScreen gameScreen;
	private com.interfacedesign.fd.Screens.PreferenceScreen preferenceScreen;
	private Graphics.DisplayMode displayMode;

	public final static int MENU = 0;
	public final static int STARTSCREEN = 1;
	public final static int GAMESCREEN = 2;
	public final static int TUTORIALSCREEN = 3;
	public final static int PREFERENCESCREEN = 4;

	private String[] players;

	public static I18NBundle text;

	//changes the screen, creates ned screen only if screen is not already created
	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(this);
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
				this.setScreen(mainMenuScreen);
				break;
			case STARTSCREEN:
				if(startScreen == null) startScreen = new StartScreen(this);
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
				this.setScreen(startScreen);
				break;
			case GAMESCREEN:
				if(gameScreen == null) gameScreen = new GameScreen(this);
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
				this.setScreen(gameScreen);
				break;
			case TUTORIALSCREEN:
				if(tutorialScreen == null) tutorialScreen = new TutorialScreen(this);
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
				this.setScreen(tutorialScreen);
				break;
			case PREFERENCESCREEN:
				if(preferenceScreen == null) preferenceScreen = new PreferenceScreen(this);
				if(preferences.isSoundEffectsEnabled()){
				    buttonSound.play(preferences.getSoundVolume());
                }
				this.setScreen(preferenceScreen);
				break;
		}
	}

	//set player string array
	public void setPlayers(String[] players) {
		this.players = players;
	}

	//get player string array
	public String[] getPlayers() {
		return players;
	}

	//get prefs
	public AppPreferences getPreferences(){
		return this.preferences;
	}

	//set music volume
	public void setMusicVolume(float volume){
	    bgMusic.setVolume(volume);
    }



    //set resolution to selection
    public void setResolution(int selection){
	    if(!getPreferences().isFullscreenEnabled()){
            int x = 0;
            int y = 0;
            switch (selection){
                case 0: x = 1440; y = 900;
                    break;
                case 1: x = 1024; y = 768;
                    break;
                case 2: x = 800; y = 600;
                    break;
                case 3: x = 640; y = 480;
                    break;
            }
            Gdx.graphics.setWindowedMode(x,y);
            GAMEWIDTH = x;
            GAMEHEIGHT = y;
        }
    }

    //set fullscreen if enabled
    public void setFullscreen(boolean fullscreen){
	    if(fullscreen){
	    	displayMode = Gdx.graphics.getDisplayMode();
	        GAMEHEIGHT = displayMode.height;
	        GAMEWIDTH = displayMode.width;
	        Gdx.graphics.setFullscreenMode(displayMode);
        }
        else{
	        setResolution(preferences.getSelectedResolution());
        }

    }

    //set music if enabled
    public void setMusic(boolean musicOn){
        if(musicOn){
            bgMusic.play();
            bgMusic.setLooping(true);
        }
        else{
            bgMusic.pause();
        }
    }

    //creates the main game class and starts music if enabled
    @Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		preferences = new AppPreferences();
		setFullscreen(getPreferences().isFullscreenEnabled());
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("menu_music.mp3"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("general_button_sound.wav"));
        float currMusicVol = preferences.getMusicVolume();
        if(preferences.isMusicEnabled()){
            bgMusic.play();
            bgMusic.setLooping(true);
        }
        bgMusic.setVolume(currMusicVol);
		this.setScreen(loadingScreen);

		FileHandle baseFileHandle = Gdx.files.internal("i18n/Text");
		text = I18NBundle.createBundle(baseFileHandle);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}

