package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor{
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("white_piece.png")));//TODO Change to white piece so we can tint the color
    private Sprite spriteborder = new Sprite(new Texture(Gdx.files.internal("white_piece_border.png")));
    private Color color;
    private int playerId;
    private int actionsLeft = 0;
    private boolean active = false;
    public Player(Color color, int playerId){
        super();
        this.color = color;
        this.playerId = playerId;
    }

    //return playerID
    public int getPlayerId() {
        return playerId;
    }

    //Draws the player and a border around the player if active
    @Override
    public void draw(Batch batch, float alpha){
        sprite.setSize(getWidth(),getHeight());
        spriteborder.setSize(getWidth(),getHeight());
        if(active){
            spriteborder.draw(batch);
        }
        sprite.setColor(color);
        sprite.draw(batch);

        //batch.draw(texture,0,0);
    }

    //Updated the sprites positions
    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(),getY());
        spriteborder.setPosition(getX(),getY());
    }
    //Make layer active and set actions left to 4
    public void makeActive(){
        active = true;
        actionsLeft = 4;
    }
    //Takes one action
    public void takeAction(){
        actionsLeft--;
    }

    //return actions left
    public int getActionsLeft() {
        return actionsLeft;
    }

    //return if player is active
    public boolean isActive() {
        return active;
    }

    //set player to active
    public void setActive(boolean active) {
        this.active = active;
    }
}
