package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TunnelTile extends Tile {

    //creates a tunnel tile
    public TunnelTile(){
        super();
        setBackTile(new Texture(Gdx.files.internal("dontTile.png")));

    }


}
