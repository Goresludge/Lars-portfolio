package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ShipPiece extends Actor{

    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("white_piece.png")));
    private int type;
    private boolean enabled;

    //creates a specfied shippiece 0=engine 1=compass 2=propeller 3=crystal
    public ShipPiece(int type){
        super();
        this.type = type;
        if (type == 0){
            sprite = new Sprite(new Texture(Gdx.files.internal("engine.png")));
        }
        if (type == 1){
            sprite = new Sprite(new Texture(Gdx.files.internal("compass.png")));
        }
        if (type == 2){
            sprite = new Sprite(new Texture(Gdx.files.internal("propeller.png")));
        }
        if (type == 3){
            sprite = new Sprite(new Texture(Gdx.files.internal("crystal.png")));
        }

    }

    //set piece to disabled and fade it
    public void setDisabled(){
        enabled =false;
        sprite.setAlpha(0.4f);
    }
    //set piece to enabled and make it fully visible
    public void setEnabled(){
        enabled = true;
        sprite.setAlpha(1f);
    }

    //return if enabled
    public boolean isEnabled() {
        return enabled;
    }

    //return type of piece
    public int getType() {
        return type;
    }

    //draws the shippiece
    @Override
    public void draw(Batch batch, float alpha) {
        sprite.setSize(getWidth(), getHeight());
        sprite.draw(batch);
    }

    //change the position of the sprite
    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(),getY());
    }
}
