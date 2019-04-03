package game.physics;

import game.GameObject;
import game.Vector2D;

public class BoxCollider {
    public Vector2D position;
    public int width;
    public int height;
    public Vector2D anchor;

    public BoxCollider(Vector2D position,Vector2D anchor, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.anchor = anchor;
    }

    public BoxCollider(GameObject master, int width, int height) {
        this.position = master.position;
        this.width = width;
        this.height = height;
        this.anchor = master.anchor;
    }

    public BoxCollider(double x, double y, int width, int height) {
        this.position.x = x;
        this.position.y = y;
        this.width = width;
        this.height = height;
        this.anchor.set(0.5,0.5);
    }

    public double top() {
        return this.position.y - this.anchor.y * this.height;
    }

    public double bot() {
        return this.top() + this.height;
    }

    public double left() {
        return this.position.x - this.anchor.x * this.width;
    }

    public double right() {
        return this.left() + this.width;
    }

    public boolean intersects(BoxCollider other) {
        // kiem tra 2 hcn 'this' vs 'other'
        return other.right() >= this.left()
                && other.left() <= this.right()
                && other.top() <= this.bot()
                && other.bot() >= this.top();
    }
}
