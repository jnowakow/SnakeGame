package GameElements;

import PositonDefiniton.Directions;
import PositonDefiniton.Vector2d;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Snake {
    private Vector2d head;
    private ArrayList<Vector2d> body = new ArrayList<>();
    private Directions direction;

    public Snake(int width, int height){
        Random random = new Random();

        direction = Directions.intToDirection(random.nextInt(4));
        head = new Vector2d(random.nextInt(width), random.nextInt(height));

        body.add(head);
        body.add(head.add(direction.opposite().toUnitVector()));
    }


    public boolean checkFoodAndEat(Food food){
        if (head.equals(food.getPosition())){
            body.add(new Vector2d(-1,-1));
            return true;
        }
        return false;
    }


    public void move(){
        for (int i = body.size() - 1 ; i > 0; i--){
            body.get(i).x = body.get(i - 1).x;
            body.get(i).y = body.get(i - 1).y;
        }

        body.remove(head);
        head = head.add(direction.toUnitVector());
        body.add(0, head);
    }

    //check if snake isn't eating itself
    public boolean checkCollision(){
        for(Vector2d v : body.subList(1, body.size())){
            if (v.equals(head)){
                return true;
            }
        }
        return false;
    }

    public boolean checkCollisionWithObstacle(LinkedList<Obstacle> obstacles){
        for(Obstacle obstacle : obstacles){
            if (obstacle.getPosition().equals(head)){
                return true;
            }
        }
        return false;
    }


    public boolean inMap(int width, int height){
        return head.x >= 0 && head.x < width && head. y >= 0 && head.y < height;
    }

    public void setDirection(Directions direction){
        if (this.direction.opposite() != direction) {
            this.direction = direction;
        }
    }

    public Vector2d getHead(){
        return this.head;
    }

    public ArrayList<Vector2d> getBody(){
        return this.body;
    }
}
