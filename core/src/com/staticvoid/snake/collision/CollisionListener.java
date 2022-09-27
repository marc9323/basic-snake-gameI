package com.staticvoid.snake.collision;

// notify us when player hits coin or when player loses life
public interface CollisionListener {

    void hitCoin();

    void lose();
}
