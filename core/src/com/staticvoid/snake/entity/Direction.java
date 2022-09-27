package com.staticvoid.snake.entity;

public enum Direction {

    UP,
    RIGHT,
    DOWN,
    LEFT;

    // == public - for readabilities sake ...
    public boolean isUp() {
        return this == UP;
    }

    public boolean isRight() {
        return this == RIGHT;
    }

    public boolean isDown() {
        return this == DOWN;
    }

    public boolean isLeft() {
        return this == LEFT;
    }

    public Direction getOpposite() {
        if (isLeft()) {
            return RIGHT;
        } else if (isRight()) {
            return LEFT;
        } else if (isUp()) {
            return DOWN;
        } else if (isDown()) {
            return UP;
        }

        // this will never happen unless a new direction is added and unaccounted for
        // basically, this line just satisfies the compiler ...
        throw new IllegalArgumentException("can't find opposite of direction: " + this);
    }

    public boolean isOpposite(Direction direction) {
        // if the opposite is the same as direction
        return getOpposite() == direction;
    }
}
