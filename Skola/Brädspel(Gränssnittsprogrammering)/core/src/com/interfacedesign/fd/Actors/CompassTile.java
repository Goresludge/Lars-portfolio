package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class CompassTile extends PieceTile {

    //Creates a compass tile with direction 1 or 2
    public CompassTile(int direction){
        super();
        this.direction = direction;
        setBackTile(new Texture(Gdx.files.internal("partOneTile.png")));
        if (direction == 2){
            rotateTile();
        }
    }


}

