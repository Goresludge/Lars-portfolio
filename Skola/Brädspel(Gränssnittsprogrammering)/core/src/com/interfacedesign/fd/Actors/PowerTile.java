package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PowerTile extends PieceTile {
    //creates a powertile with direction 1 or 2
    public PowerTile(int direction){
        super();
        this.direction = direction;
        if (direction == 2){
            setBackTile(new Texture(Gdx.files.internal("partTwoTileEW.png")));
        }
        if (direction == 1){
            setBackTile(new Texture(Gdx.files.internal("partTwoTileNS.png")));
        }
    }
}
