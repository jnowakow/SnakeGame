package PositonDefiniton;

public enum Directions {
    UP, DOWN, LEFT, RIGHT;

    public Directions opposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
            default:
                return null;
        }
    }

 public Vector2d toUnitVector(){
     switch (this) {
         case UP:
             return new Vector2d(0,-1);
         case DOWN:
             return new Vector2d(0,1);
         case RIGHT:
             return new Vector2d(1,0);
         case LEFT:
             return new Vector2d(-1,0);
         default:
             return null;
     }
 }

 public static Directions intToDirection(int n){
        switch (n){
            case 0:
                return UP;
            case 1:
                return DOWN;
            case 2:
                return LEFT;
            case 3:
                return RIGHT;
            default:
                return UP;
        }
 }
}
