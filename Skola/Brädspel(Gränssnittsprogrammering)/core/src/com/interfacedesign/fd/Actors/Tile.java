package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public abstract class Tile extends Actor {
    private int sandTiles = 0;
    private boolean flipped = false;
    private Sprite frontTile = new Sprite(new Texture(Gdx.files.internal("desertTile.png")));
    private Sprite backTile = new Sprite(new Texture(Gdx.files.internal("empty.png")));
    private Sprite sandTile = new Sprite(new Texture(Gdx.files.internal("sandTile.png")));
    private Sprite blockedTile = new Sprite(new Texture(Gdx.files.internal("sandTwoTile.png")));
    private Skin skin = new Skin(Gdx.files.internal("rusty-robot/skin/rusty-robot-ui.json"));
    private Label sandtext;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<ShipPiece> shipPieces = new ArrayList<>();


    //creates basic tile
    public Tile(){
        setBounds(frontTile.getX(),frontTile.getY(),frontTile.getWidth(),frontTile.getHeight());
        setTouchable(Touchable.enabled);
        sandtext = new Label( "1", skin );
    }
    //set front of tile
    public void setFrontTile(Texture texture){
        frontTile = new Sprite(texture);
    }
    //set back of tile
    public void setBackTile(Texture texture){
        backTile = new Sprite(texture);
    }

    //flip the tile
    public void flip(){
        flipped = !flipped;
    }

    //return if tile is flipped
    public boolean isFlipped() {
        return flipped;
    }

    //rotates a tile
    public void rotateTile(){
        backTile.rotate90(true);
    }
    //adds a shippiece to tile
    public void addShipPiece(ShipPiece piece){
        piece.setWidth(getWidth()/2);
        piece.setHeight(getHeight()/2);
        piece.setPosition(getX(Align.center), getY(Align.center));
        if (shipPieces.size() > 0){
            piece.setPosition(getX(Align.left), getY(Align.center));
        }
        shipPieces.add(piece);
    }
    //removes shippiece from tile
    public void removeShipPiece(ShipPiece piece){
        shipPieces.remove(piece);
    }

    //returns first shippiece from tile
    public ShipPiece getShipPiece() {
        if (shipPieces.isEmpty()){
            return null;
        }
        return shipPieces.get(0);
    }

    //add a player to tile at correct position if more than one player is on tile
    public void addPlayer(Player player){
        player.setWidth(getWidth()/2);
        player.setHeight(getHeight()/2);
        players.add(player);
        updatePlayerPositions();
    }

    //Updates all the positions of the players on the tile
    public void updatePlayerPositions(){
        for (int i = 0; i < players.size(); i++) {
            if (i == 0) {
                players.get(i).setPosition(getX(Align.left), getY(Align.bottom));
            }
            if (i == 1) {
                players.get(i).setPosition(getX(Align.center)-players.get(i).getWidth()/2, getY(Align.bottom));
            }
            if (i == 2) {
                players.get(i).setPosition(getX(Align.center), getY(Align.bottom));
            }
            if (i == 3) {
                players.get(i).setPosition(getX(Align.left), getY(Align.center));
            }
            if (i == 4) {
                players.get(i).setPosition(getX(Align.center), getY(Align.center));
            }
        }
    }

    //removes a player from tile
    public Player removePlayer(Player player){
        players.remove(player);
        updatePlayerPositions();
        return player;
    }

    //return players from tile
    public ArrayList<Player> getPlayers() {
        return players;
    }

    //adds sand to tile
    public void addSand(){
        sandTiles ++;
    }
    //remove sand from tile
    public void removeSand(){
        sandTiles--;
    }

    //return number of sand tiles on tile
    public int getSandTiles() {
        return sandTiles;
    }

    //change åosition of tile and all child components
    @Override
    protected void positionChanged(){
        frontTile.setPosition(getX(),getY());
        backTile.setPosition(getX(),getY());
        sandTile.setPosition(getX(),getY());
        blockedTile.setPosition(getX(),getY());
        sandtext.setPosition(getX(Align.center)-sandtext.getWidth()/2,getY(Align.center)-sandtext.getHeight()/2);

        int i = 0;
        for (Player player : players) {
            if (i == 0) {
                player.setPosition(getX(Align.left), getY(Align.bottom));
            }
            if (i == 1) {
                player.setPosition(getX(Align.center)-player.getWidth()/2, getY(Align.bottom));
            }
            if (i == 2) {
                player.setPosition(getX(Align.center), getY(Align.bottom));
            }
            if (i == 3) {
                player.setPosition(getX(Align.left), getY(Align.center));
            }
            if (i == 4) {
                player.setPosition(getX(Align.center), getY(Align.center));
            }
            i++;
        }
        for (ShipPiece piece: shipPieces){
            piece.setPosition(getX(Align.center), getY(Align.center));
        }
        super.positionChanged();
    }

    //draws the tile and all child components
    @Override
    public void draw(Batch batch, float parentAlpha){

        if(flipped){
            backTile.draw(batch);
        }
        else{
            frontTile.draw(batch);
        }
        if(sandTiles > 0){
            sandTile.draw(batch);

        }
        if (sandTiles > 1){
            blockedTile.draw(batch);
            sandtext.setText(Integer.toString(sandTiles));
            sandtext.draw(batch, parentAlpha);
        }
        for (Player player: players) {
            player.draw(batch, parentAlpha);
        }
        for (ShipPiece piece:shipPieces){
            piece.draw(batch, parentAlpha);
        }


    }

    //act on actions
    @Override
    public void act(float delta){
        super.act(delta);
    }
    //set size of all sprites
    @Override
    public void setSize(float width, float height){
        sandTile.setSize(width,height);
        blockedTile.setSize(width,height);
        frontTile.setSize(width,height);
        backTile.setSize(width,height);
        super.setSize(width,height);
    }


}
