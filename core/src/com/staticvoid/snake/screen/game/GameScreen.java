package com.staticvoid.snake.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.staticvoid.snake.SimpleSnakeGame;
import com.staticvoid.snake.assets.AssetDescriptors;
import com.staticvoid.snake.collision.CollisionListener;
import com.staticvoid.snake.common.GameManager;
import com.staticvoid.snake.screen.menu.MenuScreen;

public class GameScreen extends ScreenAdapter {

    // == attributes
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;
    private final CollisionListener listener;

    private GameRenderer renderer;
    private GameController controller;

    private Sound coinSound;
    private Sound loseSound;

    // == constructors ==
    public GameScreen(SimpleSnakeGame game) {
        this.game = game;
        assetManager = game.getAssetManager();

        listener = new CollisionListener() {
            @Override
            public void hitCoin() {
                coinSound.play();
            }

            @Override
            public void lose() {
                loseSound.play();
            }
        };
    }

    // == public methods

    @Override
    public void show() {
        coinSound = assetManager.get(AssetDescriptors.COIN_SOUND);
        loseSound = assetManager.get(AssetDescriptors.LOSE_SOUND);

        controller = new GameController(listener);
        renderer = new GameRenderer(game.getBatch(), assetManager, controller);
    }

    @Override
    public void render(float deltaTime) {
        // first update game logic
        controller.update(deltaTime);

        // call renderer to render
        renderer.render(deltaTime);

        if (GameManager.INSTANCE.isGameOver()) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        // notify viewports about resize
        renderer.resize(width, height);
        // we can do above as viewports are inside renderer
    }

    @Override
    public void hide() {
        dispose(); // screens are not automatically disposed
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}