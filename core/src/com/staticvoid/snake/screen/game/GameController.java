package com.staticvoid.snake.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.snake.collision.CollisionListener;
import com.staticvoid.snake.common.GameManager;
import com.staticvoid.snake.config.GameConfig;
import com.staticvoid.snake.entity.BodyPart;
import com.staticvoid.snake.entity.Coin;
import com.staticvoid.snake.entity.Direction;
import com.staticvoid.snake.entity.Snake;
import com.staticvoid.snake.entity.SnakeHead;

public class GameController {

    //== constants

    private static final Logger log =
            new Logger(GameController.class.getName(),
                    Logger.DEBUG);

    // attributes
    private final CollisionListener listener;

    private Snake snake;
    private float timer;

    private Coin coin;

    // == constructors
    public GameController(CollisionListener listener) {
        this.listener = listener;
        snake = new Snake();
        coin = new Coin(); // by default at 0, 0, but spawn elsewhere

        restart();
    }

    // == public methods ==
    public void update(float deltaTime) {
        GameManager.INSTANCE.updateDisplayScore(deltaTime);
        // update game world only when GameState == PLAYING
        if (GameManager.INSTANCE.isPlaying()) {
            queryInput(); // input polling
            queryDebugInput();
            timer += deltaTime;
            if (timer >= GameConfig.MOVE_TIME) {
                timer = 0;
                snake.move();

                checkOutOfBounds();
                checkCollision();
            }
            spawnCoin();
        } else {
            checkForRestart();
        }
    }

    private void checkForRestart() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            restart();
        }
    }

    private void restart() {
        //   GameManager.INSTANCE.setPlaying();
        GameManager.INSTANCE.reset();
        snake.reset();
        coin.setAvailable(false);
        timer = 0;
    }

    private void queryDebugInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            snake.insertBodyPart();
        }
    }

    private void checkCollision() {
        // head coin collision
        SnakeHead head = snake.getHead();
        Rectangle headBounds = head.getBounds();
        Rectangle coinBounds = coin.getBounds();

        boolean overlaps = Intersector.overlaps(headBounds, coinBounds);

        if (coin.isAvailable() && overlaps) {
            listener.hitCoin();
            snake.insertBodyPart();
            coin.setAvailable(false);
            GameManager.INSTANCE.incrementScore(GameConfig.COIN_SCORE);
        }

        // between head and body parts
        for (BodyPart bodyPart : snake.getBodyParts()) {
            // ignore first touch (174)
            if (bodyPart.isJustAdded()) {
                bodyPart.setJustAdded(false);
                continue; // move on to next bodypart
            }

            Rectangle bodyPartBounds = bodyPart.getBounds();

            if (Intersector.overlaps(bodyPartBounds, headBounds)) {
                listener.lose();
                GameManager.INSTANCE.updateHighScore();
                GameManager.INSTANCE.setGameOver();
            }
        }
    }

    private void spawnCoin() {
        if (!coin.isAvailable()) {
            // int to keep in cells
            float coinX = MathUtils.random((int) (GameConfig.WORLD_WIDTH
                    - GameConfig.COIN_SIZE));
            float coinY = MathUtils.random((int) (GameConfig.MAX_Y
                    - GameConfig.COIN_SIZE));

            coin.setAvailable(true);

            coin.setPosition(coinX, coinY);
        }
    }

    private void checkOutOfBounds() {
        // out of bounds on right?  re-appear on left.  vice versa
        // same with respect to top/bottom bounds
        SnakeHead head = snake.getHead();
        if (head.getX() >= GameConfig.WORLD_WIDTH) {
            head.setX(0);
        } else if (head.getX() < 0) {
            head.setX(GameConfig.WORLD_WIDTH - GameConfig.SNAKE_SPEED);
        }

        // y bounds
        if (head.getY() >= GameConfig.MAX_Y) {  // WORLD_HEIGHT) {
            head.setY(0);
        } else if (head.getY() < 0) {
            head.setY(GameConfig.MAX_Y - GameConfig.SNAKE_SPEED);
            //  head.setY(GameConfig.WORLD_HEIGHT - GameConfig.SNAKE_SPEED);
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public Coin getCoin() {
        return coin;
    }

    // private

    private void queryInput() {
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) {
            snake.setDirection(Direction.LEFT);
        } else if (rightPressed) {
            snake.setDirection(Direction.RIGHT);
        } else if (upPressed) {
            snake.setDirection(Direction.UP);
        } else if (downPressed) {
            snake.setDirection(Direction.DOWN);
        }
    }
}
