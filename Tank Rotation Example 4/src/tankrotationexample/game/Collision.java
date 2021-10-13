package tankrotationexample.game;


public class Collision {
    public static boolean check(Movable g1, GameObject g2) {
        //checks intersections between game object hitboxes:
        if(g1.getHitBox().intersects(g2.getHitBox())){
            //System.out.println("tank 1 collided with wall");
            g1.collideWith(g2); //once an intersection has been detected it calls method in Moveable which takes in the the game object that has been encountered aka g
            return true;
        }

        return false;
    }
}