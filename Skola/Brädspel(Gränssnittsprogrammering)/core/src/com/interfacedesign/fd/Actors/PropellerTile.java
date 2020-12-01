package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PropellerTile extends PieceTile {
    //creates a propeller tile with direction one or 2
    public PropellerTile(int direction){
        super();
        this.direction = direction;
        if (direction == 2){
            setBackTile(new Texture(Gdx.files.internal("partFourTileEW.png")));
        }
        if (direction == 1){
            setBackTile(new Texture(Gdx.files.internal("partFourTileNS.png")));
        }
    }
}
