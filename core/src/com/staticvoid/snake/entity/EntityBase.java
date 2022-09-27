package com.staticvoid.snake.entity;

import com.badlogic.gdx.math.Rectangle;

public abstract class EntityBase {

    // common behavior and attributes
    // position, size, bounding box

    // attributes

    protected float x; // world units
    protected float y;

    protected float width = 1; // world units
    protected float height = 1;

    protected Rectangle bounds;

    // constructors


    public EntityBase() {
        bounds = new Rectangle(x, y, width, height);
    }

    // public methods
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void updateBounds() {
        bounds.setPosition(x, y);
        bounds.setSize(width, height);
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }
}
