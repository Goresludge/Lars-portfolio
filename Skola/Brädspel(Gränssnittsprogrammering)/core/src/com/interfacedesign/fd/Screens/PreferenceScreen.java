package com.interfacedesign.fd.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.interfacedesign.fd.Game;

public class PreferenceScreen implements Screen{

    private Game parent;
    private Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Label fullScreenLabel;
    private Label resolutionLabel;
    private Texture img;

    //creates a preference screen
    public PreferenceScreen(Game game){
        parent = game;
        img = new Texture("forbiddendesert-cover.jpg");

        stage = new Stage(new ScreenViewport());

    }

    //shows the screen
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        Skin skin = new Skin(Gdx.files.internal("rusty-robot/skin/rusty-robot-ui.json"));
        Table table = new Table(skin);
        table.setBackground("list");
        mainTable.add(table);

        //full screen checkbox
        final CheckBox fullScreenCheckbox = new CheckBox(null, skin);
        fullScreenCheckbox.setChecked( parent.getPreferences().isFullscreenEnabled() );
        fullScreenCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean enabled = fullScreenCheckbox.isChecked();
                parent.getPreferences().setFullscreenEnabled( enabled );
                parent.setFullscreen(enabled);
            }
        });

        //create the selection boc for resolutions
        final SelectBox<String> resolutionSelectBox = new SelectBox(skin);
        String[] resolution = new String[4];
        resolution[0] = "1440 x 900";
        resolution[1] = "1024 x 768";
        resolution[2] = "800 x 600";
        resolution[3] = "640 x 480";
        resolutionSelectBox.setItems(resolution);
        resolutionSelectBox.setSelectedIndex(parent.getPreferences().getSelectedResolution());
        resolutionSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setResolution(resolutionSelectBox.getSelectedIndex());
                parent.setResolution(resolutionSelectBox.getSelectedIndex());
            }
        });

        //creates the volume music slider
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( parent.getPreferences().getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                parent.setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });
        //creates the volume sound slider
        final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeSoundSlider.setValue( parent.getPreferences().getSoundVolume() );
        volumeSoundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume( volumeSoundSlider.getValue() );
                return false;
            }
        });

        //creates the music enable checkbox
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled( enabled );
                parent.setMusic(enabled);
                return false;
            }
        });

        //creates the sound enabled checkbox
        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked( parent.getPreferences().isSoundEffectsEnabled() );
        soundCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled( enabled );
                return false;
            }
        });

        //creates back button
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Game.MENU);
            }
        });

        //create labels for all the options
        titleLabel = new Label( "Preferences", skin );
        fullScreenLabel = new Label("Fullscreen",skin);
        resolutionLabel = new Label("Screen size",skin);
        volumeMusicLabel = new Label( "Music volume", skin );
        volumeSoundLabel = new Label( "Effects volume", skin );
        musicOnOffLabel = new Label( "Mute music on/off", skin );
        soundOnOffLabel = new Label( "Mute sound on/off", skin );

        //adds and formats all the components in a table
        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(fullScreenLabel).left();
        table.add(fullScreenCheckbox);
        table.row().pad(10,0,0,10);
        table.add(resolutionLabel).left();
        table.add(resolutionSelectBox);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);
        table.row().pad(10,0,0,10);
        table.add(soundOnOffLabel).left();
        table.add(soundCheckbox);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);
    }

    //render the stage
    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.getBatch().begin();
        stage.getBatch().draw(img,0,0, Game.GAMEWIDTH,Game.GAMEHEIGHT);
        stage.getBatch().end();
        stage.draw();
    }

    //resize the screen
    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width,height,true);
        Gdx.graphics.setWindowedMode(width,height);
        Game.GAMEHEIGHT = height;
        Game.GAMEWIDTH = width;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
