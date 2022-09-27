package com.staticvoid.snake.entity;

import com.staticvoid.snake.config.GameConfig;

public class Coin extends EntityBase {
    // 1 coin in game:
    // when snake eats the coin:
    // coin disappears and reappears elsewhere
    private boolean available;

    public Coin() {
        setSize(GameConfig.COIN_SIZE,
                GameConfig.COIN_SIZE);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
