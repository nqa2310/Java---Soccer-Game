package game;

import game.renderer.Renderer;

public class ViewPort {

     public Vector2D position2;
     private Vector2D followOffset;
     public ViewPort() {
         position2 = new Vector2D(0,0);
         followOffset = new Vector2D(0,0);
     }

     public void followPlayer(GameObject gameObject) {
         position2.x = gameObject.position.x;
     }

    public Vector2D getFollowOffset() {
        return followOffset;
    }

    public void setFollowOffset(Vector2D followOffset) {
        this.followOffset = followOffset;
    }

    public GameObject camera(GameObject gameObject) {
        gameObject.position.substract(this.position2);
        return gameObject;

     }


}
