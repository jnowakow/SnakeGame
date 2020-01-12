package PositonDefiniton;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector2d add(Vector2d other){
        Vector2d res = new Vector2d(this.x + other.x, this.y + other.y);
        return res;
    }

    @Override
    public int hashCode(){
        int result = 17;
        result += 23 * result + 3 * this.x;
        result += 29 * result + 7 * this.y;
        return  result;
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;

        return this.x == that.x && this.y == that.y;
    }

}