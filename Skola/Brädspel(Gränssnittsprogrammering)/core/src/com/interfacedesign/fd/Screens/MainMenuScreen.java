package com.interfacedesign.fd.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.interfacedesign.fd.Game;


public class MainMenuScreen implements Screen{

    protected Stage stage;
    private Texture img;
    private Game parent;

    //Creates a mainmenu screen
    public MainMenuScreen(Game game) {
        parent = game;
        img = new Texture("forbiddendesert-cover.jpg");
        stage = new Stage(new ScreenViewport());
    }

    //shows the main menu screen
    @Override
    public void show() {
        stage.clear();
        Skin skin = new Skin(Gdx.files.internal("rusty-robot/skin/rusty-robot-ui.json"));
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);

        Table buttonTable = new Table(skin);
        //create all the buttons
        TextButton startButton = new TextButton(Game.text.get("startButton"),skin);
        TextButton tutorialButton = new TextButton(Game.text.get("tutorialButton"), skin);
        TextButton exitButton = new TextButton(Game.text.get("exitButton"),skin);
        TextButton preferenceButton = new TextButton(Game.text.get("preferenceButton"),skin);

        //adds actions to all the buttons
        preferenceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Game.PREFERENCESCREEN);
            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Game.STARTSCREEN);
            }
        });

        tutorialButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Game.TUTORIALSCREEN);
            }
        });

        exitButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event,Actor actor){
                Gdx.app.exit();
            }
        });

        //adds all the buttons to a table and adds the table to the stage
        buttonTable.add(startButton).fillX().uniformX();
        buttonTable.row().pad(10,0,10,0);
        buttonTable.add(tutorialButton).fillX().uniformX();
        buttonTable.row();
        buttonTable.add(preferenceButton).fillX().uniformX();
        buttonTable.row();
        buttonTable.add(exitButton).fillX().uniformX();
        mainTable.add(buttonTable).width(stage.getWidth()/2);

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

    //rezie the screen
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
        stage.dispose();
    }
}
