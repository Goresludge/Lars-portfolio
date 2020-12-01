package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class StartTile extends Tile {
    //creates a start tile(crash site)
    public StartTile(){
        super();
        setBackTile(new Texture(Gdx.files.internal("itemTileV1.png")));
        setFrontTile(new Texture(Gdx.files.internal("startTile.png")));
    }
}
