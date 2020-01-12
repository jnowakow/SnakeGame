import GameElements.Food;
import GameElements.Obstacle;
import GameElements.Snake;
import PositonDefiniton.Directions;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Random;


public class Main extends Application {
    //display parameters
    private int tileSize = 20;
    private int width = 30;
    private int height = 20;

    //game parameters
    private int result;
    private int bestResult = 0;
    private int speed;
    private boolean updateSpeed;
    private long lastUpdateTime;
    private boolean gameOver;
    private Random addObstacle = new Random(); // to make adding new obstacles random

    //map elements
    private Snake snake;
    private Food food;
    private LinkedList<Obstacle> obstaclesList;

    //animation and graphical classes
    private Stage gameStage;
    private Scene scene;
    private Pane root;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private AnimationTimer timer;


    public static void main (String[] args){
        launch(args);
    }

    //sets up game parameters
    private void setUpGame(){
        result = 0;
        speed = 5;
        gameOver = false;
        updateSpeed = true;
        lastUpdateTime = 0;

        snake = new Snake(width, height);
        food = new Food(width, height);
        obstaclesList = new LinkedList<>();

        root = new Pane();
        canvas = new Canvas(width*tileSize, height*tileSize);

        graphicsContext = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdateTime > 1000000000 / speed){
                    lastUpdateTime = now;
                    update();
                }
            }
        };

        scene = new Scene(root);

        //set up control over snake
        scene.setOnKeyPressed(event ->{
            switch (event.getCode()){
                case W:
                    snake.setDirection(Directions.UP);
                    break;
                case A:
                    snake.setDirection(Directions.LEFT);
                    break;
                case D:
                    snake.setDirection(Directions.RIGHT);
                    break;
                case S:
                    snake.setDirection(Directions.DOWN);
                    break;
            }
        });

        gameStage.setScene(scene);
    }


    @Override
    public void start(Stage stage) throws Exception {
        gameStage = stage;
        setUpGame();

        gameStage.show();
        timer.start();
    }

    private void update(){
        if (gameOver){
            if (result > bestResult){
                bestResult = result;
            }

            //button which enables to restart game
            Button gameOverButton = new Button("Game Over! \nClick to restart");

            gameOverButton.setTranslateX((width/2 * tileSize) - 2 * tileSize);
            gameOverButton.setTranslateY((height/2 * tileSize) - 2 * tileSize);

            gameOverButton.setOnMouseClicked(mouseEvent -> {
                setUpGame();
                root.getChildren().remove(gameOverButton);
                timer.start();
            });

            root.getChildren().add(gameOverButton);
            timer.stop();
        }

        //color background
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0,0, width*tileSize,height*tileSize);

        //show results
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(new Font("", 20));
        graphicsContext.fillText("Result: " + result, 10, 30);
        graphicsContext.fillText("Best result: " + bestResult, 450, 30);


        //draw obstacles
        graphicsContext.setFill(Color.BROWN);
        obstaclesList.forEach(obstacle -> {
            graphicsContext.fillRect(obstacle.getPosition().x * tileSize, obstacle.getPosition().y * tileSize, tileSize, tileSize);
        });

        //draw food
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillOval(food.getPosition().x * tileSize, food.getPosition().y * tileSize, tileSize, tileSize);

        snake.move();

        //draw snake and it's head
        graphicsContext.setFill(Color.GREEN);
        snake.getBody().forEach(vector2d -> {
            graphicsContext.fillRect(vector2d.x * tileSize ,vector2d.y * tileSize, tileSize, tileSize);
        });

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(snake.getHead().x * tileSize, snake.getHead().y * tileSize, tileSize, tileSize);

        //check if snake isn't eating itself or going outside map or falling into obstacle
        if(snake.checkCollision() || !snake.inMap(width, height) || snake.checkCollisionWithObstacle(obstaclesList)){
            gameOver = true;
        }

        //check if snake found food
        if(snake.checkFoodAndEat(food)){
            food.setRandomPosition(width, height);// it can be placed on obstacle but since obstacles disappear it is ok
            result++;
            if (updateSpeed){
                updateSpeed = false;
                speed++;
            }
            else {
                updateSpeed = true;
            }
        }

        //if random number is multiple of 23 then new obstacle is added
        if( addObstacle.nextInt(100) % 23 == 0){
            obstaclesList.add(new Obstacle(width, height, snake.getHead()));
        }

        //increment tours counter for each obstacle
        obstaclesList.forEach(Obstacle::incrementCounter);


        //remove all obstacles which are too long on map
        LinkedList<Obstacle> obstaclesToRemove = new LinkedList<>();

        obstaclesList.forEach(obstacle -> {
            if( obstacle.toRemove() ){
                obstaclesToRemove.add(obstacle);
            }
        });

        obstaclesList.removeAll(obstaclesToRemove);

    }
}
