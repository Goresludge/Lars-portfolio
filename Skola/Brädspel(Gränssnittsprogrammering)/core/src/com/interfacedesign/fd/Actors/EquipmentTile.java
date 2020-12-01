package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EquipmentTile extends Tile {
    int type = 0;

    //create a equipment tile with specified type(picture)
    public EquipmentTile(int type){
        super();
        this.type = type;
        setType();
    }

    //sets sprite to correct type
    public void setType() {
        switch (type){
            case 1:
                setBackTile(new Texture(Gdx.files.internal("itemTileV1.png")));
                break;
            case 2:
                setBackTile(new Texture(Gdx.files.internal("itemTileV2.png")));
                break;
            case 3:
                setBackTile(new Texture(Gdx.files.internal("itemTileV3.png")));
                break;
            case 4:
                setBackTile(new Texture(Gdx.files.internal("itemTileV4.png")));
                break;
            case 5:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            case 6:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            case 7:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            case 8:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            case 9:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            case 10:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            case 11:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            case 12:
                setBackTile(new Texture(Gdx.files.internal("itemTileV5.png")));
                break;
            default:
                break;
        }
    }
}
