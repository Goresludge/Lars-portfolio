package com.interfacedesign.fd.Screens;

import com.badlogic.gdx.Screen;
import com.interfacedesign.fd.Game;

public class LoadingScreen implements Screen {

    private Game parent;

    public LoadingScreen(Game game){
        parent = game;
    }




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        parent.changeScreen(Game.MENU);
    }

    @Override
    public void resize(int width, int height) {

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
