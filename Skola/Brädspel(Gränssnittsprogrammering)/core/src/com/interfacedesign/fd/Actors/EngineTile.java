package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EngineTile extends PieceTile {
    //creates a engine tile with direction 1 or 2
    public EngineTile(int direction){
        super();
        this.direction = direction;
        if (direction == 2){
            setBackTile(new Texture(Gdx.files.internal("partThreeTileEW.png")));
        }
        if (direction == 1){
            setBackTile(new Texture(Gdx.files.internal("partThreeTileNS.png")));
        }
    }
}
