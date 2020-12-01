package com.interfacedesign.fd.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.interfacedesign.fd.Game;

public class PlayerProfile extends Table{
    private int type;
    private int maxWaterLevel =0;
    private int waterLevel =0;
    private int actionsLeft = 0;
    private boolean activePlayer= false;
    private NinePatchDrawable activePlayerBorder;
    private NinePatchDrawable playerBorder;

    private Image classSymbol = new Image();
    private Label actionLabel;
    private Label waterLevelLabel;

    private Skin skin;

    //creates a player profile with a class type and a player name
    public PlayerProfile(int type,String playerName){
        skin = new Skin(Gdx.files.internal("rusty-robot/skin/rusty-robot-ui.json"));
        this.type = type;

        //creates border around active player
        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("active_player.png")), 3, 3, 3, 3);
        activePlayerBorder = new NinePatchDrawable(patch);

        //creates black border around profile
        NinePatch patch2 = new NinePatch(new Texture(Gdx.files.internal("background.png")),3, 3, 3, 3);
        playerBorder = new NinePatchDrawable(patch2);

        //create all item checkboxes and scales them
        float scale = 0.5f;
        CheckBox secretWaterReserveItem = new CheckBox("Water", skin);
        secretWaterReserveItem.setTransform(true);
        secretWaterReserveItem.setScale(scale);
        CheckBox solarShieldItem = new CheckBox("Solar", skin);
        solarShieldItem.setTransform(true);
        solarShieldItem.setScale(scale);
        CheckBox terrascopeItem = new CheckBox("Terra", skin);
        terrascopeItem.setTransform(true);
        terrascopeItem.setScale(scale);
        CheckBox timeThrottleItem = new CheckBox("Time", skin);
        timeThrottleItem.setTransform(true);
        timeThrottleItem.setScale(scale);
        CheckBox duneblasterItem = new CheckBox("Dune", skin);
        duneblasterItem.setTransform(true);
        duneblasterItem.setScale(scale);
        CheckBox jetPackItem = new CheckBox("Jet", skin);
        jetPackItem.setTransform(true);
        jetPackItem.setScale(scale);

        //create the correct class symbol
        createClassSymbol();
        waterLevelLabel = new Label(Game.text.format("waterLevel",waterLevel,maxWaterLevel),skin);

        Label playerLabel = new Label(playerName, skin);
        actionLabel = new Label(Game.text.format("actionsLeft",actionsLeft), skin); // Måste koppla till antalet actions kvar

        //adds player name and actions left
        Table playTable = new Table();
        playTable.add(playerLabel).grow();
        playTable.row();
        playTable.add(actionLabel).grow();

        //add item checkboxes with correct padding
        Table itemTable = new Table();
        itemTable.add(duneblasterItem).minSize(0).pad(2).left();
        itemTable.add(jetPackItem).minSize(0).pad(2).left();
        itemTable.add(secretWaterReserveItem).minSize(0).pad(2).left();
        itemTable.row();
        itemTable.add(solarShieldItem).minSize(0).pad(2).left();
        itemTable.add(terrascopeItem).minSize(0).pad(2).left();
        itemTable.add(timeThrottleItem).minSize(0).pad(2).left();

        //adds all the components that makes up the playerprofile
        this.setBackground(skin.newDrawable("white", 170/255f, 145/255f, 128/255f,1));
        this.setBackground(playerBorder);
        this.add(playTable).minSize(0);
        this.add(classSymbol).minSize(0);
        this.row();
        this.add(itemTable).colspan(2).minSize(0).pad(3);
        this.row();
        this.add(waterLevelLabel).colspan(2).minSize(0).center();

    }

    //check if player is active
    public boolean isActivePlayer() {
        return activePlayer;
    }

    //ends player turn, makes player inactive and set actions to 0
    public void endTurn(){
        activePlayer = false;
        actionsLeft = 0;
        actionLabel.setText(Game.text.format("actionsLeft",actionsLeft));
        this.setBackground(playerBorder);
    }

    //sets player to active player, sets actions left to 4 and green border
    public void setActivePlayer(boolean activePlayer) {

        actionsLeft = 4;
        actionLabel.setText(Game.text.format("actionsLeft",actionsLeft));
        this.setBackground(activePlayerBorder);
        this.activePlayer = activePlayer;
    }

    //reduce number of actions with 1
    public void takeAction(){
        actionsLeft--;
        actionLabel.setText(Game.text.format("actionsLeft",actionsLeft));
    }

    //returns actions left
    public int getActionsLeft() {
        return actionsLeft;
    }

    //drink one water
    public void loseWater(){
        waterLevel--;
        waterLevelLabel.setText(Game.text.format("waterLevel",waterLevel,maxWaterLevel));
    }

    //return waterlevel
    public int getWaterLevel() {
        return waterLevel;
    }

    //adds 2 water if exceed max waterlevel sets to max
    public void fillWaterFromWell(){
        waterLevel+= 2;
        if (waterLevel > maxWaterLevel){
            waterLevel = maxWaterLevel;
        }
        waterLevelLabel.setText(Game.text.format("waterLevel",waterLevel,maxWaterLevel));
    }

    //returns the type of player
    public int getType() {
        return type;
    }

    //create the correct class symbor corresponding to class and sets waterlevel to max
    private void createClassSymbol(){
        if(type == 1) {
            Texture navigatorSymbol = new Texture(Gdx.files.internal("navigatorSymbol.png"));
            classSymbol = new Image(navigatorSymbol); // Måste koppla till spelarens klass
            maxWaterLevel = 5;
            waterLevel = maxWaterLevel;
        }
        if(type == 2) {
            Texture climberSymbol = new Texture(Gdx.files.internal("climberSymbol.png"));
            classSymbol = new Image(climberSymbol); // Måste koppla till spelarens klass
            maxWaterLevel = 5;
            waterLevel = maxWaterLevel;
        }
        if(type == 3) {
            Texture explorerSymbol = new Texture(Gdx.files.internal("explorerSymbol.png"));
            classSymbol = new Image(explorerSymbol); // Måste koppla till spelarens klass
            maxWaterLevel = 5;
            waterLevel = maxWaterLevel;
        }
        if(type == 4) {
            Texture archeologistSymbol = new Texture(Gdx.files.internal("archeologistSymbol.png"));
            classSymbol = new Image(archeologistSymbol); // Måste koppla till spelarens klass
            maxWaterLevel = 5;
            waterLevel = maxWaterLevel;
        }
        if(type == 5) {
            Texture meterologistSymbol = new Texture(Gdx.files.internal("meterologistSymbol.png"));
            classSymbol = new Image(meterologistSymbol); // Måste koppla till spelarens klass
            maxWaterLevel = 5;
            waterLevel = maxWaterLevel;
        }
        if(type == 6) {
            Texture waterCarrierSymbol = new Texture(Gdx.files.internal("waterCarrierSymbol.png"));
            classSymbol = new Image(waterCarrierSymbol); // Måste koppla till spelarens klass
            maxWaterLevel = 5;
            waterLevel = maxWaterLevel;
        }
    }
}
