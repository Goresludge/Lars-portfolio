package com.interfacedesign.fd.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.interfacedesign.fd.Game;


public class StartScreen implements Screen{

    protected Stage stage;
    private Viewport viewPort;
    protected Skin skin;
    private Texture img;
    private Game parent;
    private Label difficultyLabel;
    private Label playersLabel;
    private Table table;
    private Table playerTable;
    private String[] players = new String[4];

    private TextField inputPlayer1;
    private TextField inputPlayer2;
    private TextField inputPlayer3;
    private TextField inputPlayer4;
    private TextField inputPlayer5;

    private TextButton startButton;
    private TextButton backButton;

    private SelectBox<String> playerBox;
    private SelectBox<String> difficultyBox;

    private String[] difficulty = new String[4];

    //creates the startscreen
    public StartScreen(Game game) {

        parent = game;
        skin = new Skin(Gdx.files.internal("rusty-robot/skin/rusty-robot-ui.json"));
        img = new Texture("forbiddendesert-cover.jpg");

        inputPlayer1 = new TextField("Player 1",skin);
        inputPlayer2 = new TextField("Player 2",skin);
        inputPlayer3 = new TextField("Player 3",skin);
        inputPlayer4 = new TextField("Player 4",skin);
        inputPlayer5 = new TextField("Player 5",skin);

        startButton = new TextButton("Start Game", skin);
        backButton = new TextButton("Back",skin);

        playerBox = new SelectBox<>(skin);
        difficultyBox = new SelectBox<>(skin);


        stage = new Stage(new ScreenViewport());
    }

    //shows the start screen
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        table = new Table(skin);
        table.setBackground("list");
        mainTable.add(table);
        stage.addActor(mainTable);
        playerTable = new Table();

        //set the options to player selection box
        players[0] = "2 Players";
        players[1] = "3 Players";
        players[2] = "4 Players";
        players[3] = "5 Players";
        playerBox.setItems(players);

        //set the options to difficulty selection box
        difficulty[0] = "Novice";
        difficulty[1] = "Normal";
        difficulty[2] = "Elite";
        difficulty[3] = "Legendary";
        difficultyBox.setItems(difficulty);

        //creates the functionallity to buttons and selectboxes
        playerBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateTable();
            }
        });
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String[] playerlist = new String[playerBox.getSelectedIndex()+2];
                playerlist[0] = inputPlayer1.getText();
                playerlist[1] = inputPlayer2.getText();
                if (playerlist.length > 2){
                    playerlist[2] = inputPlayer3.getText();
                }
                if (playerlist.length > 3){
                    playerlist[3] = inputPlayer4.getText();
                }
                if (playerlist.length > 4){
                    playerlist[4] = inputPlayer5.getText();
                }
                parent.setPlayers(playerlist);
                parent.changeScreen(Game.GAMESCREEN);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Game.MENU);
            }
        });

        difficultyLabel = new Label("Difficulty",skin);
        playersLabel = new Label("Number of players", skin);


        updateTable();
        //stage.addActor(table);

    }

    //Updates how many players is displayed
    public void updateTable(){
        table.clear();
        playerTable.clear();
        table.add(playersLabel);
        table.add(difficultyLabel);
        table.row().pad(5,0,0,5);
        table.add(playerBox);
        table.add(difficultyBox);
        table.row().pad(10,0,0,10);
        table.add(playerTable).colspan(2);
        playerTable.add(inputPlayer1).colspan(2);
        playerTable.row();
        playerTable.add(inputPlayer2).colspan(2);
        playerTable.row();
        if (playerBox.getSelectedIndex() > 0) {
            playerTable.add(inputPlayer3).colspan(2);
            playerTable.row();
        }
        if (playerBox.getSelectedIndex() > 1) {
            playerTable.add(inputPlayer4).colspan(2);
            playerTable.row();
        }
        if (playerBox.getSelectedIndex() > 2) {
            playerTable.add(inputPlayer5).colspan(2);
            playerTable.row();
        }
        table.row();
        table.add(backButton);
        table.add(startButton);
    }

    //render the stage
    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.getBatch().begin();
        stage.getBatch().draw(img,0,0, Game.GAMEWIDTH,Game.GAMEHEIGHT);
        stage.getBatch().end();
        stage.draw();
    }

    //resize the stage
    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width,height,true);
        Gdx.graphics.setWindowedMode(width,height);
        Game.GAMEHEIGHT = height;
        Game.GAMEWIDTH = width;
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
