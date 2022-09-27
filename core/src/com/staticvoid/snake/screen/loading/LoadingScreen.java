package com.staticvoid.snake.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.snake.SimpleSnakeGame;
import com.staticvoid.snake.assets.AssetDescriptors;
import com.staticvoid.snake.config.GameConfig;
import com.staticvoid.snake.screen.menu.MenuScreen;
import com.staticvoid.snake.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    // constants
    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f;
    private static final float PROGRESS_BAR_HEIGHT = 60f;

    // == attributes
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    // progress of loaded assets and base time
    private float progress;
    private float waitTime = 0.75f;

    private boolean changeScreen;

    // == constructors
    public LoadingScreen(SimpleSnakeGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {  // initialize variables here ..
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.UI_FONT); // queues loading into background task
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.COIN_SOUND);
        assetManager.load(AssetDescriptors.LOSE_SOUND);
        // display progress bar while it loads ...
    }

    @Override
    public void render(float deltaTime) {
        GdxUtils.clearScreen();

        update(deltaTime);
        draw(); // don't try to draw after the renderer has been disposed!
        // the changeScreen boolean flag addresses this

        // never switch to another screen iof the frame has not finished
        if (changeScreen) {
            game.setScreen(new MenuScreen(game));
        }
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
        renderer.dispose();
    }

    // == private
    private void update(float deltaTime) {
        // get progress from AssetManager
        progress = assetManager.getProgress();
        if (assetManager.update()) {  // when ...update returns true, everything is loaded
            waitTime -= deltaTime;

            if (waitTime <= 0) {
                // game.setScreen(new GameScreen(game));
                // avoid probolems with screen switching mid-frame
                changeScreen = true; // do this, use the flag, instead
            }
        }
    }

    private void draw() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.rect(
                (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f,
                (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f,
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT
        );

        // don't allow disposal before this finishes rendering
        renderer.end();
    }
}
