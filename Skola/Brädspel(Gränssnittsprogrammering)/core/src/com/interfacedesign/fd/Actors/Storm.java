package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Storm extends Actor{
    Sprite sprite = new Sprite(new Texture(Gdx.files.internal("cartoon-tornado.jpg")));

    //creates a storm
    public Storm(){
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);


    }

    //change the postion of the sprite
    @Override
    protected void positionChanged(){
        sprite.setPosition(getX(),getY());
        super.positionChanged();
    }

    //draws the storm
    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }

    //act on actions
    @Override
    public void act(float delta){
        super.act(delta);
    }

    //sets the size of the sprite
    @Override
    public void setSize(float width, float height){
        sprite.setSize(width,height);
        super.setSize(width,height);
    }

}

