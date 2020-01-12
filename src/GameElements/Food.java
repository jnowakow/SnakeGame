package GameElements;

import PositonDefiniton.Vector2d;

import java.util.Random;

public class Food {
    private Vector2d position;
    private Random random = new Random();


    public Food( int width, int height){
        this.position = new Vector2d(random.nextInt(width), random.nextInt(height));
    }

    public void setRandomPosition(int width, int height){
        position.x = random.nextInt(width);
        position.y = random.nextInt(height);
    }

    public Vector2d getPosition(){
        return this.position;
    }

}
