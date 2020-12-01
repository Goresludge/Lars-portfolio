package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class StormCard extends Actor {
    private int moveX = 0;
    private int moveY = 0;
    private boolean heat = false;
    private boolean picksUp = false;

    //creates a storm card, movement in x and y and if it is heat or storm picks up
    public StormCard(int moveX, int moveY, boolean heat, boolean picksUp){
        this.moveX = moveX;
        this.moveY = moveY;
        this.heat = heat;
        this.picksUp = picksUp;
    }

    //return movement in x
    public int getMoveX() {
        return moveX;
    }

    //returns movement in y
    public int getMoveY() {
        return moveY;
    }

    //return if it is heatcard
    public boolean isHeat() {
        return heat;
    }

    //return if it is storm picks up card
    public boolean isPicksUp() {
        return picksUp;
    }
}
