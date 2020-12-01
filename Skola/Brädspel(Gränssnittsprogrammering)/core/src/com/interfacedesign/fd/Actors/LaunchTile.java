package com.interfacedesign.fd.Actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LaunchTile extends Tile {
    //creates a launch tile
    public LaunchTile(){
        super();
        setBackTile(new Texture(Gdx.files.internal("launchingPadTile.png")));
    }
}
