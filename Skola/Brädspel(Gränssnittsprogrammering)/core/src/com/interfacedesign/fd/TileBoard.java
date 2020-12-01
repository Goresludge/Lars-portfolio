package com.interfacedesign.fd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import com.interfacedesign.fd.Actors.CompassTile;
import com.interfacedesign.fd.Actors.EngineTile;
import com.interfacedesign.fd.Actors.EquipmentTile;
import com.interfacedesign.fd.Actors.LaunchTile;
import com.interfacedesign.fd.Actors.PieceTile;
import com.interfacedesign.fd.Actors.Player;
import com.interfacedesign.fd.Actors.PlayerProfile;
import com.interfacedesign.fd.Actors.PowerTile;
import com.interfacedesign.fd.Actors.PropellerTile;
import com.interfacedesign.fd.Actors.ShipPiece;
import com.interfacedesign.fd.Actors.StartTile;
import com.interfacedesign.fd.Actors.Storm;
import com.interfacedesign.fd.Actors.StormCard;
import com.interfacedesign.fd.Actors.Tile;
import com.interfacedesign.fd.Actors.TunnelTile;
import com.interfacedesign.fd.Actors.WellTile;

import java.util.ArrayList;
import java.util.Collections;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class TileBoard {
    private Stage stage;
    private ArrayList<Tile> tiles;
    private int amountOfPlayers;
    private ArrayList<PlayerProfile> playerProfiles;
    private ArrayList<Player> players = new ArrayList<>();
    private int activePlayerId = 0;
    private Skin skin;
    private Skin cleanSkin;
    private Tile startTurnTile;
    private Tile launchTile;
    private AppPreferences preferences;
    private Sound stormSfx;
    private Sound stormPicksUpSfx;
    private Sound losingWaterSfx;
    private boolean cardsShowing = false;

    private Texture sunBeatsDownCard = new Texture(Gdx.files.internal("sun_beats_down.png"));
    private Texture stormPicksUpCard = new Texture(Gdx.files.internal("storm_picks_up.png"));
    private Image stormImage = new Image(stormPicksUpCard);
    private Image sunImage = new Image(sunBeatsDownCard);
    private Texture waterDrop = new Texture(Gdx.files.internal("drop.png"));
    private Label stormLevelLabel;
    private Label cardsLeftLabel;
    private Label sandLeftLabel;

    private float width ;
    private float height ;

    private boolean tutorial = false;

    private Storm storm;
    private Stormdeck stormDeck;

    private int cardsDrawn = 0;

    //creates playstorm sound action
    private Action playStormSound = new Action() {
        @Override
        public boolean act(float delta) {
            if(preferences.isSoundEffectsEnabled()){
                stormPicksUpSfx.play(preferences.getSoundVolume());
            }
            return true;
        }
    };

    //creates play water sound action
    private Action playWaterSound = new Action() {
        @Override
        public boolean act(float delta) {
            if(preferences.isSoundEffectsEnabled()){
                losingWaterSfx.play(preferences.getSoundVolume());
            }
            return true;
        }
    };

    //creates complete action
    private Action completeAction = new Action(){
        public boolean act( float delta ) {
            cardsShowing = false;
            stormImage.remove();
            sunImage.remove();
            endTurn();
            return true;
        }
    };
    private int stormCardLevel = 3;
    private int stormLevel = 0;
    private Game parent;

    private TextArea logArea;

    //creates a new tileboard with players matching player profiles, creates a tutorial board and stormn deck if tutorial is true
    public TileBoard(Stage stage, ArrayList<PlayerProfile> playerProfiles, Game parent, boolean tutorial){
        this.tutorial = tutorial;
        this.parent = parent;
        this.playerProfiles = playerProfiles;
        this.stage = stage;
        this.amountOfPlayers = playerProfiles.size();
        width = stage.getWidth()/7;
        height = stage.getHeight()/6;

        preferences = new AppPreferences();
        stormSfx = Gdx.audio.newSound(Gdx.files.internal("storm_sound.wav"));
        losingWaterSfx = Gdx.audio.newSound(Gdx.files.internal("losing_water_sfx.wav"));
        stormPicksUpSfx = Gdx.audio.newSound(Gdx.files.internal("storm_picks_up_sfx.wav"));

        skin = new Skin(Gdx.files.internal("rusty-robot/skin/rusty-robot-ui.json"));
        skin.getFont("font").getData().setScale(0.8f,0.8f);

        cleanSkin = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));

        stormImage.setPosition((width*3)/2,(height*2)/2);
        sunImage.setPosition((width*3)/2,(height*2)/2);

        stormDeck = new Stormdeck(tutorial);
        tiles = createTiles();
        for (Tile tile: tiles) {
            stage.addActor(tile);
        }
        //adds the storm to the middle to board
        storm = new Storm();
        storm.setSize(width,height);
        storm.setPosition(width*2,height*2);
        stage.addActor(storm);
        startTurnTile = getActivePlayerTile();

        //adds sand counter
        sandLeftLabel = new Label(Game.text.format("sandLeft",Game.MAX_SAND-getSandOnBoard()) ,skin);
        sandLeftLabel.setPosition((width*2)+40,stage.getHeight()-sandLeftLabel.getHeight());
        sandLeftLabel.setWidth(width);
        stage.addActor(sandLeftLabel);

        //adds log so cards drawn is logged
        logArea = new TextArea(Game.text.get("startLog"),skin);
        logArea.setDisabled(true);
        logArea.setHeight(height-sandLeftLabel.getHeight());
        logArea.setWidth(width*2);
        logArea.setPosition(width*5,0);
        stage.addActor(logArea);

        //adds storm level counter
        stormLevelLabel = new Label(Game.text.format("stormLevel",stormCardLevel) ,skin);
        stormLevelLabel.setPosition(5,stage.getHeight()-stormLevelLabel.getHeight());
        stormLevelLabel.setWidth(width);
        stage.addActor(stormLevelLabel);

        //adds cards left counter
        cardsLeftLabel = new Label(Game.text.format("cardsLeft",stormDeck.cardsLeft()),skin);
        cardsLeftLabel.setPosition(width+20,stage.getHeight()-cardsLeftLabel.getHeight());
        cardsLeftLabel.setWidth(width);
        stage.addActor(cardsLeftLabel);
        
    }

    //get start turn tile
    public Tile getStartTurnTile() {
        return startTurnTile;
    }

    //get launch tile
    public Tile getLaunchTile() {
        return launchTile;
    }

    //resets active players actions
    public void undo(){
        playerProfiles.get(activePlayerId).setActivePlayer(true);
    }

    //end active players turn and draw card equal to storm level
    public void endTurn(){
        if (cardsDrawn == stormCardLevel){//if cards drawn equal storm level set next player as active
            System.out.println("Next Player" );
            updateStormLevel();
            checkGameOver();
            sandLeftLabel.setText(Game.text.format("sandLeft",Game.MAX_SAND-getSandOnBoard()));

            playerProfiles.get(activePlayerId).endTurn();
            players.get(activePlayerId).setActive(false);
            if(activePlayerId == amountOfPlayers-1){
                activePlayerId = 0;
            }else {
                activePlayerId++;
            }
            playerProfiles.get(activePlayerId).setActivePlayer(true);
            players.get(activePlayerId).setActive(true);
            cardsDrawn = 0;
            startTurnTile = getActivePlayerTile();
            return;
        }
        //else draw a new storm card
        StormCard card = stormDeck.drawCard();
        cardsLeftLabel.setText(Game.text.format("cardsLeft",stormDeck.cardsLeft()));
        sandLeftLabel.setText(Game.text.format("sandLeft",Game.MAX_SAND-getSandOnBoard()));
        cardsDrawn++;
        System.out.println("Cards drawn " + cardsDrawn);
        if(card.isPicksUp()){//increase storm level and log it
            cardsShowing = true;
            System.out.println("Storm picks up" );
            logArea.appendText("\n" + Game.text.get("logStorm") );
            stormLevel++;

            stormImage.addAction(sequence(alpha(0),fadeIn(0.5f), playStormSound,delay(1),fadeOut(0.5f),completeAction));
            stage.addActor(stormImage);
        }
        if(card.isHeat()){ // all players need to drink if they are not in a tunnel, logs it
            cardsShowing = true;
            System.out.println("Sun beats down" );
            logArea.appendText("\n" + Game.text.get("logSun"));

            sunImage.addAction(sequence(alpha(0),fadeIn(0.5f), playWaterSound,delay(1),fadeOut(0.5f),completeAction));
            stage.addActor(sunImage);
            for (int i = 0; i < amountOfPlayers ; i++) {
                Actor from = stage.hit(players.get(i).getX(Align.center),players.get(i).getY(Align.center),true);
                if (!(from instanceof TunnelTile) ) {//TODO maybe change so the player know if it is in cover or not so it gets easier to implement solarshield
                    playerProfiles.get(i).loseWater();
                    float y = playerProfiles.get(i).getY();
                    float x = playerProfiles.get(i).getX();
                    Image drop = new Image(waterDrop);
                    drop.setPosition(x,y);
                    drop.setSize(15,25);
                    drop.addAction(sequence(alpha(0),fadeIn(0.5f),delay(1),fadeOut(0.5f),removeActor()));
                    stage.addActor(drop);
                }
                if (from instanceof TunnelTile ){
                    if (((TunnelTile) from).isFlipped()){

                    }
                    else {
                        playerProfiles.get(i).loseWater();
                        float y = playerProfiles.get(i).getY();
                        float x = playerProfiles.get(i).getX();
                        Image drop = new Image(waterDrop);
                        drop.setPosition(x, y);
                        drop.setSize(15, 25);
                        drop.addAction(sequence(alpha(0), fadeIn(0.5f), delay(1), fadeOut(0.5f), removeActor()));
                        stage.addActor(drop);
                    }
                }
            }
        }
        //if not storm or heat move storm
        if(!cardsShowing){
            moveStorm(card.getMoveX(), card.getMoveY());
        }
    }

    //check if any loose condition is met
    public void checkGameOver(){
        int gameOver = 0;

        if (stormCardLevel == 7){
            gameOver = 1;
        }
        for (PlayerProfile player : playerProfiles){
            if (player.getWaterLevel() < 0){
                gameOver = 2;
            }
        }
        if (getSandOnBoard() > Game.MAX_SAND){
            gameOver = 3;
        }
        if (gameOver != 0){
            final int finalGameOver = gameOver;
            //creates game over dialog
            new Dialog(Game.text.get("gameOverDialogTitle"),cleanSkin){
                {
                    switch (finalGameOver){
                        case 1: text(Game.text.get("gameOverDialogStorm"));
                                break;
                        case 2: text(Game.text.get("gameOverDialogWater"));
                                break;
                        case 3: text(Game.text.get("gameOverDialogSand"));
                                break;
                    }
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

    //updates storm level. if level is over a predefined amount increase the amount of cards drawn
    public void updateStormLevel(){
        if (stormLevel > 3){
            stormCardLevel = 4;
        }
        if (stormLevel > 7){
            stormCardLevel = 5;
        }
        if (stormLevel > 10){
            stormCardLevel = 6;
        }
        if (stormLevel > 12){
            stormCardLevel = 7;//TODO GAME OVER
        }
        stormLevelLabel.setText(Game.text.format("stormLevel",stormCardLevel));
    }


    //moves the storm and the tiles the storm hits when moving corresponding to the x and y value
    public void moveStorm(int x, int y) {
        if (y == 0 && x == 0){
            endTurn();
            return;
        }
        System.out.println("Move Storm :" + x + ", " + y);
        MoveByAction move = new MoveByAction();
        move.setDuration(1f);
        if (y == 0){//move storm on x axis
            if (Integer.signum(x) == 1) {
                logArea.appendText("\n" + Game.text.format("stormE",x));
            }
            else {
                logArea.appendText("\n"+ Game.text.format("stormW", Math.abs(x)));
            }
            for (int i = 1; i <= Math.abs(x); i++) {
                Actor target = stage.hit(storm.getX(Align.center)+storm.getWidth()*i*Integer.signum(x), storm.getY(Align.center), true);
                if (target != null) {
                    System.out.println("Hit target");
                    if (target instanceof Tile) {
                        ((Tile) target).addSand();
                        MoveByAction moveTile = new MoveByAction();
                        moveTile.setDuration(1f);
                        moveTile.setAmountX(storm.getWidth() * -Integer.signum(x));
                        target.addAction(moveTile);
                        move.setAmountX(move.getAmountX()+storm.getWidth() * Integer.signum(x));
                        if(preferences.isSoundEffectsEnabled()){
                            stormSfx.play(preferences.getSoundVolume());
                        }
                    }

                }
            }
            Action actions = sequence(move, completeAction);
            storm.addAction(actions);
            //storm.addAction(move);
        }
        if (x == 0){//move storm on y axis
            if (Integer.signum(y) == 1) {
                logArea.appendText("\n"+ Game.text.format("stormN",y));
            }
            else {
                logArea.appendText("\n" + Game.text.format("stormS",Math.abs(y)));
            }
            for (int i = 1; i <= Math.abs(y); i++) {
                Actor target = stage.hit(storm.getX(Align.center), storm.getY(Align.center)+storm.getHeight()*i*Integer.signum(y), true);
                if (target != null) {
                    if (target instanceof Tile) {
                        ((Tile) target).addSand();
                        MoveByAction moveTile = new MoveByAction();
                        moveTile.setDuration(1f);
                        moveTile.setAmountY(storm.getHeight() * -Integer.signum(y));
                        target.addAction(moveTile);
                        move.setAmountY(move.getAmountY()+storm.getHeight() * Integer.signum(y));
                        if(preferences.isSoundEffectsEnabled()){
                            stormSfx.play(preferences.getSoundVolume());
                        }
                    }

                }
            }
            Action actions = sequence(move, completeAction);
            storm.addAction(actions);
            //storm.addAction(move);
        }
    }

    //get active player tile
    public Tile getActivePlayerTile(){
        Actor from = stage.hit(getActivePlayer().getX(Align.center),getActivePlayer().getY(Align.center),true);
        if(from instanceof Tile) {
            return (Tile) from;
        }
        return null;
    }

    //active player takes on action.
    public void takeAction(){

        playerProfiles.get(activePlayerId).takeAction();
        players.get(activePlayerId).takeAction();
    }
    //get actions left
    public boolean actionsLeft(){
       return playerProfiles.get(activePlayerId).getActionsLeft() > 0;
    }

    //get active player
    public Player getActivePlayer(){
        for(Player player: getPlayers()){
            if(player.isActive()){
                return player;
            }
        }
        return null;
    }

    //get all players
    public ArrayList<Player> getPlayers(){
        return players;
    }

    //get compass tiles, used to calculate piece position
    public ArrayList<Tile> getCompassTiles(){
        ArrayList<Tile> compassTiles = new ArrayList<>();
        for (Tile tile :tiles) {
            if (tile instanceof CompassTile){
                compassTiles.add(tile);
            }
        }
        return compassTiles;
    }
    //get engine tiles, used to calculate piece position
    public ArrayList<Tile> getEngineTiles(){
        ArrayList<Tile> engineTiles = new ArrayList<>();
        for (Tile tile :tiles) {
            if (tile instanceof EngineTile){
                engineTiles.add(tile);
            }
        }
        return engineTiles;
    }
    //get power tiles, used to calculate piece position
    public ArrayList<Tile> getPowerTiles(){
        ArrayList<Tile> powerTiles = new ArrayList<>();
        for (Tile tile :tiles) {
            if (tile instanceof PowerTile){
                powerTiles.add(tile);
            }
        }
        return powerTiles;
    }
    //get propeller tiles, used to calculate piece position
    public ArrayList<Tile> getPropellerTiles(){
        ArrayList<Tile> propellerTiles = new ArrayList<>();
        for (Tile tile :tiles) {
            if (tile instanceof PropellerTile){
                propellerTiles.add(tile);
            }
        }
        return propellerTiles;
    }

    //calculate the compass position and adds i
    public void calculateCompassPos(){
        ArrayList<Tile> pieceTile = getCompassTiles();
        if (pieceTile.get(0).isFlipped() && pieceTile.get(1).isFlipped()){
            PieceTile tile1 = (PieceTile) pieceTile.get(0);
            PieceTile tile2 = (PieceTile) pieceTile.get(1);

            addPiece(new ShipPiece(1), tile1,tile2);
        }
    }

    //calculate the engine position and adds it
    public void calculateAndAddEngine(){
        ArrayList<Tile> pieceTile = getEngineTiles();
        if (pieceTile.get(0).isFlipped() && pieceTile.get(1).isFlipped()){
            PieceTile tile1 = (PieceTile) pieceTile.get(0);
            PieceTile tile2 = (PieceTile) pieceTile.get(1);

            addPiece(new ShipPiece(0), tile1,tile2);
        }
    }
    //calculate the power position and adds it
    public void calculateAndAddPower(){
        ArrayList<Tile> pieceTile = getPowerTiles();
        if (pieceTile.get(0).isFlipped() && pieceTile.get(1).isFlipped()){
            PieceTile tile1 = (PieceTile) pieceTile.get(0);
            PieceTile tile2 = (PieceTile) pieceTile.get(1);

            addPiece(new ShipPiece(3), tile1,tile2);
        }
    }
    //calculate the propeller position and adds it
    public void calculateAndAddPropeller(){
        ArrayList<Tile> pieceTile = getPropellerTiles();
        if (pieceTile.get(0).isFlipped() && pieceTile.get(1).isFlipped()){
            PieceTile tile1 = (PieceTile) pieceTile.get(0);
            PieceTile tile2 = (PieceTile) pieceTile.get(1);

            addPiece(new ShipPiece(2), tile1,tile2);
        }
    }

//adds a piece to a to correct position
    public void addPiece(ShipPiece piece, PieceTile tile1, PieceTile tile2){
        if (tile1.getDirection() == 1){
            Actor target = stage.hit(tile1.getX(Align.center),tile2.getY(Align.center),true);
            if (target instanceof Tile){
                ((Tile) target).addShipPiece(piece);
            }
        }
        if (tile1.getDirection() == 2){
            Actor target = stage.hit(tile2.getX(Align.center),tile1.getY(Align.center),true);
            if (target instanceof Tile){
                ((Tile) target).addShipPiece(piece);
            }
        }
    }
    //get amount of sand on board
    public int getSandOnBoard(){
        int sand = 0;
        for (Tile tile : tiles) {
            sand += tile.getSandTiles();
        }
        return sand;
    }


    //create all the tiles
    public ArrayList<Tile> createTiles(){
        ArrayList<Tile> tiles = new ArrayList<>();
        //Start and launch
        launchTile = new LaunchTile();
        tiles.add(new StartTile());
        tiles.add(new EngineTile(1));
        tiles.add(new EngineTile(2));
        tiles.add(launchTile);
        tiles.add(new TunnelTile());

        //add wells
        tiles.add(new WellTile(false));
        tiles.add(new WellTile(true));

        //Add parts
        tiles.add(new CompassTile(1));
        tiles.add(new CompassTile(2));
        tiles.add(new PowerTile(1));
        tiles.add(new PowerTile(2));
        tiles.add(new PropellerTile(1));
        tiles.add(new PropellerTile(2));

        //add tunnels
        tiles.add(new TunnelTile());
        tiles.add(new TunnelTile());

        tiles.add(new WellTile(false));
        for (int i = 1; i <= 8 ; i++) {
            tiles.add(new EquipmentTile(i));
        }

        return setTilePositions(tiles);
    }

    //set all the tiles to random position if it is not tutorial else it only adds the sand and players
    public ArrayList<Tile> setTilePositions(ArrayList<Tile> tiles){
        if (!tutorial) {
            Collections.shuffle(tiles);
        }
        int i = 0;
        int j = 0;
        for (Tile tile: tiles) {
            if(i == 2 && j == 2){
                i++;
            }
            tile.setSize(width,height);
            tile.setPosition(width*i,height*j);
            if(i==2 && (j == 0 || j==4 )){
                tile.addSand();
            }
            if(i==1 && (j == 1 || j==3 )){
                tile.addSand();
            }
            if(i==3 && (j == 1 || j==3 )){
                tile.addSand();
            }
            if(j==2 && (i == 0 || i==4 )) {
                tile.addSand();
            }
            if(tile instanceof StartTile){
                for (int k = 0; k < amountOfPlayers; k++) {
                    int playerType = playerProfiles.get(k).getType();
                    if (playerType == 1){
                        players.add(new Player(Color.YELLOW, k));
                    }
                    if (playerType == 2){
                        players.add(new Player(Color.BLACK, k));
                    }
                    if (playerType == 3){
                        players.add(new Player(Color.GREEN, k));
                    }
                    if (playerType == 4){
                        players.add(new Player(Color.RED, k));
                    }
                    if (playerType == 5){
                        players.add(new Player(Color.WHITE, k));
                    }
                    if (playerType == 6){
                        players.add(new Player(Color.BLUE, k));
                    }
                    tile.addPlayer(players.get(k));
                }
                tile.getPlayers().get(0).makeActive();
            }
            i++;
            if (i == 5){
                i = 0;
                j++;
            }

        }
        return tiles;
    }
}
