package snakegame;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnakeGameModel {

	List<SnakeModelView> observers = new LinkedList<SnakeModelView>();

	public void registerObserver(SnakeModelView observer)
	{
		observers.add(observer);
	}

	public void unregisterObserver(SnakeModelView observer)
	{
		observers.remove(observer);
		observer = null;
	}

	private void notifyObservers()
	{
		for(SnakeModelView observer : observers)
		{
			observer.update();
		}
	}

	public static final int SCREEN_WIDTH = 900;
	public static final int SCREEN_HEIGHT = 750;

	public static final int SNAKE_SIZE_X = 25;
	public static final int SNAKE_SIZE_Y = 25;

	private int startPosX;
	private int startPosY;

	private int fruitX;
    private int fruitY;

    private boolean fruitFlag;

	//possible directions for the snake,
	//"None" denotes the starting direction before user input
	enum Direction
	{
		Left, Right, Up, Down, None;
	}

	//current direction of the snake
	private Direction dir;

    //List that holds the x,y coordinates of the snake
    private LinkedList<BodyPos> snakeBody;

	public void setDirection(Direction dir)
	{
		this.dir = dir;
	}

	public Direction getDirection()
	{
		return dir;
	}

	public int getFruitX()
	{
		return fruitX;
	}

	public int getFruitY()
	{
		return fruitY;
	}

	public void setFruitX(int fruitX)
	{
		this.fruitX = fruitX;
	}

	public void setFruitY(int fruitY)
	{
		this.fruitY = fruitY;
	}

	public boolean getFruitFlag()
	{
		return fruitFlag;
	}

	public void setFruitFlag(boolean fruitFlag)
	{
		this.fruitFlag = fruitFlag;
	}

	public LinkedList<BodyPos> getSnakeBody()
	{
		return snakeBody;
	}

	//moves the snake 25 units in the current direction
	public void move()
	{
		BodyPos head = getSnakeBody().getFirst();

		if(getDirection() == SnakeGameModel.Direction.Up)
        {
            getSnakeBody().addFirst(new BodyPos(head.getX(), (head.getY() - 26)));
        }

        if(getDirection() == SnakeGameModel.Direction.Down)
        {
        	getSnakeBody().addFirst(new BodyPos(head.getX(), (head.getY() + 26)));
        }

        if(getDirection() == SnakeGameModel.Direction.Left)
        {
        	getSnakeBody().addFirst(new BodyPos((head.getX() - 26), (head.getY())));
        }

        if(getDirection() == SnakeGameModel.Direction.Right)
        {
        	getSnakeBody().addFirst(new BodyPos((head.getX() + 26), (head.getY())));
        }

        if(!(getDirection() == SnakeGameModel.Direction.None))
        {
        	//if the head touched a fruit, don't remove the last bodypart
        	if(getFruitFlag())
        	{
        		setFruitFlag(false);
        	}
        	else
        	{
        		getSnakeBody().removeLast();
        	}
        }
    }

	//returns true once the game is over
	public boolean isGameOver()
	{
		BodyPos head = getSnakeBody().getFirst();

    	//game is over when head leaves screen (with wiggle room of 5 pixels)
        if((head.getX() + SnakeGameModel.SNAKE_SIZE_X > (SnakeGameModel.SCREEN_WIDTH + 5)
        	|| head.getY() + SnakeGameModel.SNAKE_SIZE_Y > (SnakeGameModel.SCREEN_HEIGHT + 5))
        	|| head.getX() < -5 || head.getY() < -5)
        {
        	return true;
        }

        //game is over when head touches one of the body parts
        for(int i = 1; i < getSnakeBody().size(); i++)
        {
        	if(head.getX() == getSnakeBody().get(i).getX() && head.getY() == getSnakeBody().get(i).getY())
        	{
        		return true;
        	}
        }

        return false;
	}

	public void checkGameLogic()
	{
		Rectangle head = new Rectangle(getSnakeBody().getFirst().getX(), getSnakeBody().getFirst().getY(),
				SnakeGameModel.SNAKE_SIZE_X, SnakeGameModel.SNAKE_SIZE_Y);

    	Rectangle fruit = new Rectangle(getFruitX(), getFruitY(), SnakeGameModel.SNAKE_SIZE_X, SnakeGameModel.SNAKE_SIZE_Y);

    	//randomize the location of the fruit once the head of the snake touches it
    	if(head.intersects(fruit))
    	{
    	    setFruitX(new Random().nextInt(SnakeGameModel.SCREEN_WIDTH - SnakeGameModel.SNAKE_SIZE_X));
    	    setFruitY(new Random().nextInt(SnakeGameModel.SCREEN_HEIGHT - SnakeGameModel.SNAKE_SIZE_Y));

    	    setFruitFlag(true);
    	}
	}

	//Snake always starts with 3 bodyparts,
	//there are 4 possible directions the snake can face before the game starts
	//depending on the starting positions of the second and third bodyparts
	//in this case the snake faces the right side of the screen
	public SnakeGameModel()
	{
		dir = Direction.None;
		fruitFlag = false;

		startPosX = SCREEN_WIDTH / 2 - SNAKE_SIZE_X / 2;
		startPosY = SCREEN_HEIGHT / 2 - SNAKE_SIZE_Y / 2;

		fruitX = new Random().nextInt(SCREEN_WIDTH - SNAKE_SIZE_X);
	    fruitY = new Random().nextInt(SCREEN_HEIGHT - SNAKE_SIZE_Y);

		snakeBody = new LinkedList<>();

		snakeBody.add(new BodyPos(startPosX, startPosY));
		snakeBody.add(new BodyPos((startPosX - SNAKE_SIZE_X - 1), startPosY));
		snakeBody.add(new BodyPos((startPosX - (2 * SNAKE_SIZE_X) - 2), startPosY));
	}

	//moves the snake 25 units in the current direction, checks gamelogic and notifies observing classes
	public void step()
	{
		move();
		checkGameLogic();
		notifyObservers();
	}
}