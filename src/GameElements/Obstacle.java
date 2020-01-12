package GameElements;

import PositonDefiniton.Vector2d;

import java.util.Random;

public class Obstacle {
    private Vector2d position;
    private static Random random = new Random();
    private int toursToStayOnMap;//how long the obstacle will stay on map
    private int toursCounter;//how long obstacle is on map

    public Obstacle (int width, int height, Vector2d snakeHead){
        this.position = new Vector2d(random.nextInt(width), random.nextInt(height));

        //make sure that obstacle won't show to close to snake's head
        while ( Math.abs(this.position.x - snakeHead.x) < 10 && Math.abs(this.position.y - snakeHead.y) < 10 ) {
            this.position = new Vector2d(random.nextInt(width), random.nextInt(height));
        }

        this.toursCounter = 0;
        this.toursToStayOnMap = random.nextInt(100) + 100;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void incrementCounter(){
        toursCounter++;
    }

    public boolean toRemove(){
        return toursCounter >= toursToStayOnMap;
    }

}
