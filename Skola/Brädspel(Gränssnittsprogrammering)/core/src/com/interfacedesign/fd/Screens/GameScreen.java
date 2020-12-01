package com.interfacedesign.fd.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.interfacedesign.fd.Actors.CompassTile;
import com.interfacedesign.fd.Actors.EngineTile;
import com.interfacedesign.fd.Actors.Player;
import com.interfacedesign.fd.Actors.PlayerProfile;
import com.interfacedesign.fd.Actors.PowerTile;
import com.interfacedesign.fd.Actors.PropellerTile;
import com.interfacedesign.fd.Actors.ShipPiece;
import com.interfacedesign.fd.Actors.Tile;
import com.interfacedesign.fd.Actors.WellTile;
import com.interfacedesign.fd.AppPreferences;
import com.interfacedesign.fd.Game;
import com.interfacedesign.fd.TileBoard;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private Game parent;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRender;

    private Sound buttonSound;
    private Sound digSound;
    private Sound moveSound;


    private TileBoard tileBoard;
    private AppPreferences preferences;

    private TextButton digButton;
    private boolean digSelected = false;
    private TextButton moveButton;
    private boolean moveSelected = false;
    private TextButton flipButton;
    private TextButton endTurnButton;
    private TextButton pickUpButton;
    private TextButton undoButton;

    private int amountOfPlayers;

    private ShipPiece engine;
    private ShipPiece crystal;
    private ShipPiece compass;
    private ShipPiece propeller;

    private ArrayList<PlayerProfile> playerProfiles;

    private ArrayList<Tile> sandRemovedFromTiles;
    private ArrayList<Tile> flippedTiles;

    //Creates a new game screen and adds skin and sound to memory
    public GameScreen(Game game) {
        parent = game;
        cam = new OrthographicCamera(32,24);
        debugRender = new Box2DDebugRenderer(true,true,true,true,true,true);
        skin = new Skin(Gdx.files.internal("rusty-robot/skin/rusty-robot-ui.json"));
        skin = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("general_button_sound.wav"));
        digSound = Gdx.audio.newSound(Gdx.files.internal("dig_sfx.wav"));
        moveSound = Gdx.audio.newSound(Gdx.files.internal("move_sfx.wav"));
    }

    //Add all components to the game screen and shows it
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        digButton = new TextButton(Game.text.get("digButton"), skin, "toggle");
        moveButton = new TextButton(Game.text.get("moveButton"), skin, "toggle");
        flipButton = new TextButton(Game.text.get("flipButton"), skin);
        pickUpButton = new TextButton(Game.text.get("pickUpButton"),skin);
        endTurnButton = new TextButton(Game.text.get("endTurnButton"), skin);
        undoButton = new TextButton(Game.text.get("undoButton"), skin);
        playerProfiles = new ArrayList<>();
        sandRemovedFromTiles = new ArrayList<>();
        flippedTiles = new ArrayList<>();
        preferences = new AppPreferences();
        stage.getViewport().update(Game.GAMEWIDTH,Game.GAMEHEIGHT);
        Gdx.input.setInputProcessor(stage);
        final float width = stage.getWidth()/7;
        final float height = stage.getHeight()/6;
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());

        amountOfPlayers = parent.getPlayers().length;
        createShipPieces(width,height); // creates the not collected shippieces

        //Randomize the starting classes
        ArrayList<Integer> classes = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            classes.add(i);
        }
        Collections.shuffle(classes);

        //setting up the the player profiles
        playerProfiles.add(new PlayerProfile(classes.get(0),parent.getPlayers()[0]));
        playerProfiles.get(0).setActivePlayer(true);
        playerProfiles.get(0).setSize(width*2,height);
        playerProfiles.get(0).setPosition(width*5,stage.getHeight()-height);
        stage.addActor(playerProfiles.get(0));
        playerProfiles.add(new PlayerProfile(classes.get(1),parent.getPlayers()[1]));
        playerProfiles.get(1).setSize(width*2,height);
        playerProfiles.get(1).setPosition(width*5,stage.getHeight()-height*2);
        stage.addActor(playerProfiles.get(1));
        if (amountOfPlayers >= 3) {
            playerProfiles.add(new PlayerProfile(classes.get(2),parent.getPlayers()[2]));
            playerProfiles.get(2).setSize(width*2,height);
            playerProfiles.get(2).setPosition(width*5,stage.getHeight()-height*3);
            stage.addActor(playerProfiles.get(2));
        }
        if (amountOfPlayers >= 4) {
            playerProfiles.add(new PlayerProfile(classes.get(3),parent.getPlayers()[3]));
            playerProfiles.get(3).setSize(width*2,height);
            playerProfiles.get(3).setPosition(width*5,stage.getHeight()-height*4);
            stage.addActor(playerProfiles.get(3));
        }
        if (amountOfPlayers >= 5) {
            playerProfiles.add(new PlayerProfile(classes.get(4),parent.getPlayers()[4]));
            playerProfiles.get(4).setSize(width*2,height);
            playerProfiles.get(4).setPosition(width*5,stage.getHeight()-height*5);
            stage.addActor(playerProfiles.get(4));
        }
        tileBoard = new TileBoard(stage, playerProfiles, parent, false); // creates a new tileboard

        //add functionality to dig button
        digButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
                digSelected = !digSelected;
                moveSelected = false;
                //TODO make it visible that dig action is selected
            }
        });

        //add functionality to move button
        moveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
                moveSelected = !moveSelected;
                digSelected = false;
                //TODO make it visible that move action is selected
            }
        });

        //add functionality to flip button
        flipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(preferences.isSoundEffectsEnabled()){//plays sound if sound is enabled
                    buttonSound.play(preferences.getSoundVolume());
                }
                if(checkActionsLeft()) {// only able to flip if active player has actions left
                    if (tileBoard.getActivePlayerTile().getSandTiles() >0){//if tile is blocked show dialog
                        showBlockedTileDialog();
                        return;
                    }
                    if (tileBoard.getActivePlayerTile().isFlipped()){//if tie is flipped show dialog
                        //TODO dialog saying that tile is already flipped
                        return;
                    }
                    tileBoard.getActivePlayerTile().flip();
                    Tile tileTarget = tileBoard.getActivePlayerTile();
                    flippedTiles.add(tileTarget);
                    //if tile is a piece tile it checks if a piece needs to be added and adds it
                    if (tileTarget instanceof CompassTile) {
                        tileBoard.calculateCompassPos();
                    }
                    if (tileTarget instanceof EngineTile) {
                        tileBoard.calculateAndAddEngine();
                    }
                    if (tileTarget instanceof PowerTile) {
                        tileBoard.calculateAndAddPower();
                    }
                    if (tileTarget instanceof PropellerTile) {
                        tileBoard.calculateAndAddPropeller();
                    }
                    //if well tile and it is not dry all players on tile gets water.
                    if (tileTarget instanceof WellTile){
                        if (!(((WellTile) tileTarget).isDry())){
                            for (Player player: tileTarget.getPlayers()){
                                playerProfiles.get(player.getPlayerId()).fillWaterFromWell();
                            }
                        }
                    }

                    tileBoard.takeAction();
                    checkVictory();
                }
            }
        });

        //add functionality to pickup button
        pickUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
                if (!checkActionsLeft()){//create dialog if no actions is left
                    return;
                }
                ShipPiece piece = tileBoard.getActivePlayerTile().getShipPiece();
                if (piece != null && tileBoard.getActivePlayerTile().getSandTiles() == 0 && tileBoard.getActivePlayerTile().isFlipped()){
                    tileBoard.getActivePlayerTile().removeShipPiece(piece);
                    //adds the correct piece to the collected pieces
                    if (piece.getType() == 0){
                        engine.setEnabled();
                    }
                    if (piece.getType() == 1){
                        compass.setEnabled();
                    }
                    if (piece.getType() == 2){
                        propeller.setEnabled();
                    }
                    if (piece.getType() == 3){
                        crystal.setEnabled();
                    }
                    tileBoard.takeAction();
                    checkVictory();
                }
            }
        });

        //add functionality to endturn button
        endTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
                new Dialog(Game.text.get("endTurnDialogTitle"), skin) {//create are you sure dialog
                    {
                        text(Game.text.get("endTurnDialogText"));
                        button(Game.text.get("yesButton"), "Yes");
                        button(Game.text.get("noButton"), "No");
                    }

                    @Override
                    protected void result(Object object) {//if yes end active players turn
                        if (object.toString().equals("Yes")){
                            if(preferences.isSoundEffectsEnabled()){
                                buttonSound.play(preferences.getSoundVolume());
                            }
                            sandRemovedFromTiles.clear();
                            flippedTiles.clear();
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    tileBoard.endTurn();
                                }
                            }, 1);

                        }
                    }
                }.show(stage);

            }
        });
        //add functionality to undo button
        undoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(preferences.isSoundEffectsEnabled()){
                    buttonSound.play(preferences.getSoundVolume());
                }
                undoActions();
            }
        });


        //creates and add all the buttons to the stage
        ButtonGroup group = new ButtonGroup(digButton,moveButton);
        group.setMaxCheckCount(1);
        final Table buttonsTable = new Table();
        //buttonsTable.debugAll();
        buttonsTable.add(digButton).grow();
        buttonsTable.add(moveButton).grow();
        buttonsTable.add(undoButton).grow();
        buttonsTable.row();
        buttonsTable.add(pickUpButton).grow();
        buttonsTable.add(flipButton).grow();
        buttonsTable.add(endTurnButton).grow();
        buttonsTable.setWidth(width*4);
        buttonsTable.setHeight(height-20);
        buttonsTable.setPosition(0,stage.getHeight()-height,Align.bottomLeft);

        stage.addActor(buttonsTable);

        ///check if esc is pressed to display exit dialog
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE){
                    new Dialog(Game.text.get("exitDialogTitle"), skin) {
                        {
                            text(Game.text.get("exitDialogText"));
                            button(Game.text.get("yesButton"), "Yes");
                            button(Game.text.get("noButton"),"No");
                            button(Game.text.get("gotoMenuButton"),"Menu");
                        }
                        @Override
                        protected void result(Object object) {
                            if (object.toString().equals("Menu")){
                                parent.changeScreen(Game.MENU);
                            }
                            if (object.toString().equals("Yes")){
                                Gdx.app.exit();
                            }
                        }
                    }.show(stage);
                }
                return super.keyDown(event, keycode);
            }
        });



        //add click listener to stage
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Actor target = stage.hit(x,y,true);
                Tile tileFrom = tileBoard.getActivePlayerTile();
                if (target == null){return;}
                if(target instanceof Tile){
                    Tile tileTarget = (Tile)target;
                    if(digSelected && validMove(tileTarget,tileFrom)){//removes sand from clicked tile
                        if(tileTarget.getSandTiles() > 0 && checkActionsLeft()){
                            sandRemovedFromTiles.add(tileTarget);
                            tileTarget.removeSand();
                            tileBoard.takeAction();
                            checkVictory();
                            if(preferences.isSoundEffectsEnabled()){
                                digSound.play(preferences.getSoundVolume());
                            }
                        }
                    }
                    if (moveSelected && checkActionsLeft()){//moves to clicked tile
                        if(validMove(tileTarget,tileFrom)) {
                            tileTarget.addPlayer(tileFrom.removePlayer(tileBoard.getActivePlayer()));
                            //TODO add animations?
                            tileBoard.takeAction();
                            checkVictory();
                            if(preferences.isSoundEffectsEnabled()){
                                moveSound.play(preferences.getSoundVolume());
                            }
                        }
                    }
                }
            }
        });


    }

    //checks if all parts are collected
    private boolean checkAllPartsPickedUp(){
        return  (crystal.isEnabled()&& compass.isEnabled() && propeller.isEnabled() && engine.isEnabled() );
    }

    //check if all parts are collected and all players are on launch pad
    public void checkVictory(){
        if (tileBoard.getLaunchTile().getPlayers().size() == amountOfPlayers &&
                checkAllPartsPickedUp() && tileBoard.getLaunchTile().getSandTiles() == 0 && tileBoard.getLaunchTile().isFlipped()){
            new Dialog(Game.text.get("victoryDialogTitle"), skin) {//display victory dialog
                {
                    text(Game.text.get("victoryDialogText"));
                    button(Game.text.get("yesButton"), "Yes");
                }
                @Override
                protected void result(Object object) {
                    if (object.toString().equals("Yes")){
                        parent.changeScreen(Game.MENU);

                    }
                }
            }.show(stage);
        }
    }

    //create all the shippieces thats needs to be collected
    public void createShipPieces(float width,float height){
        engine = new ShipPiece(0);
        engine.setWidth(width/2);
        engine.setHeight(height/2);
        engine.setPosition(width*4,height*5);
        engine.setDisabled();
        stage.addActor(engine);

        compass = new ShipPiece(1);
        compass.setWidth(width/2);
        compass.setHeight(height/2);
        compass.setPosition(width*4+compass.getWidth(),height*5);
        compass.setDisabled();
        stage.addActor(compass);

        propeller = new ShipPiece(2);
        propeller.setWidth(width/2);
        propeller.setHeight(height/2);
        propeller.setPosition(width*4+propeller.getWidth(),height*5+propeller.getHeight());
        propeller.setDisabled();
        stage.addActor(propeller);

        crystal = new ShipPiece(3);
        crystal.setWidth(width/2);
        crystal.setHeight(height/2);
        crystal.setPosition(width*4,height*5+crystal.getHeight());
        crystal.setDisabled();
        stage.addActor(crystal);


    }

    //undo actions, remove sand, flip and move actions
    public void undoActions(){
        for (Tile tile: sandRemovedFromTiles){
            tile.addSand();
        }
        for (Tile tile: flippedTiles){
            tile.flip();
        }
        sandRemovedFromTiles.clear();
        flippedTiles.clear();
        tileBoard.getActivePlayerTile().removePlayer(tileBoard.getActivePlayer());
        tileBoard.getStartTurnTile().addPlayer(tileBoard.getActivePlayer());
        tileBoard.undo();
    }


    //checks if a move is valid, only one tile away
    public boolean validMove(Tile target, Tile from){
        float moveDistX = Math.abs(target.getX()-from.getX());
        float moveDistY = Math.abs(target.getY()-from.getY());
        System.out.println("move x" + moveDistX);
        System.out.println("move y" + moveDistY);
        if ((target.getSandTiles()>1 || from.getSandTiles() > 1) && moveSelected){
            showBlockedTileDialog();
            return false;
        }
        if ( moveDistX <= from.getWidth()*1.5 && moveDistX != 0 && moveDistY <= 1){
            return true;
        }
        if (moveDistY <= from.getHeight()*1.5 && moveDistX <= 1){
            return true;
        }
        return false;
    }

    //display blocked tile dialog
    public void showBlockedTileDialog(){
        new Dialog(Game.text.get("blockedTileDialogTitle"),skin){
            {
                text(Game.text.get("blockedTileDialogText"));
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        hide();
                    }
                }, 2);

            }
        }.show(stage);
    }


    //check if active player has actions left
    public boolean checkActionsLeft(){
        if(!tileBoard.actionsLeft()){
            new Dialog(Game.text.get("noActionsDialogTitle"),skin){
                {
                    text(Game.text.get("noActionsDialogText"));
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            hide();
                        }
                    }, 2);

                }
            }.show(stage);
            return false;
        }
        return true;
    }



    //render the stage and calls act on all actors on stage
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(155/255f, 139/255f, 118/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        stage.draw();
    }

    //resize the stage
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
