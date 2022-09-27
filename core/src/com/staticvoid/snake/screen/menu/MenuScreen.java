package com.staticvoid.snake.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.snake.SimpleSnakeGame;
import com.staticvoid.snake.assets.AssetDescriptors;
import com.staticvoid.snake.assets.ButtonStyleNames;
import com.staticvoid.snake.assets.RegionNames;
import com.staticvoid.snake.config.GameConfig;
import com.staticvoid.snake.screen.game.GameScreen;
import com.staticvoid.snake.util.GdxUtils;

import sun.jvmstat.monitor.HostIdentifier;

public class MenuScreen extends ScreenAdapter {

    // == attributes
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;
    private TextureAtlas gamePlayAtlas;

    // == constructors

    public MenuScreen(SimpleSnakeGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }


    // == private methods

    // == public methods


    @Override
    public void show() {
        // viewport will create camera for us internally automatically
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());
        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        Gdx.input.setInputProcessor(stage);

        stage.addActor(createUi());

    }

    private Actor createUi() {
        Table table = new Table(skin);
        table.defaults().pad(10);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Image titleImage = new Image(skin, RegionNames.TITLE);

        Button playButton = new Button(skin, ButtonStyleNames.PLAY);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        Button quitButton = new Button(skin, ButtonStyleNames.QUIT);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        table.add(titleImage).row();
        table.add(playButton).row();
        table.add(quitButton);

        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void quit() {
       // System.exit(0);
        Gdx.app.exit();
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
