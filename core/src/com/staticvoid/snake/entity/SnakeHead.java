package com.staticvoid.snake.entity;

import com.staticvoid.snake.config.GameConfig;

public class SnakeHead extends EntityBase {

    // move right by default
//    private Direction direction = Direction.RIGHT;

    public SnakeHead() {
        setSize(GameConfig.SNAKE_SIZE,
                GameConfig.SNAKE_SIZE);
    }

//    public void move() {
//        if (direction.isRight()) {
//            updateX(GameConfig.SNAKE_SPEED);
//        } else if (direction.isLeft()) {
//            updateX(-GameConfig.SNAKE_SPEED);
//        } else if (direction.isUp()) {
//            updateY(GameConfig.SNAKE_SPEED);
//        } else if (direction.isDown()) {
//            updateY(-GameConfig.SNAKE_SPEED);
//        }
//    }

    public void updateX(float amount) {
        x += amount;
        updateBounds();
    }

    public void updateY(float amount) {
        y += amount;
        updateBounds();
    }

//    public void setDirection(Direction direction) {
//        this.direction = direction;
//    }
}
