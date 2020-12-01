package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class WellTile extends Tile {
    boolean dry = false;
    //creates a well tile can be dry
    public WellTile(boolean dry){
        super();
        this.dry = dry;
        setFrontTile(new Texture(Gdx.files.internal("oasisTile.png")));
        setBackTile(new Texture(Gdx.files.internal("wellTile.png")));
        if (dry){
            setBackTile(new Texture(Gdx.files.internal("crackedTile.png")));
        }
    }

    //return if tile is dry
    public boolean isDry() {
        return dry;
    }
}
