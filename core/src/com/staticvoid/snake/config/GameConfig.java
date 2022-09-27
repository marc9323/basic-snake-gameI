package com.staticvoid.snake.config;

// just constants, do not instanntiate

public class GameConfig {

    // == constants

    private static final float Y_OFFSET = 2f;
    // pixels
    public static final float WIDTH = 800f; // so these pixel resolutions reflect screenvs world?
    public static final float HEIGHT = 480f;

    // 1 x 1 pixel per unit ratio
    public static final float HUD_WIDTH = 800f; // world units
    public static final float HUD_HEIGHT = 480f; // world units

    // different ratio from HUD
    public static final float WORLD_WIDTH = 25f; // world units
    public static final float WORLD_HEIGHT = 15f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f;

    public static final float SNAKE_SIZE = 1; // world units

    public static final float MOVE_TIME = 0.2f;
    public static final float SNAKE_SPEED = 1f; // world units

    public static final float COIN_SIZE = 1; // world unit

    public static final int COIN_SCORE = 20;

    public static final float MAX_Y = WORLD_HEIGHT - Y_OFFSET;


    private GameConfig() {
        // nothing yet...
    }
}


/*
The viewport has world width and height and it has screen width and height.
The difference between the two is important.

The screen width and height is set when we call update() on the Viewport
inside the resize() method.  According to those values the viewport will
automatically calculate the WORLD width and height. or the pixel
per unit ratio?  We are specifiying world width and world height when we
construct our Viewports.
 */