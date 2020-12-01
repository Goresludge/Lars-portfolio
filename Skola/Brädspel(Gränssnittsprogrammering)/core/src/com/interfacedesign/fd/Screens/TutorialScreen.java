package com.interfacedesign.fd.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.interfacedesign.fd.Actors.*;
import com.interfacedesign.fd.AppPreferences;
import com.interfacedesign.fd.Game;
import com.interfacedesign.fd.Stormdeck;
import com.interfacedesign.fd.TileBoard;

import java.util.ArrayList;
import java.util.Collections;

public class TutorialScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private Game parent;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRender;

    private Color color;
    private Color colorButton;
    private Sound buttonSound;
    private Sound digSound;
    private Sound moveSound;

    private TileBoard tileBoard;
    private AppPreferences preferences;

    private Table buttonsTable;
    private TextButton digButton;
    private boolean digSelected = false;
    private TextButton moveButton;
    private boolean moveSelected = false;
    private TextButton flipButton;
    private TextButton endTurnButton;
    private TextButton pickUpButton;
    private TextButton undoButton;
    private Texture buttonBorder = new Texture(Gdx.files.internal("tutorial_border.png"));
    private Image borderImage = new Image(buttonBorder);
    private Dialog dialog;
    private Button generalButton = null;

    private ShapeRenderer shapeRenderer;
    private boolean isDisableDialogTint;
    private boolean isDisableButtonTint;
    private int timesMoved;
    private int timesFlipped;
    private int timesDig;
    private int timesEndturn;
    private int timesPickedUp;
    private int allowedDirection; //8 = up, 2 = down, 4 = left, 6 = right, 5 = dig sand, 0 = disabled. Numpad directions

    private int amountOfPlayers;

    private ShipPiece engine;
    private ShipPiece crystal;
    private ShipPiece compass;
    private ShipPiece propeller;

    private ArrayList<PlayerProfile> playerProfiles;

    private ArrayList<Tile> sandRemovedFromTiles;
    private ArrayList<Tile> flippedTiles;

    //Creates a new tutorial screen and adds skin and sound to memory
    public TutorialScreen(Game game) {
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
        stage.getViewport().update(Game.GAMEWIDTH,Game.GAMEHEIGHT);
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
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);
        final float width = stage.getWidth()/7;
        final float height = stage.getHeight()/6;
        timesMoved = 0;
        timesFlipped = 0;
        timesDig = 0;
        timesEndturn = 0;
        timesPickedUp = 0;
        allowedDirection = 0;
        moveSelected = false;
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());
        disableTint();
        amountOfPlayers = 2;


        createShipPieces(width,height); // creates the not collected shippieces

        //Randomize the starting classes
        ArrayList<Integer> classes = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            classes.add(i);
        }
        Collections.shuffle(classes);

        //setting up the the player profiles
        playerProfiles.add(new PlayerProfile(classes.get(0),"Player 1"));
        playerProfiles.get(0).setActivePlayer(true);
        playerProfiles.get(0).setSize(width*2,height);
        playerProfiles.get(0).setPosition(width*5,stage.getHeight()-height);
        stage.addActor(playerProfiles.get(0));
        playerProfiles.add(new PlayerProfile(classes.get(1),"Player 2"));
        playerProfiles.get(1).setSize(width*2,height);
        playerProfiles.get(1).setPosition(width*5,stage.getHeight()-height*2);
        stage.addActor(playerProfiles.get(1));


        tileBoard = new TileBoard(stage, playerProfiles, parent, true);

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
                    tileBoard.getActivePlayerTile().flip();//if tile is blocked show dialog
                    Tile tileTarget = tileBoard.getActivePlayerTile();
                    flippedTiles.add(tileTarget);
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
                }
                //counts the times we have used the flip mode for the tutorial so we display the correct dialogs
                if(timesFlipped == 3){
                    timesFlipped++;
                    finalDialog();
                }
                if(timesFlipped == 2){
                    timesFlipped++;
                    moveAndPickUpDialog();
                }
                if(timesFlipped == 1){
                    timesFlipped++;
                    waterDialog();
                }
                if(timesFlipped == 0){
                    successFlipDialog();
                    timesFlipped++;
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
                    if(timesPickedUp == 0){
                        lastTurnDialog();
                        timesPickedUp++;
                    }
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
        buttonsTable = new Table();
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

        //check if esc is pressed to display exit dialog
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
                            if(timesDig == 1){
                                timesDig++;
                                endTurnDialog();
                            }
                            if(timesDig == 0){
                                successDigDialog();
                                timesDig++;
                            }
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
                            //counts times moved so it displays the correct dialogs for the tutorial
                            if(timesMoved == 5){
                                timesMoved++;
                                lastMoves();
                            }
                            if(timesMoved == 4){
                                pickUpPartEnabled();
                                timesMoved++;
                            }
                            if(timesMoved == 3){
                                digDialog();
                                timesMoved++;
                            }
                            if(timesMoved == 2){
                                flipDialog();
                                timesMoved++;
                            }
                            if(timesMoved == 1){
                                successMoveDialog2();
                                timesMoved++;
                            }
                            if(timesMoved == 0){
                                successMoveDialog();
                                timesMoved++;
                            }
                            if(preferences.isSoundEffectsEnabled()){
                                moveSound.play(preferences.getSoundVolume());
                            }
                        }
                    }
                }
            }
        });

        disableButtons(); //disables button so the user wont do anything stupid

        //displays the first
        dialog = new Dialog(Game.text.get("tutorialWelcome"), skin) {
            {
                text(Game.text.get("tutorialWelcomeText"));
                button(Game.text.get("continueButton"), "Continue");
                enableTint();
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    actionDialog();
                }
            }
        }.show(stage);
    }

    //create all the shippieces thats needs to be collected
    public void createShipPieces(float width,float height){
        engine = new ShipPiece(0);
        engine.debug();
        engine.setWidth(width/2);
        engine.setHeight(height/2);
        engine.setPosition(width*4,height*5);
        engine.setDisabled();
        stage.addActor(engine);

        compass = new ShipPiece(1);
        compass.debug();
        compass.setWidth(width/2);
        compass.setHeight(height/2);
        compass.setPosition(width*4+compass.getWidth(),height*5);
        compass.setDisabled();
        stage.addActor(compass);

        propeller = new ShipPiece(2);
        propeller.debug();
        propeller.setWidth(width/2);
        propeller.setHeight(height/2);
        propeller.setPosition(width*4+propeller.getWidth(),height*5+propeller.getHeight());
        propeller.setDisabled();
        stage.addActor(propeller);

        crystal = new ShipPiece(3);
        crystal.debug();
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
        if ( moveDistX <= from.getWidth()*1.5 && moveDistX != 0 && moveDistY <= 1){

            if(allowedDirection == 6 && (target.getX()>from.getX())){
                return true;
            }
            if(allowedDirection == 4 && (target.getX()<from.getX())){
                return true;
            }
            if(allowedDirection == 5 && digSelected){
                return true;
            }
        }
        if (moveDistY <= from.getHeight()*1.5 && moveDistX <= 1){
            if(allowedDirection == 8 && (target.getY()>from.getY())){
                return true;
            }
            if(allowedDirection == 2 && (target.getY()<from.getY())){
                return true;
            }
            if(allowedDirection == 5 && digSelected){
                return true;
            }
        }

        return false;
    }

    //display blocked tile dialog
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

    //Adds a green frame around a button to highlight it
    public void highlightButton(Button button){
        borderImage.setSize(button.getWidth(),button.getHeight());
        borderImage.setPosition(buttonsTable.getX()+button.getX(),buttonsTable.getY()+button.getY());
        stage.addActor(borderImage);
    }

    //Remove the highlight
    public void removeHighlightButton(){
        borderImage.remove();
    }

    //Enables all buttons
    public void enableAllButtons(){
        digButton.setDisabled(false);
        moveButton.setDisabled(false);
        flipButton.setDisabled(false);
        pickUpButton.setDisabled(false);
        undoButton.setDisabled(false);
        endTurnButton.setDisabled(false);
    }

    //Disables all buttons
    public void disableButtons(){
        digButton.setDisabled(true);
        moveButton.setDisabled(true);
        flipButton.setDisabled(true);
        pickUpButton.setDisabled(true);
        undoButton.setDisabled(true);
        endTurnButton.setDisabled(true);
    }

    //enable a specific button
    public void enableButton(Button button){
        button.setDisabled(false);
    }

    //disables tint
    public void disableTint(){
        color = new Color(0,0,0,0);
        shapeRenderer.setColor(color);

    }

    //enables tint, tint will shade everything but a dialog
    public void enableTint(){
        isDisableButtonTint = true;
        isDisableDialogTint = false;
        color = new Color(0,0,0,0.5f);
        shapeRenderer.setColor(color);
    }

    //disables button tint
    public void disableButtonTint(){
        colorButton = new Color(0,0,0,0);
        shapeRenderer.setColor(colorButton);
    }

    //enables button tint, button tint will tint all buttons except one
    public void enableButtonTint(Button button){
        generalButton = button;
        isDisableDialogTint = true;
        isDisableButtonTint = false;
        colorButton = new Color(0,0,0,0.5f);
        shapeRenderer.setColor(colorButton);
    }

    //Sets the tint and calculates the area that needs tinting, for buttons
    public void tintButtonArea(Button button){
        if(button != null && !isDisableButtonTint){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0,buttonsTable.getY(),buttonsTable.getWidth()-(buttonsTable.getWidth()-button.getX()),buttonsTable.getHeight());//left
            shapeRenderer.rect(buttonsTable.getWidth()-(buttonsTable.getWidth()-button.getX()),buttonsTable.getY(),button.getWidth(),button.getY());//bottom
            shapeRenderer.rect(buttonsTable.getWidth()-(buttonsTable.getWidth()-button.getX()),buttonsTable.getY()+button.getY()+button.getHeight(),button.getWidth(),buttonsTable.getHeight()-(button.getY()+button.getHeight()));//top
            shapeRenderer.rect(button.getX()+button.getWidth(),buttonsTable.getY(),buttonsTable.getWidth()- (button.getX()+button.getWidth()),buttonsTable.getHeight());//right
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

    }

    //sets the tint and calculates the area that need tinting, for dialogs
    public void tintArea(Dialog dialog){

        if(!isDisableDialogTint){
            float x = dialog.getX();
            float y = dialog.getY();
            float width = dialog.getWidth();
            float height = dialog.getHeight();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0,0,(Game.GAMEWIDTH-((x+width))),Game.GAMEHEIGHT);//left
            shapeRenderer.rect((Game.GAMEWIDTH-(x+width)),0,width,(Game.GAMEHEIGHT-(y+height)));//bottom
            shapeRenderer.rect((Game.GAMEWIDTH-(x+width)),Game.GAMEHEIGHT-y ,width,Game.GAMEHEIGHT);//top
            shapeRenderer.rect(Game.GAMEWIDTH-x,0,Game.GAMEWIDTH, Game.GAMEHEIGHT);//right
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }


    }

    //Tutorial dialog, if continue is pressed it will call the functions in the result function, in this case it
    //will disable dialog tint and enable tint on every button except the move button and highlight it with a green frame
    //We also give it the next dialog that we want to show
    public void actionDialog(){
        dialog = new Dialog(Game.text.get("tutorialAction"),skin){
            {
                text(Game.text.get("tutorialActionText"));
                button(Game.text.get("continueButton"), "Continue");
                enableTint();
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    disableTint();
                    highlightButton(moveButton);
                    enableButtonTint(moveButton);
                    moveDialog();
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void moveDialog(){
        dialog = new Dialog(Game.text.get("tutorialMove"),skin){
            {
                text(Game.text.get("tutorialMoveText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(moveButton);
                    removeHighlightButton();
                    tryMoveDialog();
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void tryMoveDialog(){
        dialog = new Dialog(Game.text.get("tutorialTryMove"),skin){
            {
                text(Game.text.get("tutorialTryMoveText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    dialog.remove();
                    allowedDirection = 6;
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void successMoveDialog(){
        allowedDirection = 0;
        disableButtons();
        disableButtonTint();
        enableButtonTint(flipButton);
        highlightButton(flipButton);
        dialog = new Dialog(Game.text.get("tutorialSuccessMove"),skin){
            {
                text(Game.text.get("tutorialSuccessMoveText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    removeHighlightButton();
                    enableButton(flipButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void successFlipDialog(){
        disableButtonTint();
        enableButtonTint(moveButton);
        dialog = new Dialog(Game.text.get("tutorialSuccessFlip"),skin){
            {
                text(Game.text.get("tutorialSuccessFlipText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    allowedDirection = 6;
                    disableButtons();
                    enableButton(moveButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void successMoveDialog2(){
        disableButtonTint();
        allowedDirection = 5;
        disableButtons();
        highlightButton(digButton);
        enableButtonTint(digButton);
        dialog = new Dialog(Game.text.get("tutorialSuccessMove2"),skin){
            {
                text(Game.text.get("tutorialSuccessMoveText2"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    removeHighlightButton();
                    enableButton(digButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void successDigDialog(){
        allowedDirection = 0;
        disableButtons();
        highlightButton(endTurnButton);
        enableButtonTint(endTurnButton);
        dialog = new Dialog(Game.text.get("tutorialDigMove"),skin){
            {
                text(Game.text.get("tutorialSuccessDigText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    removeHighlightButton();
                    enableButton(endTurnButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void stormCardsDialog(){
        allowedDirection = 0;
        disableButtons();
        disableButtonTint();
        dialog = new Dialog(Game.text.get("tutorialStormCards"),skin){
            {
                text(Game.text.get("tutorialStormCardsText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    stormCardsDialog2();
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void stormCardsDialog2(){
        dialog = new Dialog(Game.text.get("tutorialStormCards2"),skin){
            {
                text(Game.text.get("tutorialStormCardsText2"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    nextPlayersTurnDialog();
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void nextPlayersTurnDialog(){
        enableButtonTint(moveButton);
        dialog = new Dialog(Game.text.get("tutorialNextPlayer"),skin){
            {
                text(Game.text.get("tutorialNextPlayerText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    allowedDirection = 8;
                    enableButton(moveButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void flipDialog(){
        disableButtonTint();
        allowedDirection = 0;
        disableButtons();
        enableButtonTint(flipButton);
        dialog = new Dialog(Game.text.get("tutorialnextPlayerFlip"),skin){
            {
                text(Game.text.get("tutorialnextPlayerFlipText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(flipButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void waterDialog(){
        disableButtons();
        disableButtonTint();
        enableButtonTint(moveButton);
        dialog = new Dialog(Game.text.get("tutorialWaterFound"),skin){
            {
                text(Game.text.get("tutorialWaterFoundText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(moveButton);
                    allowedDirection = 8;
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void digDialog(){
        disableButtons();
        disableButtonTint();
        enableButtonTint(digButton);
        dialog = new Dialog(Game.text.get("tutorialDigAgain"),skin){
            {
                text(Game.text.get("tutorialDigAgainText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(digButton);
                    allowedDirection = 5;
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void endTurnDialog(){
        allowedDirection = 0;
        disableButtons();
        disableButtonTint();
        enableButtonTint(endTurnButton);
        dialog = new Dialog(Game.text.get("tutorialEndTurn"),skin){
            {
                text(Game.text.get("tutorialEndTurnText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(endTurnButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void nextPlayerDialog(){
        allowedDirection = 0;
        disableButtons();
        disableButtonTint();
        enableButtonTint(flipButton);
        dialog = new Dialog(Game.text.get("nextPlayersTurn"),skin){
            {
                text(Game.text.get("nextPlayersTurnText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    nextPlayerDialog2();
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void nextPlayerDialog2(){
        dialog = new Dialog(Game.text.get("nextPlayersTurn2"),skin){
            {
                text(Game.text.get("nextPlayersTurnText2"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(flipButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void moveAndPickUpDialog(){
        allowedDirection = 4;
        disableButtons();
        disableButtonTint();
        enableButtonTint(moveButton);
        dialog = new Dialog(Game.text.get("movePickUpPart"),skin){
            {
                text(Game.text.get("movePickUpPartText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(moveButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void pickUpPartEnabled(){
        allowedDirection = 8;
        disableButtons();
        disableButtonTint();
        enableButtonTint(pickUpButton);
        enableButton(pickUpButton);
    }

    //Tutorial dialog
    public void lastTurnDialog(){
        allowedDirection = 0;
        disableButtons();
        disableButtonTint();
        enableButtonTint(endTurnButton);
        enableButton(endTurnButton);
        dialog = new Dialog(Game.text.get("tutorialLastTurn"),skin){
            {
                text(Game.text.get("tutorialLastTurnText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(endTurnButton);
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void lastTurnDialog2(){
        disableButtons();
        disableButtonTint();
        enableButtonTint(moveButton);
        enableButton(moveButton);
        dialog = new Dialog(Game.text.get("tutorialLastTurn2"),skin){
            {
                text(Game.text.get("tutorialLastTurnText2"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    enableButton(moveButton);
                    allowedDirection = 8;
                }
            }
        }.show(stage);
    }

    //forces the user to only use the flip button
    public void lastMoves(){
        allowedDirection = 0;
        disableButtons();
        disableButtonTint();
        enableButton(flipButton);
        enableButtonTint(flipButton);
    }

    //Tutorial dialog
    public void finalDialog(){
        disableButtons();
        disableButtonTint();
        dialog = new Dialog(Game.text.get("tutorialFinalDialog"),skin){
            {
                text(Game.text.get("tutorialFinalDialogText"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    finalDialog2();
                }
            }
        }.show(stage);
    }

    //Tutorial dialog
    public void finalDialog2(){
        dialog = new Dialog(Game.text.get("tutorialFinalDialog2"),skin){
            {
                text(Game.text.get("tutorialFinalDialogText2"));
                button(Game.text.get("continueButton"), "Continue");
            }
            @Override
            protected void result(Object object) {
                if (object.toString().equals("Continue")){
                    parent.changeScreen(Game.MENU);
                }
            }
        }.show(stage);
    }

    //render the stage and calls act on all actors on stage
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(155/255f, 139/255f, 118/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        if(tileBoard.getSandOnBoard() > Game.MAX_SAND){
            //TODO GAME OVER SCREEN
            System.out.println("Game over, max sand reached.");
        }
        stage.draw();
        tintArea(dialog);
        tintButtonArea(generalButton);
        //What dialog to show after the first time we end our turn, increases one so we will take the next one after
        //we ended our turn once more
        if(playerProfiles.get(1).isActivePlayer() && timesEndturn == 0){
            stormCardsDialog();
            timesEndturn++;
        }
        if(playerProfiles.get(0).isActivePlayer() && timesEndturn == 1){
            nextPlayerDialog();
            timesEndturn++;
        }
        if(playerProfiles.get(1).isActivePlayer() && timesEndturn == 2){
            lastTurnDialog2();
            timesEndturn++;
        }
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
